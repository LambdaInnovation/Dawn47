package cn.dawn47.core.proxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class DWClientProps {

	//public paths
	public static final String 
			TEX_ENTITIES = "dawn47:textures/entities/",
			TEX_BLOCKS = "dawn47:textures/blocks/",
			TEX_ITEMS = "dawn47:textures/items/",
			TEX_MUZZLE = "dawn47:textures/m/",
			TEX_HUD = "dawn47:textures/hud/";
	
	//muzzleflash
	public static final String 
			GLOCK_MUZZLEFLASH [] = { TEX_MUZZLE + "m_glock1.png", TEX_MUZZLE + "m_glock2.png", TEX_MUZZLE + "m_glock3.png" },
			AR_MUZZLEFLASH[] = { TEX_MUZZLE + "m_ar1.png", TEX_MUZZLE +  "m_ar2.png", TEX_MUZZLE + "m_ar3.png" },
			SG_MUZZLEFLASH[] = { TEX_MUZZLE + "m_sg1.png", TEX_MUZZLE + "m_sg2.png", TEX_MUZZLE + "m_sg3.png" },
			LASER_MUZZLEFLASH[] = { TEX_MUZZLE + "m_laser1.png", TEX_MUZZLE + "m_laser2.png", TEX_MUZZLE + "m_laser3.png" },
			RADIATION_MUZZLEFLASH[] = { TEX_MUZZLE + "m_splash1.png", TEX_MUZZLE + "m_splash2.png", TEX_MUZZLE + "m_splash3.png" };

	//weapon&equipments
	public static final ResourceLocation 
			HANDGUN_TEXTURE_PATH = src(TEX_ENTITIES + "handgun.png"),
			MEDKIT_TEXTURE_PATH = src(TEX_ENTITIES + "medkit.png"),
			AR_TEXTURE_PATH = src(TEX_ENTITIES + "assault_rifle.png"),
			RADIATION_TEXTURE_PATH = src(TEX_ENTITIES + "radiation_launcher.png"),
			LASER_TEXTURE_PATH = src(TEX_ENTITIES + "laser_rifle.png"),
			SHOTGUN_TEXTURE_PATH = src(TEX_ENTITIES + "shotgun.png"),
			SOFT_DRINK_TEXTURE_PATH = src(TEX_ENTITIES + "softdrink.png"),
			LASERRAY_TEXTURE_PATH = src(TEX_ENTITIES + "laser_ray.png"),
			SNIPER_RIFLE_TEXTURE_PATH = src(TEX_ENTITIES + "sniper_rifle.png"),
			SN_BULLET_TEXTURE_PATH = src(TEX_ENTITIES + "sn_bullet.png"),
			SNIPER_RIFLE_SCOPE = src(TEX_HUD + "rifle.png"),
			AK_PATH = src(TEX_ENTITIES + "ak.png"),
			UZI_PATH = src(TEX_ENTITIES + "uzi.png"),
			UZI_AMMO_PATH = src(TEX_ENTITIES + "uzi_ammo.png"),
			DRONE_PATH[] = { src(TEX_ENTITIES + "spider.png"), src(TEX_ENTITIES + "spider2.png"), src(TEX_ENTITIES + "spider3.png") };
	
	//effects
	public static final ResourceLocation EFFECT_SPIT = src(TEX_ENTITIES + "tinyspit.png");
	
	//mobs
	public static final ResourceLocation ROTTEN_CREEPER_MOB = src(TEX_ENTITIES + "rotten_creeper.png");
	
	//hud
	public static final ResourceLocation HUD = src(TEX_HUD + "helmet.png");
	
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
