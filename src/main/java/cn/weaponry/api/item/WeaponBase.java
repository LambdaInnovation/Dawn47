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
package cn.weaponry.api.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import cn.weaponry.api.IItemInfoProvider;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.ItemInfoProxy;
import cn.weaponry.api.client.render.RenderInfo;
import cn.weaponry.api.ctrl.IItemCtrlListener;
import cn.weaponry.api.ctrl.KeyEventType;
import cn.weaponry.api.event.WpnEventHandler;
import cn.weaponry.api.state.WeaponStateMachine;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 */
public abstract class WeaponBase extends ItemSword implements IItemInfoProvider, IItemCtrlListener {
    
    private Map< Class<?>, List<WpnEventHandler> > handledEvents = new HashMap();
    
    public WeaponBase() {
        super(ToolMaterial.IRON);
    }
    
    public abstract void initStates(WeaponStateMachine machine);
    
    @SideOnly(Side.CLIENT)
    public abstract void initDefaultAnims(RenderInfo render);
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        return stack;
    }
    
    @Override
    public void onKeyEvent(EntityPlayer player, int key, KeyEventType type) {
        ItemInfo info = ItemInfoProxy.getInfo(player);
        WeaponStateMachine wsm = info.getAction("StateMachine");
        //System.out.println("BasicKeyEvent " + wsm); 
        if(wsm != null) {
            wsm.onCtrl(key, type);
        }
    }

    @Override
    public void onInfoStart(ItemInfo info) {
        WeaponStateMachine wsm = new WeaponStateMachine();
        initStates(wsm);
        info.addAction(wsm);
        
        if(info.getWorld().isRemote) { 
            //Dispatch render info component in client side.
            RenderInfo ri = new RenderInfo();
            initDefaultAnims(ri);
            info.addAction(ri);
        }
    }
    
    //Very simple event bus
    public void regEventHandler(WpnEventHandler handler) {
        lazy(handler.getHandledEvent()).add(handler);
    }
    
    public boolean post(ItemInfo item, Event event) {
        List<WpnEventHandler> list = handledEvents.get(event.getClass());
        if(list != null) {
            for(WpnEventHandler handler : list) {
                //System.out.println("Handle " + handler + "/" + item.getPlayer().worldObj.isRemote);
                handler.handleEvent(item, event);
            }
            return !event.isCanceled();
        }
        return true;
    }
    
    private List<WpnEventHandler> lazy(Class<?> klass) {
        List<WpnEventHandler> ret = handledEvents.get(klass);
        if(ret == null) {
            ret = new ArrayList();
            handledEvents.put(klass, ret);
        }
        return ret;
    }
    
}
