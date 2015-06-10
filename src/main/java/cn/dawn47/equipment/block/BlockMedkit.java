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
package cn.dawn47.equipment.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.network.RegNetworkCall;
import cn.annoreg.mc.s11n.StorageOption.Data;
import cn.annoreg.mc.s11n.StorageOption.Instance;
import cn.annoreg.mc.s11n.StorageOption.Target;
import cn.dawn47.Dawn47;
import cn.liutils.template.client.render.block.RenderEmptyBlock;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 */
@Registrant
public class BlockMedkit extends BlockContainer {
	
	static final String SYNC_ID = "dw_medkitCount";
	
	static int syncCooldown = 10;

	public BlockMedkit() {
		super(Material.cloth);
		setBlockName("dw_medkit");
		setBlockTextureName("dawn47:medkit");
		setCreativeTab(Dawn47.cct);
		setHardness(0.1f);
		this.setBlockBounds(.2f, 0, .2f, .8f, .3f, .8f);
		
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onDrawHighlight(DrawBlockHighlightEvent event) {
		Block b = event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
		if(this == b)
			event.setCanceled(true);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(player != null) {
			if(--syncCooldown == 0) {
				syncCooldown = 20;
				query(player);
			}
		}
	}
	
	@Override
	public int getRenderType() {
		return RenderEmptyBlock.id;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity target) {
		if(target instanceof EntityPlayer && !((EntityPlayer)target).capabilities.isCreativeMode) {
			world.setBlockToAir(x, y, z);
			EntityPlayer player = (EntityPlayer) target;
			setMedkitCount(player, getMedkitCount(player) + 1);
			
			if(world.isRemote)
				query(player);
		}
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
    	int l = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, l, 0x03);
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileMedkit();
	}
	
	public static int getMedkitCount(EntityPlayer player) {
		return player.getEntityData().getInteger(SYNC_ID);
	}
	
	public static void setMedkitCount(EntityPlayer player, int c) {
		player.getEntityData().setInteger(SYNC_ID, c);
		if(!player.worldObj.isRemote) {
			received(player, c);
		}
	}
	
	@RegNetworkCall(side = Side.SERVER)
	private static void query(@Instance EntityPlayer player) {
		received(player, getMedkitCount(player));
	}
	
	@RegNetworkCall(side = Side.CLIENT)
	private static void received(@Target EntityPlayer player, @Data Integer count) {
		setMedkitCount(player, count);
	}
	

}
