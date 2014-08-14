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
package cn.dawn47.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.equipment.client.model.ModelMedkit;
import cn.dawn47.equipment.client.model.ModelSuperDrink;
import cn.dawn47.equipment.entities.EntityMedkit;
import cn.dawn47.mob.client.render.RenderDrone;
import cn.dawn47.mob.client.render.RenderRottenCreeper;
import cn.dawn47.mob.entity.EntityDroneBase;
import cn.dawn47.mob.entity.EntityRottenCreeper;
import cn.dawn47.weapon.EntityLaser;
import cn.dawn47.weapon.EntityRadiationBall;
import cn.dawn47.weapon.client.entity.fx.EntitySpitFX;
import cn.dawn47.weapon.client.model.ModelAmmoSniperRifle;
import cn.dawn47.weapon.client.model.ModelUzi;
import cn.dawn47.weapon.client.model.ModelUziAmmo;
import cn.dawn47.weapon.client.render.RenderLaser;
import cn.liutils.api.client.model.ItemModelCustom;
import cn.liutils.api.client.render.RenderIcon;
import cn.liutils.api.client.render.RenderIcon_Animated;
import cn.liutils.api.client.render.RenderModel;
import cn.liutils.api.client.render.RenderModelItem;
import cn.weaponmod.api.client.render.RenderModelBulletWeapon;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author WeAthFolD
 *
 */
public class DWClientProxy extends DWCommonProxy {
	
	public static final String 
		SND_WEAPONS[] = { "melee_hit", "melee_swing", "weapon_fire_empty", "weapon_fire_empty_noclick", "weapon_get" },
			SND_HANDGUN[] = { "glock_fire", "glock_magin", "glock_magout", "glock_magplace" }, 
			SND_ARIFLE[] = {"ar_magin", "ar_magout", "ar_fire", "ar_switch"},
			SND_SHOTGUN[] = {"fire", "insert", "pump_seq"},
			SND_LASER[] = { "laser" },
			SND_RADIATION[] = {"launch"},
			SND_SNIPER_RIFLE[] = { "fire", "cork" },
			SND_ENTITIES[] = {"medshot"},
			SND_AK[] = { "fire", "magin", "magout"},
			SND_UZI[] = {"fire", "magin", "magout"};
	
	
	
	@Override
	public void preInit() {
		
		//-----------------SOUNDS------------------
				
		//------------------------------------------
		
	}
	
