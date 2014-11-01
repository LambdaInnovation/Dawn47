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
package cn.dawn47.core.register;

import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import cn.dawn47.Dawn47;
import cn.dawn47.core.item.DWAmmo;
import cn.dawn47.equipment.item.ItemSuperDrink;
import cn.dawn47.misc.item.ItemPosterPlacer;
import cn.dawn47.mob.entity.EntityDemonSeed;
import cn.dawn47.mob.entity.EntityDrone;
import cn.dawn47.mob.entity.EntityRottenCreeper;
import cn.dawn47.mob.entity.EntityScoutRobot;
import cn.dawn47.mob.entity.EntitySoldier;
import cn.dawn47.weapon.item.DWGeneralWeapon;
import cn.dawn47.weapon.item.WeaponAR;
import cn.dawn47.weapon.item.WeaponHandgun;
import cn.dawn47.weapon.item.WeaponLaserRifle;
import cn.dawn47.weapon.item.WeaponRaditLauncher;
import cn.dawn47.weapon.item.WeaponSAR;
import cn.dawn47.weapon.item.WeaponShotgun;
import cn.liutils.api.item.LIMobSpawner;
import cn.liutils.api.util.RegUtils;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author WeAthFolD
 *
 */
public class DWItems {
	
	//----------Ammo and Weapons--------------
	public static DWGeneralWeapon 
		handgun,
		assaultRifle,
		laserRifle,
		raditLauncher,
		shotgun,
		sniperRifle,
		sar,
		uzi;
	
	public static Item 
		ammoHandgun,
		ammoAR,
		ammoLaser,
		ammoRadit,
		ammoShotgun,
		posters[],
		logo;
		//sn_ammo,
		//ammoUzi;
	//------------------------------
	
	//---------Equipments---------------
	
	public static Item superdrink;
	
	//-------------------------------
	
	//---------Mobs-------------------
	public static Item
		spawnerScoutRobot,
		spawnerRottenCreeper,
		spawnerDrone,
		spawnerDemonSeed,
		spawnerSoldier;
	
	//------------------------------
	
	/**
	 * 实际加载，在主mod中被调用
	 * @param conf
	 */
	public static void init(Configuration conf) {
		
		ammoHandgun = new DWAmmo().setStackAndDamage(1, 9).setIAndU("ammo_handgun");
		ammoAR = new DWAmmo().setStackAndDamage(1, 25).setIAndU("ammo_ar");
		ammoLaser = new DWAmmo().setStackAndDamage(1, 40).setIAndU("ammo_laser");
		ammoRadit = new DWAmmo().setStackAndDamage(1, 20).setIAndU("ammo_radit");
		ammoShotgun = new DWAmmo().setStackAndDamage(64, 1).setIAndU("ammo_shotgun");
		//sn_ammo = new DWAmmo().setStackAndDamage(64, 1).setIAndU("sn_ammo");
		//ammoUzi = new DWAmmo().setStackAndDamage(1, 20).setIAndU("uzi_ammo");
		
		handgun = new WeaponHandgun();
		sar = new WeaponSAR();
		raditLauncher = new WeaponRaditLauncher();
		laserRifle = new WeaponLaserRifle();
		assaultRifle = new WeaponAR();
		shotgun = new WeaponShotgun();
		
		superdrink = new ItemSuperDrink();
		
		posters = RegUtils.reg(ItemPosterPlacer.class, 5, "dw_poster");
		spawnerDrone = new LIMobSpawner(EntityDrone.class).setCreativeTab(Dawn47.cct);
		
		spawnerScoutRobot = new LIMobSpawner(EntityScoutRobot.class)
			.setCreativeTab(Dawn47.cct)
			.setUnlocalizedName("dw_scout_robot")
			.setTextureName("dawn47:egg4");
		spawnerRottenCreeper = new LIMobSpawner(EntityRottenCreeper.class)
			.setCreativeTab(Dawn47.cct)
			.setUnlocalizedName("dw_rotten_creeper")
			.setTextureName("dawn47:egg0");;
		spawnerDrone = new LIMobSpawner(EntityDrone.class)
			.setCreativeTab(Dawn47.cct)
			.setUnlocalizedName("dw_drone")
			.setTextureName("dawn47:egg1");
		spawnerDemonSeed = new LIMobSpawner(EntityDemonSeed.class)
			.setCreativeTab(Dawn47.cct)
			.setUnlocalizedName("dw_demonseed")
			.setTextureName("dawn47:egg2");
		spawnerDemonSeed = new LIMobSpawner(EntitySoldier.class)
			.setCreativeTab(Dawn47.cct)
			.setUnlocalizedName("dw_soldier")
			.setTextureName("dawn47:egg0");
		
		logo = new Item().setTextureName("dawn47:logo");
		
		GameRegistry.registerItem(ammoHandgun, "hg_ammo");
		GameRegistry.registerItem(ammoAR, "ar_ammo");
		GameRegistry.registerItem(ammoLaser, "laser_ammo");
		GameRegistry.registerItem(ammoRadit, "radiation_ammo");
		GameRegistry.registerItem(ammoShotgun, "sg_ammo");
		//GameRegistry.registerItem(ammoSAR, "ak_ammo");
		//GameRegistry.registerItem(ammoUzi, "uzi_ammo");
		
		GameRegistry.registerItem(logo, "dw_logo");
		GameRegistry.registerItem(handgun, "dw_handgun");
		GameRegistry.registerItem(sar, "dw_superassaultrifleiknowthisisalongname");
		GameRegistry.registerItem(raditLauncher, "dw_niconiconi");
		GameRegistry.registerItem(laserRifle, "dw_laser");
		GameRegistry.registerItem(assaultRifle, "dw_assault_rifle");
		GameRegistry.registerItem(shotgun, "dw_shotgun");
		GameRegistry.registerItem(spawnerScoutRobot, "dw_spawner_robot");
		GameRegistry.registerItem(spawnerRottenCreeper, "dw_rotten_creeper");
		GameRegistry.registerItem(spawnerDrone, "dw_drone");
		GameRegistry.registerItem(spawnerDemonSeed, "dw_dseed");
		
		/*
		GameRegistry.registerItem(ak47, "dw_ak47");
		GameRegistry.registerItem(uzi, "dw_uzi");
		*/
		GameRegistry.registerItem(superdrink, "dw_superdrink");
	}
	
	public static void addRecipes() {
	}

}
