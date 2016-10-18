package cn.dawn47.weapon.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEntity;
import cn.dawn47.weapon.client.render.RendererRadiationBall;
import cn.liutils.entityx.EntityAdvanced;
import cn.liutils.entityx.EntityCallback;
import cn.liutils.entityx.event.CollideEvent;
import cn.liutils.entityx.event.CollideEvent.CollideHandler;
import cn.liutils.entityx.handlers.Rigidbody;
import cn.liutils.util.client.ViewOptimize.IAssociatePlayer;
import cn.liutils.util.generic.MathUtils;
import cn.liutils.util.helper.Motion3D;
import cn.liutils.util.mc.EntitySelectors;
import cn.liutils.util.mc.WorldUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Registrant
@RegEntity
@RegEntity.HasRender
public class EntityRadiationBall extends EntityAdvanced implements IAssociatePlayer {
    
    @SideOnly(Side.CLIENT)
    @RegEntity.Render
    public static RendererRadiationBall renderer;
    
    EntityPlayer spawner;

    public int splashTick = 0;
    public boolean isHit;
    public int ticksAfterHit;

    protected double VELOCITY=3;

    public EntityRadiationBall(final EntityPlayer ent) {
        super(ent.worldObj);
        
        spawner = ent;
        
        this.setSize(0.7F, 0.2F);

        new Motion3D(ent, true).multiplyMotionBy(VELOCITY).applyToEntity(EntityRadiationBall.this);
        
        Rigidbody rb = new Rigidbody();
        addMotionHandler(rb);
        rb.entitySel = new IEntitySelector() {

            @Override
            public boolean isEntityApplicable(Entity entity) {
                return entity != ent;
            }
            
        };
        
        this.regEventHandler(new CollideHandler() {

            @Override
            public void onEvent(CollideEvent event) {
                if (!isHit) {
                    MovingObjectPosition res = event.result;
                    if(res.entityHit != null && res.entityHit != spawner) {
                        res.entityHit.attackEntityFrom(DamageSource.causeMobDamage(ent), 12);
                    }

                    if(res.typeOfHit == MovingObjectType.BLOCK) {
                        Vec3 v = res.hitVec;
                        posX = v.xCoord;
                        posY = v.yCoord;
                        posZ = v.zCoord;
                    }

                    executeAfter(new EntityCallback() {
                        @Override
                        public void execute(Entity target) {
                            setDead();
                        }
                    }, 20);
                }
                
                isHit = true;
                motionX = motionY = motionZ = 0;
            }
            
        });
    }

    public EntityRadiationBall(World world) {
        super(world);
        
        addMotionHandler(new Rigidbody());
        
        regEventHandler(new CollideHandler() {
            @Override
            public void onEvent(CollideEvent event) {
                isHit = true;
                motionX = motionY = motionZ = 0;
                //TODO: Spawn particles
            }
        });
    }
    
    @Override
    public void entityInit() {
        super.entityInit();

        dataWatcher.addObject(10, 0.0f);
        dataWatcher.addObject(11, 0.0f);
        dataWatcher.addObject(12, 0.0f);

        dataWatcher.addObject(15, (byte) 0);
        dataWatcher.addObject(16, (int)  0);
    }
    
    @Override
    public void onUpdate() {
        if(isHit) {
            ++ticksAfterHit;
            if(ticksAfterHit == 20)
                this.setDead();
        }
        syncData();

        super.onUpdate();
    }
    
    void syncData() {
        if (worldObj.isRemote) {
            byte b = dataWatcher.getWatchableObjectByte(15);
            if (b == 0) {
                isHit = false;
                ticksAfterHit = 0;
            } else {
                isHit = true;
                ticksAfterHit = b >> 1;
            }
            
            if (spawner != null) {
                Entity e = worldObj.getEntityByID(dataWatcher.getWatchableObjectInt(16));
                if (e instanceof EntityPlayer) {
                    spawner = (EntityPlayer) e;
                }
            }

            motionX = dataWatcher.getWatchableObjectFloat(10);
            motionY = dataWatcher.getWatchableObjectFloat(11);
            motionZ = dataWatcher.getWatchableObjectFloat(12);
            if (isHit) {
                motionX = motionY = motionZ = 0;
            }
        } else {
            if (isHit) {
                dataWatcher.updateObject(15, (byte) (1 | (ticksAfterHit << 1)));
            } else {
                dataWatcher.updateObject(15, (byte) 0);
            }
            
            dataWatcher.updateObject(16, spawner.getEntityId());
            dataWatcher.updateObject(10, (float) motionX);
            dataWatcher.updateObject(11, (float) motionY);
            dataWatcher.updateObject(12, (float) motionZ);
        }
    }
    
    @Override
    public boolean shouldRenderInPass(int pass){
        return pass==1;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        setDead();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}

    @Override
    public EntityPlayer getPlayer() {
        return spawner;
    }
    
    

}
