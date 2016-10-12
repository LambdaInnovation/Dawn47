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
package cn.dawn47.mob.entity;

import java.util.ArrayList;
import java.util.List;

import cn.annoreg.core.RegWithName;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEntity;
import cn.annoreg.mc.network.RegNetworkCall;
import cn.annoreg.mc.s11n.StorageOption.Data;
import cn.annoreg.mc.s11n.StorageOption.Instance;
import cn.annoreg.mc.s11n.StorageOption.RangedTarget;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.mob.client.render.RenderWeaponSoldier;
import cn.dawn47.weapon.DawnWeapon;
import cn.liutils.template.entity.LIEntityMob;
import cn.liutils.util.generic.RandUtils;
import cn.liutils.util.generic.VecUtils;
import cn.liutils.util.helper.Motion3D;
import cn.liutils.util.mc.EntitySelectors;
import cn.liutils.util.raytrace.Raytrace;
import cn.weaponry.impl.generic.entity.EntityBullet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
@Registrant
@RegWithName("WeaponSoldier")
@RegEntity
@RegEntity.HasRender
public class EntityWeaponSoldier extends LIEntityMob implements IRangedAttackMob {
	
	@RegEntity.Render
	@SideOnly(Side.CLIENT)
	public static RenderWeaponSoldier render;
	
	static List<DawnWeapon> weapons = new ArrayList();
	static boolean loaded = false;
	
	public int weaponID = -1;
	public boolean isShooting;
	int lastShootTick;
	
	int shootCount;
	
    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);

    public EntityWeaponSoldier(World world) {
        super(world);
        if(!loaded) {
        	loaded = true;
        	weapons.add(DWItems.soldierAR);
        	weapons.add(DWItems.soldierHandgun);
        }
        
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

        if (world != null && !world.isRemote)
        {
        	weaponID = rand.nextInt(weapons.size());
            this.setCombatTask();
        }
    }
	
	public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
	
    protected boolean isAIEnabled() {
        return true;
    }
	
	@Override
	public void entityInit() {
		super.entityInit();
		
		dataWatcher.addObject(15, (byte) 0);
		dataWatcher.addObject(16, (byte) 0);
	}
	
	public void onUpdate() {
		super.onUpdate();
		isShooting = ticksExisted - lastShootTick < 10;
		if(this.isShooting) {
			Entity target = getAttackTarget();
			if(target != null)
				this.faceEntity(target, 20, 20);
		}
		sync();
	}
	
	private void sync() {
		if(worldObj.isRemote) {
			weaponID = dataWatcher.getWatchableObjectByte(15);
			isShooting = dataWatcher.getWatchableObjectByte(16) != 0;
		} else {
			dataWatcher.updateObject(15, (byte) weaponID);
			dataWatcher.updateObject(16, (byte) (isShooting ? 1 : 0));
		}
	}
	
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
		tag.setInteger("weaponID", weaponID);
	}
	
    public void readEntityFromNBT(NBTTagCompound tag) {
    	super.readEntityFromNBT(tag);
    	weaponID = tag.getInteger("weaponID");
    }
    
    DawnWeapon getWeapon() {
    	return weaponID == -1 ? null : weapons.get(weaponID);
    }

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float wtf) {
		EntityBullet bullet;
		if(rand.nextDouble() < 0.3) {
			double yOffset = 1.5;
			double dx = target.posX - posX, dy = target.posY + 1.0 - (posY + yOffset), dz = target.posZ - posZ;
			
			Vec3 start = VecUtils.vec(posX, posY + yOffset, posZ), vel = VecUtils.vec(dx, dy, dz).normalize(),
				end = new Motion3D(start, vel).move(20).getPosVec();
			
			// CLIENT
			spawnBulletEffect(this, this, VecUtils.vec(posX, posY + yOffset, posZ), vel);
			
			// SERVER
			MovingObjectPosition result = Raytrace.perform(worldObj, start, end, 
				EntitySelectors.combine(EntitySelectors.living, EntitySelectors.excludeOf(this)));
			if(result != null && result.entityHit != null) {
				EntityLivingBase hit = (EntityLivingBase) result.entityHit;
				double lmx = hit.motionX, lmy = hit.motionY, lmz = hit.motionZ;
				hit.hurtResistantTime = -1;
				hit.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
				if(RandUtils.ranged(0, 1) < 0.8) {
					hit.motionX = lmx;
					hit.motionY = lmy;
					hit.motionZ = lmz;
				}
			}
		}
		worldObj.playSoundAtEntity(this, getWeapon().shootSound, 0.5f, 1.0f);
		
		lastShootTick = ticksExisted;
		++shootCount;
	}

	@Override
	protected double getMaxHealth2() {
		return 18;
	}

	@Override
	protected double getFollowRange() {
		return 16;
	}

	@Override
	protected double getMoveSpeed() {
		return 0.3;
	}

	@Override
	protected double getKnockBackResistance() {
		return 3;
	}

	@Override
	protected double getAttackDamage() {
		return 4;
	}

	@Override
	public ResourceLocation getTexture() {
		return null;
	}
	
	ItemStack equip;
	
	@Override
    public ItemStack getEquipmentInSlot(int i) {
        if(i == 0) {
        	if(equip == null && weaponID != -1) {
        		equip = new ItemStack(getWeapon());
        	}
        	return equip;
        }
        return super.getEquipmentInSlot(i);
    }
	
	@Override
    protected Item getDropItem() {
    	return null;
    }
	
    /**
     * sets this entity's combat AI.
     */
    public void setCombatTask() {
    	DawnWeapon wpn = getWeapon();
    	this.tasks.addTask(3, new EntityAIArrowAttack
    		(this, 1.0D, wpn.shootInterval, wpn.shootInterval, 15.0F) {
        	
    		int thisMax = 3;
    		
        	public void resetTask() {
        		shootCount = 0;
        		thisMax = RandUtils.rangei(3, 5);
        	}
        	
            public boolean continueExecuting() {
            	boolean b = super.continueExecuting();
            	if(!b)
            		return false;
            	
            	return shootCount <= thisMax;
            }
            
        });
    }
    
    @RegNetworkCall(side = Side.CLIENT)
    private static void spawnBulletEffect(
    	@RangedTarget(range = 15) EntityLivingBase _player, 
    	@Instance EntityLivingBase player,
    	@Data Vec3 pos,
    	@Data Vec3 vel) {
    	player.worldObj.spawnEntityInWorld(new EntityBullet(player, new Motion3D(
    		pos.xCoord, pos.yCoord, pos.zCoord,
    		vel.xCoord, vel.yCoord, vel.zCoord), 0.06));
    }
}
