/**
 * 
 */
package cn.dawn47.weapon.item;

import cn.dawn47.core.register.DWItems;
import cn.dawn47.weapon.entity.EntityLaserDelayed;
import cn.liutils.api.entity.EntityBullet;
import cn.weaponmod.api.action.ActionShoot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * 
 * @author WeathFolD
 */
public class WeaponLaserRifle extends DWGeneralWeapon {
	
	public static class ActionLaser extends ActionShoot {

		public ActionLaser() {
			super(10, 10, "dawn47:weapons.laser.laser");
			setShootRate(30);
		}
		
		@Override
		protected Entity getProjectileEntity(World world, EntityPlayer player) {
			return new EntityLaserDelayed(world, player);
		}
		 
	}

	public WeaponLaserRifle() {
		super(DWItems.ammoLaser);
		this.setIAndU("laser_rifle");
		this.setMaxDamage(35);
		this.actionShoot = new ActionLaser();
	}

	@Override
	public float getButtDamage() {
		return 4;
	}

}
