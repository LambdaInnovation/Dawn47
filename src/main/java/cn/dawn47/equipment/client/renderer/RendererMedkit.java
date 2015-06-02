package cn.dawn47.equipment.client.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.equipment.entities.EntityMedkit;

public class RendererMedkit extends Render {
	
	IModelCustom model = DWResources.loadModel("medkit");

	public RendererMedkit() {
	}
	
	@Override
	public void doRender(Entity entity, double par2, double par4, double par6,
			float par8, float par9) {
		EntityMedkit medkit = (EntityMedkit) entity; // 转型成medkit实例
		int side = medkit.getSide();
		GL11.glCullFace(GL11.GL_FRONT);
		GL11.glPushMatrix();
		{
			bindTexture(DWResources.MEDKIT_TEX);
			GL11.glTranslatef((float) par2, (float) par4 + 2 * entity.height,
					(float) par6);
			GL11.glTranslatef(0, 1, 0);
			
			//Transformation Sequence
			final double offsetA = 0.72, offsetB = -0.8;
			switch(side) {
			case 2: //-Z
				GL11.glTranslated(0, offsetB, -offsetA);
				GL11.glRotatef(90, 0, -1, 0);
				GL11.glRotatef(90, 0, 0, 1);
				break;
			case 3: //+Z
				GL11.glTranslated(0, offsetB, offsetA);
				GL11.glRotatef(90, 0, 1, 0);
				GL11.glRotatef(90, 0, 0, 1);
				break;
			case 4: //-X
				GL11.glTranslated(-offsetA, offsetB, 0);
				GL11.glRotatef(90, 0, 0, 1);
				break;
			case 5: //+X
				GL11.glTranslated(offsetA, offsetB, 0);
				GL11.glRotatef(90, 0, 0, -1);
				break;
			default:
				break;
			}
			
			GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
			
			final float scale = 0.03f;
			GL11.glScalef(-1.0F * scale, -1.0F * scale, 1.0F * scale);
			model.renderAll();
		}
		GL11.glPopMatrix();
		GL11.glCullFace(GL11.GL_BACK);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		// TODO Auto-generated method stub
		return null;
	}

}
