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

import org.lwjgl.input.Keyboard;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cn.dawn47.core.client.key.MedkitUse;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.equipment.client.renderer.RendererMedkit;
import cn.dawn47.equipment.entities.EntityMedkit;
import cn.dawn47.misc.client.RendererPoster;
import cn.dawn47.misc.entity.EntityPoster;
import cn.dawn47.mob.client.render.RenderRottenCreeper;
import cn.dawn47.mob.client.render.RendererDemonSeed;
import cn.dawn47.mob.client.render.RendererDrone;
import cn.dawn47.mob.client.render.RendererScoutRobot;
import cn.dawn47.mob.client.render.RendererSoldier;
import cn.dawn47.mob.entity.EntityDemonSeed;
import cn.dawn47.mob.entity.EntityDrone;
import cn.dawn47.mob.entity.EntityRottenCreeper;
import cn.dawn47.mob.entity.EntityScoutRobot;
import cn.dawn47.mob.entity.EntitySeedSpit;
import cn.dawn47.mob.entity.EntitySoldier;
import cn.dawn47.weapon.client.render.RendererLaserDelayed;
import cn.dawn47.weapon.client.render.RendererRadiationBall;
import cn.dawn47.weapon.entity.EntityLaserDelayed;
import cn.dawn47.weapon.entity.EntityRadiationBall;
import cn.liutils.api.client.model.ItemModelCustom;
import cn.liutils.api.client.render.RenderIcon;
import cn.liutils.api.client.render.RenderModelItem;
import cn.liutils.core.LIUtilsMod;
import cn.liutils.core.client.register.LIKeyProcess;
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
		DWClientProps.init();
		LIKeyProcess.addKey("key.medkituse", Keyboard.KEY_V, true, new MedkitUse());
	}
	
	@Override
	public void init() {
		
		//----------------ITEMS-----------		
		//这几行信息量灰常大哟，小心修改TAT
		//Lyt99:完全看花眼啊QAQ
		
		IItemRenderer renderHandgun = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_HANDGUN), DWItems.handgun, 
			DWClientProps.HANDGUN_TEXTURE_PATH[0])
			.setInvOffset(4.0F, 2.438F)
			.setStdRotation(-2.375F,-89.45F,184.2F)
			.setOffset(-1.778F, -0.024F, -0.209F)
			.setInvScale(1.18F)
			.setInvRotation(-32.67F, -75.816F, 0F)
			.setEquipOffset(0.565, -0.15, 0.187)
			.setEquipRotation(0.0F, -2.761F, 9.103F)
			.setInventorySpin(false).setScale(0.441);
		((RendererModelBulletWeapon)renderHandgun).setUpliftFactor(4F);
		//.setMuzzleflashOffset(-.05F, .0F, .0F)
		
		//.setMuzzleflashOffset(-.05F, .0F, .0F)
		
		IItemRenderer renderAssaultRifle = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_ASSAULT_RIFLE), DWItems.assaultRifle,
			DWClientProps.AR_TEXTURE_PATH[0])
			.setEquipOffset(0.824, 0.042, 0.062)
			.setEquipRotation(0.0F, 2.521F, -3.738F)
			.setInventorySpin(false)
			.setInvRotation(-33.876F, -57.218F, 0.142F)
			.setInvOffset(0.806F, -0.808F)
			.setInvScale(0.948F)
			.setOffset(-3.87F, -0.069F, -0.814F)
			.setStdRotation(178.025F,-82.650F - 180F,4.950F)
			.setScale(0.330D);
		((RendererModelBulletWeapon)renderAssaultRifle).setUpliftFactor(2.7F);
		//.setMuzzleflashOffset(.27F, .11F, .1F)
		
		IItemRenderer renderLaser = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_LASER_RIFLE), DWItems.laserRifle, 
			DWClientProps.LASER_TEXTURE_PATH[0])
			.setOffset(-2.199F, -1.167F, -1.217F)
			.setEquipOffset(0.64F, 0.198F, 0.109F)
			.setInvOffset(1.45F, -0.82F)
			.setInvScale(0.86F)
			.setInvRotation(-29.594F, -62.04F, -8.98F)
			.setStdRotation(0.025F, 266.375F, -172.825F)
			.setScale(0.351)
			.setInventorySpin(false)
			.setRenderEntityItem(true);
		//.setMuzzleflashOffset(0.809, -0.449, -0.043)
		
		IItemRenderer renderRaditLauncher = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_RAD_LAUNCHER),
				DWItems.raditLauncher, DWClientProps.RADIATION_TEXTURE_PATH)
			.setInvOffset(-2.9F, -1.846F)
			.setInvScale(-0.221)
			.setInvRotation(-42.818F, -60.998F, -12.588F)
			.setOffset(1.488F, -0.786F, -1.069F)
			.setEquipOffset(0.893F, -0.131F, 0.433)
			.setStdRotation(-0.25F,632.275F,-167.775F)
			.setEquipRotation(0.0F, 5.151F, 5.602F)
			.setScale(0.333)
			.setInventorySpin(false)
			.setRenderEntityItem(true);
		
		IItemRenderer renderShotgun = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_SHOTGUN), DWItems.shotgun, 
			DWClientProps.SHOTGUN_TEXTURE_PATH[0])
			.setStdRotation(0F, -90F, 0F)
			.setOffset(.894F, -.567F, 1.116F)
			.setEquipOffset(0.581, -0.141, 0.066)
			.setInventorySpin(false)
			.setInvScale(-0.798F)
			.setScale(-0.316)
			.setEquipRotation(-2.329F, 1.54F, -9.2F)
			.setInvOffset(2.27F, 3.95F)
			.setInvRotation(498.078F, -46.724F, 26.35F)
			.setRenderEntityItem(true);
		
		IItemRenderer renderSuperDrink = new RenderModelItem(new ItemModelCustom(DWClientProps.MDL_SUPER_DRINK), DWClientProps.SUPER_DRINK_TEXTURE_PATH)
			.setScale(-0.283).setEquipOffset(0.488, -0.096, -0.21)
			.setEquipRotation(0, -91, 0).setInvOffset(0.34F, 7.25F)
			.setInvScale(1.233F).setInventorySpin(false);//.disableInvRotation();
				
		IItemRenderer renderSAR = new RendererModelBulletWeapon(new ItemModelCustom(DWClientProps.MDL_SUPER_AR), DWItems.sar, DWClientProps.SUPER_AR_TEX_PATH)
			.setOffset(-8.12F,-1.457F,0.134F)
			.setEquipOffset(0.642, 0.005, 0.128)
			.setEquipRotation(5.078F, -3.847F, -8.504F)
			.setInvOffset(1.58F, 2.85F)
			.setInvScale(0.836F)
			.setStdRotation(6.8F,266.8F,-178.05F)
			.setInvRotation(-41.592F, -63.238F, -20.288F)
			.setScale(0.359D)
			.setInventorySpin(false);
		((RendererModelBulletWeapon)renderSAR).setUpliftFactor(2F);
		//.setMuzzleflashOffset(0.79F, 0.0F, 0.09F)
		
		//RenderModelItem ak_ammo_render = new RenderModelItem(new ModelAKAmmo(), DWClientProps.SUPER_AR_TEX_PATH)
		//	.setScale(1.698F).setEquipOffset(0.5F, 0F, -0.268F).setOffset(-0.06F, 0.4F, 0F).
		//	setInventorySpin(false).setInvScale(1.43F).setInvOffset(3.19F, 2.866F).setInvRotation(-52.824F, 162.97F, -10.478F);//.disableInvRotation();

		
		MinecraftForgeClient.registerItemRenderer(DWItems.handgun,  renderHandgun);
		MinecraftForgeClient.registerItemRenderer(DWItems.sar, renderSAR);
		MinecraftForgeClient.registerItemRenderer(DWItems.raditLauncher, renderRaditLauncher);
		MinecraftForgeClient.registerItemRenderer(DWItems.laserRifle, renderLaser);
		MinecraftForgeClient.registerItemRenderer(DWItems.assaultRifle, renderAssaultRifle);
		MinecraftForgeClient.registerItemRenderer(DWItems.shotgun, renderShotgun);
		MinecraftForgeClient.registerItemRenderer(DWItems.superdrink, renderSuperDrink);
		RenderingRegistry.registerEntityRenderingHandler(EntityMedkit.class, new RendererMedkit());
		/*
		MinecraftForgeClient.registerItemRenderer(DWItems.assault_rifle, assault_rifle_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.laser_rifle, laser_rifle_render);
		
		MinecraftForgeClient.registerItemRenderer(DWItems.shotgun, shotgun_render);
		
		MinecraftForgeClient.registerItemRenderer(DWItems.uzi, uzi_render);
		
		
		MinecraftForgeClient.registerItemRenderer(DWItems.medkit, medkit_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.sn_ammo, sn_ammo_render);
		//MinecraftForgeClient.registerItemRenderer(DWItems.ak_ammo, ak_ammo_render);
		MinecraftForgeClient.registerItemRenderer(DWItems.uzi_ammo, uzi_ammo_render);*/
		
		//------------------------------------------
		
		//-----------------ENTITIES---------------
		
		//RenderingRegistry.registerEntityRenderingHandler(EntityLaserDelayed.class, new RenderLaser());
		RenderingRegistry.registerEntityRenderingHandler(EntityRadiationBall.class, new RendererRadiationBall());
		//RenderingRegistry.registerEntityRenderingHandler(EntitySpitFX.class, new RenderIcon(DWClientProps.EFFECT_SPIT).setHasLight(false).setSize(0.5F).setColorRGB(0.1F, 0.8F, 0.1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRottenCreeper.class, new RenderRottenCreeper());
		RenderingRegistry.registerEntityRenderingHandler(EntityDrone.class, new RendererDrone());
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserDelayed.class, new RendererLaserDelayed());
		RenderingRegistry.registerEntityRenderingHandler(EntityScoutRobot.class, new RendererScoutRobot());
		RenderingRegistry.registerEntityRenderingHandler(EntityPoster.class, new RendererPoster());
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonSeed.class, new RendererDemonSeed());
		RenderingRegistry.registerEntityRenderingHandler(EntitySeedSpit.class, 
				new RenderIcon(DWClientProps.EFFECT_DEMON_SPIT).setHasLight(false).setBlend(.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySoldier.class, new RendererSoldier());
		
		//----------------------------------------
		
	}

}
