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
package cn.dawn47.mob.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEntity;
import cn.annoreg.mc.network.RegNetworkCall;
import cn.annoreg.mc.s11n.StorageOption.Data;
import cn.annoreg.mc.s11n.StorageOption.Instance;
import cn.annoreg.mc.s11n.StorageOption.Target;
import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.mob.client.render.RendererBattleSoldier;
import cn.liutils.template.entity.LIEntityMob;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
@Registrant
@RegEntity
@RegEntity.HasRender
public class EntityBattleSoldier extends LIEntityMob {
	
	@RegEntity.Render
	@SideOnly(Side.CLIENT)
	public static RendererBattleSoldier renderer;
	
	boolean isCharging = false;
	
	boolean synced = false;
	
	int weaponID = rand.nextInt(2);
	int reqTicker = 10;

	public EntityBattleSoldier(World world) {
		super(world);
		updateWeapon();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(worldObj.isRemote) {
			updateClient();
		} else {
			
		}
	}
	
	@SideOnly(Side.CLIENT)
	private void updateClient() {
		if(!synced && reqTicker++ == 10) {
			//TODO: Crash?
			req(Minecraft.getMinecraft().thePlayer);
			reqTicker = 0;
		}
	}
	
	private void updateServer() {
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("weapon", weaponID);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		weaponID = tag.getInteger("weapon");
	}
	
	@RegNetworkCall(side = Side.SERVER)
	private void req(@Instance EntityPlayer player) {
		get(player, weaponID);
	}
	
	@RegNetworkCall(side = Side.CLIENT)
	private void get(@Target EntityPlayer player, @Data Integer wid) {
		weaponID = wid;
		updateWeapon();
		synced = true;
	}
	
	private void updateWeapon() {
		this.setCurrentItemOrArmor(0, new ItemStack(weaponID == 0 ? DWItems.solCrowbar : DWItems.solAxe));
	}

	@Override
	protected double getMaxHealth2() {
		return 15;
	}

	@Override
	protected double getFollowRange() {
		return 10;
	}

	@Override
	protected double getMoveSpeed() {
		return 2.4;
	}

	@Override
	protected double getKnockBackResistance() {
		return 5;
	}

	@Override
	protected double getAttackDamage() {
		return 4;
	}

	ResourceLocation texture = DWResources.texture("entities/soldier");
	
	@Override
	public ResourceLocation getTexture() {
		return texture;
	}
	
}
