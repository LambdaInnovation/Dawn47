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

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import cn.annoreg.core.RegWithName;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEntity;
import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.mob.client.render.RenderRottenCreeper;
import cn.liutils.template.entity.LIEntityMob;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
@Registrant
@RegEntity
@RegWithName("RottenCreeper")
@RegEntity.HasRender
public class EntityRottenCreeper extends LIEntityMob {
	
	@SideOnly(Side.CLIENT)
	@RegEntity.Render
	public static RenderRottenCreeper renderer;
	
	int JUDGE_JUMP_TIME = 40;
	
	boolean jumped;
	int lastJumpTick;

	/**
	 * @param par1World
	 */
	public EntityRottenCreeper(World par1World) {
		super(par1World);
		
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(ticksExisted - lastJumpTick > JUDGE_JUMP_TIME)
			jumped = false;
		
		if(this.worldObj.isRemote && (!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ) || 
				!this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded)) {	
			
		} else if(!this.handleWaterMovement() && !this.handleLavaMovement() && !this.onGround && jumped){
			this.motionY += 0.07D;
		}
	}
	
    @Override
	protected void jump()
    {
    	this.motionY = 0.14;
        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);
        jumped = true;
        lastJumpTick = ticksExisted;
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
		return DWResources.ROTTEN_CREEPER_MOB;
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
