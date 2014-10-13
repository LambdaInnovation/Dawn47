/**
 * Copyright (C) Lambda-Innovation, 2013-2014
 * This code is open-source. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 */
package cn.dawn47.weapon.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cn.liutils.api.entity.EntityBullet;
import cn.liutils.api.util.Motion3D;

/**
 * 
 * @author WeAthFolD
 */
public class EntityLaserDelayed extends EntityBullet {
	
	final int DELAY_TICK = 20;
	final float VELOCITY = 2F;
	
	public int bornTick = 0;
	
	public boolean isHit = false;
	public int ticksAfterHit = 0;

	public EntityLaserDelayed(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving, 22.0F);
		this.setSize(0.7F, 0.2F);
		lifeTime = 200;
	}

	
	public EntityLaserDelayed(World world) {
		super(world);
		this.setSize(0.7F, 0.2F);
		lifeTime = 200;
	}
	
	@Override
	public void onUpdate() {
		if(!worldObj.isRemote && bornTick == 0) {
			this.playSound("dawn47:laser_charge", .5F, 1F);
		}
		
		if(++bornTick < DELAY_TICK) {
			//痛苦的等待
		} else if(bornTick == DELAY_TICK) {
			this.initPosition(getThrower());
		} else {
			if(isHit)  {
				motionX = motionY = motionZ = 0D;
				if(++ticksAfterHit > 20) setDead();
			}
			super.onUpdate();
		}
	}
	
	@Override
	protected void doBlockCollision(MovingObjectPosition result) {
		isHit = true;
		motionX = motionY = motionZ = 0D;
	}
	
	@Override
	protected void doEntityCollision(MovingObjectPosition result) {
		super.doEntityCollision(result);
		isHit = true;
		motionX = motionY = motionZ = 0D;
	}
	
	public boolean isCharging() {
		return bornTick < DELAY_TICK;
	}
	
	@Override
	protected float func_70182_d() {
		return VELOCITY;
	}

}
