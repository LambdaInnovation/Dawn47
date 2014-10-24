/**
 * 
 */
package cn.dawn47.core.events;

import cn.dawn47.hud.DWHudDrawer;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

/**
 * @author WeathFolD
 *
 */
public class DWEventListener {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderOverlay(RenderGameOverlayEvent event) {
		if(event.type == ElementType.CROSSHAIRS) {
			DWHudDrawer.drawHud(event.resolution);
		}
	}
}
