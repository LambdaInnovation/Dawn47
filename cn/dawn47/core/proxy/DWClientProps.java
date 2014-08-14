package cn.dawn47.core.proxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class DWClientProps {

	//public paths
	public static final String 
			TEX_ENTITIES = "dawn47:textures/entities/",
			TEX_BLOCKS = "dawn47:textures/blocks/",
			TEX_ITEMS = "dawn47:textures/items/",
			TEX_MUZZLE = "dawn47:textures/m/",
			TEX_HUD = "dawn47:textures/hud/",
			TEX_MODELS = "dawn47:textures/models/";
	
	//muzzleflash
	public static final ResourceLocation 
			GLOCK_MUZZLEFLASH [] = { src(TEX_MUZZLE + "m_glock1.png"), src(TEX_MUZZLE + "m_glock2.png"), src(TEX_MUZZLE + "m_glock3.png") },
			AR_MUZZLEFLASH[] = { src(TEX_MUZZLE + "m_ar1.png"), src(TEX_MUZZLE +  "m_ar2.png"), src(TEX_MUZZLE + "m_ar3.png") },
			SG_MUZZLEFLASH[] = { src(TEX_MUZZLE + "m_sg1.png"), src(TEX_MUZZLE + "m_sg2.png"), src(TEX_MUZZLE + "m_sg3.png") },
			LASER_MUZZLEFLASH[] = { src(TEX_MUZZLE + "m_laser1.png"), src(TEX_MUZZLE + "m_laser2.png"), src(TEX_MUZZLE + "m_laser3.png") },
			RADIATION_MUZZLEFLASH[] = { src(TEX_MUZZLE + "m_splash1.png"), src(TEX_MUZZLE + "m_splash2.png"), src(TEX_MUZZLE + "m_splash3.png") };

	//weapon&equipments
	public static final ResourceLocation 
			MEDKIT_TEXTURE_PATH = src(TEX_ENTITIES + "medkit.png"),
			SOFT_DRINK_TEXTURE_PATH = src(TEX_ENTITIES + "softdrink.png"),
			LASERRAY_TEXTURE_PATH = src(TEX_ENTITIES + "laser_ray.png"),
			SNIPER_RIFLE_TEXTURE_PATH = src(TEX_ENTITIES + "sniper_rifle.png"),
			SN_BULLET_TEXTURE_PATH = src(TEX_ENTITIES + "sn_bullet.png"),
			SNIPER_RIFLE_SCOPE = src(TEX_HUD + "rifle.png"),
			UZI_PATH = src(TEX_ENTITIES + "uzi.png"),
			UZI_AMMO_PATH = src(TEX_ENTITIES + "uzi_ammo.png"),
			DRONE_PATH[] = { src(TEX_ENTITIES + "spider.png"), src(TEX_ENTITIES + "spider2.png"), src(TEX_ENTITIES + "spider3.png") };
	
	//effects
	public static final ResourceLocation EFFECT_SPIT = src(TEX_ENTITIES + "tinyspit.png");
	
	//mobs
	public static final ResourceLocation ROTTEN_CREEPER_MOB = src(TEX_ENTITIES + "rotten_creeper.png");
	
	//hud
	public static final ResourceLocation HUD = src(TEX_HUD + "helmet.png");
	
	//models
	public static final IModelCustom
		MDL_ASSAULT_RIFLE = AdvancedModelLoader.loadModel(src("dawn47:models/assault_rifle.obj")),
		MDL_HANDGUN = AdvancedModelLoader.loadModel(src("dawn47:models/hand_gun.obj")),
		MDL_SUPER_AR = AdvancedModelLoader.loadModel(src("dawn47:models/super_assualt_rifle.obj")),
		MDL_LASER_RIFLE =  AdvancedModelLoader.loadModel(src("dawn47:models/laser_rifle.obj")),
		MDL_RAD_LAUNCHER =  AdvancedModelLoader.loadModel(src("dawn47:models/radiation_launcher.obj")),
		MDL_SCOUT_ROBOT =  AdvancedModelLoader.loadModel(src("dawn47:models/scout_robot.obj")),
		MDL_SHOTGUN =  AdvancedModelLoader.loadModel(src("dawn47:models/shotgun.obj"));
	
	//model textures
	public static final ResourceLocation
		HANDGUN_TEXTURE_PATH[] = { src(TEX_MODELS + "handgun.png"), src(TEX_MODELS + "handgun1.png") },
		AR_TEXTURE_PATH[] = { src(TEX_MODELS + "assault_rifle.png"), src(TEX_MODELS + "assault_rifle1.png") },
		RADIATION_TEXTURE_PATH = src(TEX_MODELS + "radiation_launcher.png"),
		LASER_TEXTURE_PATH[] = { src(TEX_MODELS + "laser_rifle.png"), src(TEX_MODELS + "laser_rifle1") },
		SHOTGUN_TEXTURE_PATH[] = { src(TEX_MODELS + "shotgun.png"), src(TEX_MODELS + "shotgun1.png") },
		SUPER_AR_TEX_PATH = src(TEX_MODELS + "gabirel_rifle.png"),
		SCOUT_ROBOT_TEX = src(TEX_MODELS + "scout_robot.png");
	
	public static ResourceLocation NUMBERS[] = new ResourceLocation[10];
	static {
		for(int i = 0; i <= 9; i++)
			NUMBERS[i] = src(TEX_HUD + i + ".png");
	}
	public static ResourceLocation HUD_SHIELD = src(TEX_HUD + "shield.png"),
			HUD_AMMO = src(TEX_HUD + "ammo.png"),
			HUD_MID = src(TEX_HUD + "mid.png"),
			HUD_HEALTH = src(TEX_HUD + "health.png");
	
	public static final ResourceLocation EFFECT_SMOKE[] = { 
		src(TEX_ENTITIES + "smoke001.png"), src(TEX_ENTITIES + "smoke002.png"),
				src(TEX_ENTITIES + "smoke003.png"), src(TEX_ENTITIES + "smoke004.png"),
				src(TEX_ENTITIES + "smoke005.png")
	};
	
	private static ResourceLocation src(String s) {
		return new ResourceLocation(s);
	}

}
