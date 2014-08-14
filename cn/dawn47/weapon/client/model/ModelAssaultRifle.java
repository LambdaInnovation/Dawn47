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
package cn.dawn47.weapon.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import cn.liutils.api.client.model.IItemModel;

/**
 * @author WeAthFolD
 * 
 */
public class ModelAssaultRifle extends ModelBase implements IItemModel {

	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12;

	public ModelAssaultRifle() {
		textureWidth = 32;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 9, 13);
		Shape1.addBox(0F, 0F, 0F, 1, 3, 5);
		Shape1.setRotationPoint(0F, 0F, 1F);
		Shape1.setTextureSize(32, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape1.mirror = false;
		
		Shape2 = new ModelRenderer(this, 22, 0);
		Shape2.addBox(1F, 1F, -3F, 1, 3, 4);
		Shape2.setRotationPoint(-1F, 0F, 0F);
		Shape2.setTextureSize(32, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0.0174533F, 0F);
		Shape2.mirror = false;
		
		Shape3 = new ModelRenderer(this, 6, 23);
		Shape3.addBox(0F, -1F, -1F, 1, 1, 6);
		Shape3.setRotationPoint(0F, 0F, -3F);
		Shape3.setTextureSize(32, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape3.mirror = false;
		
		Shape4 = new ModelRenderer(this, 18, 9);
		Shape4.addBox(0F, 0F, -5F, 1, 1, 3);
		Shape4.setRotationPoint(0F, 0F, 0F);
		Shape4.setTextureSize(32, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0.0174533F, 0F);

		Shape5 = new ModelRenderer(this, 1, 16);
		Shape5.addBox(0F, 0F, 6F, 1, 2, 3);
		Shape5.setRotationPoint(0F, 0F, 0F);
		Shape5.setTextureSize(32, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape5.mirror = false;
		
		Shape7 = new ModelRenderer(this, 6, 9);
		Shape7.addBox(0F, 0F, 9F, 1, 1, 3);
		Shape7.setRotationPoint(0F, 0F, 0F);
		Shape7.setTextureSize(32, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		
		Shape8 = new ModelRenderer(this, 0, 0);
		Shape8.addBox(0F, 1F, -10F, 1, 3, 6);
		Shape8.setRotationPoint(0F, -1F, -1F);
		Shape8.setTextureSize(32, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0.0872665F, 0F, 0F);
		
		Shape9 = new ModelRenderer(this, 21, 23);
		Shape9.addBox(0F, 0F, 0F, 3, 5, 2);
		Shape9.setRotationPoint(-2F, 0F, 0F);
		Shape9.setTextureSize(32, 32);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, -0.1745329F, 0.2617994F);
		
		Shape10 = new ModelRenderer(this, 26, 8);
		Shape10.addBox(0F, 3F, -3F, 1, 3, 2);
		Shape10.setRotationPoint(0F, 0F, 0F);
		Shape10.setTextureSize(32, 32);
		Shape10.mirror = true;
		setRotation(Shape10, -0.2094395F, 0F, 0F);
		
		Shape11 = new ModelRenderer(this, 0, 10);
		Shape11.addBox(1F, 0F, 1F, 1, 2, 1);
		Shape11.setRotationPoint(0.5F, 0.2F, 10F);
		Shape11.setTextureSize(32, 32);
		Shape11.mirror = true;
		setRotation(Shape11, -1.570796F, 0F, 0.7853982F);
		
		Shape12 = new ModelRenderer(this, 3, 13);
		Shape12.addBox(0F, 0F, 0F, 1, 1, 2);
		Shape12.setRotationPoint(0.5F, -1F, 2F);
		Shape12.setTextureSize(32, 32);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0.7853982F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
		Shape9.render(f5);
		Shape10.render(f5);
		Shape11.render(f5);
		Shape12.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void render(ItemStack is, float f5, float f) {
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
		Shape9.render(f5);
		Shape10.render(f5);
		Shape11.render(f5);
		Shape12.render(f5);
	}

	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {

	}

}
