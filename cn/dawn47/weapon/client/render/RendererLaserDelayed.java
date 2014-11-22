/**
 * 
 */
package cn.dawn47.weapon.client.render;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.weapon.entity.EntityLaserDelayed;
import cn.liutils.api.client.render.RenderIcon;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author FolD
 *
 */
public class RendererLaserDelayed extends RenderIcon {

	public RendererLaserDelayed() {
		super(null);
		this.setSize(.8F);
		setViewOptimize();
	}
	
	@Override
	public void doRender(Entity entity, double par2, double par4,
			double par6, float par8, float par9) {
		EntityLaserDelayed laser = (EntityLaserDelayed) entity;
		ResourceLocation texture;
		float blend = laser.isHit ? 1F - laser.ticksAfterHit / 20F : 1F;
		this.setBlend(blend);
		this.setSize(laser.isHit ? 1F : 1.5F);
		
		if(laser.isCharging()) {
			texture = DWClientProps.BLANK;
		} else texture = laser.isHit ? DWClientProps.EFFECT_LASERHIT: DWClientProps.EFFECT_LASER;
		RenderUtils.loadTexture(texture);
		
		super.doRender(entity, par2, par4, par6, par8, par9);
	}	
	
}
