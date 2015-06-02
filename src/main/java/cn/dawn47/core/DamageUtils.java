package cn.dawn47.core;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cn.dawn47.weapon.entity.EntityRadiationBall;
import cn.liutils.util.generic.MathUtils;
import cn.liutils.util.mc.EntitySelectors;
import cn.liutils.util.mc.WorldUtils;

public class DamageUtils {
	
	/**
	 * Using the simple linear attenuation calculation and apply a range damage.
	 */
	public static void doRangeDamage(World world, DamageSource cause, double x, double y, double z, double range, float damage, Entity... excls) {
		List<Entity> list = WorldUtils.getEntities(world, x, y, z, range, 
			new EntitySelectors.SelectorList(
					EntitySelectors.living, 
					new EntitySelectors.Exclusion(excls)
		));
		for(Entity e : list) {
			float distance = (float) e.getDistance(x, y, z);
			float dmg = damage * MathUtils.lerpf(0.4f, 1, (float) (1 - distance / range));
			e.attackEntityFrom(cause, dmg);
		}
	}
	
}
