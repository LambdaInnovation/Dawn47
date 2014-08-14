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
package cn.dawn47.core.item;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import cn.dawn47.DawnMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author WeAthFolD
 *
 */
public class DWMobSpawner extends DWGenericItem {
	
	protected Class<? extends Entity> spawnEntity = EntityCreeper.class;
	private Constructor<? extends Entity> constructor;
	
	public DWMobSpawner(int par1) {
		super(par1);
		try {
			constructor = EntityCreeper.class.getConstructor(World.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param par1
	 */
	public DWMobSpawner(int par1, Class<? extends Entity> entityToSpawn, String name) {
		super(par1);
		this.setIAndU(name);
		spawnEntity = entityToSpawn;
		try {
			constructor = spawnEntity.getConstructor(World.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y,
    		int z, int side, float xOffset, float yOffset, float zOffset)
    {
    	if(!world.isRemote) {
    		double posX = x + 0.5, posY = y + 0.5, posZ = z + 0.5;
    		ForgeDirection dir = ForgeDirection.values()[side];
    		posX += dir.offsetX * 1.5;
    		posY += dir.offsetY * 1.5;
    		posZ += dir.offsetZ * 1.5;
    		if(constructor != null) {
    			try {
					Entity e = constructor.newInstance(world);
					e.setPositionAndRotation(posX, posY, posZ, player.rotationYaw, player.rotationPitch);
					world.spawnEntityInWorld(e);
				} catch (Exception e) {
					DawnMod.log.severe("Fail to find the default constructor for entity " + spawnEntity + " in DWMobSpawner");
					e.printStackTrace();
				}
    			
    		}
    	}
        return false;
    }
    
}
