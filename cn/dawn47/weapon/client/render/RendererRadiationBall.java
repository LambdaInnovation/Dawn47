/**
 * 
 */
package cn.dawn47.weapon.client.render;

import net.minecraft.entity.Entity;
import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.weapon.entity.EntityRadiationBall;
import cn.liutils.template.client.render.entity.RenderIcon;

/**
 * @author WeathFolD
 *
 */
public class RendererRadiationBall extends RenderIcon {

	public RendererRadiationBall() {
		super(DWClientProps.EFFECT_RADBALL);
		setHasLight(false);
		setSize(1.5F);
		setBlend(0.7F);
		setViewOptimize();
	}
	
	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		EntityRadiationBall rad = (EntityRadiationBall) par1Entity;
		icon = rad.isHit ? DWClientProps.EFFECT_RADBALL_END[rad.ticksAfterHit/3] : DWClientProps.EFFECT_RADBALL;
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}

}
