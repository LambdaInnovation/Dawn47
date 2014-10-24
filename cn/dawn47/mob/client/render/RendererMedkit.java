package cn.dawn47.mob.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cn.dawn47.core.proxy.DWClientProps;
import cn.liutils.api.client.model.ModelBaseCustom;
import cn.liutils.api.client.render.RenderModel;

public class RendererMedkit extends RenderModel {

  public RendererMedkit() {
    super(new ModelBaseCustom(DWClientProps.MDL_MEDKIT).setScale(0.03F).setOffset(0, 1, 0),DWClientProps.MEDKIT_TEX, 0.03F);
  }

  @Override
  protected ResourceLocation getEntityTexture(Entity var1) {
    return DWClientProps.MEDKIT_TEX;
  }
  
	@Override
	public void doRender(Entity entity, double par2, double par4, double par6,
			float par8, float par9) {
		GL11.glCullFace(GL11.GL_FRONT);
		super.doRender(entity, par2, par4, par6, par8, par9);
		GL11.glCullFace(GL11.GL_BACK);
	}

}