	@Override
	public void init() {
		
		//----------------ITEMS-----------
		//这几行信息量灰常大哟，小心修改TAT
		RenderModelItem handgun_render = new RenderModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_HANDGUN), DWItems.handgun, 
			DWClientProps.HANDGUN_TEXTURE_PATH[0], DWClientProps.GLOCK_MUZZLEFLASH)
			.setMuzzleflashOffset(-.05F, .0F, .0F).setInvOffset(4.0F, 2.438F).setStdRotation(0F, -90F, 0F).setOffset(0.0F, 0.25F, 0F)
			.setInvScale(1.18F).setInvRotation(-32.67F, -75.816F, 0F).setEquipOffset(.5F, 0F, 0F).setInventorySpin(false);
		RenderModelItem uzi_render = new RenderModelBulletWeapon(new ModelUzi(), DWItems.uzi, DWClientProps.UZI_PATH, DWClientProps.GLOCK_MUZZLEFLASH)
			.setMuzzleflashOffset(-.05F, .0F, .0F).setOffset(.0F, .12F, .0F).setInvScale(1.37F).setInvOffset(0.744F, 0.584F).setEquipOffset(0.75F, .10F, .0F)
			.setStdRotation(0F, 180F, 0F).setInventorySpin(false).setInvRotation(-39.6F, -54.87F, 4.044F); 
		RenderModelItem assault_rifle_render = new RenderModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_ASSAULT_RIFLE), DWItems.assault_rifle,
			DWClientProps.AR_TEXTURE_PATH[0], DWClientProps.AR_MUZZLEFLASH).setMuzzleflashOffset(.27F, .11F, .1F).setEquipOffset(0.74F, 0.235F, 0.0F)
			.setInventorySpin(false).setInvRotation(-33.876F, -57.218F, 0.142F).setInvOffset(0.806F, -0.808F).setInvScale(0.948F);
		RenderModelItem laser_rifle_render = new RenderModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_LASER_RIFLE), DWItems.laser_rifle, 
			DWClientProps.LASER_TEXTURE_PATH[0], DWClientProps.LASER_MUZZLEFLASH)
			.setMuzzleflashOffset(.38f, 0f, -0.005f).setOffset(0.0F, 0.2F, -0.65F).setEquipOffset(.64F, 0.2F, 0.0F)
			.setInvOffset(1.45F, -0.82F).setInvScale(0.86F).setInvRotation(-29.594F, -62.04F, -8.98F).setStdRotation(0F, 180F, 0F).setInventorySpin(false);
		RenderModelItem radiation_launcher_render = new RenderModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_RAD_LAUNCHER), DWItems.radiation_launcher, 
			DWClientProps.RADIATION_TEXTURE_PATH, DWClientProps.RADIATION_MUZZLEFLASH)
			.setInvOffset(-2.9F, -1.846F).setInvScale(0.568F).setInvRotation(-42.818F, -60.998F, -12.588F).setOffset(0.0F, 0.25F, 0.0F).setEquipOffset(0.898, 0.058, 0.334).setStdRotation(0F, 180F, 0F).setInventorySpin(false);
		RenderModelItem shotgun_render = new RenderModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_SHOTGUN), DWItems.shotgun, 
			DWClientProps.SHOTGUN_TEXTURE_PATH[0], DWClientProps.SG_MUZZLEFLASH)
			.setMuzzleflashOffset(.38F, .05F, .11F).setStdRotation(0F, -90F, 0F).setOffset(.894F, -.567F, 1.116F)
			.setEquipOffset(.60F, -.17F, -0.026F).setInventorySpin(false).setInvScale(-0.798F).setScale(-.391)
			.setEquipRotation(-2.329F, 1.54F, -9.20F).setInvOffset(2.27F, 3.95F).setInvRotation(498.078F, -46.724F, 26.35F);
		RenderModelItem superdrink_render = new RenderModelItem(new ModelSuperDrink(true), DWClientProps.SOFT_DRINK_TEXTURE_PATH)
			.setEquipOffset(.40F, 0.25F, -.35F).setInvOffset(0.0F, 1.5F).setOffset(0.0F, 0.0F, .0F)
			.setInvScale(1.5F).setInventorySpin(true);//.disableInvRotation();
		RenderModelItem medkit_render = new RenderModelItem(new ModelMedkit(true), DWClientProps.MEDKIT_TEXTURE_PATH)
			.setOffset(-.2F, 0.0F, 0.0F).setEquipOffset(.39F, 0.26F, -.2F).setStdRotation(0F, 0F, 90F)
			.setInvRotation(-89.496F, -44.768F, -0.104F).setInvScale(1.5F).setInvOffset(1.542F, -1.090F)
			.setInventorySpin(false);//.disableInvRotation()
		RenderModelItem sn_ammo_render =  new RenderModelItem(new ModelAmmoSniperRifle(true), DWClientProps.SN_BULLET_TEXTURE_PATH)
			.setOffset(-.2F, 0.0F, 0.0F).setEquipOffset(.39F, 0.26F, -.2F).setStdRotation(0F, 0F, 90F)
			.setInvScale(1.64F).setInvOffset(6.112F, 0.07F).setInventorySpin(false);//.disableInvRotation();
		RenderModelItem sar_render = new RenderModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_SUPER_AR), DWItems.ak47, DWClientProps.SUPER_AR_TEX_PATH, DWClientProps.AR_MUZZLEFLASH)
			.setMuzzleflashOffset(0.79F, 0.0F, 0.09F).setOffset(.0F, 0.2F, -0.5F).setEquipOffset(.8F, 0F, 0F)
			.setInvOffset(1.58F, 2.85F).setInvScale(0.836F).setStdRotation(0F, 180F, 0F).setInvRotation(-41.592F, -63.238F, -20.288F).setInventorySpin(false);
		//RenderModelItem ak_ammo_render = new RenderModelItem(new ModelAKAmmo(), DWClientProps.SUPER_AR_TEX_PATH)
		//	.setScale(1.698F).setEquipOffset(0.5F, 0F, -0.268F).setOffset(-0.06F, 0.4F, 0F).
		//	setInventorySpin(false).setInvScale(1.43F).setInvOffset(3.19F, 2.866F).setInvRotation(-52.824F, 162.97F, -10.478F);//.disableInvRotation();
		RenderModelItem uzi_ammo_render =  new RenderModelItem(new ModelUziAmmo(), DWClientProps.UZI_AMMO_PATH)
			.setScale(2.0F).setOffset(0.032F, 0.13F, 0.066F).setStdRotation(-28F, 0F, 0F)
			.setInvScale(1.512F).setInvOffset(0.474F, -1.24F).setInvRotation(-41.814F, -60.274F, 0F).setInventorySpin(false)
			.setEquipRotation(0.0F, 0.0F, 27.37F).setEquipOffset(0.524F, 0.24F, -0.33F);
		
		MinecraftForgeClient.registerItemRenderer(DWItems.handgun,  handgun_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.assault_rifle, assault_rifle_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.laser_rifle, laser_rifle_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.radiation_launcher, radiation_launcher_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.shotgun, shotgun_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.ak47, sar_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.uzi, uzi_render);
		
		MinecraftForgeClient.registerItemRenderer(DWItems.superdrink, superdrink_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.medkit, medkit_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.sn_ammo, sn_ammo_render);
		//MinecraftForgeClient.registerItemRenderer(DWItems.ak_ammo, ak_ammo_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.uzi_ammo, uzi_ammo_render);
		
		//------------------------------------------
		
		//-----------------ENTITIES---------------
		
		RenderingRegistry.registerEntityRenderingHandler(EntityMedkit.class, new RenderModel(new ModelMedkit(false), DWClientProps.MEDKIT_TEXTURE_PATH, 1.0F).setOffset(0.0F, 1.0F, 0.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityLaser.class, new RenderLaser());
		RenderingRegistry.registerEntityRenderingHandler(EntityRadiationBall.class, new RenderIcon_Animated(DWClientProps.EFFECT_SMOKE).setHasLight(false).setSize(1.5F).setColorRGB(0.1F, 0.8F, 0.1F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpitFX.class, new RenderIcon(DWClientProps.EFFECT_SPIT).setHasLight(false).setSize(0.5F).setColorRGB(0.1F, 0.8F, 0.1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRottenCreeper.class, new RenderRottenCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityDroneBase.class, new RenderDrone());
		
		//----------------------------------------
		
	}

}
