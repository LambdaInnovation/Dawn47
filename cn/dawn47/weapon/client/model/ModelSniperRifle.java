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
public class ModelSniperRifle extends ModelBase implements IItemModel {

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
	ModelRenderer Shape15;
	ModelRenderer Shape16;
	ModelRenderer Shape17;
	ModelRenderer Shape18;
	ModelRenderer Shape19;
	ModelRenderer Shape20;
	ModelRenderer Shape21;
	ModelRenderer Shape22;

	public ModelSniperRifle() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 19, 16);
		Shape1.addBox(0F, 0F, 0F, 13, 3, 1);
		Shape1.setRotationPoint(-4F, 0F, 0F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 13);
		Shape2.addBox(0F, 0F, 0F, 22, 1, 1);
		Shape2.setRotationPoint(0F, 0.5F, 0F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 9);
		Shape3.addBox(0F, 0F, -2F, 1, 1, 2);
		Shape3.setRotationPoint(21F, 0.5F, 1F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0.0523599F, 0.3490659F, 0F);
		Shape4 = new ModelRenderer(this, 7, 8);
		Shape4.addBox(0F, 0F, 0F, 17, 2, 2);
		Shape4.setRotationPoint(-8F, 0F, 1F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, -0.7853982F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 7, 8);
		Shape5.addBox(0F, 0F, 0F, 17, 2, 2);
		Shape5.setRotationPoint(-8F, 0F, 0F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, -0.7853982F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 0, 9);
		Shape6.addBox(0F, 0F, 0F, 1, 1, 2);
		Shape6.setRotationPoint(21F, 0.5F, 0F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, -0.0523599F, -0.3490659F, 0F);
		Shape7 = new ModelRenderer(this, 46, 9);
		Shape7.addBox(0F, 0F, 0F, 8, 2, 1);
		Shape7.setRotationPoint(-12F, 0F, 0F);
		Shape7.setTextureSize(64, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0.0017453F);
		Shape8 = new ModelRenderer(this, 27, 29);
		Shape8.addBox(0F, 0F, 0F, 8, 2, 1);
		Shape8.setRotationPoint(-3F, 1F, 0F);
		Shape8.setTextureSize(64, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0.0174533F, 0F, 1.896109F);
		Shape9 = new ModelRenderer(this, 28, 26);
		Shape9.addBox(0F, 0F, 0F, 3, 1, 1);
		Shape9.setRotationPoint(-5F, 5F, 0F);
		Shape9.setTextureSize(64, 32);
		Shape9.mirror = true;
		setRotation(Shape9, 0F, 0F, 0F);
		Shape10 = new ModelRenderer(this, 42, 26);
		Shape10.addBox(-4F, 0F, 0F, 3, 1, 1);
		Shape10.setRotationPoint(-9.5F, 6F, 0F);
		Shape10.setTextureSize(64, 32);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, -2.792527F);
		Shape11 = new ModelRenderer(this, 11, 23);
		Shape11.addBox(0F, 0F, 0F, 10, 1, 1);
		Shape11.setRotationPoint(-3F, 5F, 0F);
		Shape11.setTextureSize(64, 32);
		Shape11.mirror = true;
		setRotation(Shape11, 0.0174533F, 0F, -0.2974289F);
		Shape12 = new ModelRenderer(this, 50, 16);
		Shape12.addBox(0F, 0F, 0F, 1, 7, 2);
		Shape12.setRotationPoint(-9F, 0F, -0.5F);
		Shape12.setTextureSize(64, 32);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelRenderer(this, 57, 16);
		Shape13.addBox(0F, 0F, 0F, 1, 6, 2);
		Shape13.setRotationPoint(-12F, 0F, -0.5F);
		Shape13.setTextureSize(64, 32);
		Shape13.mirror = true;
		setRotation(Shape13, 0F, 0F, -0.0174533F);
		Shape14 = new ModelRenderer(this, 57, 25);
		Shape14.addBox(0F, 0F, 0F, 2, 1, 1);
		Shape14.setRotationPoint(-11F, 4F, -0.25F);
		Shape14.setTextureSize(64, 32);
		Shape14.mirror = true;
		setRotation(Shape14, 0.7853982F, 0F, 0F);
		Shape15 = new ModelRenderer(this, 7, 16);
		Shape15.addBox(0F, 0F, 0F, 1, 2, 1);
		Shape15.setRotationPoint(8F, 2F, 0F);
		Shape15.setTextureSize(64, 32);
		Shape15.mirror = true;
		setRotation(Shape15, 0.0174533F, 0F, 0.5205006F);
		Shape16 = new ModelRenderer(this, 0, 20);
		Shape16.addBox(0F, 0F, 0F, 8, 1, 1);
		Shape16.setRotationPoint(7.2F, 3F, -1F);
		Shape16.setTextureSize(64, 32);
		Shape16.mirror = true;
		setRotation(Shape16, 0F, 0.0523599F, 0F);
		Shape17 = new ModelRenderer(this, 0, 20);
		Shape17.addBox(0F, 0F, 0F, 8, 1, 1);
		Shape17.setRotationPoint(7.2F, 3F, 1F);
		Shape17.setTextureSize(64, 32);
		Shape17.mirror = true;
		setRotation(Shape17, 0F, -0.0523599F, 0F);
		Shape18 = new ModelRenderer(this, 14, 3);
		Shape18.addBox(0F, 0F, 0F, 5, 2, 2);
		Shape18.setRotationPoint(1F, -1F, -0.9F);
		Shape18.setTextureSize(64, 32);
		Shape18.mirror = true;
		setRotation(Shape18, 0.7853982F, 0F, 0F);
		Shape19 = new ModelRenderer(this, 40, 3);
		Shape19.addBox(0F, 0F, 0F, 2, 2, 2);
		Shape19.setRotationPoint(-5F, -1F, -0.9F);
		Shape19.setTextureSize(64, 32);
		Shape19.mirror = true;
		setRotation(Shape19, 0.7853982F, 0F, 0F);
		Shape20 = new ModelRenderer(this, 29, 4);
		Shape20.addBox(0F, 0F, 0F, 4, 1, 1);
		Shape20.setRotationPoint(-3F, -1.5F, 0F);
		Shape20.setTextureSize(64, 32);
		Shape20.mirror = true;
		setRotation(Shape20, 0F, 0F, 0F);
		Shape21 = new ModelRenderer(this, 7, 5);
		Shape21.addBox(0F, 0F, 0F, 2, 1, 1);
		Shape21.setRotationPoint(4F, 0F, 1F);
		Shape21.setTextureSize(64, 32);
		Shape21.mirror = true;
		setRotation(Shape21, 0.7853982F, 0F, 0F);
		Shape22 = new ModelRenderer(this, 7, 5);
		Shape22.addBox(0F, 0F, 0F, 2, 1, 1);
		Shape22.setRotationPoint(4F, 0F, -1.4F);
		Shape22.setTextureSize(64, 32);
		Shape22.mirror = true;
		setRotation(Shape22, 0.7853982F, 0F, 0F);
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
		Shape15.render(f5);
		Shape16.render(f5);
		Shape17.render(f5);
		Shape18.render(f5);
		Shape19.render(f5);
		Shape20.render(f5);
		Shape21.render(f5);
		Shape22.render(f5);
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
		Shape15.render(f5);
		Shape16.render(f5);
		Shape17.render(f5);
		Shape18.render(f5);
		Shape19.render(f5);
		Shape20.render(f5);
		Shape21.render(f5);
		Shape22.render(f5);
	}

	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {
		// TODO Auto-generated method stub
		
	}

}
