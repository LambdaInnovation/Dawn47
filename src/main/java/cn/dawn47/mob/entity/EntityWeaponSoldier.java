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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEntity;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.mob.client.render.RenderWeaponSoldier;
import cn.dawn47.weapon.DawnWeapon;
import cn.liutils.template.entity.LIEntityMob;
import cn.liutils.util.generic.RandUtils;
import cn.liutils.util.helper.Motion3D;
import cn.weaponry.impl.generic.entity.EntityBullet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
@Registrant
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
        	weapons.add(DWItems.weaponAR);
        	weapons.add(DWItems.weaponHandgun);
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
			bullet = new EntityBullet(this, new Motion3D(posX, posY + yOffset, posZ, dx, dy, dz), 0.06, 3);
			worldObj.spawnEntityInWorld(bullet);
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
	
    /**
     * sets this entity's combat AI.
     */
    public void setCombatTask() {
    	DawnWeapon wpn = getWeapon();
    	this.tasks.addTask(3, new EntityAIArrowAttack(this, 1.0D, wpn.shootInterval, wpn.shootInterval, 15.0F) {
        	
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
}
