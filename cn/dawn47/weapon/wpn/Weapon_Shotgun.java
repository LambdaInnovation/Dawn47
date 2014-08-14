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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cn.dawn47.core.register.DWItems;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionBuckshot;
import cn.weaponmod.api.action.ActionMultipleReload;
import cn.weaponmod.api.action.ActionReload;
import cn.weaponmod.api.action.ActionUplift;
import cn.weaponmod.core.proxy.WMClientProxy;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_Shotgun extends DWGeneralWeapon {

	/**
	 * @param par1
	 * @param par2ammoID
	 */
	public Weapon_Shotgun() {
		super(DWItems.sg_ammo);
		setMaxDamage(7);
		setIAndU("shotgun");
	}
	
    /**
     * Returns the damage against a given entity.
     **/
	@Override
    public float getButtDamage()
    {
        return 6F;
    }
	
	public Action getActionUplift() {
		return new ActionUplift(6F, .8F, .5F, 36F);
	}
	
	public Action getActionShoot() {
		return new ActionBuckshot(5, 3, "weapons.shotgun.fire");
	}
	
	public Action getActionReload() {
		return new ActionMultipleReload(13, 300).setSound("weapons.shotgun.insert").setSoundFinish("weapons.shotgun.pump_seq");
	}
}
