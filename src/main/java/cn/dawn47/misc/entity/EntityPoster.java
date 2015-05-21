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
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEntity;
import cn.dawn47.misc.client.RendererPoster;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 宣传海报的实体
 * @author WeathFolD
 *
 */
@Registrant
@RegEntity
@RegEntity.HasRender
public class EntityPoster extends EntityHanging {
	
	@SideOnly(Side.CLIENT)
	@RegEntity.Render
	public static RendererPoster renderer;
	
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
	
	public void initAABB() {
		double x0 = 0, y0 = 0, z0 = 0, x1 = 0, y1 = 0, z1 = 0;
		
		PosterInfo inf = infs[posterID];
		switch(attachedSide) {
		case NORTH:
			z0 = -0.1;
			
			x1 = 1;
			x0 = 1 - inf.width;
			y1 = inf.height;
			z1 = 0.1;
			break;
		case SOUTH:
			z0 = -0.1;
			
			x1 = inf.width;
			y1 = inf.height;
			z1 = 0.1;
			break;
		case EAST:
			x0 = -0.1;
			
			z1 = 1;
			z0 = 1 - inf.width;
			y1 = inf.height;
			x1 = 0.1;
			break;
		case WEST:
			x0 = -0.1;
			
			z1 = inf.width;
			y1 = inf.height;
			x1 = 0.1;
			break;
		default:
			break;
		}
		
		this.boundingBox.minX = posX + x0;
		this.boundingBox.minY = posY + y0;
		this.boundingBox.minZ = posZ + z0;
		this.boundingBox.maxX = posX + x1;
		this.boundingBox.maxY = posY + y1;
		this.boundingBox.maxZ = posZ + z1;
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
		initAABB(); //Update BoundingBox
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
		posterID = nbt.getInteger("posterID");
		
		updatePosition();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("attachedX", attachedX);
		nbt.setInteger("attachedY", attachedY);
		nbt.setInteger("attachedZ", attachedZ);
		nbt.setInteger("side", attachedSide.ordinal());
		nbt.setInteger("posterID", posterID);
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
	public void onBroken(Entity var1) {}

}
