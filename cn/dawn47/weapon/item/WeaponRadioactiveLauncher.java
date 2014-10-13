/**
 * 
 */
package cn.dawn47.weapon.item;

import cn.dawn47.core.register.DWItems;
import cn.dawn47.weapon.entity.EntityRadiationBall;
import cn.liutils.api.entity.EntityBullet;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionShoot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * @author WeathFolD
 *
 */
public class WeaponRadioactiveLauncher extends DWGeneralWeapon {

	private static class ActionBallGen extends ActionShoot {

		public ActionBallGen() {
			super(0, 3, "dawn47:weapons.rad.launch");
		}
		
		@Override
		protected Entity getProjectileEntity(World world, EntityPlayer player) {
			return world.isRemote ? null : new EntityRadiationBall(world, player);
		}
		
	}
	
	public WeaponRadioactiveLauncher() {
		super(DWItems.ammoRadit);
		setIAndU("rad_launcher");
		initAction();
	}
	
	protected void initAction() {
		this.actionShoot = new ActionAutomaticShoot(new ActionBallGen(), 20, 400);
	}

	@Override
	public float getButtDamage() {
		return 7;
	}

}
