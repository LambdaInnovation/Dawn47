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
public class ModelAK extends ModelBase implements IItemModel {
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape6;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12;
	ModelRenderer Shape13;
	ModelRenderer Shape14;

	public ModelAK() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 9, 15);
		Shape1.addBox(0F, 0F, 0F, 1, 1, 15);
		Shape1.setRotationPoint(0F, 0F, -11F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0.7853982F);
		Shape2 = new ModelRenderer(this, 3, 19);
		Shape2.addBox(0F, 0F, 0F, 1, 1, 9);
		Shape2.setRotationPoint(0F, 1F, -5F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0.7853982F);
		Shape3 = new ModelRenderer(this, 0, 10);
		Shape3.addBox(-1F, 0F, 0F, 2, 3, 5);
		Shape3.setRotationPoint(0F, -0.3F, -1F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0.1047198F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 15, 5);
		Shape4.addBox(0F, 0F, 0F, 2, 2, 7);
		Shape4.setRotationPoint(0F, 0F, 0F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0.7853982F);
		Shape5 = new ModelRenderer(this, 27, 11);
		Shape5.addBox(-1F, 0F, 0F, 2, 3, 7);
		Shape5.setRotationPoint(0F, -0.1F, 4.466667F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 27, 22);
		Shape7.addBox(0F, 0F, 0F, 1, 6, 2);
		Shape7.setRotationPoint(-0.5F, 2F, 5.6F);
		Shape7.setTextureSize(64, 32);
		Shape7.mirror = true;
		setRotation(Shape7, -0.4363323F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 39, 22);
		Shape8.addBox(0F, 0F, 0F, 1, 4, 1);
		Shape8.setRotationPoint(-0.5F, 2F, 10F);
		Shape8.setTextureSize(64, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0.4712389F, 0F, 0.0174533F);
		Shape6 = new ModelRenderer(this, 39, 4);
		Shape6.addBox(0F, 0F, 0F, 1, 1, 6);
		Shape6.setRotationPoint(-0.5F, 0.2F, 11F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape9 = new ModelRenderer(this, 51, 21);
		Shape9.addBox(-1F, 0F, 0F, 2, 5, 1);
		Shape9.setRotationPoint(0F, -0.2F, 16F);
		Shape9.setTextureSize(64, 32);
		Shape9.mirror = true;
		setRotation(Shape9, 0.1570796F, 0F, 0F);
		Shape10 = new ModelRenderer(this, 42, 22);
		Shape10.addBox(0F, 0F, 0F, 1, 1, 6);
		Shape10.setRotationPoint(-0.5F, 4.8F, 11F);
		Shape10.setTextureSize(64, 32);
		Shape10.mirror = true;
		setRotation(Shape10, 0.3490659F, 0F, 0F);
		Shape11 = new ModelRenderer(this, 46, 12);
		Shape11.addBox(0F, 0F, 0F, 1, 1, 7);
		Shape11.setRotationPoint(0F, -0.5F, 9.8F);
		Shape11.setTextureSize(64, 32);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0.7853982F);
		Shape12 = new ModelRenderer(this, 9, 25);
		Shape12.addBox(0F, 0F, 0F, 1, 2, 0);
		Shape12.setRotationPoint(-0.5F, -1.2F, -10F);
		Shape12.setTextureSize(64, 32);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelRenderer(this, 34, 22);
		Shape13.addBox(0F, 0F, 0F, 0, 2, 2);
		Shape13.setRotationPoint(0F, 2F, 9F);
		Shape13.setTextureSize(64, 32);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);
		Shape14 = new ModelRenderer(this, 27, 22);
		Shape14.addBox(0F, -6F, 0F, 1, 6, 2);
		Shape14.setRotationPoint(-1.5F, 4.1F, 7F);
		Shape14.setTextureSize(64, 32);
		Shape14.mirror = true;
		setRotation(Shape14, 2.722714F, 0F, 0F);
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
		Shape6.render(f5);
		Shape9.render(f5);
		Shape10.render(f5);
		Shape11.render(f5);
		Shape12.render(f5);
		Shape13.render(f5);
		Shape14.render(f5);
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
		Shape6.render(f5);
		Shape9.render(f5);
		Shape10.render(f5);
		Shape11.render(f5);
		Shape12.render(f5);
		Shape13.render(f5);
		Shape14.render(f5);
	}

	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {
		// TODO Auto-generated method stub
		
	}

}
