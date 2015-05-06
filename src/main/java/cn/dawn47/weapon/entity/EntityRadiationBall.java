package cn.dawn47.weapon.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cn.annoreg.core.RegistrationClass;
import cn.annoreg.mc.RegEntity;
import cn.dawn47.weapon.client.render.RendererRadiationBall;
import cn.liutils.entityx.EntityAdvanced;
import cn.liutils.entityx.event.CollideEvent;
import cn.liutils.entityx.event.CollideEvent.CollideHandler;
import cn.liutils.entityx.handlers.Rigidbody;
import cn.liutils.util.GenericUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@RegistrationClass
@RegEntity
@RegEntity.HasRender
public class EntityRadiationBall extends EntityAdvanced {
	
	@SideOnly(Side.CLIENT)
	@RegEntity.Render
	public static RendererRadiationBall renderer;

	public int splashTick = 0;
	public boolean isHit;
	public int ticksAfterHit;

	public EntityRadiationBall(World world, final EntityPlayer ent) {
		super(world);
		
		addMotionHandler(new Rigidbody());
		regEventHandler(new CollideHandler() {

			@Override
			public void onEvent(CollideEvent event) {
				isHit = true;
				motionX = motionY = motionZ = 0;
				GenericUtils.doRangeDamage(worldObj, 
					DamageSource.causeMobDamage(ent), 
					Vec3.createVectorHelper(posX, posY, posZ), 
					16.0F, 4.0F, EntityRadiationBall.this, ent);
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
		dataWatcher.addObject(15, (byte) 0);
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
		} else {
			if(isHit)
				dataWatcher.updateObject(15, Byte.valueOf((byte) (1 | (ticksAfterHit << 1))));
			else dataWatcher.updateObject(15, Byte.valueOf((byte) 0));
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

}
