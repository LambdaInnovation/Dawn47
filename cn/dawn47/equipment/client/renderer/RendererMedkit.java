package cn.dawn47.equipment.client.renderer;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.equipment.entities.EntityMedkit;
import cn.liutils.api.render.model.ModelBaseCustom;
import cn.liutils.template.client.render.entity.RenderModel;

public class RendererMedkit extends RenderModel {

	public RendererMedkit() {
		super(new ModelBaseCustom(DWClientProps.MDL_MEDKIT).setScale(0.03F)
				.setOffset(0, 1, 0), DWClientProps.MEDKIT_TEX, 0.03F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return DWClientProps.MEDKIT_TEX;
	}

	@Override
	public void doRender(Entity entity, double par2, double par4, double par6,
			float par8, float par9) {
		EntityMedkit medkit = (EntityMedkit) entity; // 转型成medkit实例
		int side = medkit.getSide();
		GL11.glCullFace(GL11.GL_FRONT);
		GL11.glPushMatrix();
		{
			bindTexture(texture);
			GL11.glTranslatef((float) par2, (float) par4 + 2 * entity.height,
					(float) par6);
			GL11.glTranslatef(offsetX, offsetY, offsetZ);
			
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
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F,
					0.0625F * modelScale);
		}
		GL11.glPopMatrix();
		GL11.glCullFace(GL11.GL_BACK);
	}

}
