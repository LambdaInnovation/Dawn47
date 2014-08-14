package cn.dawn47.core.register;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Property;
import cn.dawn47.DawnMod;

public class GeneralRegistry {

	private static final int BLOCK_BEGIN = 400;
	private static Config config;

	/**
	 * 加载一个含有可设置参数的类。
	 * 
	 * @param conf
	 *            公用设置
	 * @param cl
	 *            类，要注册的参数必须为Static
	 */
	public static void loadConfigurableClass(Config conf, Class<?> cl) {
		Property prop;
		for (Field f : cl.getFields()) {
			Configurable c = f.getAnnotation(Configurable.class);
			if (c != null) {
				try {
					prop = conf
							.getProperty(c.category(), c.key(), c.defValue());
					prop.comment = c.comment();
					Class<?> type = f.getType();
					if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
						f.setInt(null,
								prop.getInt(Integer.parseInt(c.defValue())));
					} else if (type.equals(Boolean.TYPE)
							|| type.equals(Boolean.class)) {
						f.setBoolean(null, prop.getBoolean(Boolean
								.parseBoolean(c.defValue())));
					} else if (type.equals(Double.TYPE)
							|| type.equals(Double.class)) {
						f.setDouble(null, prop.getDouble(Double.parseDouble(c
								.defValue())));
					} else if (type.equals(String.class)) {
						f.set(null, prop.getString());
					} else {
						throw new UnsupportedOperationException();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获得一个空的物品ID。（调用Config配置）
	 * 
	 * @param name
	 *            物品名字
	 * @param cat
	 *            物品分类
	 * @see cn.lambdacraft.core.proxy.GeneralProps
	 * @return 获取的ID
	 */
	public static int getItemId(String name, int cat) {
		config = DawnMod.config;
		try {
			return config.getItemID(name, getEmptyItemId(cat)) - 256;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 获得一个空的方块ID。（调用Config配置）
	 * 
	 * @param name
	 *            方块名字
	 * @param cat
	 *            方块分类
	 * @see cn.lambdacraft.core.proxy.GeneralProps
	 * @return 获取的ID
	 */
	public static int getBlockId(String name, int cat) {
		config = DawnMod.config;
		try {
			return config.GetBlockID(name, getEmptyBlockId(cat));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int getFixedBlockId(String name, int def) {
		config = DawnMod.config;
		try {
			return config.GetBlockID(name, def);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int getFixedBlockId(String name, int def, int max) {
		config = DawnMod.config;
		try {
			int id =  config.getSpecialBlockID(name, def);
			if(id >= max)
				throw new IllegalArgumentException("Block id has been set as a value too large : " + name + "as id + " + id + " , it must be below the value of " + max);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	private static int getEmptyItemId(int cat) {
		int begin = 3000;
		begin += cat * 50;
		int theId = 0;
		for (int i = 0; i < 50; i++) {
			theId = begin + i;
			if (Item.itemsList[theId] == null)
				return theId;
		}
		return -1;
	}

	private static int getEmptyBlockId(int cat) {
		int begin = 400;
		begin += cat * 50;
		int theId = 0;
		for (int i = 0; i < 50; i++) {
			theId = begin + i;
			if (Block.blocksList[theId] == null)
				return theId;
		}
		return -1;
	}

}