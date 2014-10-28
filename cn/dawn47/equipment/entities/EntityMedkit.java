package cn.dawn47.equipment.entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cn.dawn47.core.entitis.ExtendedPlayer;
import cn.liutils.api.util.GenericUtils;

public class EntityMedkit extends Entity {

  private int side;

  public EntityMedkit(World world) {
    super(world);
    this.setSize(0.25f, 0.4f);
  }

  public EntityMedkit(World world, double x, double y, double z, int side) {
    this(world);
    this.setPosition(x, y, z);
    this.side = side;
  }

  public int getSide() {
    return side;
  }

  @Override
  public void onUpdate() {

    if (worldObj.isRemote) {
      side = dataWatcher.getWatchableObjectByte(10);
      return;
    } else {
      dataWatcher.updateObject(10, Byte.valueOf((byte) side));
    }

    // FIXME: the range number?
    double xRange = 0.15;
    double yRange = 0.3;
    double zRange = 0.15;

    AxisAlignedBB box =
        AxisAlignedBB.getBoundingBox(posX - xRange, posY - yRange, posZ - zRange, posX + xRange,
            posY + yRange, posZ + zRange);

    List<EntityPlayer> list =
        worldObj.getEntitiesWithinAABBExcludingEntity(this, box, GenericUtils.selectorPlayer);
    if (list != null && list.size() != 0) {
      EntityPlayer player = list.get(0);// get first one

      this.setDead();
      ExtendedPlayer props = ExtendedPlayer.get(player);
      props.setMedkitCount(props.getMedkitCount() + 1);
    }
  }

  @Override
  public boolean canBeCollidedWith() {
    return false;
  }

  @Override
  protected void entityInit() {
    this.dataWatcher.addObject(10, Byte.valueOf((byte) 0));
  }

  @Override
  protected void readEntityFromNBT(NBTTagCompound nbt) {
    side = nbt.getInteger("side");
  }

  @Override
  protected void writeEntityToNBT(NBTTagCompound nbt) {
    nbt.setInteger("side", side);
  }

}
