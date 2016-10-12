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
package cn.weaponry.impl.classic.ammo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * A ammo handling strategy representing 1-slot ammo structure.
 * @author WeAthFolD
 */
public interface AmmoStrategy {
    
    int getAmmo(ItemStack stack);
    
    int getMaxAmmo(ItemStack stack);
    
    void setAmmo(ItemStack stack, int n);
    
    boolean canConsume(EntityPlayer player, ItemStack stack, int amt);
    
    boolean consumeAmmo(EntityPlayer player, ItemStack stack, int amt);
    
    String getDescription(ItemStack stack);
    
}
