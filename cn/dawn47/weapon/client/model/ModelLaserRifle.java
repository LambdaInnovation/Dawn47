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
public class ModelLaserRifle extends ModelBase implements IItemModel {

	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12;
	ModelRenderer Shape13;
	ModelRenderer Shape14;

	public ModelLaserRifle() {
		textureWidth = 128;
		textureHeight = 128;

		Shape1 = new ModelRenderer(this, 34, 15);
		Shape1.addBox(0F, 0F, 0F, 3, 2, 7);
		Shape1.setRotationPoint(0F, 0F, 0F);
		Shape1.setTextureSize(128, 128);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape1.mirror = false;

		Shape2 = new ModelRenderer(this, 30, 27);
		Shape2.addBox(0F, 0F, 0F, 3, 3, 11);
		Shape2.setRotationPoint(0F, 3F, 1F);
		Shape2.setTextureSize(128, 128);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape2.mirror = false;

		Shape3 = new ModelRenderer(this, 38, 6);
		Shape3.addBox(0F, 1F, 0F, 3, 2, 4);
		Shape3.setRotationPoint(0F, 0F, 7F);
		Shape3.setTextureSize(128, 128);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);

		Shape4 = new ModelRenderer(this, 22, 18);
		Shape4.addBox(0F, 0F, 0F, 1, 4, 2);
		Shape4.setRotationPoint(1F, 2F, -1F);
		Shape4.setTextureSize(128, 128);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);

		Shape5 = new ModelRenderer(this, 0, 43);
		Shape5.addBox(1F, 1F, -2F, 1, 1, 2);
		Shape5.setRotationPoint(0F, 0F, 0F);
		Shape5.setTextureSize(128, 128);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);

		Shape6 = new ModelRenderer(this, 45, 0);
		Shape6.addBox(0F, 3F, -2F, 1, 1, 3);
		Shape6.setRotationPoint(0F, 0F, 0F);
		Shape6.setTextureSize(128, 128);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);

		Shape7 = new ModelRenderer(this, 35, 0);
		Shape7.addBox(2F, 3F, -2F, 1, 1, 3);
		Shape7.setRotationPoint(0F, 0F, 0F);
		Shape7.setTextureSize(128, 128);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);

		Shape8 = new ModelRenderer(this, 5, 10);
		Shape8.addBox(0F, 6F, 0F, 1, 1, 4);
		Shape8.setRotationPoint(1F, 0F, 1F);
		Shape8.setTextureSize(128, 128);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0F, 0F);

		Shape9 = new ModelRenderer(this, 58, 0);
		Shape9.addBox(3F, 3F, 8F, 1, 1, 3);
		Shape9.setRotationPoint(0F, 0F, 0F);
		Shape9.setTextureSize(128, 128);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);

		Shape10 = new ModelRenderer(this, 0, 29);
		Shape10.addBox(1F, 9.333333F, 1F, 1, 7, 2);
		Shape10.setRotationPoint(0F, 0F, 0F);
		Shape10.setTextureSize(128, 128);
		Shape10.mirror = true;
		setRotation(Shape10, 0.7853982F, -0.0090881F, 0F);

		Shape11 = new ModelRenderer(this, 0, 0);
		Shape11.addBox(1F, 3F, 12F, 1, 2, 6);
		Shape11.setRotationPoint(0F, 0F, 0F);
		Shape11.setTextureSize(128, 128);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);

		Shape12 = new ModelRenderer(this, 22, 0);
		Shape12.addBox(0F, 3F, 18F, 3, 7, 1);
		Shape12.setRotationPoint(0F, 0F, 0F);
		Shape12.setTextureSize(128, 128);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);

		Shape13 = new ModelRenderer(this, 0, 17);
		Shape13.addBox(0F, 8F, 11F, 3, 1, 7);
		Shape13.setRotationPoint(0F, 0F, 0F);
		Shape13.setTextureSize(128, 128);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, 0F);

		Shape14 = new ModelRenderer(this, 57, 5);
		Shape14.addBox(1F, 0F, 11F, 1, 2, 5);
		Shape14.setRotationPoint(0F, 1F, 0F);
		Shape14.setTextureSize(128, 128);
		Shape14.mirror = true;
		setRotation(Shape14, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
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
		Shape6.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
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
		
	}
}
