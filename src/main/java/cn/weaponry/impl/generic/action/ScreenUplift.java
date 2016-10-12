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
package cn.weaponry.impl.generic.action;

import javax.vecmath.Vector2d;

import net.minecraft.entity.player.EntityPlayer;
import cn.liutils.util.generic.RandUtils;
import cn.weaponry.api.action.Action;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Represents a single time of uplift.
 * @author WeAthFolD
 */
@SideOnly(Side.CLIENT)
public class ScreenUplift extends Action {
    
    //TODO: Open up the rad limit?
    public double upliftRadius = 4; //Expected uplift radius. For better experience use RangeRandom(rad*0.8, rad*1.2).
    
    public double upliftSpeed = 2.5; //Uplift speed, unit: deg/tick
    public double recoverSpeed = 0.9; //Angle recover speed, unit: deg/tick
    
    public double degreeFrom = 50, degreeTo = 130; //The allowed "uplift" vector scatter range, in degrees.
    
    private Vector2d dir;
    private double uplift;
    
    private double changedAngles = 0.0;

    @Override
    public String getName() {
        return "Uplift";
    }
    
    boolean recovering = false;
    
    @Override
    public void onStart() {
        double rad = RandUtils.ranged(degreeFrom, degreeTo) * Math.PI / 180;
        dir = new Vector2d(Math.sin(rad), Math.cos(rad));
        
        uplift = RandUtils.ranged(0.8, 1.2) * upliftRadius;
        
        EntityPlayer player = getPlayer();
        
        //lastRecoverTime = Minecraft.getSystemTime();
    }
    
    @Override
    public void onRenderTick() {
        EntityPlayer player = getPlayer();
        long dt = itemInfo.getDeltaTime();
        
        if(!recovering) {
            double change = upliftSpeed * dt / 50.0;
            if(changedAngles + change > uplift) {
                change = uplift - changedAngles;
//              System.out.println("UE" + change + " " + changedAngles);
                
                player.rotationYaw += dir.y * change;
                player.rotationPitch -= dir.x * change;
                
                changedAngles = 0;
                recovering = true;
            } else {
//              System.out.println("U " + change + " " + changedAngles);
                player.rotationYaw += dir.y * change;
                player.rotationPitch -= dir.x * change;
                changedAngles += change;
            }
            
        } else {
            double change = recoverSpeed * dt / 50.0;
            if(changedAngles + change > uplift) {
                change = uplift - changedAngles;
                disposed = true;
//              System.out.println("RE" + change + " " + changedAngles);
            }
            player.rotationYaw -= dir.y * change;
            player.rotationPitch += dir.x * change;
            changedAngles += change;
        }
    }

}
