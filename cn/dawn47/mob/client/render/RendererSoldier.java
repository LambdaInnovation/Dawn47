/**
 * 
 */
package cn.dawn47.mob.client.render;

import net.minecraft.entity.EntityLiving;
import cn.dawn47.mob.client.model.ModelSoldier;
import cn.dawn47.mob.entity.EntitySoldier;
import cn.liutils.api.client.render.LIRenderMob;

/**
 * @author WeathFolD
 *
 */
public class RendererSoldier extends LIRenderMob {

	/**
	 * @param par1ModelBase
	 * @param par2
	 */
	public RendererSoldier() {
		super(new ModelSoldier(), 1.0F);
	}
	
    @Override
	public void doRender(EntityLiving ent, double cx, double cy, double cz, float par8, float par9)
    {
    	EntitySoldier soldier = (EntitySoldier) ent;
    	//Draw Weapon
    	super.doRender(ent, cx, cy, cz, par8, par9);
    }

}
