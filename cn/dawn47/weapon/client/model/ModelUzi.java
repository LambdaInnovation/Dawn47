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
public class ModelUzi extends ModelBase implements IItemModel {

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

	public ModelUzi() {
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 12, 11);
		Shape1.addBox(0F, 0F, 0F, 3, 3, 8);
		Shape1.setRotationPoint(0F, 0F, -4F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 1, 11);
		Shape2.addBox(0F, 0F, 0F, 1, 1, 4);
		Shape2.setRotationPoint(1F, 1F, -8F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 3, 17);
		Shape3.addBox(0F, 0F, 0F, 3, 4, 1);
		Shape3.setRotationPoint(0F, 0F, -6F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0.3346075F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 44, 13);
		Shape4.addBox(0F, 0F, 0F, 3, 4, 1);
		Shape4.setRotationPoint(0F, 0F, 4F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0.4089647F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 1, 23);
		Shape5.addBox(0F, 0F, 0F, 3, 1, 4);
		Shape5.setRotationPoint(0F, 3F, -5F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0.3717861F, 0.0017453F, 0F);
		Shape6 = new ModelRenderer(this, 19, 5);
		Shape6.addBox(0F, 0F, 0F, 3, 2, 2);
		Shape6.setRotationPoint(0F, -1F, 0F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, -0.669215F, 0F, 0.0017453F);
		Shape7 = new ModelRenderer(this, 24, 23);
		Shape7.addBox(0F, 0F, 0F, 1, 5, 2);
		Shape7.setRotationPoint(1F, 2F, -1F);
		Shape7.setTextureSize(64, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0.3490659F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 31, 28);
		Shape8.addBox(0F, 0F, 0F, 1, 1, 2);
		Shape8.setRotationPoint(1F, 6F, 0F);
		Shape8.setTextureSize(64, 32);
		Shape8.mirror = true;
		setRotation(Shape8, 0F, 0.0017453F, 0F);
		Shape9 = new ModelRenderer(this, 35, 13);
		Shape9.addBox(0F, 0F, 0F, 1, 2, 3);
		Shape9.setRotationPoint(1F, 1F, 2F);
		Shape9.setTextureSize(64, 32);
		Shape9.mirror = true;
		setRotation(Shape9, 0.2230717F, 0F, 0F);
		Shape10 = new ModelRenderer(this, 17, 23);
		Shape10.addBox(0F, 0F, 0F, 1, 1, 2);
		Shape10.setRotationPoint(1F, 3F, -2F);
		Shape10.setTextureSize(64, 32);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0.0017453F, 0F);
	}

	
	@Override
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
	}

	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {}

}
