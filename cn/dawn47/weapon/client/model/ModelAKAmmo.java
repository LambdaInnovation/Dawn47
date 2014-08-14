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

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * @author WeAthFolD
 *
 */
public class ModelAKAmmo extends ModelAK {

	/**
	 * 
	 */
	public ModelAKAmmo() {
		super();
		Shape7.setRotationPoint(0F, 0F, 0F);
		Shape14.setRotationPoint(-1F, 2.1F, 1.4F);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		Shape7.render(f5);
		Shape14.render(f5);
	}

	@Override
	public void render(ItemStack is, float f5, float f) {
		Shape7.render(f5);
		Shape14.render(f5);
	}

}
