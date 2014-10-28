/**
 * 
 */
package cn.dawn47.weapon.item;

import cn.dawn47.core.proxy.DWClientProps;
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
 * 辐射球发射器~
 * @author WeathFolD
 */
public class WeaponRaditLauncher extends DWGeneralWeapon {

	private static class ActionBallGen extends ActionShoot {

		public ActionBallGen() {
			super(0, 3, "dawn47:weapons.rad.launch");
			this.setMuzzle(DWClientProps.RADIT_MUZZLEFLASH);
			this.setMuzzleOffset(0.18, -0.4, 0.2);
			this.setMuzzleScale(1.5F);
		}
		
		@Override
		protected Entity getProjectileEntity(World world, EntityPlayer player) {
			return world.isRemote ? null : new EntityRadiationBall(world, player);
		}
		
	}
	
	public WeaponRaditLauncher() {
		super(DWItems.ammoRadit);
		this.setIAndU("radit_launcher");
		this.setMaxDamage(4);
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
