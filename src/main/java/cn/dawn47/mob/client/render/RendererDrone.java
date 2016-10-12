/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.dawn47.mob.client.render;

import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.mob.client.model.ModelDrone;
import cn.dawn47.mob.entity.EntityDrone;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

/**
 * @author WeAthFolD
 *
 */
public class RendererDrone extends RenderLiving {

    /**
     * @param par1ModelBase
     * @param par2
     */
    public RendererDrone() {
        super(new ModelDrone(), 0.6F);
    }
    
    @Override
    public void doRender(EntityLiving a, double b, double c, double d, float e, float f) {
        EntityDrone drone = (EntityDrone) a;
        if(drone.updated) {
            super.doRender(a, b, c, d, e, f);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return DWResources.DRONE_PATH[((EntityDrone)entity).style];
    }

}
