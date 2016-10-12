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
package cn.dawn47.weapon;

import cn.dawn47.weapon.entity.EntityLaserDelayed;
import cn.liutils.util.helper.Motion3D;
import cn.liutils.util.mc.EntitySelectors;
import cn.liutils.util.raytrace.Raytrace;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.event.WeaponCallback;
import cn.weaponry.impl.classic.event.ClassicEvents.ShootEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class LaserRifle extends DawnWeapon {

    @Override
    public Entity createBulletEffect(ItemInfo item) {
        return new EntityLaserDelayed(item.getPlayer());
    }
    
    @Override
    public void spawnClientBullet(ItemInfo info, ShootEvent event) {}
    
    @Override
    public void onShootSvr(ItemInfo info, ShootEvent event) {}
    
    @WeaponCallback(side = Side.SERVER)
    public void spawnServerBullet(ItemInfo info, ShootEvent event) {
        World world = info.getWorld();
        EntityPlayer player = info.getPlayer();
        world.spawnEntityInWorld(createBulletEffect(info));
    }
    
    
}
