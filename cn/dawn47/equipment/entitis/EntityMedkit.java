package cn.dawn47.equipment.entitis;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cn.dawn47.Dawn47;
import cn.liutils.api.util.GenericUtils;

public class EntityMedkit extends Entity {

  private float containHP = 10;
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

  @Override
  public void onUpdate() {
    
    //Dawn47.log.info("entity side::" + side + " remote:" + worldObj.isRemote + " x:" + posX + " y:" + posY + " z:" + posZ);
    //if(worldObj.isRemote) {
    //  return;
   // }

    //FIXME: the range number?
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
      
      Dawn47.log.info("get player HP:" +  player.getHealth() + " max HP:" + player.getMaxHealth());
      //Dawn47.log.info("entity side::" +  side + "remote:"+worldObj.isRemote);
      Dawn47.log.info("entity side::" + side + " remote:" + worldObj.isRemote + " x:" + posX + " y:" + posY + " z:" + posZ);
      this.setDead();

      if (player.getHealth() < player.getMaxHealth()) {
        player.setHealth(player.getHealth() + containHP);
        this.setDead();
      }
    }
  }

  @Override
  public boolean canBeCollidedWith() {
    return false;
  }

  @Override
  protected void entityInit() {

  }

  @Override
  protected void readEntityFromNBT(NBTTagCompound nbt) {
    posX = nbt.getDouble("posX");
    posY = nbt.getDouble("posY");
    posZ = nbt.getDouble("posZ");
    side = nbt.getInteger("side");
  }

  @Override
  protected void writeEntityToNBT(NBTTagCompound nbt) {
    nbt.setDouble("posX", posX);
    nbt.setDouble("posY", posY);
    nbt.setDouble("posZ", posZ);
    nbt.setInteger("side", side);
  }

}
