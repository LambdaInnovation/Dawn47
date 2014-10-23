package cn.dawn47.mob.client.render;

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

}
