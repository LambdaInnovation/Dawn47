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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cn.annoreg.core.RegistrationClass;
import cn.annoreg.mc.RegEntity;
import cn.liutils.api.entityx.EntityX;
import cn.liutils.api.entityx.MotionHandler;
import cn.liutils.api.entityx.motion.CollisionCheck;
import cn.liutils.api.entityx.motion.LifeTime;
import cn.liutils.api.entityx.motion.VelocityUpdate;
import cn.liutils.util.space.Motion3D;

/**
 * 
 * @author WeAthFolD
 */
@RegistrationClass
@RegEntity
public class EntityLaserDelayed extends EntityX {
	
	final int DELAY_TICK = 0;
	final float VELOCITY = 2F;
	
	public int bornTick = 0;
	
	public boolean isHit = false;
	public int ticksAfterHit = 0;

	public EntityLaserDelayed(World world, final EntityLivingBase ent) {
		super(world);
		this.setSize(0.7F, 0.2F);
		
		this.playSound("dawn47:laser_charge", .5F, 1F);
		
		new Motion3D(ent, true).applyToEntity(EntityLaserDelayed.this);
		this.setHeading(motionX, motionY, motionZ, VELOCITY);
		
		addDaemonHandler(new VelocityUpdate(this));
		addDaemonHandler(new CollisionCheck(this) {
			@Override
			protected void onCollided(MovingObjectPosition res) {
				motionX = motionY = motionZ = 0;
				isHit = true;
				if(res.entityHit != null) {
					res.entityHit.attackEntityFrom(DamageSource.causeMobDamage(ent), 12);
				}
			}
		});
		addDaemonHandler(new LifeTime(this, 200));
	}
	
	public EntityLaserDelayed(World world) {
		super(world);
		addDaemonHandler(new VelocityUpdate(this));
		this.setSize(0.7F, 0.2F);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(15, (byte) 0);
	} 
	
	void syncData() {
		if(worldObj.isRemote) {
			byte b = dataWatcher.getWatchableObjectByte(15);
			if(b == 0) {
				isHit = false;
				ticksAfterHit = 0;
			} else {
				isHit = true;
				ticksAfterHit = b >> 1;
			}
		} else {
			if(isHit)
				dataWatcher.updateObject(15, Byte.valueOf((byte) (1 | (ticksAfterHit << 1))));
			else dataWatcher.updateObject(15, Byte.valueOf((byte) 0));
		}
	}
	
	@Override
	public void onUpdate() {
		syncData();
		super.onUpdate();
	}
	
	public boolean isCharging() {
		return bornTick < DELAY_TICK;
	}
}
