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
package cn.weaponry.api.action;

import java.lang.reflect.Field;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cn.liutils.util.helper.TypeHelper;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.core.Weaponry;

/**
 * The action somehow supports prototype pattern so it included copy() method to avoid boilerplate. 
 * If an action isn't intended to be copied, it should warn the user.
 * @author WeAthFolD
 */
public abstract class Action {
    
    /**
     * The itemInfo instance this Action is attached to. DONT MODIFY THIS FIELD!
     */
    @CopyIgnored
    public ItemInfo itemInfo;
    
    /**
     * Whether this action is being disposed(Needs to get removed).
     *  Do not directly change it, use finishAction() or abortAction().
     */
    @CopyIgnored
    public boolean disposed = false;
    
    int tick;
    
    public Action() {}
    
    public abstract String getName();
    
    //---Events implemented by subclasses
    
    public void onStart() {}
    
    public void onTick(int tick) {}
    
    public void onRenderTick() {}
    
    public void onNormalEnd() {}
    
    public void onAborted() {}
    
    /**
     * onFinalize will be called when either onNormalEnd and onAborted is called.
     */
    public void onFinalize() {}
    
    //---
    
    //---Events to drive this action
    
    public final void startAction() {
        tick = 0;
        onStart();
    }
    
    public final void finishAction() {
        disposed = true;
        onNormalEnd();
        onFinalize();
    }
    
    public final void abortAction() {
        disposed = true;
        onAborted();
        onFinalize();
    }
    
    public final void tickAction() {
        ++tick;
        onTick(tick);
    }
    
    //---Sandbox utils
    public EntityPlayer getPlayer() {
        return itemInfo.getPlayer();
    }
    
    public World getWorld() {
        return getPlayer().worldObj;
    }
    
    public ItemStack getStack() {
        return itemInfo.getStack();
    }
    
    public NBTTagCompound getData() {
        return itemInfo.dataTag();
    }
    
    public boolean isRemote() {
        return getPlayer().worldObj.isRemote;
    }
    
    public int getTick() {
        return tick;
    }
    
    //---
    public <T extends Action> T copy() {
        try {
            T ret = (T) getClass().newInstance();
            for(Field f : getClass().getFields()) {
                if(!f.isAnnotationPresent(CopyIgnored.class) && TypeHelper.isTypeSupported(f.getType())) {
                    TypeHelper.set(f, ret, f.get(this));
                }
            }
            return ret;
        } catch(Exception e) {
            Weaponry.log.error("Error copying action " + getName());
            e.printStackTrace();
            return null;
        }
    }
    
    public @interface CopyIgnored {}
}
