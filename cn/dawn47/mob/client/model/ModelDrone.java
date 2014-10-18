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
package cn.dawn47.mob.client.model;

import org.lwjgl.opengl.GL11;

import cn.dawn47.mob.entity.EntityDrone;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * @author WeAthFolD
 * 
 */
public class ModelDrone extends ModelBase {

	// fields
	ModelRenderer body;
	ModelRenderer head;
	ModelRenderer neck;
	ModelRenderer legfr1;
	ModelRenderer legfr2;
	ModelRenderer legfl2;
	ModelRenderer legfl1;
	ModelRenderer legbr1;
	ModelRenderer legbr2;
	ModelRenderer legbl1;
	ModelRenderer legbl2;

	public ModelDrone() {
		textureWidth = 128;
		textureHeight = 64;

		  
	      body = new ModelRenderer(this, 45, 0);
	      body.addBox(0F, 0F, 0F, 3, 3, 3);
	      body.setRotationPoint(-2F, 17F, 0F);
	      body.setTextureSize(128, 64);
	      body.mirror = true;
	      head = new ModelRenderer(this, 24, 0);
	      head.addBox(-1.5F, -3F, -4F, 3, 3, 5);
	      head.setRotationPoint(-0.5F, 17F, 2.4F);
	      head.setTextureSize(128, 64);
	      head.mirror = true;
	      setRotation(head, -0.2579783F, 0.0523599F, 0.0174533F);
	      neck = new ModelRenderer(this, 0, 0);
	      neck.addBox(0F, -2F, 0F, 1, 1, 1);
	      neck.setRotationPoint(-1F, 18F, 1F);
	      neck.setTextureSize(128, 64);
	      neck.mirror = true;
	      setRotation(neck, 0F, 0F, 0F);
	      legfr1 = new ModelRenderer(this, 10, 0);
	      legfr1.addBox(-1F, -7F, -1F, 1, 7, 1);
	      legfr1.setRotationPoint(-1F, 20F, 1F);
	      legfr1.setTextureSize(128, 64);
	      legfr1.mirror = true;
	      setRotation(legfr1, 0.4492416F, 0F, -0.6363601F);
	      legfr2 = new ModelRenderer(this, 16, 0);
	      legfr2.addBox(0F, 0F, 0F, 1, 10, 1);
	      legfr2.setRotationPoint(-5F, 14.53333F, -3F);
	      legfr2.setTextureSize(128, 64);
	      legfr2.mirror = true;
	      setRotation(legfr2, 0.0083652F, -0.671487F, 0.2463978F);
	      legfl2 = new ModelRenderer(this, 16, 0);
	      legfl2.addBox(0F, 0F, -1F, 1, 10, 1);
	      legfl2.setRotationPoint(4F, 14F, -1F);
	      legfl2.setTextureSize(128, 64);
	      legfl2.mirror = true;
	      setRotation(legfl2, -0.0083601F, 0.5493075F, -0.2289523F);
	      legfl1 = new ModelRenderer(this, 10, 0);
	      legfl1.addBox(0F, -7F, 0F, 1, 7, 1);
	      legfl1.setRotationPoint(0F, 20F, 0F);
	      legfl1.setTextureSize(128, 64);
	      legfl1.mirror = true;
	      setRotation(legfl1, 0.2921681F, 0F, 0.5948606F);
	      legbr1 = new ModelRenderer(this, 10, 0);
	      legbr1.addBox(0F, -6F, 0F, 1, 7, 1);
	      legbr1.setRotationPoint(-2F, 20F, 2F);
	      legbr1.setTextureSize(128, 64);
	      legbr1.mirror = true;
	      setRotation(legbr1, -0.4583297F, 0F, -0.6724303F);
	      legbr2 = new ModelRenderer(this, 16, 0);
	      legbr2.addBox(0F, 0F, -1F, 1, 10, 1);
	      legbr2.setRotationPoint(-5F, 15F, 5.466667F);
	      legbr2.setTextureSize(128, 64);
	      legbr2.mirror = true;
	      setRotation(legbr2, 0.0083652F, 0.6714805F, 0.2463978F);
	      legbl1 = new ModelRenderer(this, 10, 0);
	      legbl1.addBox(0F, -7F, 0F, 1, 7, 1);
	      legbl1.setRotationPoint(0F, 20F, 1F);
	      legbl1.setTextureSize(128, 64);
	      legbl1.mirror = true;
	      setRotation(legbl1, -0.4932362F, -0.0139626F, 0.6410071F);
	      legbl2 = new ModelRenderer(this, 16, 0);
	      legbl2.addBox(0F, 0F, -1F, 1, 10, 1);
	      legbl2.setRotationPoint(3F, 15F, 5F);
	      legbl2.setTextureSize(128, 64);
	      legbl2.mirror = true;
	      setRotation(legbl2, 0.0083652F, -0.6714805F, -0.2464056F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		EntityDrone base = (EntityDrone) entity;
		GL11.glPushMatrix();
		body.render(f5);
		head.render(f5);
		neck.render(f5);
		GL11.glPopMatrix();
		legfr1.render(f5);
		legfr2.render(f5);
		legfl2.render(f5);
		legfl1.render(f5);
		legbr1.render(f5);
		legbr2.render(f5);
		legbl1.render(f5);
		legbl2.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity e) {
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
		
		float r1 = MathHelper.cos(f * 1F + 0.002F) * 0.7F * f1, r2 = MathHelper.sin(f * 1F + 0.002F) * 0.7F * f1;
		if(!e.onGround) r1 = r2 = 0F;
		legfl2.rotateAngleZ = r1 - 0.2289523F;
		legbr2.rotateAngleZ = r1 + 0.2463978F;
		
		legfr2.rotateAngleZ = r2 + 0.2463978F;
		legbl2.rotateAngleZ = r2 - 0.2464056F;
	}

}
