/**
 * 
 */
package cn.dawn47.misc.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * 宣传海报的实体
 * @author WeathFolD
 *
 */
public class EntityPoster extends EntityHanging {
	
	public static final PosterInfo[] infs = {
		new PosterInfo(4, 2),
		new PosterInfo(4, 2),
		new PosterInfo(4, 2),
		new PosterInfo(4, 2),
		new PosterInfo(4, 2)
	};
	
	public int posterID; //海报ID(0~4 Currently)
	
	/* 实际依附的方块坐标（最x- y- z-侧的那个） */
	public int
		attachedX,
		attachedY,
		attachedZ;
	
	public ForgeDirection attachedSide = ForgeDirection.UNKNOWN;
	
	private EntityPoster(World world, int bx, int by, int bz, int side, int id) {
		super(world);
		posterID = id;
		attachedX = bx;
		attachedY = by;
		attachedZ = bz;
		attachedSide = ForgeDirection.values()[side];
		updatePosition();
		this.hangingDirection = side;
		this.setSize(1F, 1F);
	}
	
	/**
	 * 客户端构造器
	 */
	public EntityPoster(World world) {
		super(world);
		this.setSize(1F, 1F);
		this.ignoreFrustumCheck = true;
	}
	
	@Override
	public void entityInit() {
		this.dataWatcher.addObject(10, Byte.valueOf((byte)0)); //posterID
		this.dataWatcher.addObject(11, Integer.valueOf(0)); //attachedX
		this.dataWatcher.addObject(12, Integer.valueOf(0)); //attachedY
		this.dataWatcher.addObject(13, Integer.valueOf(0)); //attachedZ
		this.dataWatcher.addObject(14, Byte.valueOf((byte)0)); //side
	}
	
	@Override
	/**
	 * 愉快的帧更新
	 */
	public void onUpdate() {
		this.onGround = false;
		doSync(); //数据同步
		updatePosition(); //更新位置
	}
	
	private void updatePosition() {
		if(attachedX == 0 && attachedY == 0 && attachedZ == 0) { //State bad, return
			return;
		}
		//强制设置位置，防止MC的同步及检查函数显示设置
		lastTickPosX = posX = attachedX + (attachedSide.offsetX != 0 ? (attachedSide.offsetX == 1 ?
				1.01 : -0.01) : 0);
		lastTickPosY = posY = attachedY + 0.01;
		lastTickPosZ = posZ = attachedZ + (attachedSide.offsetZ != 0 ? (attachedSide.offsetZ == 1 ?
				1.01 : -0.01) : 0);
	}
	
	/**
	 * 数据同步工作
	 */
	private void doSync() {
		if(worldObj.isRemote) {
			
			posterID = dataWatcher.getWatchableObjectByte(10);
			attachedX = dataWatcher.getWatchableObjectInt(11);
			attachedY = dataWatcher.getWatchableObjectInt(12);
			attachedZ = dataWatcher.getWatchableObjectInt(13);
			attachedSide = ForgeDirection.values()[dataWatcher.getWatchableObjectByte(14)];
			
		} else {
			
			dataWatcher.updateObject(10, Byte.valueOf((byte)posterID));
			dataWatcher.updateObject(11, Integer.valueOf(attachedX));
			dataWatcher.updateObject(12, Integer.valueOf(attachedY));
			dataWatcher.updateObject(13, Integer.valueOf(attachedZ));
			dataWatcher.updateObject(14, Byte.valueOf((byte)this.attachedSide.ordinal()));
			
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		attachedX = nbt.getInteger("attachedX");
		attachedY = nbt.getInteger("attachedY");
		attachedZ = nbt.getInteger("attachedZ");
		attachedSide = ForgeDirection.values()[nbt.getInteger("side")];
		super.readEntityFromNBT(nbt);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("attachedX", attachedX);
		nbt.setInteger("attachedY", attachedY);
		nbt.setInteger("attachedZ", attachedZ);
		nbt.setInteger("side", attachedSide.ordinal());
		super.writeEntityToNBT(nbt);
	}
    
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if (this.isEntityInvulnerable())
			return false;

		if (!this.isDead && !this.worldObj.isRemote) {
			this.setDead();
			this.setBeenAttacked();
		}
		return true;
	}
	
	/**
	 * 在某空间位置和side生成一个海报实体，若找不到可行的空间位置，返回null
	 */
	public static EntityPoster createEntity(World world, int x, int y, int z, int side, int id) {
		int[] coords = findPosition(world, x, y, z, side);
		if(coords == null) {
			return null;
		}
		return new EntityPoster(world, coords[0], coords[1], coords[2], side, id);
	}
	
	/**
	 * 自动为海报寻找放置空间（复杂度：O(width*height)）
	 */
	static int[] findPosition(World world, int x, int y, int z, int side) {
		return new int[] { x, y, z };
	}

	@Override
	public int getWidthPixels() {
		return 512;
	}

	@Override
	public int getHeightPixels() {
		return 256;
	}

	@Override
	public void onBroken(Entity var1) {
	}

}
