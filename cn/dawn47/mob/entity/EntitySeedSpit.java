/**
 * 
 */
package cn.dawn47.mob.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * 恶魔种子吐出酸液的实体。
 * TODO:Undone
 * @author WeathFolD
 *
 */
public class EntitySeedSpit extends EntityThrowable {

	/**
	 * @param par1World
	 */
	public EntitySeedSpit(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param par1World
	 * @param par2EntityLivingBase
	 */
	public EntitySeedSpit(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param par1World
	 * @param par2
	 * @param par4
	 * @param par6
	 */
	public EntitySeedSpit(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.projectile.EntityThrowable#onImpact(net.minecraft.util.MovingObjectPosition)
	 */
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		// TODO Auto-generated method stub

	}

}
