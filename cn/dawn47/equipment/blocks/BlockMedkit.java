package cn.dawn47.equipment.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cn.dawn47.Dawn47;

public class BlockMedkit extends Block {

  private float containHP = 10;

  public BlockMedkit() {
    super(Material.air);
    setCreativeTab(Dawn47.cct);
    setTickRandomly(true);
    setBlockName("medkit");
    setBlockTextureName("dawn47:medkit");
  }

  @Override
  public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    if (entity instanceof EntityPlayerMP) {
      EntityPlayerMP player = (EntityPlayerMP) entity;
      if (player.getHealth() < player.getMaxHealth()) {
        player.setHealth(player.getHealth() + containHP);
        world.setBlock(x, y, z, Blocks.air);
      }
    }
  }

  @Override
  public boolean isBlockSolid(IBlockAccess blockAccess, int x, int y, int z, int side) {
    return false;
  }

  @Override
  public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
    ForgeDirection direction = ForgeDirection.getOrientation(side);
    ForgeDirection opposite = direction.getOpposite();
    Dawn47.log.info("side:"+side);
    Dawn47.log.info("x:" + x + " y:" + y + " z:" + z);
    Dawn47.log.info("can place:"+world.isSideSolid(x, y, z - 1, opposite));

    return true;
/*
    switch(direction) {
      case NORTH:
        return world.isSideSolid(x, y, z - 1, opposite);
      case SOUTH:
        return world.isSideSolid(x, y, z + 1, opposite);
      case WEST:
        return world.isSideSolid(x - 1, y, z, opposite);
      case EAST:
        return world.isSideSolid(x + 1, y, z, opposite);
      case DOWN:
        return world.isSideSolid(x, y - 1, z, opposite);
      case UP:
        return false;
      default:
        return false;
    }*/
  }

  @Override
  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase  entityLiving, ItemStack itemStack) {

    int l = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
    int pitch = Math.round(entityLiving.rotationPitch);
    int kk = -1;

    if (pitch >= 65) {
      kk = 1;
    } else if (pitch <= -65) {
      kk = 0;
    } else {
      if (l == 0) {
        kk = 2;
      }

      if (l == 1) {
        kk = 5;
      }

      if (l == 2) {
        kk = 3;
      }

      if (l == 3) {
        kk = 4;
      }
    }
    
    Dawn47.log.info("vvv side:"+kk);


    //////////////////////////////

    Vec3 blockPosition = world.getWorldVec3Pool().getVecFromPool(x, y, z);
    Vec3 entityLivingPosition = world.getWorldVec3Pool().getVecFromPool(entityLiving.posX, entityLiving.posY, entityLiving.posZ);

    MovingObjectPosition entityLivingToBlock = world.func_147447_a(entityLivingPosition, blockPosition, false, false, true);
    Dawn47.log.info("ccc x:" + x + " y:" + y + " z:" + z);
    Dawn47.log.info("ddd x:" + entityLiving.posX + " y:" + entityLiving.posY + " z:" + entityLiving.posZ);
    Dawn47.log.info("xxx side:"+entityLivingToBlock.sideHit);

    world.setBlockMetadataWithNotify(x, y, z, entityLivingToBlock.sideHit, 0x2);
  }

}
