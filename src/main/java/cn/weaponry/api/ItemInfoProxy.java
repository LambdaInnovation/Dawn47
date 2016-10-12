/**
 * Copyright (c) Lambda Innovation, 2013-2015
 * 本作品版权由Lambda Innovation所有。
 * http://www.li-dev.cn/
 *
 * This project is open-source, and it is distributed under  
 * the terms of GNU General Public License. You can modify
 * and distribute freely as long as you follow the license.
 * 本项目是一个开源项目，且遵循GNU通用公共授权协议。
 * 在遵照该协议的情况下，您可以自由传播和修改。
 * http://www.gnu.org/licenses/gpl.html
 */
package cn.weaponry.api;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.world.WorldEvent;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEventHandler;
import cn.annoreg.mc.RegEventHandler.Bus;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * The class handling ItemInfo loading.
 * @author WeAthFolD
 */
@Registrant
public class ItemInfoProxy {
	
	private static Proxy proxy;

	private interface Proxy {
		ItemInfo getInfo(EntityPlayer player);
		
		void tick(boolean isRemote);
		
		void clear();
	}
	
	private static class SimpleProxy implements Proxy {
		
		ItemInfo client, server;
		
		@Override
		public ItemInfo getInfo(EntityPlayer player) {
			ItemInfo playerInfo = player.worldObj.isRemote ? client : server;
			
			if(playerInfo != null) {
				playerInfo.checkStack();
				
				if(playerInfo.disposed || playerInfo.player != player) {
					playerInfo.onDisposed();
					playerInfo = null;
				}
			}
			
			if(playerInfo == null) {
				ItemStack stack = player.getCurrentEquippedItem();
				
				if(stack != null && stack.getItem() instanceof IItemInfoProvider) {
					//Type safe is guaranteed.
					playerInfo = new ItemInfo(player);
				}
			}
			
			//Update instance
			if(player.worldObj.isRemote) {
				client = playerInfo;
			} else {
				server = playerInfo;
			}
			
//			if(playerInfo != null)
//				playerInfo.player = player;
			return playerInfo;
		}

		@Override
		public void tick(boolean isRemote) {
			ItemInfo targ = isRemote ? client : server;
			if(targ != null)
				targ.tick();
		}

		@Override
		public void clear() {
			client = server = null;
		}
		
	}
	
	private static class ServerProxy implements Proxy {
		
		Map<EntityPlayer, SimpleProxy> proxies = new WeakHashMap();

		@Override
		public ItemInfo getInfo(EntityPlayer player) {
			SimpleProxy proxy = proxies.get(player);
			if(proxy == null) {
				proxy = new SimpleProxy();
				proxies.put(player, proxy);
			}
			return proxy.getInfo(player);
		}

		@Override
		public void tick(boolean isRemote) {
			for(SimpleProxy sp : proxies.values()) {
				sp.tick(isRemote);
			}
		}

		@Override
		public void clear() {
			proxies.clear();
		}
		
	}
	
	/**
	 * Get the <code>ItemInfo</code> instance for current player. You will get different instance at client and server.
	 * It is guaranteed the info corresponds to the current holding itemStack.
	 * @param player
	 * @return The ItemInfo if current stack is of item type IItemInfoProvider, or null
	 */
	public static ItemInfo getInfo(EntityPlayer player) {
		return getProxy().getInfo(player);
	}
	
	private static Proxy getProxy() {
		if(proxy == null) {
			if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
				proxy = new SimpleProxy();
			} else {
				proxy = new ServerProxy();
			}
		}
		return proxy;
	}
	
	@RegEventHandler(Bus.FML)
	public static class Ticker {
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void onClientTick(ClientTickEvent event) {
			if(event.phase == Phase.END)
				return;
			if(Minecraft.getMinecraft().thePlayer == null)
				return;
			
			getProxy().getInfo(Minecraft.getMinecraft().thePlayer);
			getProxy().tick(true);
		}
		
		@SubscribeEvent
		public void onServerTick(ServerTickEvent event) {
			if(event.phase == Phase.END)
				return;
			for(Object p : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				EntityPlayer player = (EntityPlayer) p;
				getProxy().getInfo(player);
			}
			getProxy().tick(false);
		}
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void onRenderTick(RenderTickEvent event) {
			if(Minecraft.getMinecraft().thePlayer == null || event.phase == Phase.END)
				return;
			
			ItemInfo info = getProxy().getInfo(Minecraft.getMinecraft().thePlayer);
			if(info != null) {
				info.renderTick();
			}
		}
		
		// Clear all the previously exist info instances to prevent collision
		@SubscribeEvent
		public void onWorldLoad(WorldEvent.Load event) {
			getProxy().clear();
		}
		
	}
	
}
