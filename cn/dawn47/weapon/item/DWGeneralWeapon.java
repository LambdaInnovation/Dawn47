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
package cn.dawn47.weapon.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.dawn47.Dawn47;
import cn.dawn47.weapon.action.ActionButtAttack;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.action.ActionJam;
import cn.weaponmod.api.information.InfWeapon;
import cn.weaponmod.api.weapon.WeaponGeneric;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 * 
 */
public abstract class DWGeneralWeapon extends WeaponGeneric implements IDWAmmoInfProvider {


	/**
	 * @param par2ammoID
	 */
	public DWGeneralWeapon(Item ammo) {
		super(ammo);
		setCreativeTab(Dawn47.cct);
		this.actionJam = new ActionJam(20, "dawn47:weapons.weapon_fire_empty");
	}
	
	public DWGeneralWeapon setIAndU(String name) {
		setTextureName("dawn47:" + name);
		setUnlocalizedName(name);
		return this;
	}
	
	protected Action actionButtAttack = new ActionButtAttack(getSoundButtHit(), getButtDamage());
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, int keyid) {
		if(keyid == 1) {
			InfWeapon inf = this.loadInformation(player);
			if(!inf.isActionPresent(actionShoot.name))
				inf.executeAction(actionButtAttack);
		} else
			super.onItemClick(world, player, stack, keyid);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		this.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * @param mode
	 * @return sound path
	 */
	public String getSoundButtHit() {
		return "dawn47:weapons.melee_swing";
	}
	
	/**
	 * 获取枪托攻击的伤害。
	 */
	public abstract float getButtDamage();
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}
	
	@Override
	public String getAmmoForHud(EntityPlayer player, ItemStack stack) {
		return getAmmo(stack) + "|" + WeaponHelper.getAmmoCapacity(ammoItem, player.inventory);
	}
	
	public int getMuzzleConsistentTick() {
		return 3;
	}

}
