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
package cn.dawn47.equipment.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.dawn47.core.item.DWGenericItem;

/**
 * @author WeAthFolD
 *
 */
public class ItemEntityPlacer extends DWGenericItem {

	private Class<? extends Entity> entityClass;
	
	/**
	 * @param par1
	 */
	public ItemEntityPlacer(int par1, Class<? extends Entity> entity) {
		super(par1);
		entityClass = entity;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		if (!par3World.isRemote)
			spawnEntityAt(par1ItemStack, par3World, par2EntityPlayer, par4, par5, par6, par7);
		if (!par2EntityPlayer.capabilities.isCreativeMode) {
			par1ItemStack.splitStack(1);
		}
		return true;
	}

	public void spawnEntityAt(ItemStack itemStack, World par0World,
			EntityPlayer player, int par1, int par2, int par3, int side) {
		Entity entity = null;
		double x = par1 + 0.5, y = par2 + 0.5, z = par3 + 0.5;
		if (side == 0) {
			return;
		} else if (side == 1) {
			y += 0.8;
		} else if (side == 2) {
			z -= 0.8;
		} else if (side == 3) {
			z += 0.8;
		} else if (side == 4) {
			x -= 0.8;
		} else if (side == 5) {
			x += 0.8;
		}
		try {
			entity = entityClass.getConstructor(World.class).newInstance(par0World);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		entity.setPosition(x, y, z);
		par0World.spawnEntityInWorld(entity);
	}

}
