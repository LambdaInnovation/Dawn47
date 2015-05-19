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

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cn.annoreg.core.RegistrationClass;
import cn.annoreg.mc.RegEntity;
import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.mob.client.render.RendererDrone;
import cn.liutils.template.entity.LIEntityMob;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
@RegistrationClass
@RegEntity
@RegEntity.HasRender
public class EntityDrone extends LIEntityMob {
	
	@RegEntity.Render
	@SideOnly(Side.CLIENT)
	public static RendererDrone renderer;
	
	public int tickCharging = 0;
	public Entity jumpEntity;
	public int style;
	private int tID = 0;
	
	public boolean updated = false;

	/**
	 * @param par1World
	 */
	public EntityDrone(World par1World) {
		super(par1World);
		style = rand.nextInt(3);
		this.setSize(0.8F, 0.8F);
	}
	
	@Override
	public void entityInit() {
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(18, Byte.valueOf((byte) 0));
		super.entityInit();
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.EntityLiving#getMaxHealth()
	 */
	@Override
	public double getMaxHealth2() {
		return 10D;
	}
	
    @Override
	protected boolean isMovementCeased()
    {
        return tickCharging > 0;
    }
	
	@Override
	public void onUpdate() {
		if(worldObj.isRemote) {
			style = dataWatcher.getWatchableObjectByte(18);
			updated = true;
		} else {
			dataWatcher.updateObject(18, Byte.valueOf((byte) style));
		}
		
		if(tickCharging > 0) {
			--tickCharging;
			if(tickCharging == 0 && jumpEntity != null) {
				Entity entity = jumpEntity;
				double d0 = entity.posX - this.posX;
                double d1 = entity.posZ - this.posZ;
                float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
                this.motionX = d0 / f2 * 0.5D * 1.2D + this.motionX * 0.20000000298023224D;
                this.motionZ = d1 / f2 * 0.5D * 1.2D + this.motionZ * 0.20000000298023224D;
                this.motionY = 0.6;
                jumpEntity = null;
			}
		}
		if(worldObj.isRemote) {
			if(tickCharging == 0)
				tickCharging =  dataWatcher.getWatchableObjectByte(16);
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) tickCharging));
		}
		super.onUpdate();
	}
	
    public boolean attackEntityFrom(DamageSource ds, int par2)
    {
    	if(ds.equals(DamageSource.fall)) return false;
    	return super.attackEntityFrom(ds, par2);
    }
	
    @Override
	protected void attackEntity(Entity entity, float dist)
    {
    	if(dist > 20.0)
    		entity = null;
    	else {
    		if(dist > 2.0F && dist < 6.0F && this.rand.nextInt(10) == 0) {
    			if(tickCharging <= 0) {
    				jumpEntity = entity;
    				tickCharging = 30;
    				this.motionX *= 0.2;
    				this.motionY *= 0.2;
    			}
    		} else {
    			super.attackEntity(entity, dist);
    		}
    	}
    }
    
    @Override
	public boolean attackEntityAsMob(Entity par1Entity)
    {
    	if(style == 1 && par1Entity instanceof EntityPlayer) {
    		((EntityPlayer)par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 100));
    	}
    	return super.attackEntityAsMob(par1Entity);
    }

	@Override
	protected double getFollowRange() {
		return 15;
	}

	@Override
	protected double getMoveSpeed() {
		return 3;
	}

	@Override
	protected double getKnockBackResistance() {
		return 5;
	}

	@Override
	protected double getAttackDamage() {
		return 4 + style == 2 ? 4 : 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getTexture() {
		return DWResources.DRONE_PATH[tID];
	}

}
