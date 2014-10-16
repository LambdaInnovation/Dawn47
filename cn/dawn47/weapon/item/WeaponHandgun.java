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
public class WeaponHandgun extends DWGeneralWeapon {

	public WeaponHandgun() {
		super(DWItems.ammoHandgun);
		this.setMaxDamage(6);
		this.setIAndU("handgun");
		initActions();
	}
	
	protected void initActions() {
		this.actionShoot = new ActionAutomaticShoot(300, 10, 4, "dawn47:weapons.glock.glock_fire")
			.setMuzzleflash(DWClientProps.GLOCK_MUZZLEFLASH);
	}

	@Override
	public float getButtDamage() {
		return 3;
	}

}
