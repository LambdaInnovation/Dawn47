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
public class WeaponSAR extends DWGeneralWeapon {

	public WeaponSAR() {
		super(DWItems.ammoSAR);
		setIAndU("sar");
		setMaxDamage(30);
		initAction();
	}
	
	protected void initAction() {
		actionShoot = new ActionAutomaticShoot(300, 4, 5, "dawn47:weapons.sar.fire")
			.setMuzzleflash(DWClientProps.GLOCK_MUZZLEFLASH).setMuzOffset(.45, 0.32, -0.1);
	}

	@Override
	public float getButtDamage() {
		return 7;
	}

}
