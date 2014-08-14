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
package cn.dawn47.weapon.dual;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.dawn47.weapon.wpn.DWGeneralWeapon;
import cn.liutils.api.entity.EntityBullet;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.events.ItemHelper;
import cn.weaponmod.proxy.WMClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class DWGeneralDualWield extends DWGeneralWeapon {

	public boolean isAutomatic = false;
	public int capacity = 5;
	
	/**
	 * @param par1
	 * @param par2ammoID
	 */
	public DWGeneralDualWield(int par1, int par2ammoID) {
		super(par1, par2ammoID);
	}
	
	public void setCapacityPerWeapon(int cap) {
		setMaxDamage(2 * cap);
		capacity = cap;
	}
	
	public boolean doesShoot(InformationBullet inf, EntityPlayer player,
			ItemStack itemStack, boolean side) {
		boolean canUse;
		canUse = canShoot(player, itemStack, side);
		return getShootTime(side) != 0 && canUse && inf.getDeltaTick(side) >= getShootTime(side) && !inf.isReloading;
	}
	
	public boolean canShoot(EntityPlayer player, ItemStack is, boolean side) {
		return (getMaxDamageForSide(is, side) - getDamageForSide(is, side) > 0)
				|| player.capabilities.isCreativeMode;
	}
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, boolean left) {
		InformationBullet information = (InformationBullet) loadInformation(stack, player);
		Boolean canUse = canShoot(player, stack, left);
		if (canUse) {
			if(this.doesShoot(information, player, stack, left)) {
				this.onBulletWpnShoot(stack, world, player, information, left);
			}
			information.isReloading = false;
			if(isAutomatic) {
				ItemHelper.setItemInUse(player, stack, this.getMaxItemUseDuration(stack), left); 
			}
		} else {
			onSetReload(stack, player);
		}

		return;
	}
	
	public void onBulletWpnReload(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information) {

		EntityPlayer player = par3Entity;

		int dmg = par1ItemStack.getItemDamage();
		if (dmg <= 0) {
			information.setLastTick(false);
			information.isReloading = false;
			return;
		}

		//以left/2优先填充左边，然后填右边
		int left = par1ItemStack.getItemDamage() - WeaponHelper.consumeAmmo(player, this, par1ItemStack.getItemDamage());
		int a = left / 2;
		left -= a;
		int dmg1 = getDamageForSide(par1ItemStack, true);
		dmg1 -= a;
		if(dmg1 < 0) 
			left -= dmg1; //give the ammo back
		setItemDamage(par1ItemStack, true, dmg1);
		setItemDamage(par1ItemStack, false, getDamageForSide(par1ItemStack, false) - left);
		
		par3Entity.playSound(this.getSoundReloadFinish(), 0.5F, 1.0F);

		information.isReloading = false;
		information.setLastTick(false);

		return;
	}
	
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information, boolean left) {
		
		EntityBullet b = new EntityBullet(par2World, player, this.getWeaponDamage(left));
		if(left) b.setRenderFromLeft();
		par2World.spawnEntityInWorld(b);
		if(!player.capabilities.isCreativeMode)
			this.damageItem(par1ItemStack, left, 1);
		par2World.playSoundAtEntity(player,this.getSoundShoot(left), 0.5F, 1.0F);
		WMClientProxy.cth.setUplift(this.upLiftRadius);
		information.setMuzzleTick(left);
		information.setLastTick_Shoot(left);
		
	}
	
	public boolean doesAbortAnim(ItemStack itemStack, EntityLiving player) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(StatCollector.translateToLocal("ammocap.left.name")
				+ ": "
				+ (getMaxDamageForSide(par1ItemStack, true) - getDamageForSide(par1ItemStack, true)) + "/"
				+ (getMaxDamageForSide(par1ItemStack, false)));
		par3List.add(StatCollector.translateToLocal("ammocap.right.name")
				+ ": "
				+ (getMaxDamageForSide(par1ItemStack, false) - getDamageForSide(par1ItemStack, false)) + "/"
				+ (getMaxDamageForSide(par1ItemStack, false)));
	}
	
	public int getDamageForSide(ItemStack is, boolean left) {
		String s = left ? "ammo0" : "ammo1";
		return loadNBT(is).getInteger(s);
	}
	
	public void damageItem(ItemStack is, boolean left, int cap) {
		String s = left ? "ammo0" : "ammo1";
		NBTTagCompound nbt = loadNBT(is);
		int origin = nbt.getInteger(s) + cap;
		if(origin < 0) origin  = 0;
		setItemDamage(is, left, origin);
	}
	
	
    /**
     * Return the itemDamage represented by this ItemStack. Defaults to the itemDamage field on ItemStack, but can be overridden here for other sources such as NBT.
     *
     * @param stack The itemstack that is damaged
     * @return the damage value
     */
    public int getItemDamageFromStack(ItemStack stack)
    {
    	NBTTagCompound nbt = loadNBT(stack);
    	return nbt.getInteger("ammo0") + nbt.getInteger("ammo1");
    }
    
    /**
     * Return the itemDamage display value represented by this itemstack.
     * @param stack the stack
     * @return the damage value
     */
    public int getItemDamageFromStackForDisplay(ItemStack stack)
    {	
    	return 0;
    }
    
    /**
     * Return if this itemstack is damaged. Note only called if {@link #isDamageable()} is true.
     * @param stack the stack
     * @return if the stack is damaged
     */
    public boolean isItemStackDamaged(ItemStack stack)
    {
        return false;
    }
	
	public void setItemDamage(ItemStack is, boolean left, int dmg) {
		loadNBT(is).setInteger(left ? "ammo0" : "ammo1", dmg < 0 ? 0 : dmg);
	}
	
	public int getMaxDamageForSide(ItemStack is, boolean left) {
		return capacity;
	}

	@Override
	public final int getDamageVsEntity() {
		return 0;
	}
	
	@Override
	public String getAmmo(EntityPlayer player, ItemStack stack) {
		String str = String.valueOf(getMaxDamageForSide(stack, true) - getDamageForSide(stack, true)) + "|" + (getMaxDamageForSide(stack, false) - getDamageForSide(stack, false));
		return str;
	}

}
