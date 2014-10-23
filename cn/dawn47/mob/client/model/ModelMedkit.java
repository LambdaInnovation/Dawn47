package cn.dawn47.mob.client.model;

import org.lwjgl.opengl.GL11;

import cn.dawn47.core.proxy.DWClientProps;
import cn.liutils.api.client.model.ModelBaseCustom;
import cn.liutils.api.client.model.ModelPart;

public class ModelMedkit extends ModelBaseCustom {
  //ModelPart box001;

  public ModelMedkit() {
    super(DWClientProps.MDL_MEDKIT);
    //box001 = new ModelPart(theModel, "Box001");
  }

  @Override
  public void doRenderModel(float amp, float head) {
    double height = 0.0;

    GL11.glPushMatrix();
    {
      //GL11.glDisable(GL11.GL_CULL_FACE);
      //GL11.glCullFace(GL11.GL_CULL_FACE);
      GL11.glCullFace(GL11.GL_BACK);
      //GL11.glTranslated(0, height, 0);

      //box001.render();
      theModel.renderAll();
      
      //GL11.glEnable(GL11.GL_CULL_FACE);

    }
    GL11.glPopMatrix();
  }



}
