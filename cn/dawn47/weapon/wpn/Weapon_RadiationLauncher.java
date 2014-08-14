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
import net.minecraft.world.World;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.weapon.EntityRadiationBall;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_RadiationLauncher extends DWGeneralWeapon {

	/**
	 * @param par1
	 * @param par2ammoID
	 */
	public Weapon_RadiationLauncher(int par1) {
		super(par1, DWItems.radiation_ammo.itemID);
		setIAndU("radiation_launcher");
		setMaxDamage(21);
		this.reloadTime = 20;
	}
	
    /**
     * Returns the damage against a given entity.
     */
	@Override
    public int getDamageVsEntity()
    {
        return 7;
    }
	
	@Override
	protected Entity getBulletEntity(ItemStack is, World world,
			EntityPlayer player, boolean left) {
		return world.isRemote ? null : new EntityRadiationBall(world, player);
	}
	
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public String getSoundShoot(boolean left) {
		return "dawn47:weapons.rad.launch";
	}
	
	/**
	 * Get the shoot time corresponding to the mode.
	 * 
	 * @param mode
	 * @return shoot time
	 */
	public int getShootTime(boolean left) {
		return 20;
	}

}
