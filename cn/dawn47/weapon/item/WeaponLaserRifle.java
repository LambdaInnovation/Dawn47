/**
 * 
 */
package cn.dawn47.weapon.item;

import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.weapon.entity.EntityLaserDelayed;
import cn.weaponmod.api.action.ActionAutomaticShoot;
import cn.weaponmod.api.action.ActionShoot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * 
 * @author WeathFolD
 */
public class WeaponLaserRifle extends DWGeneralWeapon {
	
	public static class ActionLaser extends ActionShoot {

		public ActionLaser() {
			super(0, 10, "dawn47:weapons.laser.laser");
			this.setMuzzle(DWClientProps.LASER_MUZZLEFLASH);
			setMuzzleOffset(0.25, 0.0, 0.03);
		}
		
		@Override
		protected Entity getProjectileEntity(World world, EntityPlayer player) {
			return world.isRemote ? null : new EntityLaserDelayed(world, player);
		}
		 
	}

	public WeaponLaserRifle() {
		super(DWItems.ammoLaser);
		this.setIAndU("laser_rifle");
		this.setMaxDamage(35);
		this.actionShoot = new ActionAutomaticShoot(new ActionLaser(), 10, 400);
	}

	@Override
	public float getButtDamage() {
		return 4;
	}

}
