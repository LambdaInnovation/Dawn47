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
import cn.dawn47.core.register.DWItems;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_Handgun extends DWGeneralWeapon {

	/**
	 * @param par1
	 * @param par2ammoID
	 */
	public Weapon_Handgun(int par1) {
		super(par1, DWItems.handgun_ammo.itemID);
		setIAndU("handgun");
		setMaxDamage(9);
		setLiftProps(3F, 0.35F);
		this.reloadTime = 30;
		this.isAutomatic = false;
	}
	
    /**
     * Returns the damage against a given entity.
     **/
	@Override
    public int getDamageVsEntity()
    {
        return 2;
    }
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getOffset(boolean)
	 */
	@Override
	public int getOffset(boolean left) {
		return 4;
	}
	
	@Override
	public int getWeaponDamage(boolean left) {
		return 4;
	}
	
	/**
	 * Get the shoot time corresponding to the mode.
	 * 
	 * @param mode
	 * @return shoot time
	 */
	@Override
	public int getShootTime(boolean left) {
		return 4;
	}
	
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	@Override
	public String getSoundShoot(boolean left) {
		return "dawn47:weapons.glock.glock_fire" ;
	}
	
	/**
	 * Get the reload sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	@Override
	public String getSoundReload() {
		return "dawn47:weapons.glock.glock_magout";
	}
	
	@Override
	public String getSoundReloadFinish() {
		return "dawn47:weapons.glock.glock_magin";
	}

}
