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

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.equipment.client.model.ModelMedkit;
import cn.dawn47.equipment.client.model.ModelSuperDrink;
import cn.dawn47.equipment.entities.EntityMedkit;
import cn.dawn47.mob.client.render.RenderDrone;
import cn.dawn47.mob.client.render.RenderRottenCreeper;
import cn.dawn47.mob.entity.EntityDroneBase;
import cn.dawn47.mob.entity.EntityRottenCreeper;
import cn.dawn47.weapon.client.model.ModelAmmoSniperRifle;
import cn.dawn47.weapon.client.model.ModelUzi;
import cn.dawn47.weapon.client.model.ModelUziAmmo;
import cn.dawn47.weapon.client.render.RendererLaserDelayed;
import cn.dawn47.weapon.entity.EntityLaserDelayed;
import cn.dawn47.weapon.entity.EntityRadiationBall;
import cn.liutils.api.client.model.ItemModelCustom;
import cn.liutils.api.client.render.RenderIcon;
import cn.liutils.api.client.render.RenderModel;
import cn.liutils.api.client.render.RenderModelItem;
import cn.liutils.core.LIUtilsMod;
import cn.weaponmod.api.client.render.RendererModelBulletWeapon;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author WeAthFolD
 *
 */
public class DWClientProxy extends DWCommonProxy {
	
	@Override
	public void preInit() {
		if(LIUtilsMod.DEBUG) {
			
		}
	}
	
