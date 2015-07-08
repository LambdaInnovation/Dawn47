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
import net.minecraft.util.ResourceLocation;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegInit;
import cn.annoreg.mc.RegItem;
import cn.dawn47.Dawn47;
import cn.dawn47.core.item.DWMobSpawner;
import cn.dawn47.equipment.item.ItemElectronicBalancer;
import cn.dawn47.equipment.item.ItemSuperDrink;
import cn.dawn47.misc.item.ItemPosterPlacer;
import cn.dawn47.mob.entity.EntityBattleSoldier;
import cn.dawn47.mob.entity.EntityDrone;
import cn.dawn47.mob.entity.EntityRottenCreeper;
import cn.dawn47.mob.entity.EntityWeaponSoldier;
import cn.dawn47.weapon.DawnWeapon;
import cn.dawn47.weapon.DawnWeaponLoader;
import cn.liutils.loading.item.ItemLoader;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author WeAthFolD
 *
 */
@Registrant
@RegInit
public class DWItems {
	
	public static ItemLoader itemLoader;
	public static DawnWeaponLoader weaponLoader;
	public static DawnWeaponLoader weaponLoader2;
	
	//----------Ammo and Weapons--------------
	
	public static Item[] posters;

	@RegItem
	public static Item logo = new Item().setTextureName("dawn47:logo");
	
	@RegItem
	public static ItemSuperDrink superdrink;
	
	@RegItem
	public static Item
		spawnerRottenCreeper = new DWMobSpawner(EntityRottenCreeper.class, "rotten_creeper", 1),
		spawnerDrone = new DWMobSpawner(EntityDrone.class, "drone", 0),
		spawnerSoldier = new DWMobSpawner(EntityBattleSoldier.class, "battle_soldier", 3),
		spawnerWSoldier = new DWMobSpawner(EntityWeaponSoldier.class, "weapon_soldier", 4);
	
	@RegItem
	public static Item
		solCrowbar = new Item().setTextureName("dawn47:crowbar").setFull3D(),
		solAxe = new Item().setTextureName("dawn47:axe").setFull3D();
	
	@RegItem
	public static Item electronicBalancer = new ItemElectronicBalancer();
	
	public static DawnWeapon 
		soldierAR,
		soldierHandgun;
	
	public static void init() {
		
		itemLoader = new ItemLoader();
		itemLoader.feed(new ResourceLocation("dawn47:items.json"));
		itemLoader.loadAll();
		Dawn47.log.info("Dawn47 itemLoader loaded " + itemLoader.getEnumeration().size() + " items.");
		
		weaponLoader = new DawnWeaponLoader();
		weaponLoader.feed(new ResourceLocation("dawn47:weapons.json"));
		weaponLoader.loadAll();
		
		weaponLoader2 = new DawnWeaponLoader();
		weaponLoader2.feed(new ResourceLocation("dawn47:soldier_weapons.json"));
		weaponLoader2.loadAll();
		
		Dawn47.log.info("Dawn47 weaponLoader loaded " + weaponLoader.getEnumeration().size() + " items.");
		Dawn47.log.info("Dawn47 weaponLoader2 loaded " + weaponLoader2.getEnumeration().size() + " items.");
		
		posters = new Item[5];
		for(int i = 0; i < 5; ++i) {
			posters[i] = new ItemPosterPlacer(i);
			GameRegistry.registerItem(posters[i], "dw_poster" + i);
		}
		
		soldierAR = weaponLoader2.getObject("soldier_ar");
		soldierHandgun = weaponLoader2.getObject("soldier_handgun");
	}
	
	public static void addRecipes() {
	}

}
