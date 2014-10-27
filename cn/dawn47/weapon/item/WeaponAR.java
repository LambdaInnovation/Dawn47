/**
 * 
 */
package cn.dawn47.weapon.item;

import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.core.register.DWItems;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import net.minecraft.item.Item;

/**
 * @author WeathFolD
 *
 */
public class WeaponAR extends DWGeneralWeapon {

	public WeaponAR() {
		super(DWItems.ammoAR);
		setIAndU("assault_rifle");
		setMaxDamage(25);
		actionShoot = new ActionAutomaticShoot(300, 4, 5, "dawn47:weapons.ar.ar_fire")
			.setMuzzleflash(DWClientProps.GLOCK_MUZZLEFLASH).setMuzOffset(.3, -.068, .24);
	}

	@Override
	public float getButtDamage() {
		return 3;
	}

}
