package cn.dawn47.weapon;

import cpw.mods.fml.relauncher.Side;
import cn.dawn47.weapon.entity.EntityRadiationBall;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.event.WeaponCallback;
import cn.weaponry.impl.classic.event.ClassicEvents.ShootEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class RadiationLauncher extends DawnWeapon{

    @Override 
    public Entity createBulletEffect(ItemInfo i){
        return new EntityRadiationBall(i.getPlayer());
        
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
