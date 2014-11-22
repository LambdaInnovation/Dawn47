/**
 * 
 */
package cn.dawn47.mob.entity;

import cn.weaponmod.api.WeaponHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * 恶魔种子吐出酸液的实体。
 * TODO:Undone
 * @author WeathFolD
 *
 */
public class EntitySeedSpit extends EntityLargeFireball {

	public EntitySeedSpit(World par1World) {
		super(par1World);
	}

	public EntitySeedSpit(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
	}

	public EntitySeedSpit(World par1World,
			EntityLivingBase par2EntityLivingBase, double par3, double par5,
			double par7) {
		super(par1World, par2EntityLivingBase, par3, par5, par7);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		this.extinguish();
	}

	@Override
	protected void onImpact(MovingObjectPosition res) {
        if (!this.worldObj.isRemote)
        {
            if (res.entityHit != null)
            {
                res.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 4.0F);
            }
            WeaponHelper.doRangeDamage(worldObj, DamageSource.generic, 
            		Vec3.createVectorHelper(posX, posY, posZ),
            		11.0F, 4, this);
        }
	}

}
