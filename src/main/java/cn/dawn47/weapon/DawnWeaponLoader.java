/**
 * Copyright (c) Lambda Innovation, 2013-2015
 * 本作品版权由Lambda Innovation所有。
 * http://www.li-dev.cn/
 *
 * This project is open-source, and it is distributed under  
 * the terms of GNU General Public License. You can modify
 * and distribute freely as long as you follow the license.
 * 本项目是一个开源项目，且遵循GNU通用公共授权协议。
 * 在遵照该协议的情况下，您可以自由传播和修改。
 * http://www.gnu.org/licenses/gpl.html
 */
package cn.dawn47.weapon;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.WavefrontObject;
import cn.dawn47.core.register.DWItems;
import cn.liutils.loading.Loader;
import cn.liutils.loading.item.ItemLoadRule;
import cn.liutils.loading.item.ItemLoader;
import cn.weaponry.impl.classic.loading.ClassicRenderRule;
import cn.weaponry.impl.classic.loading.ClassicWeaponRule;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class DawnWeaponLoader extends ItemLoader<DawnWeapon> {

	public DawnWeaponLoader() {
		additionalRules.add(new ClassicWeaponRule());
		additionalRules.add(new Rule());
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			addRenderRule();
		}
	}
	
	@SideOnly(Side.CLIENT)
	private void addRenderRule() {
		additionalRules.add(new CustomRenderRule());
	}
	
	@SideOnly(Side.CLIENT)
	private class CustomRenderRule extends ClassicRenderRule {
		protected WavefrontObject loadModel() {
			return new WavefrontObject(new ResourceLocation("dawn47:models/" + name + ".obj"));
		}
		
		protected ResourceLocation loadTexture() {
			return new ResourceLocation("dawn47:textures/models/" + name + ".png");
		}
	}
	
	private static class Rule extends ItemLoadRule<DawnWeapon> {

		@Override
		public void load(DawnWeapon item,
				cn.liutils.loading.Loader.ObjectNamespace ns, String name)
				throws Exception {
			//Must be guaranteed that MiscItems are loaded first.
			item.setUnlocalizedName("dw_" + name);
			item.setTextureName("dawn47:" + name);
			item.ammoType = (Item) DWItems.itemLoader.getObject(ns.getString("weapon", "ammo"));
			
			//Load sounds
			item.shootSound = "dawn47:weapons." + name + ".fire";
			item.reloadEndSound = item.jamSound = "dawn47:weapons.abort";
			item.reloadStartSound = "dawn47:weapons." + name + ".magout";
			item.reloadEndSound = "dawn47:weapons." + name + ".magin";
			
			item.stockDamage = ns.getFloat("weapon", "stockDamage");
		}
		
	}
	
}
