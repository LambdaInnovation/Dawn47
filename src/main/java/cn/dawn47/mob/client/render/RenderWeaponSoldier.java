/**
 * Copyright (c) Lambda Innovation, 2013-2015
 * 本作品版权由Lambda Innovation所有。
 * http://www.li-dev.cn/
 *
 * This project is open-source, and it is distributed under  
 * the terms of GNU General Public License. You can modify
 * and distribute freely as long as you follow the license.
 * 本项目是一个开源项目，且遵循GNU通用公共授权协议。
 * 在遵照该协议的情况下，您可以自由传播和修改。
 * http://www.gnu.org/licenses/gpl.html
 */
package cn.dawn47.mob.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.mob.entity.EntityWeaponSoldier;

/**
 * @author WeAthFolD
 */
public class RenderWeaponSoldier extends RenderBiped {
    
    ResourceLocation[] textures;

    public RenderWeaponSoldier() {
        super(new Model(), 0.5f);
        textures = DWResources.getTextures("entities/soldier_w", 2);
    }
    
    public void doRender(EntityLivingBase a, double b, double c, double d, float e, float f) {
        EntityWeaponSoldier sol = (EntityWeaponSoldier) a;
        if(sol.weaponID == -1)
            return;
        super.doRender(a, b, c, d, e, f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(Entity ent) {
        EntityWeaponSoldier ews = (EntityWeaponSoldier) ent;
        return textures[ews.weaponID];
    }
    
    private static class Model extends ModelBiped {
        public Model() {
            super(0.0f);
        }
        
        @Override
        public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
        {
            super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
            if(!((EntityWeaponSoldier)par7Entity).isShooting) {
                setRotation(bipedRightArm, -0.5205006F, -0.7807508F, 0F);
                setRotation(bipedLeftArm, -0.2602503F, 0.3346075F, -0.0371786F);
            } else {
                setRotation(bipedRightArm, -1.373038F, -0.2974289F, 0.0371786F);
                setRotation(bipedLeftArm, -1.461502F, 0.5948578F, -0.0371786F);
            }
        }
        
        private void setRotation(ModelRenderer model, float x, float y, float z) {
           model.rotateAngleX = x;
           model.rotateAngleY = y;
           model.rotateAngleZ = z;
        }
    }

}
