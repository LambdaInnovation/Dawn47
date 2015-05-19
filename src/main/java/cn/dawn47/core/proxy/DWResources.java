package cn.dawn47.core.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cn.annoreg.core.RegistrationClass;
import cn.annoreg.mc.RegSubmoduleInit;

@RegistrationClass
public class DWResources {

	//public paths
	public static final String 
			TEX_ENTITIES = "dawn47:textures/entities/",
			TEX_BLOCKS = "dawn47:textures/blocks/",
			TEX_ITEMS = "dawn47:textures/items/",
			TEX_MUZZLE = "dawn47:textures/muz/",
			TEX_HUD = "dawn47:textures/hud/",
			TEX_MODELS = "dawn47:textures/models/";
	
	//muzzleflash
	public static final ResourceLocation 
			GLOCK_MUZZLEFLASH [] = { src(TEX_MUZZLE + "muz_handgun.png") };

	//weapon&equipments
	public static final ResourceLocation 
			SUPER_DRINK_TEXTURE_PATH = src(TEX_MODELS + "superdrink.png"),
			SNIPER_RIFLE_TEXTURE_PATH = src(TEX_ENTITIES + "sniper_rifle.png"),
			SN_BULLET_TEXTURE_PATH = src(TEX_ENTITIES + "sn_bullet.png"),
			SNIPER_RIFLE_SCOPE = src(TEX_HUD + "rifle.png"),
			UZI_PATH = src(TEX_ENTITIES + "uzi.png"),
			UZI_AMMO_PATH = src(TEX_ENTITIES + "uzi_ammo.png"),
			DRONE_PATH[] = { src(TEX_ENTITIES + "spider.png"), src(TEX_ENTITIES + "spider2.png"), src(TEX_ENTITIES + "spider3.png") };
	
	//effects
	public static final ResourceLocation 
		EFFECT_SPIT = src(TEX_ENTITIES + "tinyspit.png"),
		EFFECT_LASER = src(TEX_ENTITIES + "laser.png"),
		EFFECT_LASERHIT = src(TEX_ENTITIES + "laser_hit.png"),
		EFFECT_RADBALL = src(TEX_ENTITIES + "radiation_ball.png"),
		BLANK = src(TEX_ENTITIES + "blank.png"),
		EFFECT_DEMON_SPIT = src(TEX_ENTITIES + "dsegg.png"),
		EFFECT_RADBALL_END[] = {
			src(TEX_ENTITIES + "radiation_ball_end0.png"),
			src(TEX_ENTITIES + "radiation_ball_end1.png"),
			src(TEX_ENTITIES + "radiation_ball_end2.png"),
			src(TEX_ENTITIES + "radiation_ball_end3.png"),
			src(TEX_ENTITIES + "radiation_ball_end4.png"),
			src(TEX_ENTITIES + "radiation_ball_end5.png"),
			src(TEX_ENTITIES + "radiation_ball_end6.png")
		},
		RADIT_MUZZLEFLASH[] = {
			src(TEX_MUZZLE + "muz_radit.png")
		},
		LASER_MUZZLEFLASH[] = {
			src(TEX_MUZZLE + "muz_laser.png")
		};
	
	//mobs
	public static final ResourceLocation 
		ROTTEN_CREEPER_MOB = src(TEX_ENTITIES + "rotten_creeper.png"),
		DEMON_SEED_MOB = src(TEX_ENTITIES + "demon_seed.png");
	
	//hud
	public static final ResourceLocation HUD = src(TEX_HUD + "helmet.png");
	
	//model textures
	public static final ResourceLocation
		HANDGUN_TEXTURE_PATH[] = { src(TEX_MODELS + "handgun.png"), src(TEX_MODELS + "handgun1.png") },
		AR_TEXTURE_PATH[] = { src(TEX_MODELS + "assault_rifle.png"), src(TEX_MODELS + "assault_rifle1.png") },
		RADIATION_TEXTURE_PATH = src(TEX_MODELS + "radiation_launcher.png"),
		LASER_TEXTURE_PATH[] = { src(TEX_MODELS + "laser_rifle.png"), src(TEX_MODELS + "laser_rifle1") },
		SHOTGUN_TEXTURE_PATH[] = { src(TEX_MODELS + "shotgun.png"), src(TEX_MODELS + "shotgun1.png") },
		SUPER_AR_TEX_PATH = src(TEX_MODELS + "super_ar.png"),
		SCOUT_ROBOT_TEX = src(TEX_MODELS + "scout_robot.png"),
		MEDKIT_TEX = src(TEX_MODELS + "medkit.png"),
		SOLDIER_PATH[] = { src(TEX_ENTITIES + "soldier.png") };
	
	//poster
	public static final ResourceLocation
		POSTER_TEXTURE_PATH[] = {
			src(TEX_ENTITIES + "poster0.png"),
			src(TEX_ENTITIES + "poster1.png"),
			src(TEX_ENTITIES + "poster2.png"),
			src(TEX_ENTITIES + "poster3.png"),
			src(TEX_ENTITIES + "poster4.png")
		};
	
	public static ResourceLocation NUMBERS[] = new ResourceLocation[10];
	static {
		for(int i = 0; i <= 9; i++)
			NUMBERS[i] = src(TEX_HUD + i + ".png");
	}
	public static ResourceLocation HUD_SHIELD = src(TEX_HUD + "shield.png"),
			HUD_AMMO = src(TEX_HUD + "ammo.png"),
			HUD_MID = src(TEX_HUD + "mid.png"),
			HUD_HEALTH = src(TEX_HUD + "health.png"),
			HUD_MEDKIT = src(TEX_HUD + "medkit.png");
	
	public static final ResourceLocation EFFECT_SMOKE[] = { 
		src(TEX_ENTITIES + "smoke001.png"), src(TEX_ENTITIES + "smoke002.png"),
				src(TEX_ENTITIES + "smoke003.png"), src(TEX_ENTITIES + "smoke004.png"),
				src(TEX_ENTITIES + "smoke005.png")
	};
	
	private static ResourceLocation src(String s) {
		return new ResourceLocation(s);
	}
	
	public static ResourceLocation[] getMdlMultiTexture(String name, int count) {
		ResourceLocation[] ret = new ResourceLocation[count];
		for(int i = 0; i < count; ++i) {
			ret[i] = getMdlTexture(name + "_" + i);
		}
		return ret;
	}
	
	public static ResourceLocation getMdlTexture(String name) {
		return new ResourceLocation("dawn47:textures/models/" + name + ".png");
	}
	
	public static ResourceLocation texture(String path) {
		return src("dawn47:textures/" + path + ".png");
	}
	
	public static ResourceLocation entityTexture(String name) {
		return texture("entities/" + name);
	}
	
	static Map<String, IModelCustom> loadedModels = new HashMap();
	public static IModelCustom loadModel(String name) {
		IModelCustom mdl = loadedModels.get(name);
		if(mdl != null) return mdl;
		
		mdl = AdvancedModelLoader.loadModel(src("dawn47:models/" + name + ".obj"));
		loadedModels.put(name, mdl);
		return mdl;
	}

}
