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
package cn.dawn47.weapon.client.render;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.weapon.client.model.ModelSniperRifle;
import cn.dawn47.weapon.wpn.Weapon_SniperRifle;
import cn.weaponmod.api.client.render.RenderModelBulletWeapon;
import cn.weaponmod.api.information.InformationBullet;

/**
 * @author WeAthFolD
 *
 */
public class RenderSniperRifle extends RenderModelBulletWeapon {

	/**
	 * @param mdl
	 * @param type
	 * @param texture
	 * @param muzzleflashPath
	 */
	public RenderSniperRifle() {
		super(new ModelSniperRifle(), DWItems.sniper_rifle, DWClientProps.SNIPER_RIFLE_TEXTURE_PATH, DWClientProps.SG_MUZZLEFLASH);
		setUpliftRatio(0.3F, 0.04F);
		setMuzzleflashOffset(1.0F, .0F, .0F);
		setRotation(0F, -90F, 0F);
		setOffset(.0F, .25F, .0F);
		setEquipOffset(.95F, .0F, .0F);
		setInvOffset(0.746F, 2.364F);
		setInvScale(0.638F);
		setInvRotation(-39.334F, -69.474F, 1.354F);
		setInventorySpin(false);
	}
	
	@Override
	public void doRenderEquipped(ItemStack item, RenderBlocks render,
			EntityLivingBase entity, boolean left, ItemRenderType type) {
			
		Weapon_SniperRifle weaponType = (Weapon_SniperRifle) DWItems.sniper_rifle;
		InformationBullet inf = (InformationBullet) weaponType.getInformation(item, entity.worldObj);
		boolean dorender = true;
		boolean isZooming = weaponType.isItemZooming(item, null, (EntityPlayer) entity);
		if(inf != null) {
			int dt = inf.getDeltaTick(false);
			if(dt < 10) {
				if(!isZooming)
					dt = 10 - dt;
				GL11.glTranslatef(-dt * 0.03F, dt * 0.03F, -dt * 0.11F);
			} else if(isZooming)
				dorender = false;
		}
		if(dorender)
			super.doRenderEquipped(item, render, entity, left, type);
	}

}
