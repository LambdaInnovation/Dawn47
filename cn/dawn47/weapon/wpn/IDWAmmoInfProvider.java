package cn.dawn47.weapon.wpn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IDWAmmoInfProvider {
	String getAmmo(EntityPlayer player, ItemStack stack);
}
