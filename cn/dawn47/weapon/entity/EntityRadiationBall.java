package cn.dawn47.weapon.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cn.dawn47.core.client.DWParticleHelper;
import cn.liutils.api.entity.EntityBullet;
import cn.weaponmod.api.WeaponHelper;

public class EntityRadiationBall extends EntityBullet {

	public int splashTick = 0;

	public EntityRadiationBall(World par1World, EntityLivingBase ent) {
		super(par1World, ent, 15);
	}

	public EntityRadiationBall(World world) {
		super(world);
	}

	protected void doBlockCollision(MovingObjectPosition result) {
		WeaponHelper.doRangeDamage(worldObj, DamageSource
				.causeMobDamage(getThrower()), worldObj.getWorldVec3Pool()
				.getVecFromPool(posX, posY, posZ), 16.0F, 4.0F, this);
		motionX = motionY = motionZ = 0;
		spawnParticles(result);
		setDead();
	}

	public void doEntityCollision(MovingObjectPosition result) {
		WeaponHelper.doRangeDamage(worldObj, DamageSource
				.causeMobDamage(getThrower()), worldObj.getWorldVec3Pool()
				.getVecFromPool(posX, posY, posZ), 16.0F, 4.0F, this);
		motionX = motionY = motionZ = 0;
		spawnParticles(result);
		setDead();
	}

	private void spawnParticles(MovingObjectPosition result) {
		if (!worldObj.isRemote)
			return;
		for (int i = 0; i < 34; i++) {

			double moX = motionX, moY = motionY, moZ = motionZ;
			double posX = result.hitVec.xCoord ,
			posY = result.hitVec.yCoord,
			posZ = result.hitVec.zCoord;
			if (result.typeOfHit == MovingObjectType.BLOCK) {
				ForgeDirection dir = ForgeDirection.values()[result.sideHit];
				if (dir.offsetX != 0) {
					moX *= dir.offsetX * 0.3;
				} else
					moX *= 0.3;
				if (dir.offsetY != 0) {
					moY *= dir.offsetY * 0.3;
				} else
					moY *= 0.3;
				if (dir.offsetZ != 0) {
					moZ *= dir.offsetZ * 0.3;
				} else
					moZ *= 0.3;
				posX +=  0.01 * dir.offsetX;
				posY += 0.01 * dir.offsetY;
				posZ += 0.01 * dir.offsetZ;
			}
			moX += (rand.nextGaussian() - 0.5) * 0.6;
			moY += 0.4 + (rand.nextGaussian() - 0.5) * 0.6;
			moZ += (rand.nextGaussian() - 0.5) * 0.6;

			DWParticleHelper.spawnParticle(worldObj, "spit",
					result.hitVec.xCoord, result.hitVec.yCoord, result.hitVec.zCoord,
					moX, moY, moZ);
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	protected float func_70182_d() {
		return 2.2F;
	}

	@Override
	public float getGravityVelocity() {
		return 0.005F;
	}

}
