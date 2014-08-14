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
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import cn.dawn47.core.item.DWAmmo;
import cn.dawn47.core.item.DWMobSpawner;
import cn.dawn47.equipment.entities.EntityMedkit;
import cn.dawn47.equipment.items.ItemEntityPlacer;
import cn.dawn47.equipment.items.ItemSuperDrink;
import cn.dawn47.mob.entity.EntityDroneBase;
import cn.dawn47.mob.entity.EntityRottenCreeper;
import cn.dawn47.weapon.wpn.DWGeneralWeapon;
import cn.dawn47.weapon.wpn.Weapon_AK;
import cn.dawn47.weapon.wpn.Weapon_AssaultRifle;
import cn.dawn47.weapon.wpn.Weapon_Handgun;
import cn.dawn47.weapon.wpn.Weapon_LaserRifle;
import cn.dawn47.weapon.wpn.Weapon_RadiationLauncher;
import cn.dawn47.weapon.wpn.Weapon_Shotgun;
import cn.dawn47.weapon.wpn.Weapon_Uzi;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * @author WeAthFolD
 *
 */
public class DWItems {
	
	//----------弹药和武器--------------

	public static DWGeneralWeapon handgun, assault_rifle, laser_rifle, radiation_launcher, shotgun, sniper_rifle, ak47, uzi;
	
	//public static DWGeneralDualWield dual_handgun, dual_uzi;
	
	public static Item handgun_ammo, ar_ammo, laser_ammo, radiation_ammo, sg_ammo, sn_ammo, ak_ammo, uzi_ammo;
	
	//------------------------------
	
	//---------装备部分---------------
	
	public static Item medkit, superdrink;
	
	//-------------------------------
	
	//---------生物-------------------
	
	public static Item spw_rotten_creeper, spw_drone;
	
	//------------------------------
	
	/**
	 * 实际加载，在主mod中被调用
	 * @param conf
	 */
	public static void init(Configuration conf) {
		
		handgun_ammo = new DWAmmo().setStackAndDamage(1, 9).setIAndU("handgun_ammo");
		ar_ammo = new DWAmmo().setStackAndDamage(1, 26).setIAndU("ar_ammo");
		laser_ammo = new DWAmmo().setStackAndDamage(1, 40).setIAndU("laser_ammo");
		radiation_ammo = new DWAmmo().setStackAndDamage(1, 21).setIAndU("radiation_ammo");
		sg_ammo = new DWAmmo().setStackAndDamage(64, 1).setIAndU("shotgun_ammo");
		sn_ammo = new DWAmmo().setStackAndDamage(64, 1).setIAndU("sn_ammo");
		ak_ammo = new DWAmmo().setStackAndDamage(1, 31).setIAndU("ak_ammo");
		uzi_ammo = new DWAmmo().setStackAndDamage(1, 21).setIAndU("uzi_ammo");
		
		handgun = new Weapon_Handgun();
		assault_rifle = new Weapon_AssaultRifle();
		laser_rifle = new Weapon_LaserRifle();
		radiation_launcher = new Weapon_RadiationLauncher();
		shotgun = new Weapon_Shotgun();
		ak47 = new Weapon_AK();
		uzi = new Weapon_Uzi();
		
		medkit = new ItemEntityPlacer(EntityMedkit.class).setIAndU("medkit");
		superdrink = new ItemSuperDrink();
		
		spw_rotten_creeper = new DWMobSpawner(EntityRottenCreeper.class, "spw_rotten_creeper");
		spw_drone = new DWMobSpawner(EntityDroneBase.class, "spw_drone_test");
		
		addLanguages();
		
		GameRegistry.registerItem(handgun_ammo, "hg_ammo");
		GameRegistry.registerItem(ar_ammo, "ar_ammo");
		GameRegistry.registerItem(laser_ammo, "laser_ammo");
		GameRegistry.registerItem(radiation_ammo, "radiation_ammo");
		GameRegistry.registerItem(sg_ammo, "sg_ammo");
		GameRegistry.registerItem(sn_ammo, "sn_ammo");
		GameRegistry.registerItem(ak_ammo, "ak_ammo");
		GameRegistry.registerItem(uzi_ammo, "uzi_ammo");
		GameRegistry.registerItem(handgun, "dw_handgun");
		GameRegistry.registerItem(assault_rifle, "assault_rifle");
		GameRegistry.registerItem(laser_rifle, "laser_rifle");
		GameRegistry.registerItem(radiation_launcher, "radiation_launcher");
		GameRegistry.registerItem(shotgun, "dw_shotgun");
		GameRegistry.registerItem(ak47, "dw_ak47");
		GameRegistry.registerItem(uzi, "dw_uzi");
		GameRegistry.registerItem(medkit, "dw_medkit");
		GameRegistry.registerItem(superdrink, "dw_superdrink");
		GameRegistry.registerItem(spw_rotten_creeper, "spw_rotten_creeper");
		GameRegistry.registerItem(spw_drone, "spw_drone");
	}
	
