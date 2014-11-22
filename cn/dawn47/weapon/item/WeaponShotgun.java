/**
 * 
 */
package cn.dawn47.weapon.item;

import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.core.register.DWItems;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionBuckshot;
import cn.weaponmod.api.action.ActionMultipleReload;

/**
 * @author WeathFolD
 *
 */
public class WeaponShotgun extends DWGeneralWeapon {

	public WeaponShotgun() {
		super(DWItems.ammoShotgun);
		this.setMaxDamage(5);
		this.setIAndU("shotgun");
		this.actionShoot = new ActionAutomaticShoot(
				new ActionBuckshot(4, 8, "dawn47:weapons.shotgun.fire"),
				20, 300)
			.setMuzzleflash(DWClientProps.GLOCK_MUZZLEFLASH)
			.setMuzScale(0.7F)
			.setMuzOffset(0.5, 0.3, 0.08);
		this.actionReload = new ActionMultipleReload(15, 300)
			.setSound("dawn47:weapons.shotgun.insert")
			.setSoundFinish("dawn47:weapons.shotgun.pump_seq");
	}

	@Override
	public float getButtDamage() {
		return 4;
	}

}
