/**
 * 
 */
package cn.dawn47.mob.client.model;

import org.lwjgl.opengl.GL11;

import cn.dawn47.mob.entity.EntitySoldier;
import cn.dawn47.mob.entity.EntitySoldier.WeaponTemplate;
import cn.dawn47.weapon.item.DWGeneralWeapon;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

/**
 * @author WeathFolD
 *
 */
public class ModelSoldier extends ModelBiped {
	
	public ModelSoldier() {
		super();
	}
	
	@Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        
        this.bipedHead.render(par7);
        this.bipedBody.render(par7);
        this.bipedRightArm.render(par7);
        this.bipedLeftArm.render(par7);
        this.bipedRightLeg.render(par7);
        this.bipedLeftLeg.render(par7);
        this.bipedHeadwear.render(par7);
        
        EntitySoldier soldier = (EntitySoldier) par1Entity;
        GL11.glPushMatrix(); {
        	WeaponTemplate template = EntitySoldier.wpnTemplates.get(soldier.weapon);
    		ItemStack fake = new ItemStack(template.wpn);
    		IItemRenderer render = MinecraftForgeClient.getItemRenderer
    				(fake, ItemRenderType.EQUIPPED_FIRST_PERSON);
    		if(soldier.isShooting) {
    			GL11.glTranslatef(-0.26F, 0, 0.35F);
    		} else {
    			GL11.glTranslatef(-0.65F, 0.2F, -0.2F);
    			GL11.glRotatef(70, 0, -1, 0);
    			GL11.glRotatef(20, 1, 0, 0);
    		}
    		
    		GL11.glTranslated(template.off.xCoord, template.off.yCoord, template.off.zCoord);
    		GL11.glScalef(template.scale, -template.scale, template.scale);
    		GL11.glRotatef(-45, 1, 0, 0);
    		GL11.glRotatef(90, 0, 1, 0);
    		render.renderItem(ItemRenderType.EQUIPPED, fake, null, soldier);
        } GL11.glPopMatrix();
    }
	
	@Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		if(!((EntitySoldier)par7Entity).isShooting) {
			setRotation(bipedRightArm, -0.5205006F, -0.7807508F, 0F);
			setRotation(bipedLeftArm, -0.2602503F, 0.3346075F, -0.0371786F);
		} else {
			setRotation(bipedRightArm, -1.673038F, -0.2974289F, 0.0371786F);
			setRotation(bipedLeftArm, -1.561502F, 0.5948578F, -0.0371786F);
		}
    }
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
	   model.rotateAngleX = x;
	   model.rotateAngleY = y;
	   model.rotateAngleZ = z;
	}

}
