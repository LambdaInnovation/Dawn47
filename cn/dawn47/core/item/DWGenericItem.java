package cn.dawn47.core.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import cn.dawn47.DawnMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DWGenericItem extends Item {

	public DWGenericItem() {
		super();
		setCreativeTab(DawnMod.cct);
	}

	public DWGenericItem setIAndU(String name) {
		setTextureName("dawn47:" + name);
		setUnlocalizedName(name);
		return this;
	}

	public DWGenericItem setStackAndDamage(int stack, int damage) {
		setMaxStackSize(stack);
		setMaxDamage(damage);
		return this;
	}
	

}