	@Override
	public void init() {
		
		//----------------ITEMS-----------		
		//这几行信息量灰常大哟，小心修改TAT
		//Lyt99:完全看花眼啊QAQ
		
		IItemRenderer renderHandgun = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_HANDGUN), DWItems.handgun, 
			DWClientProps.HANDGUN_TEXTURE_PATH[0])
			.setInvOffset(4.0F, 2.438F).setStdRotation(-2.375F,-89.45F,184.2F)
			.setOffset(-1.778F, -0.024F, -0.209F).setInvScale(1.18F)
			.setInvRotation(-32.67F, -75.816F, 0F).setEquipOffset(.5F, 0F, 0F)
			.setInventorySpin(false).setScale(0.526D);
		//.setMuzzleflashOffset(-.05F, .0F, .0F)
		
		IItemRenderer uzi_render = new RendererModelBulletWeapon(new ModelUzi(), DWItems.uzi, DWClientProps.UZI_PATH)
			.setOffset(.0F, .12F, .0F).setInvScale(1.37F)
			.setInvOffset(0.744F, 0.584F).setEquipOffset(0.75F, .10F, .0F)
			.setStdRotation(0F, 180F, 0F).setInventorySpin(false)
			.setInvRotation(-39.6F, -54.87F, 4.044F); 
		//.setMuzzleflashOffset(-.05F, .0F, .0F)
		
		IItemRenderer renderAssaultRifle = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_ASSAULT_RIFLE), DWItems.assaultRifle,
			DWClientProps.AR_TEXTURE_PATH[0]).setEquipOffset(0.74F, 0.235F, 0.0F)
			.setInventorySpin(false).setInvRotation(-33.876F, -57.218F, 0.142F)
			.setInvOffset(0.806F, -0.808F).setInvScale(0.948F)
			.setOffset(3.917F,-0.043F,0.714F).setStdRotation(178.025F,-82.650F,4.950F)
			.setScale(0.330D);
		//.setMuzzleflashOffset(.27F, .11F, .1F)
		
		IItemRenderer renderLaser = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_LASER_RIFLE), DWItems.laserRifle, 
			DWClientProps.LASER_TEXTURE_PATH[0])
			.setOffset(-2.199F, -1.167F, -1.217F).setEquipOffset(.64F, 0.2F, 0.0F)
			.setInvOffset(1.45F, -0.82F).setInvScale(0.86F)
			.setInvRotation(-29.594F, -62.04F, -8.98F).setStdRotation(0.025F, 266.375F, -172.825F)
			.setScale(0.417D).setInventorySpin(false);
		//.setMuzzleflashOffset(0.809, -0.449, -0.043)
		
		IItemRenderer renderRaditLauncher = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_RAD_LAUNCHER),
				DWItems.raditLauncher, DWClientProps.RADIATION_TEXTURE_PATH)
			.setInvOffset(-2.9F, -1.846F).setInvScale(0.568F)
			.setInvRotation(-42.818F, -60.998F, -12.588F).setOffset(1.488F, -0.786F, -1.069F)
			.setEquipOffset(0.898, 0.058, 0.334).setStdRotation(-0.25F,632.275F,-167.775F)
			.setScale(0.375D).setInventorySpin(false);
		
		IItemRenderer shotgun_render = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_SHOTGUN), DWItems.shotgun, 
			DWClientProps.SHOTGUN_TEXTURE_PATH[0])
			.setStdRotation(0F, -90F, 0F).setOffset(.894F, -.567F, 1.116F)
			.setEquipOffset(.60F, -.17F, -0.026F).setInventorySpin(false)
			.setInvScale(-0.798F).setScale(-.391)
			.setEquipRotation(-2.329F, 1.54F, -9.20F).setInvOffset(2.27F, 3.95F)
			.setInvRotation(498.078F, -46.724F, 26.35F);
		
		IItemRenderer superdrink_render = new RenderModelItem(new ModelSuperDrink(true), DWClientProps.SOFT_DRINK_TEXTURE_PATH)
			.setEquipOffset(.40F, 0.25F, -.35F).setInvOffset(0.0F, 1.5F).setOffset(0.0F, 0.0F, .0F)
			.setInvScale(1.5F).setInventorySpin(true);//.disableInvRotation();
		
		IItemRenderer medkit_render = new RenderModelItem(new ModelMedkit(true), DWClientProps.MEDKIT_TEXTURE_PATH)
			.setOffset(-.2F, 0.0F, 0.0F).setEquipOffset(.39F, 0.26F, -.2F).setStdRotation(0F, 0F, 90F)
			.setInvRotation(-89.496F, -44.768F, -0.104F).setInvScale(1.5F).setInvOffset(1.542F, -1.090F)
			.setInventorySpin(false);//.disableInvRotation()
		
		IItemRenderer sn_ammo_render =  new RenderModelItem(new ModelAmmoSniperRifle(true), DWClientProps.SN_BULLET_TEXTURE_PATH)
			.setOffset(-.2F, 0.0F, 0.0F).setEquipOffset(.39F, 0.26F, -.2F).setStdRotation(0F, 0F, 90F)
			.setInvScale(1.64F).setInvOffset(6.112F, 0.07F).setInventorySpin(false);//.disableInvRotation();
		
		IItemRenderer renderSAR = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_SUPER_AR), DWItems.sar, DWClientProps.SUPER_AR_TEX_PATH)
			.setOffset(-8.12F,-1.457F,0.134F).setEquipOffset(.8F, 0F, 0F)
			.setInvOffset(1.58F, 2.85F).setInvScale(0.836F)
			.setStdRotation(6.8F,266.8F,-178.05F).setInvRotation(-41.592F, -63.238F, -20.288F)
			.setScale(0.359D).setInventorySpin(false);
		//.setMuzzleflashOffset(0.79F, 0.0F, 0.09F)
		
		//RenderModelItem ak_ammo_render = new RenderModelItem(new ModelAKAmmo(), DWClientProps.SUPER_AR_TEX_PATH)
		//	.setScale(1.698F).setEquipOffset(0.5F, 0F, -0.268F).setOffset(-0.06F, 0.4F, 0F).
		//	setInventorySpin(false).setInvScale(1.43F).setInvOffset(3.19F, 2.866F).setInvRotation(-52.824F, 162.97F, -10.478F);//.disableInvRotation();
		
		IItemRenderer uzi_ammo_render =  new RenderModelItem(new ModelUziAmmo(), DWClientProps.UZI_AMMO_PATH)
			.setScale(2.0F).setOffset(0.032F, 0.13F, 0.066F)
			.setStdRotation(-28F, 0F, 0F).setInvScale(1.512F)
			.setInvOffset(0.474F, -1.24F).setInvRotation(-41.814F, -60.274F, 0F)
			.setInventorySpin(false).setEquipRotation(0.0F, 0.0F, 27.37F)
			.setEquipOffset(0.524F, 0.24F, -0.33F);
		
		
		MinecraftForgeClient.registerItemRenderer(DWItems.handgun,  renderHandgun);
		MinecraftForgeClient.registerItemRenderer(DWItems.sar, renderSAR);
		MinecraftForgeClient.registerItemRenderer(DWItems.raditLauncher, renderRaditLauncher);
		MinecraftForgeClient.registerItemRenderer(DWItems.laserRifle, renderLaser);
		MinecraftForgeClient.registerItemRenderer(DWItems.assaultRifle, renderAssaultRifle);
		
		/*
		MinecraftForgeClient.registerItemRenderer(DWItems.assault_rifle, assault_rifle_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.laser_rifle, laser_rifle_render);
		
		MinecraftForgeClient.registerItemRenderer(DWItems.shotgun, shotgun_render);
		
		MinecraftForgeClient.registerItemRenderer(DWItems.uzi, uzi_render);
		
		MinecraftForgeClient.registerItemRenderer(DWItems.superdrink, superdrink_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.medkit, medkit_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.sn_ammo, sn_ammo_render);
		//MinecraftForgeClient.registerItemRenderer(DWItems.ak_ammo, ak_ammo_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.uzi_ammo, uzi_ammo_render);*/
		
		//------------------------------------------
		
		//-----------------ENTITIES---------------
		
		RenderingRegistry.registerEntityRenderingHandler(EntityMedkit.class, new RenderModel(new ModelMedkit(false), DWClientProps.MEDKIT_TEXTURE_PATH, 1.0F).setOffset(0.0F, 1.0F, 0.0F));
		//RenderingRegistry.registerEntityRenderingHandler(EntityLaserDelayed.class, new RenderLaser());
		RenderingRegistry.registerEntityRenderingHandler(EntityRadiationBall.class, new RenderIcon(DWClientProps.EFFECT_RADBALL).setHasLight(false).setSize(1.5F).setBlend(0.7F));
		//RenderingRegistry.registerEntityRenderingHandler(EntitySpitFX.class, new RenderIcon(DWClientProps.EFFECT_SPIT).setHasLight(false).setSize(0.5F).setColorRGB(0.1F, 0.8F, 0.1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRottenCreeper.class, new RenderRottenCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityDroneBase.class, new RenderDrone());
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserDelayed.class, new RendererLaserDelayed());
		
		//----------------------------------------
		
	}

}
