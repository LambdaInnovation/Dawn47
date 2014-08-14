/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.dawn47.mob.entity;

import cn.dawn47.core.proxy.DWClientProps;
import cn.liutils.api.entity.LIEntityMob;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class EntityRottenCreeper extends LIEntityMob {

	/**
	 * @param par1World
	 */
	public EntityRottenCreeper(World par1World) {
		super(par1World);
		
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.EntityLiving#getMaxHealth()
	 */
	@Override
	public double getMaxHealth2() {
		return 12;
	}
	
	@Override
	public ResourceLocation getTexture() {
		return DWClientProps.ROTTEN_CREEPER_MOB;
	}

	@Override
	protected double getFollowRange() {
		return 10.0D;
	}

	@Override
	protected double getMoveSpeed() {
		return 0.4D;
	}

	@Override
	protected double getKnockBackResistance() {
		return 1.0D;
	}

	@Override
	protected double getAttackDamage() {
		return 5D;
	}

}
