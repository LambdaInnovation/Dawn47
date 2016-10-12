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
package cn.weaponry.api.event;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.item.WeaponBase;

/**
 * @author WeAthFolD
 */
public class WpnEventLoader {
    
    public static void load(WeaponBase target) {
        load(target, target);
    }
    
    public static void load(WeaponBase target, Object eventProvider) {
//      System.out.println("---------------");
//      System.out.println("Loading " + eventProvider);
        for(Method m : eventProvider.getClass().getMethods()) {
            if(m.isAnnotationPresent(WeaponCallback.class)) {
                WeaponCallback anno = m.getAnnotation(WeaponCallback.class);
                Side side = FMLCommonHandler.instance().getSide();
                if((anno.side() == Side.CLIENT && side == Side.CLIENT) || anno.side() == Side.SERVER) {
                    //System.out.println("Registered " + m.getName() + " as WpnEventHandler");
                    target.regEventHandler(new Callback((Class<? extends Event>) m.getParameterTypes()[1], m, eventProvider, anno.side()));
                }
//              System.out.println("Registered " + m.getName() + " as WpnEventHandler");
            }
        }
    }
    
    private static class Callback extends WpnEventHandler {

        final boolean side;
        final Method method;
        final Object obj;
        
        public Callback(Class<? extends Event> klass, Method _m, Object _obj, Side _s) {
            super(klass);
            method = _m;
            obj = _obj;
            side = _s == Side.CLIENT;
        }

        @Override
        public void handleEvent(ItemInfo item, Event event) {
            try {
                if(item.player.worldObj.isRemote == side) {
                    method.invoke(obj, item, event);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
}
