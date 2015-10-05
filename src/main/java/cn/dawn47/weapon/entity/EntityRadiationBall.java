package cn.dawn47.weapon.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEntity;
import cn.dawn47.weapon.client.render.RendererRadiationBall;
import cn.liutils.entityx.EntityAdvanced;
import cn.liutils.entityx.event.CollideEvent;
import cn.liutils.entityx.event.CollideEvent.CollideHandler;
import cn.liutils.entityx.handlers.Rigidbody;
import cn.liutils.util.client.ViewOptimize.IAssociatePlayer;
import cn.liutils.util.generic.MathUtils;
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

	public EntityRadiationBall(World world, final EntityPlayer ent) {
		super(world);
		
		spawner = ent;
		
		addMotionHandler(new Rigidbody());
		regEventHandler(new CollideHandler() {

			@Override
			public void onEvent(CollideEvent event) {
				isHit = true;
				final float range = 4;
				
				motionX = motionY = motionZ = 0;
				List<Entity> list = WorldUtils.getEntities(EntityRadiationBall.this, range, 
					new EntitySelectors.SelectorList(
							EntitySelectors.living, 
							new EntitySelectors.Exclusion(EntityRadiationBall.this, ent)
				));
				for(Entity e : list) {
					float distance = EntityRadiationBall.this.getDistanceToEntity(e);
					float damage = 8 * MathUtils.lerpf(0.4f, 1, 1 - distance / range);
					e.attackEntityFrom(DamageSource.causePlayerDamage(ent), damage);
				}
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
		dataWatcher.addObject(15, Byte.valueOf((byte) 0));
		dataWatcher.addObject(16, Integer.valueOf(0));
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
		if(worldObj.isRemote) {
			byte b = dataWatcher.getWatchableObjectByte(15);
			if(b == 0) {
				isHit = false;
				ticksAfterHit = 0;
			} else {
				isHit = true;
				ticksAfterHit = b >> 1;
			}
			
			if(spawner != null) {
				Entity e = worldObj.getEntityByID(dataWatcher.getWatchableObjectInt(16));
				if(e instanceof EntityPlayer) {
					spawner = (EntityPlayer) e;
				}
			}
		} else {
			if(isHit)
				dataWatcher.updateObject(15, Byte.valueOf((byte) (1 | (ticksAfterHit << 1))));
			else dataWatcher.updateObject(15, Byte.valueOf((byte) 0));
			
			dataWatcher.updateObject(16, spawner.getEntityId());
		}
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
