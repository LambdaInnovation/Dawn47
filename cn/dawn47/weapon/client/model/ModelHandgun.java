package cn.dawn47.weapon.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import cn.liutils.api.client.model.IItemModel;

public class ModelHandgun extends ModelBase implements IItemModel {

	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Piece1;

	public ModelHandgun() {
		textureWidth = 128;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 0, 9);
		Shape1.addBox(0F, 2F, 0F, 9, 5, 2);
		Shape1.setRotationPoint(1F, -2F, 0F);
		Shape1.setTextureSize(128, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 20);
		Shape2.addBox(5F, 5F, 0F, 5, 1, 2);
		Shape2.setRotationPoint(0F, 0F, 0F);
		Shape2.setTextureSize(128, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 16, 16);
		Shape3.addBox(0F, 3F, 0F, 3, 5, 2);
		Shape3.setRotationPoint(1F, 1F, 0F);
		Shape3.setTextureSize(128, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0.418879F);
		Shape4 = new ModelRenderer(this, 25, 8);
		Shape4.addBox(0F, 0F, 0F, 1, 1, 2);
		Shape4.setRotationPoint(0F, 0F, 0F);
		Shape4.setTextureSize(128, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 33, 0);
		Shape5.addBox(0F, 0F, 0F, 1, 1, 2);
		Shape5.setRotationPoint(7.9F, -0.6F, 1F);
		Shape5.setTextureSize(128, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 1.570796F, 0.7853982F);
		Shape6 = new ModelRenderer(this, 42, 0);
		Shape6.addBox(0F, -1F, 0F, 1, 2, 1);
		Shape6.setRotationPoint(3F, 0F, -1F);
		Shape6.setTextureSize(128, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0.8028515F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 0, 0);
		Shape7.addBox(0F, -1F, 0F, 1, 1, 2);
		Shape7.setRotationPoint(-1F, 0F, 0F);
		Shape7.setTextureSize(128, 32);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Piece1 = new ModelRenderer(this, "Piece1");
		Piece1.setRotationPoint(0F, 0F, 0F);
		setRotation(Piece1, 0F, 0F, 0F);
		Piece1.mirror = true;
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

	@Override
	public void render(ItemStack is, float f5, float f) {
		setRotationAngles(is, 0, 0, 0, f);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
	}

	@Override
	public void setRotationAngles(ItemStack is, double posX, double posY,
			double posZ, float f) {
	}

}
