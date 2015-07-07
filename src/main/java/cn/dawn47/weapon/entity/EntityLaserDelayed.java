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

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEntity;
import cn.dawn47.weapon.client.render.RendererLaserDelayed;
import cn.liutils.entityx.EntityAdvanced;
import cn.liutils.entityx.EntityCallback;
import cn.liutils.entityx.event.CollideEvent;
import cn.liutils.entityx.event.CollideEvent.CollideHandler;
import cn.liutils.entityx.handlers.Rigidbody;
import cn.liutils.util.client.ViewOptimize.IAssociatePlayer;
import cn.liutils.util.helper.Motion3D;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * @author WeAthFolD
 */
@Registrant
@RegEntity
@RegEntity.HasRender
public class EntityLaserDelayed extends EntityAdvanced implements IAssociatePlayer {
	
	private EntityPlayer player;
	
	@RegEntity.Render
	@SideOnly(Side.CLIENT)
	public static RendererLaserDelayed renderer;
	
	final int DELAY_TICK = 0;
	final float VELOCITY = 2F;
	
	public int bornTick = 0;
	
	public boolean isHit = false;
	public int ticksAfterHit = 0;

	public EntityLaserDelayed(final EntityPlayer ent) {
		super(ent.worldObj);
		
		player = ent;
		
		this.setSize(0.7F, 0.2F);
		
		this.playSound("dawn47:laser_charge", .5F, 1F);
		
		new Motion3D(ent, true).multiplyMotionBy(VELOCITY).applyToEntity(EntityLaserDelayed.this);
		
		Rigidbody rb = new Rigidbody();
		addMotionHandler(rb);
		rb.entitySel = new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				return entity != ent;
			}
			
		};
		
		this.regEventHandler(new CollideHandler() {

			@Override
			public void onEvent(CollideEvent event) {
				motionX = motionY = motionZ = 0;
				isHit = true;
				
				MovingObjectPosition res = event.result;
				if(res.entityHit != null) {
					res.entityHit.attackEntityFrom(DamageSource.causeMobDamage(ent), 12);
				}
				
				executeAfter(new EntityCallback() {
					@Override
					public void execute(Entity target) {
						setDead();
					}
				}, 20);
				
				if(res.typeOfHit == MovingObjectType.BLOCK) {
					Vec3 v = res.hitVec;
					posX = v.xCoord;
					posY = v.yCoord;
					posZ = v.zCoord;
				}
			}
			
		});
	}
	
	public EntityLaserDelayed(World world) {
		super(world);
		addMotionHandler(new Rigidbody());
		this.setSize(0.7F, 0.2F);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(15, (byte) 0);
		dataWatcher.addObject(16, Integer.valueOf(0));
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
			
			if(player == null) {
				Entity e = worldObj.getEntityByID(dataWatcher.getWatchableObjectInt(16));
				if(e instanceof EntityPlayer) {
					player = (EntityPlayer) e;
				}
			}
		} else {
			if(isHit)
				dataWatcher.updateObject(15, Byte.valueOf((byte) (1 | (ticksAfterHit << 1))));
			else dataWatcher.updateObject(15, Byte.valueOf((byte) 0));
			
			dataWatcher.updateObject(16, player.getEntityId());
		}
	}
	
	@Override
	public void onUpdate() {
		syncData();
		if(isHit) {
			ticksAfterHit++;
		}
		
		super.onUpdate();
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 1;
	}
	
	public boolean isCharging() {
		return bornTick < DELAY_TICK;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		setDead();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}

	@Override
	public EntityPlayer getPlayer() {
		return player;
	}
}
