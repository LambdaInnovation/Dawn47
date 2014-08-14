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
public class Weapon_Uzi extends DWGeneralWeapon {

	public Weapon_Uzi() {
		super(DWItems.uzi_ammo);
		this.setMaxDamage(31);
		this.setUnlocalizedName("weapon_uzi");
	}
	
    /**
     * Returns the damage against a given entity.
     **/
	@Override
    public float getButtDamage()
    {
        return 4F;
    }
	
	public Action getActionUplift() {
		return new ActionUplift(2F, .4F, .3F, 14F);
	}
	
	public Action getActionAutomaticShoot() {
		return new ActionAutomaticShoot(300, 3, 5, 4, "weapons.uzi.fire");
	}
	
	public Action getActionReload() {
		return new ActionReload(30, "weapons.uzi.magout", "weapons.uzi.magin");
	}

}
