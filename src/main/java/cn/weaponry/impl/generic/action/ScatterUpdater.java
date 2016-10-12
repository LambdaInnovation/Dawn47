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

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import cn.weaponry.api.action.Action;
import cn.weaponry.api.state.WeaponStateMachine;
import cn.weaponry.impl.classic.WeaponClassic;
import cn.weaponry.impl.classic.WeaponClassic.StateReload;
import cn.weaponry.impl.classic.WeaponClassic.StateShoot;

/**
 * Used to silence Minecraft swing action.
 * @author WeAthFolD
 */
public class ScatterUpdater extends Action {
    
    //base value
    private double shootScatterMin;
    private double shootScatterIncrement;
    private double movingScatterIncrement;
    private double shootScatterStability;

    //temp value
    private double shootScatter;
    private double movingScatter;
    private int shootCount;
    private int shootCooldown;
    //temp value
    private double renderShootScatter;
    private double renderMovingScatter;
    
    private double oldX, oldY, oldZ;
    private Random r = new Random();
    //as ms
    private int recoverTime = 300;
    
    public void onStart() {
        WeaponClassic weapon = (WeaponClassic) itemInfo.getItemType();
        
        this.shootScatterMin = weapon.shootScatterMin;
        this.shootScatterIncrement = weapon.shootScatterIncrement;
        this.shootScatterStability = weapon.shootScatterStability;
        if(this.shootScatterStability < 0)
            this.shootScatterStability = 0;
        this.movingScatterIncrement = weapon.movingScatterIncrement;
    
        this.shootScatter = 0;
        this.movingScatter = 0;
        this.shootCount = 0;
        this.shootCooldown = 0;
        
        EntityPlayer player = itemInfo.getPlayer();
        this.oldX = player.posX;
        this.oldY = player.posY;
        this.oldZ = player.posZ;
    }

    public void onTick(int tick) {
        shootScatterStability = 3;
        WeaponStateMachine machine = (WeaponStateMachine) itemInfo.getAction("StateMachine");
        EntityPlayer player = itemInfo.getPlayer();
        
        if(machine != null && machine.getCurrentState() != null){
            //flags
            Vec3 motion = Vec3.createVectorHelper(player.posX - oldX, player.posY - oldY, player.posZ - oldZ);
            boolean moving = motion.lengthVector() > 0.1 || player.isSneaking();
            boolean shooting = machine.getCurrentState() instanceof StateShoot;
            boolean reloading = machine.getCurrentState() instanceof StateReload;
            //for both shoot, reload & move
            double recoverTicks = this.recoverTime/50;
            double movingScatterMulti = player.isSprinting() ? 1.25 : player.isSneaking() ? 0.1 : 1;
            double movingPhase = movingScatterIncrement * movingScatterMulti/recoverTicks;
            
            //incresing
            if(shooting){
                shootCooldown = 5;
            }else if(reloading && shootScatter < shootScatterIncrement){
                shootCount = 0;
                shootScatter += shootScatterIncrement/recoverTicks;
                if(shootScatter > shootScatterIncrement)
                    shootScatter = shootScatterIncrement;
            }
            
            if(moving && movingScatter < movingScatterIncrement * movingScatterMulti){
                //System.out.println(movingScatterIncrement  * movingScatterMulti);
                movingScatter += movingPhase;
                if(movingScatter > movingScatterIncrement * movingScatterMulti)
                    movingScatter = movingScatterIncrement * movingScatterMulti;
                oldX = player.posX;
                oldY = player.posY;
                oldZ = player.posZ;
            }
            
            //recovering
            if(shootScatter > 0 && !reloading){
                shootScatter -= shootScatterIncrement/(recoverTicks * (shooting ? 10 : 1));
                if(shootScatter < 0)
                    shootScatter = 0;   
            }
            
            if(movingScatter > 0){
                movingScatter -= movingPhase / (moving ? 5 : 1);
                if(movingScatter < 0)
                    movingScatter = 0;  
            }
            //judge continuous fire
            if(shootCooldown >= 0)
                shootCooldown--;
            if(shootCooldown == 0)
                shootCount = 0;
        }
    }
    
    @Override
    public void onRenderTick(){
        long msPerFrame = itemInfo.getDeltaTime();
        //System.out.println(dt);
        WeaponStateMachine machine = (WeaponStateMachine) itemInfo.getAction("StateMachine");
        EntityPlayer player = itemInfo.getPlayer();
        
        if(machine != null && machine.getCurrentState() != null){
            //flags
            Vec3 motion = Vec3.createVectorHelper(player.motionX, player.motionY, player.motionZ);
            boolean moving = motion.lengthVector() > 0.1 || player.isSneaking();
            boolean shooting = machine.getCurrentState() instanceof StateShoot;
            boolean reloading = machine.getCurrentState() instanceof StateReload;
            
            //for both shoot & move
            double recoverFrames = this.recoverTime/msPerFrame;
            double movingScatterMulti = player.isSprinting() ? 1.25 : player.isSneaking() ? 0.1 : 1;
            double movingPhase = movingScatterIncrement * movingScatterMulti/recoverFrames; 
            
            //incresing
            if(shooting && shootScatterIncrement > 0){
                //different cal to make shaking effect, renderShootScatter update following server-side shootScatter
                if(renderShootScatter <= shootScatter){
                    renderShootScatter += 0.08 + ((1.25 * (Math.pow(2, shootScatterStability) - 1))/1000 * shootCount * shootCount) * msPerFrame / 50.0;
                    if(renderShootScatter > shootScatter)
                        renderShootScatter = shootScatter + 0.08;
                }else if(renderShootScatter > shootScatter){
                    renderShootScatter -= 0.04;
                    if(renderShootScatter < shootScatter)
                        renderShootScatter = shootScatter;
                }   
            }else if(reloading && renderShootScatter < shootScatter){
                renderShootScatter += shootScatterIncrement/recoverFrames;
                if(renderShootScatter > shootScatter)
                    renderShootScatter = shootScatter;
            }
            
            if(moving && renderMovingScatter < movingScatter){
                renderMovingScatter += movingPhase;
                    if(renderMovingScatter > movingScatter)
                        renderMovingScatter = movingScatter;
            }
            
            //recovering
            if(renderShootScatter > shootScatter){
                renderShootScatter -= shootScatterIncrement/(recoverFrames * (shooting ? 10 : 1));
                if(renderShootScatter < shootScatter)
                    renderShootScatter = shootScatter;  
            }
            
            if(renderMovingScatter > movingScatter){
                renderMovingScatter -= movingPhase / (moving ? 5 : 1);
                if(renderMovingScatter < movingScatter)
                    renderMovingScatter = movingScatter;    
            }           
        }
    }
    
    public void callShoot(){
        if(shootScatter < shootScatterIncrement){
            shootScatter += (1.25 * (Math.pow(2, shootScatterStability) - 1))/1000 * shootCount * shootCount;
        }
        if(shootScatter > shootScatterIncrement)
            shootScatter = shootScatterIncrement;
        shootCount++;
    }
    
    public double getCurrentScatter(){
        //System.out.println("第" + shootCount + "发精度" + (shootScatterMin + shootScatter + movingScatter));
        return shootScatterMin + shootScatter + movingScatter;
    }
    
    public double getRenderScatter(){
        return shootScatterMin + renderShootScatter + renderMovingScatter;
    }
    
    @Override
    public String getName() {
        return "ScatterUpdater";
    }

}
