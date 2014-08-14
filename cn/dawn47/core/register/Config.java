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

import java.io.File;
import java.io.IOException;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 * @author WeAthFolD
 *
 */
public class Config {

	private static Configuration config;

	public Config(File configFile) {
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				System.out.println(e);
				return;
			}
		}
		config = new Configuration(configFile);
		config.load();
	}

	public void initliazeConfig(File ConfigFile) {
		if (this != null) {
			return;
		}
		config = new Configuration(ConfigFile);
	}

	public String getGeneralProperties(String PropertyName, String DefaultValue)
			throws Exception {
		if (this == null) {
			throw new NullPointerException();
		}
		return config.get("general", PropertyName, DefaultValue).getString();
	}

	public Property getProperty(String category, String propertyName,
			String defaultValue) throws Exception {
		if (this == null) {
			throw new NullPointerException();
		}
		return config.get(category, propertyName, defaultValue);
	}

	public Boolean getBoolean(String name, Boolean defaultValue)
			throws Exception {
		if (this == null) {
			throw new NullPointerException();
		}
		return config.get("general", name, defaultValue).getBoolean(
				defaultValue);
	}

	public int getInteger(String name, Integer defaultValue) throws Exception {
		if (this == null) {
			throw new NullPointerException();
		}
		return config.get("general", name, defaultValue).getInt();
	}

	public int getItemID(String itemName, int defaultValue) throws Exception {
		if (this == null) {
			throw new NullPointerException();
		}
		return config.getItem("item", "ID." + itemName, defaultValue).getInt();
	}

	public int GetBlockID(String blockName, int defaultID) throws Exception {
		if (this == null) {
			throw new NullPointerException();
		}
		return config.getBlock("ID." + blockName, defaultID).getInt();
	}
	
	public int getSpecialBlockID(String name, Integer defaultValue) throws Exception {
		if (this == null) {
			throw new NullPointerException();
		}
		return config.get("gen", name, defaultValue).getInt();
	}

	public void SaveConfig() {
		config.save();
	}
}
