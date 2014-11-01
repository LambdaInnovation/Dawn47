/**
 * 
 */
package cn.dawn47.mob.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.mob.client.model.ModelSoldier;
import cn.dawn47.mob.entity.EntitySoldier;
import cn.dawn47.weapon.item.DWGeneralWeapon;
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
	
    public void doRender(EntityLiving ent, double cx, double cy, double cz, float par8, float par9)
    {
    	EntitySoldier soldier = (EntitySoldier) ent;
    	//Draw Weapon
    	super.doRender(ent, cx, cy, cz, par8, par9);
    }

}
