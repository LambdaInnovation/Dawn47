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

import java.util.logging.Logger;

import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import cn.dawn47.core.misc.DWCreativeTab;
import cn.dawn47.core.proxy.DWCommonProxy;
import cn.dawn47.core.proxy.DWGeneralProps;
import cn.dawn47.core.register.Config;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.core.register.DWPacketHandler;
import cn.dawn47.equipment.entities.EntityMedkit;
import cn.dawn47.mob.entity.EntityDroneBase;
import cn.dawn47.mob.entity.EntityRottenCreeper;
import cn.dawn47.weapon.EntityRadiationBall;
import cn.weaponmod.WeaponMod;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * @author WeAthFolD
 *
 */
@Mod(modid = "Dawn47", name = "Dawn47 the Assistance Mod", version = DawnMod.VERSION, dependencies = WeaponMod.DEPENDENCY)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
clientPacketHandlerSpec = @SidedPacketHandler(channels = { DWGeneralProps.NET_CHANNEL_CLIENT }, packetHandler = DWPacketHandler.class), 
serverPacketHandlerSpec = @SidedPacketHandler(channels = { DWGeneralProps.NET_CHANNEL_SERVER }, packetHandler = DWPacketHandler.class))
public class DawnMod {

	public static final String VERSION = "0.0.0.1dev";
	
	@SidedProxy(serverSide = "cn.dawn47.core.proxy.DWCommonProxy", clientSide = "cn.dawn47.core.proxy.DWClientProxy")
	public static DWCommonProxy proxy;
	
	public static CreativeTabs cct = new DWCreativeTab("Dawn47");
	
	/**
	 * 日志
	 */
	public static Logger log = Logger.getLogger("Dawn47");
	
	public static Config config;
	
	private int nextEntityID = 0;
	
	/**
	 * 预加载（设置、世界生成、注册Event）
	 * 
	 * @param event
	 */
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {

		config = new Config(event.getSuggestedConfigurationFile());
		
		log.setParent(FMLLog.getLogger());
		log.info("Starting Dawn47 " + VERSION);
		log.info("Copyright (c) Lambda Innovation & Power Mill, 2013");
		log.info("http://www.lambdacraft.cn");
		
		DWItems.init(config);
		MinecraftForge.EVENT_BUS.register(new DWEventHandler());
		
		proxy.preInit();
	}
	
	/**
	 * 加载（方块、物品、网络处理、其他)
	 * 
	 * @param Init
	 */
	@Init
	public void init(FMLInitializationEvent Init) {
		LanguageRegistry.instance().addStringLocalization("itemGroup.Dawn47", "Dawn 47");
		
		//-----Entity Registry--------
		
		registerEntity(EntityMedkit.class, "medkit");
		registerEntity(EntityRadiationBall.class, "radiation_ball");
		registerEntity(EntityRottenCreeper.class, "rotten_creeper");
		registerEntity(EntityDroneBase.class, "drone_test");
		
		//-----------------------
		proxy.init();
	}
	
	 
	
	protected void registerEntity(Class<? extends Entity> entClass, String entName) {
		EntityRegistry.registerModEntity(entClass, entName, ++nextEntityID, this, 48, 3, true);
	}
	
	protected void registerEntity(Class<? extends Entity> entClass, String entName, int trackingRange, int updateFreq, boolean velUpdate) {
		EntityRegistry.registerModEntity(entClass, entName, ++nextEntityID, this, trackingRange, updateFreq, velUpdate);
	}
	
	/**
	 * 加载后（保存设置）
	 * 
	 * @param Init
	 */
	@PostInit
	public void postInit(FMLPostInitializationEvent Init) {
		
	}
	
	/**
	 * 服务器加载（注册指令）
	 * 
	 * @param event
	 */
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
		CommandHandler commandManager = (CommandHandler) event.getServer()
				.getCommandManager();
	}

	
}
