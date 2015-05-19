/**
 * 
 */
package cn.dawn47.mob.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.mob.client.model.ModelDemonSeed;

/**
 * @author WeathFolD
 *
 */
public class RendererDemonSeed extends RenderLiving {
	
	public RendererDemonSeed() {
		super(new ModelDemonSeed(), 0.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return DWResources.DEMON_SEED_MOB;
	}
	
    @Override
	public void doRender(EntityLiving par1EntityLiving, double par2, 
    		double par4, double par6, float par8, float par9) {
    	super.doRender(par1EntityLiving, par2, par4, par6, par8, par9);
    }

}
