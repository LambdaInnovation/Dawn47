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

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cn.dawn47.core.register.DWItems;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.proxy.WMClientProxy;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_Shotgun extends DWGeneralWeapon {

	/**
	 * @param par1
	 * @param par2ammoID
	 */
	public Weapon_Shotgun(int par1) {
		super(par1, DWItems.sg_ammo.itemID);
		setMaxDamage(7);
		setIAndU("shotgun");
		setLiftProps(12.5F, .8F);
		this.reloadTime = 13;
	}
	
    /**
     * Returns the damage against a given entity.
     **/
	@Override
    public int getDamageVsEntity()
    {
        return 6;
    }
	
	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information, boolean left) {
		
		for(int i = 0; i < 6; i ++)
			WeaponHelper.Shoot(this.getWeaponDamage(left), player, par2World);
		if (!(player.capabilities.isCreativeMode))
			this.setWpnStackDamage(par1ItemStack, this.getWpnStackDamage(par1ItemStack) + 1);
		
		player.playSound(getSoundShoot(left), .5F, 1F);
		WMClientProxy.cth.setUplift(upLiftRadius);
		information.setLastTick_Shoot(left);
		information.setMuzzleTick(left);
	}
	
	@Override
	public void onBulletWpnReload(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information) {

		int dmg =this.getWpnStackDamage(par1ItemStack);
		if (dmg <= 0) {
			information.setLastTick(false);
			information.isReloading = false;
			return;
		}
		
		if (WeaponHelper.consumeAmmo(player, this, 1) == 0) {
			this.setWpnStackDamage(par1ItemStack, this.getWpnStackDamage(par1ItemStack) - 1);
			par2World.playSoundAtEntity(player, par1ItemStack.getItemDamage() == 0 ? getSoundReloadFinish() : getSoundReload(), 0.5F, 1.0F);
		} else
			information.isReloading = false;

		information.setLastTick(false);
		return;
	}
	
	@Override
	public float getRotationForReload(ItemStack itemStack) {
		InformationBullet inf = (InformationBullet) WMInformation.getInformation(itemStack, true);
		int dt = inf.getDeltaTick(true);
		float changeTime = reloadTime / 5F;
		float rotation = 1.0F;
		if(dt < changeTime) {
			rotation = MathHelper.sin(dt / changeTime * (float)Math.PI * 0.5F);
		} else if(dt > 6 * reloadTime - changeTime) {
			rotation = MathHelper.sin((reloadTime - dt) / changeTime * (float)Math.PI * 0.5F);
		}
		return rotation;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getOffset(boolean)
	 */
	@Override
	public int getOffset(boolean left) {
		return 15;
	}
	
	@Override
	public int getWeaponDamage(boolean left) {
		return 3;
	}
	
	/**
	 * Get the shoot time corresponding to the mode.
	 * 
	 * @param mode
	 * @return shoot time
	 */
	@Override
	public int getShootTime(boolean left) {
		return 16;
	}
	
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	@Override
	public String getSoundShoot(boolean left) {
		return "dawn47:weapons.shotgun.fire" ;
	}
	
	/**
	 * Get the reload sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	@Override
	public String getSoundReload() {
		return "dawn47:weapons.shotgun.insert";
	}
	
	@Override
	public String getSoundReloadFinish() {
		return "dawn47:weapons.shotgun.pump_seq";
	}
	
	@Override
	public boolean doesAbortReloadingWhenClick() {
		return true;
	}


}
