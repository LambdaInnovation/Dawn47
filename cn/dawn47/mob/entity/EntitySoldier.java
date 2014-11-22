/**
 * 
 */
package cn.dawn47.mob.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.weapon.item.DWGeneralWeapon;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.entity.LIEntityMob;
import cn.weaponmod.api.action.ActionAutomaticShoot;

/**
 * @author WeathFolD
 *
 */
public class EntitySoldier extends LIEntityMob {
	
	public static List<WeaponTemplate> wpnTemplates = new ArrayList<WeaponTemplate>();
	static {
		wpnTemplates.add(new WeaponTemplate(DWItems.assaultRifle, 0.08, 0, 0, 1.0F));
		wpnTemplates.add(new WeaponTemplate(DWItems.handgun, 0, -0.2F, 0, 1.0F));
	};
	
	public static class WeaponTemplate {
		public DWGeneralWeapon wpn;
		public Vec3 off = Vec3.createVectorHelper(0.0, 0.0, 0.0);
		public float scale = 1.0F;
		public WeaponTemplate(DWGeneralWeapon wpn) {
			this.wpn = wpn;
		}
		public WeaponTemplate(DWGeneralWeapon wpn, double tx, double ty, double tz, float scale) {
			this(wpn);
			off.xCoord = tx;
			off.yCoord = ty;
			off.zCoord = tz;
			this.scale = scale;
		}
	}
	
	public int skin;
	public int weapon;
	
	public boolean isShooting;
	public int shootTick;
	public int shootTime; //Randomly-Generated shoot time.
	public int lastShootTick;

	/**
	 * @param par1World
	 */
	public EntitySoldier(World par1World) {
		super(par1World);
		skin = rand.nextInt(DWClientProps.SOLDIER_PATH.length);
		weapon = rand.nextInt(wpnTemplates.size());
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(15, Byte.valueOf((byte) weapon));
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}
	
	@Override
	public void onUpdate() {
		boolean preonGround = this.onGround;
		if(isShooting) {
			this.motionX = this.motionZ = 0;
			if(preonGround) motionY = 0;
		}
		super.onUpdate();
		if(preonGround)
			this.motionY = 0;
		
		if(worldObj.isRemote) {
			weapon = dataWatcher.getWatchableObjectByte(15);
			int res = dataWatcher.getWatchableObjectByte(16);
			if(res == 0) {
				isShooting = false;
			} else {
				isShooting = true;
				shootTick = res >> 1;
			}
			shootTime = dataWatcher.getWatchableObjectByte(17);
		} else {
			dataWatcher.updateObject(15, Byte.valueOf((byte) weapon));
			byte res = 0;
			if(isShooting) {
				res = (byte) (1 | (shootTick << 1));
			}
			dataWatcher.updateObject(16, Byte.valueOf(res));
			dataWatcher.updateObject(17, Byte.valueOf((byte) shootTime));
		}
		
		if(isShooting && !dead) {
			++shootTick;
			//Make soldier face the player
//			if(entityToAttack != null) {
//				double dx = entityToAttack.posX - posX,
//						dz = entityToAttack.posZ - posZ;
//				double angle = Math.atan2(dx, dz) * 180 / Math.PI;
//				
////				System.out.
//				System.out.println(angle + " " + this.rotationYaw + " " + this.rotationPitch + " " + worldObj.isRemote);
//				
//				this.rotationYawHead = (float) -angle;
//				this.newRotationYaw = this.prevRotationYaw = this.rotationYaw = (float) -angle;
//			}
//			
//			EntityPlayer ep;
//			ep.attackEntityFrom(par1DamageSource, par2);
			if(ticksExisted % getShootRate() == 0)
				shoot();
			if(shootTick >= shootTime)
				isShooting = false;
		}
	}
	
//	@Override
//	protected boolean isMovementBlocked()
//	{
//		return isShooting;
//	}
	
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		System.out.println(worldObj.isRemote);
		/**
		 * Check if the distance is between 3 and 16 and more than 40 ticks passed from ticksExisted and randomed and not running
		 */
		if(par2 >= 3.0F && par2 <= 16.0F && ticksExisted - lastShootTick > 40 && rand.nextInt(5) == 0 && !isShooting) {
			isShooting = true;
			shootTick = 0;
			shootTime = 20 + rand.nextInt(45);
		} else {
			super.attackEntity(par1Entity, par2);
		}
	}
	
	private void shoot() {
		if(entityToAttack == null) {
			return;
		}
		this.playSound(((ActionAutomaticShoot)wpnTemplates.get(weapon).wpn.actionShoot).getSound(), 0.5F, 1.0F);
		if(rand.nextInt() % 4 == 0) {//1/4
			EntityBullet bullet = new EntityBullet(worldObj, this, this.entityToAttack, getDamage());
			worldObj.spawnEntityInWorld(bullet);
		}
		lastShootTick = ticksExisted;
	}
	
	private int getShootRate() {
		return ((ActionAutomaticShoot)wpnTemplates.get(weapon).wpn.actionShoot).getShootRate();
	}
	
	private float getDamage() {
		return ((ActionAutomaticShoot)wpnTemplates.get(weapon).wpn.actionShoot).getDamage() * 0.3F;
	}

	/* (non-Javadoc)
	 * @see cn.liutils.api.entity.LIEntityMob#getMaxHealth2()
	 */
	@Override
	protected double getMaxHealth2() {
		return 10;
	}

	/* (non-Javadoc)
	 * @see cn.liutils.api.entity.LIEntityMob#getFollowRange()
	 */
	@Override
	protected double getFollowRange() {
		return 50;
	}

	/* (non-Javadoc)
	 * @see cn.liutils.api.entity.LIEntityMob#getMoveSpeed()
	 */
	@Override
	protected double getMoveSpeed() {
		return 1.0;
	}

	@Override
	protected double getKnockBackResistance() {
		// TODO Auto-generated method stub
		return 2.0;
	}

	@Override
	protected double getAttackDamage() {
		return 6.0;
	}

	@Override
	public ResourceLocation getTexture() {
		return DWClientProps.SOLDIER_PATH[skin];
	}

}
