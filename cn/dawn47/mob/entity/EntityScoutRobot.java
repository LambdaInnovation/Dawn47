/**
 * 
 */
package cn.dawn47.mob.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cn.dawn47.core.proxy.DWClientProps;
import cn.liutils.api.entity.LIEntityMob;

/**
 * @author WeathFolD
 *
 */
public class EntityScoutRobot extends LIEntityMob {

	public EntityScoutRobot(World par1World) {
		super(par1World);
		setSize(2.5F, 4.0F);
	}

	@Override
	protected double getMaxHealth2() {
		return 35;
	}

	@Override
	protected double getFollowRange() {
		return 15;
	}

	@Override
	protected double getMoveSpeed() {
		return 2;
	}

	@Override
	protected double getKnockBackResistance() {
		return 5;
	}

	@Override
	protected double getAttackDamage() {
		return 14;
	}

	@Override
	public ResourceLocation getTexture() {
		return DWClientProps.SCOUT_ROBOT_TEX;
	}

}
