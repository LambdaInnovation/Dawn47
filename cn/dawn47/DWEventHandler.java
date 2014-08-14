/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.dawn47;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import cn.dawn47.hud.DWHudDrawer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class DWEventHandler {
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ItemStack curItem = player.getCurrentEquippedItem();
		boolean drawScope = false;
		//TODO:Determine if the scope can be draw
		
		if(!drawScope) {
			if(event.type != ElementType.ALL) {
				if(event.type == ElementType.CROSSHAIRS) DWHudDrawer.drawHud(event.resolution);
				if(event.isCancelable() && (event.type == ElementType.HEALTH || event.type == ElementType.ARMOR))
						event.setCanceled(true);
			}
		} else {
			if(event.type == ElementType.CROSSHAIRS)
				DWHudDrawer.drawScope(event.resolution);
			if(event.isCancelable() && event.type != ElementType.ALL) {
				event.setCanceled(true);
			}
		}
	}
	
}
