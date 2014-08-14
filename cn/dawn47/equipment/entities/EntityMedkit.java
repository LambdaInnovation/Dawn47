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
package cn.dawn47.equipment.entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cn.dawn47.core.util.DWGenericUtils;

/**
 * @author WeAthFolD
 *
 */
public class EntityMedkit extends Entity {

	private boolean spawnItem = true;

	public EntityMedkit(World world) {
		super(world);
		this.setSize(1.0F, 0.25F);
	}

	public EntityMedkit(World world, EntityPlayer entityPlayer, double x, double y, double z, ItemStack itemStack) {
		super(world);
		this.setPosition(x, y, z);
		this.setSize(0.8F, 0.4F);
		if (entityPlayer.capabilities.isCreativeMode)
			spawnItem = false;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}

		if (++this.ticksExisted < 20 || worldObj.isRemote)
			return;

		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX - 0.15,
				posY - 0.3, posZ - 0.15, posX + 0.15, posY + 0.3, posZ + 0.15);
		List<EntityPlayer> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, box, DWGenericUtils.selectorPlayer);
		if (list == null || list.size() == 0)
			return;
		EntityPlayer player = list.get(0);
		applyEffects(player);

	}

	private void applyEffects(EntityPlayer player) {
		if(player.getHealth() < player.getMaxHealth()) {
			player.heal(8);
			this.playSound("dawn.entities.medshot", 0.5F, 1.0F);
			this.setDead();
		}
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		posX = nbt.getDouble("posX");
		posY = nbt.getDouble("posY");
		posZ = nbt.getDouble("posZ");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("posX", posX);
		nbt.setDouble("posY", posY);
		nbt.setDouble("posZ", posZ);
	}

	private static NBTTagCompound loadCompound(ItemStack stack) {
		if (stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound;
	}

}
