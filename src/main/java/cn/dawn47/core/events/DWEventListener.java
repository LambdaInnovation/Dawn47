/**
 * 
 */
package cn.dawn47.core.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import cn.dawn47.core.entitis.ExtendedPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeathFolD
 *
 */
public class DWEventListener {
	
	@SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            if (ExtendedPlayer.get((EntityPlayer) event.entity) == null)
                ExtendedPlayer.register((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
          ExtendedPlayer.get((EntityPlayer) event.entity).sync();
        }
    }
    
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
