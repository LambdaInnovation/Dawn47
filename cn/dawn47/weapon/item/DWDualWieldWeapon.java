/**
 * 
 */
package cn.dawn47.weapon.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cn.weaponmod.api.weapon.WeaponDualWield;
import cn.weaponmod.api.weapon.WeaponGeneric;

/**
 * @author WeathFolD
 *
 */
public class DWDualWieldWeapon extends WeaponDualWield implements IDWAmmoInfProvider {

	/**
	 * @param template
	 */
	public DWDualWieldWeapon(WeaponGeneric template) {
		super(template);
	}

	@Override
	public String getAmmoForHud(EntityPlayer player, ItemStack stack) {
		return getAmmoLeft(stack) + "|" + getAmmoRight(stack);
	}

}
