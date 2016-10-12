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
package cn.weaponry.core.ctrl;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEventHandler;
import cn.annoreg.mc.RegEventHandler.Bus;
import cn.liutils.registry.KeyHandlerRegistration.RegKeyHandler;
import cn.weaponry.api.ctrl.IItemCtrlListener;
import cn.weaponry.api.ctrl.KeyEventType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

/**
 * @author WeAthFolD
 */
@Registrant
@RegEventHandler(Bus.FML)
public class WeaponControllerServer {
    
    static Map<EntityPlayer, PlayerCtrlState> states = new WeakHashMap();

    public static void sendEvent(EntityPlayer player, int keyID, SyncEventType type) {
        check(player).onSyncEvent(keyID, type);
    }
    
    private static PlayerCtrlState check(EntityPlayer player) {
        PlayerCtrlState ret = states.get(player);
        if(ret == null || ret.player != player) {
            ret = new PlayerCtrlState(player);
            states.put(player, ret);
        }
        return ret;
    }
    
    @SubscribeEvent
    public void serverTick(ServerTickEvent event) {
        if(event.phase == Phase.END)
            return;
        for(Object p : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            EntityPlayer player = (EntityPlayer) p;
            if(states.containsKey(player)) //TODO: May be faster if we remove the 'silent' CtrlStates.
                check(player).tick();
        }
    }
    
    private static class PlayerCtrlState {
        
        final EntityPlayer player;
        
        public PlayerCtrlState(EntityPlayer _p) {
            player = _p;
        }
        
        boolean kdArray[] = new boolean[3];
        int kaTimeouts[] = new int[3];
        
        public void onSyncEvent(int keyID, SyncEventType type) {
            boolean kd = kdArray[keyID];
            int timeout = kaTimeouts[keyID];
            
            switch(type) {
            case DOWN:
                if(!kd) {
                    kd = true;
                    timeout = 0;
                    locateAndSend(keyID, KeyEventType.DOWN);
                }
                break;
            case UP:
                if(kd) {
                    kd = false;
                    locateAndSend(keyID, KeyEventType.UP);
                }
                break;
            case ABORT:
                if(kd) {
                    kd = false;
                    locateAndSend(keyID, KeyEventType.ABORT);
                }
                break;
            case KEEPALIVE:
                if(kd) {
                    timeout = 0;
                }
                break;
            default:
                break;
            }
            
            kdArray[keyID] = kd;
            kaTimeouts[keyID] = timeout;
        }
        
        private void locateAndSend(int keyID, KeyEventType event) {
            ItemStack stack = player.getCurrentEquippedItem();
            if(stack != null && stack.getItem() instanceof IItemCtrlListener) {
                ((IItemCtrlListener) stack.getItem()).onKeyEvent(player, keyID, event);
            }
        }
        
        public void tick() {
            for(int i = 0; i < 3; ++i) {
                if(kdArray[i]) {
                    if(++kaTimeouts[i] == 20) {
                        kdArray[i] = false;
                        locateAndSend(i, KeyEventType.ABORT);
                    } else {
                        locateAndSend(i, KeyEventType.TICK);
                    }
                }
            }
        }
        
    }
    
    
    
}
