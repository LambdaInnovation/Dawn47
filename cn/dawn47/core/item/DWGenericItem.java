package cn.dawn47.core.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cn.dawn47.DawnMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DWGenericItem extends Item {

	public String iconName = "none";

	public DWGenericItem(int par1) {
		super(par1);
		setCreativeTab(DawnMod.cct);
	}

	public DWGenericItem setIconName(String name) {
		iconName = name;
		return this;
	}

	public DWGenericItem setIAndU(String name) {
		iconName = name;
		setUnlocalizedName(name);
		return this;
	}

	public DWGenericItem setStackAndDamage(int stack, int damage) {
		setMaxStackSize(stack);
		setMaxDamage(damage);
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("dawn47:" + iconName);
	}
	

}
