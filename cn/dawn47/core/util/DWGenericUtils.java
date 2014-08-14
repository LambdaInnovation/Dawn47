package cn.dawn47.core.util;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import cn.liutils.api.util.Motion3D;
import cn.weaponmod.api.WeaponHelper;

public class DWGenericUtils {

	public static void doPlayerAttack(EntityPlayer player, int damage) {
		Motion3D motion = new Motion3D(player, true);
		MovingObjectPosition e = WeaponHelper.rayTraceEntities(null, player.worldObj, motion.asVec3(player.worldObj), motion.move(3.0F).asVec3(player.worldObj), player);
		if(e != null) {
			Entity ent = e.entityHit;
			ent.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
		}
	}
	
	public static IEntitySelector selectorPlayer = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity entity) {
			if (entity instanceof EntityPlayer)
				return true;
			return false;
		}
		
	} ,
	selectorLiving = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity entity) {
			boolean b = IEntitySelector.selectAnything.isEntityApplicable(entity)
					&& entity instanceof EntityLiving && !entity.isEntityInvulnerable();
			if(entity instanceof EntityPlayer) {
				b &= !((EntityPlayer)entity).capabilities.isCreativeMode;
			}
			return b;
		}
		
	};

}
