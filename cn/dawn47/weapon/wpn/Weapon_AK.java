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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import cn.dawn47.core.register.DWItems;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_AK extends DWGeneralWeapon {

	/**
	 * @param par1
	 * @param par2ammoID
	 */
	public Weapon_AK(int par1) {
		super(par1, DWItems.ak_ammo.itemID);
		this.setMaxDamage(31);
		this.setLiftProps(4, 0.2F);
		this.reloadTime = 30;
	}
	
    /**
     * Returns the damage against a given entity.
     **/
	@Override
    public int getDamageVsEntity()
    {
        return 4;
    }
    
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getOffset(boolean)
	 */
	@Override
	public int getOffset(boolean left) {
		return 3;
	}
	
	@Override
	public int getWeaponDamage(boolean left) {
		return 5;
	}
	
	/**
	 * Get the shoot time corresponding to the mode.
	 * 
	 * @param mode
	 * @return shoot time
	 */
	@Override
	public int getShootTime(boolean left) {
		return 3;
	}
	
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	@Override
	public String getSoundShoot(boolean left) {
		return "dawn47:weapons.ak.fire" ;
	}
	
	/**
	 * Get the reload sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	@Override
	public String getSoundReload() {
		return "dawn47:weapons.ak.magout";
	}
	
	@Override
	public String getSoundReloadFinish() {
		return "dawn47:weapons.ak.magin";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(EnumChatFormatting.RED + StatCollector.translateToLocal("classicalwpn.name"));
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}

}
