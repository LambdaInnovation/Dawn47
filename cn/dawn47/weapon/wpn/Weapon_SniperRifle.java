/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.dawn47.weapon.wpn;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.core.util.DWGenericUtils;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.weapon.IZoomable;
import cn.weaponmod.events.ItemHelper;
import cn.weaponmod.proxy.WMClientProxy;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_SniperRifle extends DWGeneralWeapon implements IZoomable {

	/**
	 * @param par1
	 * @param par2ammoID
	 */
	public Weapon_SniperRifle(int par1) {
		super(par1, DWItems.sn_ammo.itemID);
		setUnlocalizedName("sniper_rifle");
		this.setMaxDamage(0);
		this.setLiftProps(15, 0.5F);
	}
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack,
			boolean left) {
		InformationBullet information = (InformationBullet) loadInformation(stack, player);
		if (left) {
			if(this.doesShoot(information, player, stack, left)) {
				this.onBulletWpnShoot(stack, world, player, information, left);
			}
			information.isReloading = false;
			if(isAutomatic)
				ItemHelper.setItemInUse(player, stack, this.getMaxItemUseDuration(stack), left); 
		} else {
			setItemZooming(stack, !isItemZooming(stack, world, player));
			information.setLastTick(false);
		}
		return;
	}
	
	public boolean onSetReload(ItemStack itemStack, EntityPlayer player) {
		return false;
	}
	
	@Override
	public boolean canShoot(EntityPlayer player, ItemStack is, boolean side) {
		InformationBullet inf = (InformationBullet) loadInformation(is, player);
		
		return (inf != null ? inf.getDeltaTick(false) > 10 : true) && (WeaponHelper.hasAmmo(this, player) || player.capabilities.isCreativeMode);
	}
	
	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information, boolean left) {

		WeaponHelper.Shoot(getWeaponDamage(left), player, par2World);
		if (!(player.capabilities.isCreativeMode))
			WeaponHelper.consumeAmmo(player, this, 1);
		par2World.playSoundAtEntity(player,this.getSoundShoot(left), 0.5F, 1.0F);
		WMClientProxy.cth.setUplift();
		information.setLastTick_Shoot(left);
		information.setMuzzleTick(left);
	}
	
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	@Override
	public String getSoundShoot(boolean left) {
		return "dawn47:weapons.sniper.fire";
	}
	
	@Override
	public int getWeaponDamage(boolean mode) {
		return 30;
	}
	
	/**
	 * Get the shoot time corresponding to the mode.
	 * 
	 * @param mode
	 * @return shoot time
	 */
	@Override
	public int getShootTime(boolean left) {
		return 45;
	}

	@Override
	public boolean isItemZooming(ItemStack stack, World world,
			EntityPlayer player) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if(nbt != null)
			return nbt.getBoolean("isZooming");
		return false;
	}
	
	
	private void setItemZooming(ItemStack stack, boolean b) {
		if(stack.stackTagCompound == null) stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setBoolean("isZooming", b);
	}

	@Override
	public boolean doesSlowdown(ItemStack stack, World world,
			EntityPlayer player) {
		return true;
	}

	@Override
	public int getDamageVsEntity() {
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(StatCollector.translateToLocal("frominv.name"));
	}
	
	

}
