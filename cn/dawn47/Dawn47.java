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
package cn.dawn47;


import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

import cn.dawn47.core.misc.DWCreativeTab;
import cn.dawn47.core.proxy.DWCommonProxy;
import cn.dawn47.core.proxy.DWGeneralProps;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.misc.entity.EntityPoster;
import cn.dawn47.mob.entity.EntityRottenCreeper;
import cn.dawn47.mob.entity.EntityScoutRobot;
import cn.dawn47.weapon.entity.EntityRadiationBall;
import cn.weaponmod.core.WeaponMod;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * @author WeAthFolD
 *
 */
@Mod(modid = "dawn47", name = "Dawn47", version = Dawn47.VERSION, dependencies = WeaponMod.DEPENDENCY)
public class Dawn47 {

	public static final String VERSION = "0.8";
	
	@SidedProxy(
		serverSide = "cn.dawn47.core.proxy.DWCommonProxy",
		clientSide = "cn.dawn47.core.proxy.DWClientProxy")
	public static DWCommonProxy proxy;
	
	@Instance("dawn47")
	public static Dawn47 INSTANCE;
	
	public static CreativeTabs cct = new DWCreativeTab("dawn47");
	
	/**
	 * 日志
	 */
	public static Logger log = FMLLog.getLogger();
	
	public static Configuration config;
	
	public static SimpleNetworkWrapper netHandler;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		config = new Configuration(event.getSuggestedConfigurationFile());
		
		log.info("Starting Dawn47 " + VERSION);
		log.info("Copyright (c) Lambda Innovation & The Ancient Stone, 2013-2014");
		log.info("http://www.lambdacraft.cn");
		
		DWItems.init(config);
		
		netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(DWGeneralProps.NET_CHANNEL);
		
		proxy.preInit();
	}
	
	/**
	 * 加载（方块、物品、网络处理、其他)
	 * 
	 * @param Init
	 */
	@EventHandler
	public void init(FMLInitializationEvent Init) {
		
		//-----Entity Registry--------
		
		registerEntity(EntityRadiationBall.class, "dw_radiation_ball");
		registerEntity(EntityScoutRobot.class, "dw_scout_robot");
		registerEntity(EntityRottenCreeper.class, "dw_rotten_creeper");
		registerEntity(EntityPoster.class, "dw_posterrr");
		
		//-----------------------
		proxy.init();
	}
	 
	private int nextEntityID = -1;
	
	protected void registerEntity(Class<? extends Entity> entClass, String entName) {
		EntityRegistry.registerModEntity(entClass, entName, ++nextEntityID, this, 48, 3, true);
	}
	
	protected void registerEntity(Class<? extends Entity> entClass, String entName, int trackingRange, int updateFreq, boolean velUpdate) {
		EntityRegistry.registerModEntity(entClass, entName, ++nextEntityID, this, trackingRange, updateFreq, velUpdate);
	}
	
	private static int nextId = 0;
	public static int getNextNetID() {
		return nextId++;
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent Init) {
		
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		CommandHandler commandManager = (CommandHandler) event.getServer()
				.getCommandManager();
	}

	
}
