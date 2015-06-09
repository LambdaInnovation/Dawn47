/**
 * 
 */
package cn.dawn47.core.events;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * @author WeathFolD
 *
 */
public class DWEventListener {
	   
    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {

    }

    @SubscribeEvent
    public void onLivingFallEvent(LivingFallEvent event) {

    }
    
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {

    }
}
