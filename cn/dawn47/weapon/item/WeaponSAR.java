/**
 * 
 */
package cn.dawn47.weapon.item;

import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.core.register.DWItems;
import cn.weaponmod.api.action.ActionAutomaticShoot;

/**
 * @author WeathFolD
 *
 */
public class WeaponSAR extends DWGeneralWeapon {

	public WeaponSAR() {
		super(DWItems.ammoAR);
		this.setIAndU("sar");
		this.setMaxDamage(25);
		initAction();
	}
	
	protected void initAction() {
		actionShoot = new ActionAutomaticShoot(300, 4, 5, "dawn47:weapons.sar.fire")
			.setMuzzleflash(DWClientProps.GLOCK_MUZZLEFLASH).setMuzOffset(.48, 0.06, 0.05);
	}

	@Override
	public float getButtDamage() {
		return 7;
	}

}
