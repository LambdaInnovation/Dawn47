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
package cn.dawn47.weapon.client.render;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.weapon.EntityLaser;
import cn.liutils.api.client.util.RenderUtils;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * @author WeAthFolD
 *
 */
public class RenderLaser extends Render {

	public static double WIDTH = 0.09F;
	private static Tessellator tessellator = Tessellator.instance;

	public RenderLaser() {
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {

		EntityLaser gauss = (EntityLaser) par1Entity;
		
		
		tessellator = Tessellator.instance;

		GL11.glPushMatrix();

		double d = gauss.distanceToRender;
		Vec3 v1 = RenderUtils.newV3(0.05, 0, -WIDTH), v2 = RenderUtils.newV3(0.05, 0, WIDTH), v3 = RenderUtils.newV3(
				d, 0, WIDTH), v4 = RenderUtils.newV3(d, 0, -WIDTH),

		v5 = RenderUtils.newV3(0.05, WIDTH, 0), v6 = RenderUtils.newV3(0.05, -WIDTH, 0), v7 = RenderUtils.newV3(d,
				-WIDTH, 0), v8 = RenderUtils.newV3(d, WIDTH, 0);

		// Translations and rotations
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		bindTexture(DWClientProps.LASERRAY_TEXTURE_PATH);
		GL11.glRotatef(90.0F - gauss.rotationYaw, 0.0F, -1.0F, 0.0F); // 左右旋转
		GL11.glRotatef(gauss.rotationPitch, 0.0F, 0.0F, 1.0F); // 上下旋转
		GL11.glTranslatef(0, 0.2F, 0.1F);
		GL11.glRotatef(7.5F, -1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0, -0.4F, 0);
		// drawing>)
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		tessellator.startDrawingQuads();
		tessellator.setBrightness(15728880);
		
		addVertex(v1, 0, 0);
		addVertex(v2, 1, 0);
		addVertex(v3, 1, d);
		addVertex(v4, 0, d);
		
		addVertex(v5, 0, 0);
		addVertex(v6, 1, 0);
		addVertex(v7, 1, d);
		addVertex(v8, 0, d);
		
		tessellator.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	protected void addVertex(Vec3 vec3, double texU, double texV) {
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
