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
package cn.weaponry.api.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cn.liutils.util.client.RenderUtils;
import cn.liutils.util.generic.VecUtils;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.ItemInfoProxy;

/**
 * @author WeAthFolD
 *
 */
public class RendererWeapon implements IItemRenderer {
    
    public CompTransform 
        stdTransform = new CompTransform(), 
        fpTransform = new CompTransform(), 
        tpTransform = new CompTransform(), 
        entityItemTransform = new CompTransform();
    
    public PartedModel model;
    public ResourceLocation texture;
    
    public RendererWeapon() {}
    
    public RendererWeapon(PartedModel _model, ResourceLocation _texture) {
        model = _model;
        texture = _texture;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if(type == ItemRenderType.INVENTORY)
            return false;
        return true;
    }

    @Override
    public final boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
            ItemRendererHelper helper) {
        return type == ItemRenderType.ENTITY;
    }

    @Override
    public final void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        if(type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED) {
            ItemInfo info = ItemInfoProxy.getInfo(player);
            RenderInfo ri = (RenderInfo) (info == null ? null : RenderInfo.get(info));
            EntityLivingBase holder = (EntityLivingBase) data[1];
            if(ri != null && holder == player) {
                handleHeldRender(ri, type == ItemRenderType.EQUIPPED_FIRST_PERSON);
            } else {
                handleSimpleRender(item);
            }
        } else {
            if(type == ItemRenderType.ENTITY) {
                handleEntityItemRender(item);
            } else {
                handleSimpleRender(item);
            }
        }
        
    }
    
    private void handleHeldRender(RenderInfo info, boolean firstPerson) {
        GL11.glPushMatrix();
        model.pushTransformState();
        {
            
            if(firstPerson) {
                doFirstPersonTansform();
                fpTransform.doTransform();
            } else {
                doThirdPersonTransform();
                tpTransform.doTransform();
            }
            stdTransform.doTransform();
            doFixedTransform();
            
            info.renderAll(model, firstPerson, 0);
            
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPushMatrix();
            info.renderAll(model, firstPerson, 1);
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPopMatrix();
            
            GL11.glDisable(GL11.GL_CULL_FACE);
            
            RenderUtils.loadTexture(texture);
            model.renderAll();
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
        model.popTransformState();
        GL11.glPopMatrix();
    }
    
    private void handleSimpleRender(ItemStack stack) {
        GL11.glPushMatrix(); 
        {
            stdTransform.doTransform();
            doFixedTransform();
            RenderUtils.loadTexture(texture);
            model.renderAll();
        } 
        GL11.glPopMatrix();
    }
    
    private void handleEntityItemRender(ItemStack stack) {
        GL11.glPushMatrix(); 
        {
            GL11.glRotated(30, 0, 0, 1);
            stdTransform.doTransform();
            RenderUtils.loadTexture(texture);
            model.renderAll();
        } 
        GL11.glPopMatrix();
    }
    
    private void doFirstPersonTansform() {
        
    }
//  
    private void doThirdPersonTransform() {
        
    }
    
    private void doFixedTransform() {
        GL11.glRotated(35, 0, 0, 1);
        GL11.glTranslated(0.8, -.12, 0);
    }

}
