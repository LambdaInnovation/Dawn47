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

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.dawn47.DawnMod;
import cn.dawn47.core.util.DWGenericUtils;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.weapon.WeaponGeneralBullet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 * 
 */
public abstract class DWGeneralWeapon extends WeaponGeneralBullet implements IDWAmmoInfProvider {

	public String iconName = "none";

	/**
	 * @param par1
	 * @param par2ammoID
	 */
	public DWGeneralWeapon(int par1, int par2ammoID) {
		super(par1, par2ammoID);
		setCreativeTab(DawnMod.cct);
	}
	
	public DWGeneralWeapon setIAndU(String name) {
		iconName = name;
		setUnlocalizedName(name);
		return this;
	}
	
    /**
     * Returns the damage against a given entity.
     *
	@Override
    public int getDamageVsEntity(Entity par1Entity)
    {
        return 3;
    }
	*/
	
	public abstract int getDamageVsEntity();
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack,
			boolean left) {
		InformationBullet information = (InformationBullet) loadInformation(stack, player);
		if (left) {
			super.onItemClick(world, player, stack, left);
			information.setLastTick_Shoot(true);
		} else {
			//右键特殊：枪托攻击
			if(information.getDeltaTick(false) > 10) {
				player.playSound(this.getSoundHit(left), 0.5F, 1.0F);
				DWGenericUtils.doPlayerAttack(player, getDamageVsEntity());
				player.swingItem();
				information.setLastTick_Shoot(false);
			}
		}
		return;
	}
	
	public void superOnItemClick(World world, EntityPlayer player, ItemStack stack,
			boolean left) {
		super.onItemClick(world, player, stack, left);
	}
	
	@Override
	public boolean doesAbortAnim(ItemStack itemStack, EntityLivingBase player) {
		if(player instanceof EntityPlayer) {
			InformationBullet inf = (InformationBullet) loadInformation(itemStack, (EntityPlayer) player);
			if(inf != null) {
				return inf.getDeltaTick(true) < inf.getDeltaTick(false);
			} else return false;
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		this.itemIcon = ir.registerIcon("dawn47:" + iconName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.weaponmod.api.weapon.WeaponGeneralBullet#onUpdate(net.minecraft.item
	 * .ItemStack, net.minecraft.world.World, net.minecraft.entity.Entity, int,
	 * boolean)
	 */
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		this.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getDamage(boolean)
	 */
	@Override
	public int getWeaponDamage(boolean left) {
		return 10;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getOffset(boolean)
	 */
	@Override
	public int getOffset(boolean left) {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#doWeaponUplift()
	 */
	@Override
	public boolean doWeaponUplift() {
		return true;
	}
	
	
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public String getSoundShoot(boolean left) {
		return "";
	}
	
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public String getSoundHit(boolean left) {
		return "dawn47:weapons.melee_swing";
	}
	

	/**
	 * Get the gun jamming sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public String getSoundJam(boolean left) {
		return "dawn47:weapons.weapon_fire_empty";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}
	
	@Override
	public String getAmmo(EntityPlayer player, ItemStack stack) {
		if(this.getMaxDamage() == 0) {
			return String.valueOf(WeaponHelper.getAmmoCapacity(ammoID, player.inventory));
		} else {
			String str = String.valueOf(stack.getMaxDamage() - getWpnStackDamage(stack) - 1) + "|" + WeaponHelper.getAmmoCapacity(ammoID, player.inventory);
			return str;
		}
	}

}