	private static void addLanguages() {
		LanguageRegistry instance = LanguageRegistry.instance();
		instance.addNameForObject(handgun_ammo, "zh_CN", "手枪弹药");
		instance.addNameForObject(ar_ammo, "zh_CN", "步枪弹药");
		instance.addNameForObject(laser_ammo, "zh_CN", "激光步枪弹药");
		instance.addNameForObject(radiation_ammo, "zh_CN", "辐射枪弹药");
		instance.addNameForObject(sg_ammo, "zh_CN", "霰弹枪弹药");
		instance.addNameForObject(uzi_ammo, "zh_CN", "乌兹冲锋枪弹药");
		instance.addNameForObject(ak_ammo, "zh_CN", "AK47弹药");
		instance.addNameForObject(sn_ammo, "zh_CN", "狙击步枪弹药");
		
		instance.addNameForObject(handgun, "zh_CN", "塔尔手枪");
		instance.addNameForObject(assault_rifle, "zh_CN", "G43步枪");
		instance.addNameForObject(laser_rifle, "zh_CN", "激光步枪");
		instance.addNameForObject(radiation_launcher, "zh_CN", "辐射枪");
		instance.addNameForObject(shotgun, "zh_CN", "粗水管");
		//instance.addNameForObject(sniper_rifle, "zh_CN", "审判者狙击枪");
		instance.addNameForObject(ak47, "zh_CN", "新AK-47");
		instance.addNameForObject(uzi, "zh_CN", "乌兹冲锋枪");
		
		instance.addNameForObject(medkit, "zh_CN", "医疗包");
		instance.addNameForObject(superdrink, "zh_CN", "超能饮料");
		
		instance.addNameForObject(spw_rotten_creeper, "zh_CN", "生成腐坏爬行者");
		instance.addNameForObject(spw_drone, "zh_CN", "生成小蜘蛛");
		
		instance.addStringLocalization("ammocap.name", "zh_CN", "弹药量");
		instance.addStringLocalization("ammocap.left.name", "zh_CN", "左弹药量");
		instance.addStringLocalization("ammocap.right.name", "zh_CN", "右弹药量");
		instance.addStringLocalization("frominv.name", "zh_CN", "直接消耗物品栏弹药");
		instance.addStringLocalization("classicalwpn.name", "zh_CN", "经典必捡");
		
		
		
		//----------------------------------------
		
		instance.addNameForObject(handgun_ammo, "en_US", "Handgun Ammo");
		instance.addNameForObject(ar_ammo, "en_US", "Assault Rifle Ammo");
		instance.addNameForObject(laser_ammo, "en_US", "Laser Rifle Ammo");
		instance.addNameForObject(radiation_ammo, "en_US", "Radiation Gun Ammo");
		instance.addNameForObject(sg_ammo, "en_US", "Shotgun Ammo");
		
		instance.addNameForObject(handgun, "en_US", "Tar Handgun");
		instance.addNameForObject(assault_rifle, "en_US", "G43 Assault Rifle");
		instance.addNameForObject(laser_rifle, "en_US", "Laser Rifle");
		instance.addNameForObject(radiation_launcher, "en_US", "Radiation Launcher");
		instance.addNameForObject(shotgun, "en_US", "Thick Pipe Shotgun");
		//instance.addNameForObject(sniper_rifle, "en_US", "Judger Sniper Rifle");
		instance.addNameForObject(ak47, "en_US", "New AK-47");
		
		instance.addNameForObject(medkit, "en_US", "Medkit");
		instance.addNameForObject(superdrink, "en_US", "Super Drink");
		
		instance.addStringLocalization("ammocap.name", "en_US", "Ammo Capacity");
		instance.addStringLocalization("ammocap.left.name", "en_US", "Left Ammo");
		instance.addStringLocalization("ammocap.right.name", "en_US", "Right Ammo");
		instance.addStringLocalization("frominv.name", "en_US", "Directly consumes ammo from inventory");
		instance.addStringLocalization("classicalwpn.name", "en_US", "Historical Classic");
	}
	
	public static void addRecipes() {
	}

}
