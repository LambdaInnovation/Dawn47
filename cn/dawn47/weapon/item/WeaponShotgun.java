/**
 * 
 */
package cn.dawn47.weapon.item;

import cn.dawn47.core.register.DWItems;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionBuckshot;
import cn.weaponmod.api.action.ActionMultipleReload;
import net.minecraft.item.Item;

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
				20, 300);
		this.actionReload = new ActionMultipleReload(15, 300)
			.setSound("dawn47:weapons.shotgun.insert")
			.setSoundFinish("dawn47:weapons.shotgun.pump_seq");
	}

	@Override
	public float getButtDamage() {
		return 4;
	}

}
