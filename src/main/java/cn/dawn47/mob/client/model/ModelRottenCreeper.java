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

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * Rotten Creeper Model
 * 
 * @author WeAthFolD, Sabo917
 * 
 */
public class ModelRottenCreeper extends ModelBase {

	ModelRenderer head;
	ModelRenderer body;
	ModelRenderer legr;
	ModelRenderer neck;
	ModelRenderer legl;
	ModelRenderer feet;

	public ModelRottenCreeper() {
		textureWidth = 128;
		textureHeight = 256;

		head = new ModelRenderer(this, 49, 0);
		head.addBox(-5F, -10F, -4F, 10, 10, 9);
		head.setRotationPoint(0F, 3F, -2F);
		head.setTextureSize(128, 256);
		head.mirror = true;
		setRotation(head, 0.1858931F, 0F, 0F);
		body = new ModelRenderer(this, 51, 40);
		body.addBox(0F, 0F, -1F, 10, 19, 9);
		body.setRotationPoint(-5F, 5F, -4F);
		body.setTextureSize(128, 256);
		body.mirror = true;
		setRotation(body, 0.4089647F, 0F, 0F);
		legr = new ModelRenderer(this, 23, 0);
		legr.addBox(0F, 0F, 0F, 3, 20, 4);
		legr.setRotationPoint(-8F, 4F, -2F);
		legr.setTextureSize(128, 256);
		legr.mirror = true;
		setRotation(legr, -0.3346075F, 0F, 0F);
		neck = new ModelRenderer(this, 59, 26);
		neck.addBox(0F, 0F, 0F, 6, 3, 5);
		neck.setRotationPoint(-3F, 3F, -3F);
		neck.setTextureSize(128, 256);
		neck.mirror = true;
		setRotation(neck, 0.2230717F, 0F, 0F);
		legl = new ModelRenderer(this, 23, 0);
		legl.addBox(0F, 0F, 0F, 3, 20, 4);
		legl.setRotationPoint(5F, 4F, -2F);
		legl.setTextureSize(128, 256);
		legl.mirror = true;
		setRotation(legl, -0.3346075F, 0F, 0F);
		feet = new ModelRenderer(this, 0, 31);
		feet.addBox(0F, 0F, 0F, 6, 3, 13);
		feet.setRotationPoint(-3F, 21F, 3F);
		feet.setTextureSize(128, 256);
		feet.mirror = true;
		setRotation(feet, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(entity, f, f1, f2, f3, f4, f5);
		head.render(f5);
		body.render(f5);
		legr.render(f5);
		neck.render(f5);
		legl.render(f5);
		feet.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(Entity e, float f, float f1, float f2,
			float f3, float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
		float legRotation = MathHelper.cos(f * 1.0F) * 0.7F * f1;
		legl.rotateAngleX = -0.27F + legRotation;
		legr.rotateAngleX = -0.27F + MathHelper.cos(f * 1F + 0.002F) * 0.7F * f1;
		feet.rotateAngleX = legRotation * 0.2F;
	}

}
