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
package cn.weaponry.impl.generic.entity;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEntity;
import cn.liutils.entityx.EntityAdvanced;
import cn.liutils.entityx.event.CollideEvent;
import cn.liutils.entityx.event.CollideEvent.CollideHandler;
import cn.liutils.entityx.handlers.Rigidbody;
import cn.liutils.template.client.render.entity.RenderCrossedProjectile;
import cn.liutils.util.generic.RandUtils;
import cn.liutils.util.helper.Motion3D;
import cn.liutils.util.mc.WorldUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This entity is now just for client effect.
 * @author WeAthFolD
 */
@Registrant
@RegEntity(clientOnly = true)
@RegEntity.HasRender
public class EntityBullet extends EntityAdvanced {
    
    @RegEntity.Render
    @SideOnly(Side.CLIENT)
    public static Renderer render;
    
    static final double VELOCITY = 80.0;
    
    double scatter;
    
    EntityLivingBase spawner;
    
    Motion3D m3d;
    
    public EntityBullet(EntityPlayer player, double scatter) {
        this(player, new Motion3D(player, true), scatter);
    }

    public EntityBullet(EntityLivingBase _spawner, Motion3D m3d, double scatter) {
        this(_spawner.worldObj);
        
        spawner = _spawner;
        this.scatter = scatter;
        
        m3d.normalize();
        double vel = 6 * RandUtils.ranged(1, 1.2);
        
        m3d.setMotionOffset(scatter);
        m3d.applyToEntity(this);
        motionX = m3d.vx * vel;
        motionY = m3d.vy * vel;
        motionZ = m3d.vz * vel;
        
        this.m3d = m3d;
    }
    
    public EntityBullet(World world) {
        super(world);
        
        Rigidbody rb = new Rigidbody();
        this.addMotionHandler(rb);
        rb.entitySel = new IEntitySelector() {
            @Override
            public boolean isEntityApplicable(Entity e) {
                return spawner == null || !e.equals(spawner);
            }
        };
        
        setSize(0.2f, 0.2f);
    }
    
    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }
    
    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        setDead();
    }
    
    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}
    
    public static class Renderer extends RenderCrossedProjectile {

        public Renderer() {
            super(1.2, 0.6, new ResourceLocation("weaponry:textures/effects/bullet.png"));
            this.ignoreLight = true;
            super.fpOffsetX = 0.8;
            super.fpOffsetZ = 0.6;
            super.fpOffsetY = -0.3;
        }
        
        @Override
        public void doRender(Entity entity, double par2, double par4,
                double par6, float par8, float par9) {
            EntityBullet bullet = (EntityBullet) entity;
            this.fpOffsetX = 0.5;
            this.fpOffsetZ = 0.36;
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            super.doRender(entity, par2, par4, par6, par8, par9);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
        }
        
    }

}
