/**
 * 
 */
package cn.dawn47.misc.item;

import cn.dawn47.Dawn47;
import cn.dawn47.misc.entity.EntityPoster;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


/**
 * @author WeathFolD
 */
public class ItemPosterPlacer extends Item {

	final int id;
	
	public ItemPosterPlacer(int i) {
		id = i;
		setCreativeTab(Dawn47.cct);
		setTextureName("dawn47:poster" + id);
		setUnlocalizedName("dw_poster" + id);
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player,
    		World world, int x, int y, int z, int side, float a, float b, float c)
    {
		
		if(!world.isRemote && side != 0 && side != 1) {
			EntityPoster poster = EntityPoster.createEntity(world, x, y, z, side, id);
			if(poster != null) {
				world.spawnEntityInWorld(poster);
				return true;
			}
			return false;
		}
        return true;
    }
	
}
