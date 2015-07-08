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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

import cn.annoreg.core.RegistrationManager;
import cn.annoreg.core.RegistrationMod;
import cn.annoreg.mc.RegMessageHandler;
import cn.dawn47.core.events.DWEventListener;
import cn.dawn47.core.misc.DWCreativeTab;
import cn.dawn47.core.proxy.DWGeneralProps;
import cn.liutils.ripple.ScriptProgram;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
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
@Mod(modid = "dawn47", name = "Dawn47", version = Dawn47.VERSION)
@RegistrationMod(pkg = "cn.dawn47.", res = "dawn47", prefix = "dw_")
public class Dawn47 {

	public static final String VERSION = "1.0a2";
	
	@Instance("dawn47")
	public static Dawn47 INSTANCE;
	
	public static CreativeTabs cct = new DWCreativeTab("dawn47");
	
	/**
	 * 日志
	 */
	public static Logger log = FMLLog.getLogger();
	
	public static Configuration config;
	
	@RegMessageHandler.WrapperInstance
	public static SimpleNetworkWrapper netHandler;
	
	public static ScriptProgram script;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		
		log.info("Starting Dawn47 " + VERSION);
		log.info("Copyright (c) Lambda Innovation & The Ancient Stone, 2013-2015");
		log.info("http://www.li-dev.cn/");
		
		script = new ScriptProgram();
		String scripts[] = { "generic" };
		for(String s : scripts) {
			script.loadScript(new ResourceLocation("dawn47:scripts/" + s + ".r"));
		}
		
		netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(DWGeneralProps.NET_CHANNEL);
		MinecraftForge.EVENT_BUS.register(new DWEventListener());
		
		RegistrationManager.INSTANCE.registerAll(this, "PreInit");
	}
	
	/**
	 * 加载（方块、物品、网络处理、其他)
	 * 
	 * @param Init
	 */
	@EventHandler
	public void init(FMLInitializationEvent Init) {
		RegistrationManager.INSTANCE.registerAll(this, "Init");
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
		RegistrationManager.INSTANCE.registerAll(this, "PostInit");
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		CommandHandler commandManager = (CommandHandler) event.getServer()
				.getCommandManager();
		RegistrationManager.INSTANCE.registerAll(this, "StartServer");
	}

	
}
