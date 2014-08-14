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
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionReload;
import cn.weaponmod.api.action.ActionUplift;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_Handgun extends DWGeneralWeapon {

	/**
	 * @param par1
	 * @param par2ammoID
	 */
	public Weapon_Handgun() {
		super(DWItems.handgun_ammo);
		setIAndU("handgun");
		setMaxDamage(9);
	}
	
    /**
     * Returns the damage against a given entity.
     */
	@Override
    public float getButtDamage()
    {
        return 4F;
    }
	
	public Action getActionUplift() {
		return new ActionUplift(3F, .4F, .3F, 15F);
	}
	
	public Action getActionAutomaticShoot() {
		return new ActionAutomaticShoot(300, 3, 4, 4, "weapons.glock.glock_fire");
	}
	
	public Action getActionReload() {
		return new ActionReload(30, "weapons.glock.glock_magout", "weapons.glock.glock_magin");
	}
}
