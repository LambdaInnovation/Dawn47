package cn.dawn47.core.events.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEventHandler;
import cn.annoreg.mc.RegEventHandler.Bus;
import cn.weaponry.impl.classic.WeaponClassic;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Registrant
@RegEventHandler(Bus.Forge)
public class DWRenderOverlayListener {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
		//System.out.println("updating");
		if(event.type == ElementType.CROSSHAIRS){
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof WeaponClassic)
				event.setCanceled(true);
		}
    }

}
