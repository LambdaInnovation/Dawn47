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
package cn.dawn47.core.client;

import cn.dawn47.weapon.client.entity.fx.EntitySpitFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
/**
 * @author WeAthFolD
 *
 */
@SideOnly(Side.CLIENT)
public class DWParticleHelper {

	 public static void spawnParticle(World world, String particleName, double posX, double posY, double posZ) {
		spawnParticle(world, particleName, posX, posY, posZ, 0F, 0F, 0F); 
	 }
	 
	 public static void spawnParticle(World world, String particleName, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		 EntityFX e = null;
		 if(particleName == "spit") {
			 e = new EntitySpitFX(world, posX, posY, posZ, motionX, motionY, motionZ);
		 }
		 if(e != null)
			 world.spawnEntityInWorld(e);
	 }
}
