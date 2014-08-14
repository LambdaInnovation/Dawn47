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
package cn.dawn47.weapon.client.entity.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
@SideOnly(Side.CLIENT)
public class EntitySpitFX extends EntityFX {

	/**
	 * @param par1World
	 * @param par2
	 * @param par4
	 * @param par6
	 */
	public EntitySpitFX(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	/**
	 * @param par1World
	 * @param par2
	 * @param par4
	 * @param par6
	 * @param par8
	 * @param par10
	 * @param par12
	 */
	public EntitySpitFX(World par1World, double par2, double par4, double par6,
			double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += par8 * 0.4D;
        this.motionY += par10 * 0.4D;
        this.motionZ += par12 * 0.4D;
        this.particleRed = this.particleGreen = this.particleBlue = (float)(Math.random() * 0.30000001192092896D + 0.6000000238418579D);
        this.particleScale *= 0.75F;
        this.particleMaxAge = (int) (30 + rand.nextGaussian() * 4.0);
        this.noClip = false;
	}
	
	   /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        if(!this.onGround) {
        	this.motionY -= 0.04F;
        }
    }
	
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
       //NOPE
    }

}
