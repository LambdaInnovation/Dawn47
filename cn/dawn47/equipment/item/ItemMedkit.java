package cn.dawn47.equipment.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cn.dawn47.Dawn47;
import cn.dawn47.core.item.DWGenericItem;
import cn.dawn47.equipment.entitis.EntityMedkit;

public class ItemMedkit extends DWGenericItem {

  public ItemMedkit() {
    super();
    setIAndU("medkit");
    setMaxStackSize(1);
    setMaxDamage(1); 
  }

  @Override
  public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World,
      int par4X, int par5Y, int par6Z, int par7Side, float par8, float par9, float par10) {

    if (!par3World.isRemote) {
      Dawn47.log.info("can spawn:" + canSpawnMedkit(par3World, par4X, par5Y, par6Z, par7Side));
      if (canSpawnMedkit(par3World, par4X, par5Y, par6Z, par7Side)) {
        double x = par4X + 0.5;
        double y = par5Y + 0.5;
        double z = par6Z + 0.5;
        if (par7Side == 0) {
            //return;
        } else if (par7Side == 1) {
            y += 0.8;
        } else if (par7Side == 2) {
            z -= 0.8;
        } else if (par7Side == 3) {
            z += 0.8;
        } else if (par7Side == 4) {
            x -= 0.8;
        } else if (par7Side == 5) {
            x += 0.8;
        }
        EntityMedkit entity = new EntityMedkit(par3World, x, y, z, par7Side);
        par3World.spawnEntityInWorld(entity);
        return true;
      }
    }
    return false;
  }

  private boolean canSpawnMedkit(World world, int x, int y, int z, int side) {
    ForgeDirection direction = ForgeDirection.getOrientation(side);
    ForgeDirection opposite = direction.getOpposite();
    
    if(direction == ForgeDirection.UP || direction == ForgeDirection.UNKNOWN) {
      return false;
    }
    return world.isSideSolid(x, y, z, opposite);
    
    /*
    Dawn47.log.info("www side:" + side);
    Dawn47.log.info("www isSideSolid:"+world.isSideSolid(x, y, z - 1, opposite)+","
      +world.isSideSolid(x, y, z + 1, opposite)+","
      +world.isSideSolid(x - 1, y, z, opposite)+","
      +world.isSideSolid(x + 1, y, z, opposite)+","
      +world.isSideSolid(x, y - 1, z, opposite)+","
      +world.isSideSolid(x, y + 1, z, opposite)+" "
      +world.isSideSolid(x, y, z, opposite));

    switch (direction) {
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

}
