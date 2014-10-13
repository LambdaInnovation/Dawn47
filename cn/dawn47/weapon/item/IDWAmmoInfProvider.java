package cn.dawn47.weapon.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IDWAmmoInfProvider {
	String getAmmoForHud(EntityPlayer player, ItemStack stack);
}
