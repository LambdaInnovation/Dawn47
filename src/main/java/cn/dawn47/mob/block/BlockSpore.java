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
package cn.dawn47.mob.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cn.dawn47.Dawn47;
import cn.liutils.template.client.render.block.RenderEmptyBlock;

/**
 * @author WeAthFolD
 *
 */
public class BlockSpore extends BlockContainer {

    public BlockSpore() {
        super(Material.craftedSnow);
        this.setStepSound(soundTypeSnow);
        this.setBlockTextureName("dawn47:spore");
        this.setBlockName("dw_spore");
        setCreativeTab(Dawn47.cct);
        
        this.setBlockBounds(0, 0, 0, 1, 0.2f, 1);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    public int getRenderType() {
        return RenderEmptyBlock.id;
    }
    
    @Override
    public int getRenderBlockPass() {
        return -1;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileSpore();
    }

}
