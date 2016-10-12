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

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEventHandler;
import cn.annoreg.mc.RegEventHandler.Bus;
import cn.annoreg.mc.RegInit;
import cn.annoreg.mc.network.RegNetworkCall;
import cn.annoreg.mc.s11n.StorageOption;
import cn.liutils.util.helper.KeyHandler;
import cn.liutils.util.helper.KeyManager;
import cn.weaponry.api.ctrl.IItemCtrlListener;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 */
@Registrant
@RegInit(side = RegInit.Side.CLIENT_ONLY)
@RegEventHandler(Bus.FML)
public class WeaponControllerClient {
    
    public static int KEYS[] = { KeyManager.MOUSE_LEFT, KeyManager.MOUSE_RIGHT, Keyboard.KEY_R };
    
    @SideOnly(Side.CLIENT)
    private static KH handlers[];
    
    @SideOnly(Side.CLIENT)
    public static void init() {
        handlers = new KH[3];
        
        for(int i = 0; i < 3; ++i) {
            KeyManager.dynamic.addKeyHandler("weaponry_" + i, KEYS[i], handlers[i] = new KH(i));
        }
    }
    
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        if(event.phase == Phase.END) return;
        for(KH kh : handlers) {
            kh.tick();
        }
    }
    
    @RegNetworkCall(side = Side.SERVER)
    static void serverSendEvent(
        @StorageOption.Instance EntityPlayer player,
        @StorageOption.Data Byte keyID,
        @StorageOption.Instance SyncEventType type) {
        WeaponControllerServer.sendEvent(player, keyID, type);
    }
    
    private static void localSendEvent(EntityPlayer player, int keyID, SyncEventType type) {
        ItemStack stack = player.getCurrentEquippedItem();
        if(stack != null && stack.getItem() instanceof IItemCtrlListener) {
            ((IItemCtrlListener)stack.getItem()).onKeyEvent(player, keyID, type.keyEvent);
        }
    }
    
    @SideOnly(Side.CLIENT)
    private static class KH extends KeyHandler {
        
        final int virtualKey;
        
        boolean keyDown;
        
        int ticker;
        
        int tickSendCounter = 0;
        
        public KH(int i) {
            virtualKey = i;
        }
        
        @Override
        public void onKeyDown() {
            doKeyDown();
        }

        @Override
        public void onKeyUp() {
            doKeyUp();
        }

        @Override
        public void onKeyTick() {
            doKeyTick();
        }
        
        @Override
        public void onKeyAbort() {
            doKeyAbort();
        }
        
        public void tick() {
            if(keyDown) {
                if(++ticker > 5) {
                    ticker = 0;
                    doKeyAbort();
                }
            }
        }
        
        private void doKeyDown() {
            if(!keyDown) {
                sendEvent(SyncEventType.DOWN);
                keyDown = true;
            }
        }
        
        private void doKeyAbort() {
            if(keyDown) {
                sendEvent(SyncEventType.ABORT);
                keyDown = false;
            }
        }
        
        private void doKeyUp() {
            if(keyDown) {
                sendEvent(SyncEventType.UP);
                keyDown = false;
            }
        }
        
        private void doKeyTick() {
            if(keyDown) {
                if(++tickSendCounter == 5) { //Only send once per 5 ticks~
                    tickSendCounter = 0;
                    sendEvent(SyncEventType.KEEPALIVE);
                }
                ticker = 0;
            }
        }
        
        @SideOnly(Side.CLIENT)
        private void sendEvent(SyncEventType type) {
            //Check player
            if(Minecraft.getMinecraft().thePlayer == null)
                return;
            
            localSendEvent(Minecraft.getMinecraft().thePlayer, virtualKey, type); //Dispatch to Client
            
            serverSendEvent(Minecraft.getMinecraft().thePlayer, (byte) virtualKey, type); //Dispatch to Server
        }
        
    }
    
}
