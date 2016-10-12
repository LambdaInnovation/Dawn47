/**
 * 
 */
package cn.dawn47.weapon.client.render;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.weapon.entity.EntityLaserDelayed;
import cn.liutils.template.client.render.entity.RenderIcon;
import cn.liutils.util.client.RenderUtils;

/**
 * @author FolD
 *
 */
public class RendererLaserDelayed extends RenderIcon {

    public RendererLaserDelayed() {
        super(null);
        this.setSize(.8F);
    }
    
    @Override
    public void doRender(Entity entity, double par2, double par4,
            double par6, float par8, float par9) {
        EntityLaserDelayed laser = (EntityLaserDelayed) entity;
        ResourceLocation texture;
        float blend = laser.isHit ? 1F - laser.ticksAfterHit / 20F : 1F;
        color.a = blend;
        this.setSize(laser.isHit ? 1F : 1.5F);
        
        if(laser.isCharging()) {
            texture = DWResources.BLANK;
        } else texture = laser.isHit ? DWResources.EFFECT_LASERHIT: DWResources.EFFECT_LASER;
        super.icon = texture;
        
        super.doRender(entity, par2, par4, par6, par8, par9);
    }   
    
}
