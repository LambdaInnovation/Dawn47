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
package cn.dawn47.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cn.dawn47.core.entitis.ExtendedPlayer;
import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.weapon.item.IDWAmmoInfProvider;
import cn.liutils.api.client.util.HudUtils;
import cn.liutils.api.client.util.RenderUtils;

/**
 * @author WeAthFolD
 *
 */
public class DWHudDrawer {

	private static final int DEFAULT_FONT_SIZE = 16;
	private static final float OFFSET_SCALE = 0.8F;
	
	public static void drawScope(ScaledResolution sr) {
		//draw_fs(DWClientProps.SNIPER_RIFLE_SCOPE, sr);
		GL11.glPushMatrix();
		draw(DWClientProps.SNIPER_RIFLE_SCOPE, sr);
		GL11.glPopMatrix();
		
	}
	
	public static void drawHud(ScaledResolution sr) {
		int i = sr.getScaledWidth(), j = sr.getScaledHeight();
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		//health section
		GL11.glPushMatrix();
		String hlr = String.valueOf((int) (player.getHealth() * 5));
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderUtils.loadTexture(DWClientProps.HUD_HEALTH);
		HudUtils.drawTexturedModalRect(8, j - 40, 64, 16);
		drawString(hlr, 16 + 64, j - 40);
		
		String shi = String.valueOf(player.getTotalArmorValue() * 5);
		RenderUtils.loadTexture(DWClientProps.HUD_SHIELD);
		HudUtils.drawTexturedModalRect(8, j - 20, 64, 16);
		drawString(shi, 16 + 64, j - 20);
		
		//Ammo
		ItemStack st = player.getCurrentEquippedItem();
		if(st != null && st.getItem() instanceof IDWAmmoInfProvider) {
			IDWAmmoInfProvider prv = (IDWAmmoInfProvider) st.getItem();
			RenderUtils.loadTexture(DWClientProps.HUD_AMMO);
			HudUtils.drawTexturedModalRect(i - 64, j - 37, 64, 16);
			String str = prv.getAmmoForHud(player, st);
			drawString(str, i - 5 - getStrLen(str, DEFAULT_FONT_SIZE), j - 21);
		}
		
		//medkit
		ExtendedPlayer props = ExtendedPlayer.get(player);
		String medkitCount = String.valueOf(props.getMedkitCount());
		RenderUtils.loadTexture(DWClientProps.HUD_MEDKIT);
        HudUtils.drawTexturedModalRect(8, j - 60, 16, 16);
        drawString(medkitCount, 16 + 16, j - 60);
        
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	private static void drawString(String str, int x, int y) {
		drawString(str, x, y, DEFAULT_FONT_SIZE);
	}
	
	private static void drawString(String str, int x, int y, int FONT_SIZE) {
		int cx = x, cy = y;
		int offset = (int) (FONT_SIZE * OFFSET_SCALE);
		GL11.glPushMatrix();
		for(int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if(ch == '|') RenderUtils.loadTexture(DWClientProps.HUD_MID);
			else RenderUtils.loadTexture(DWClientProps.NUMBERS[ch - 48]);
			HudUtils.drawTexturedModalRect(cx, cy, FONT_SIZE, FONT_SIZE);
			cx += offset;
		}
		GL11.glPopMatrix();
	}
	
	private static int getStrLen(String strr, int FONT_SIZE) {
		return (int) (strr.length() * (DEFAULT_FONT_SIZE * OFFSET_SCALE) + (DEFAULT_FONT_SIZE * (1 - OFFSET_SCALE)));
	}
	
	private static void drawNumberAt(int num, int x, int y) { 
		drawNumberAt(num, x, y, DEFAULT_FONT_SIZE);
	}
	
	private static void drawNumberAt(int num, int x, int y, int FONT_SIZE) {
		RenderUtils.loadTexture(DWClientProps.NUMBERS[num]);
		HudUtils.drawTexturedModalRect(x, y, FONT_SIZE, FONT_SIZE);
	}
	
	private static void draw_fs(ResourceLocation tex, ScaledResolution sr) {
		TextureManager eg = Minecraft.getMinecraft().renderEngine;
		Tessellator t = Tessellator.instance;
		int i = sr.getScaledWidth();
		int j = sr.getScaledHeight();
		
		GL11.glPushMatrix();
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		eg.bindTexture(tex);
		t.startDrawingQuads();
		t.setColorRGBA(255, 255, 255, 50);
		t.addVertexWithUV( 0, j, -90D, 0.0D, 1.0D);
		t.addVertexWithUV( i, j, -90D, 1.0D, 1.0D);
		t.addVertexWithUV( i, 0, -90D, 1.0D, 0.0D);
		t.addVertexWithUV( 0, 0, -90D, 0.0D, 0.0D);
		t.draw();
		GL11.glDepthMask(true);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	private static void draw(ResourceLocation tex, ScaledResolution sr) {
		TextureManager eg = Minecraft.getMinecraft().renderEngine;
		Tessellator t = Tessellator.instance;
		int i = sr.getScaledWidth();
		int j = sr.getScaledHeight();
		float len = j * 0.4F;
		
		GL11.glPushMatrix();
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_COLOR, GL11.GL_SRC_COLOR);
		GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		eg.bindTexture(tex);
		t.startDrawingQuads();
		t.setColorRGBA(255, 255, 255, 50);
		t.addVertexWithUV( i / 2 - len, j / 2 + len, -90D, 0.0D, 1.0D);
		t.addVertexWithUV( i / 2 + len, j / 2 + len, -90D, 1.0D, 1.0D);
		t.addVertexWithUV( i / 2 + len, j / 2 - len, -90D, 1.0D, 0.0D);
		t.addVertexWithUV( i / 2 - len, j / 2 - len, -90D, 0.0D, 0.0D);
		t.draw();
		GL11.glDepthMask(true);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
		t.startDrawingQuads();
		t.addVertex(0.0, 0.0, -90D);
		t.addVertex(0.0, j, -90D);
		t.addVertex(i / 2 - len, j, -90D);
		t.addVertex(i / 2 - len, 0, -90D);
		
		t.addVertex(0.0, 0.0, -90D);
		t.addVertex(0.0, j / 2 - len , -90D);
		t.addVertex(i, j / 2 - len, -90D);
		t.addVertex(i, 0, -90D);
		
		t.addVertex(i / 2 + len, 0.0, -90D);
		t.addVertex(i / 2 + len, j, -90D);
		t.addVertex(i, j, -90D);
		t.addVertex(i, 0, -90D);
		
		t.addVertex(i / 2 - len, j / 2 + len, -90D);
		t.addVertex(i / 2 - len, j , -90D);
		t.addVertex(i / 2 + len, j, -90D);
		t.addVertex(i / 2 + len, j / 2 + len, -90D);
		t.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	

}
