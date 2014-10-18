/**
 * 
 */
package cn.dawn47.mob.client.render;

import cn.dawn47.core.proxy.DWClientProps;
import cn.liutils.api.client.model.ModelBaseCustom;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * @author WeathFolD
 *
 */
public class RendererScoutRobot extends RenderLiving {

	/**
	 * @param par1ModelBase
	 * @param par2
	 */
	public RendererScoutRobot() {
		super(new ModelBaseCustom(DWClientProps.MDL_SCOUT_ROBOT).setScale(0.03F).setOffset(0, 1, 0), 1.5F);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.renderer.entity.Render#getEntityTexture(net.minecraft.entity.Entity)
	 */
	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return DWClientProps.SCOUT_ROBOT_TEX;
	}

}
