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
package cn.dawn47.equipment.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cn.annoreg.core.Registrant;
import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.equipment.block.BlockMedkit;
import cn.liutils.api.gui.AuxGui;
import cn.liutils.cgui.gui.LIGui;
import cn.liutils.cgui.gui.Widget;
import cn.liutils.cgui.gui.annotations.GuiCallback;
import cn.liutils.cgui.gui.component.DrawTexture;
import cn.liutils.cgui.gui.event.FrameEvent;
import cn.liutils.cgui.loader.EventLoader;
import cn.liutils.cgui.loader.xml.CGUIDocLoader;
import cn.liutils.registry.AuxGuiRegistry.RegAuxGui;
import cn.liutils.util.client.HudUtils;
import cn.liutils.util.client.RenderUtils;
import cn.weaponry.impl.classic.WeaponClassic;

/**
 * @author WeAthFolD
 */
@Registrant
public class HudGui extends AuxGui {
	
	private enum Align { LEFT, CENTER, RIGHT };
	
	static ResourceLocation[] numbers;
	static ResourceLocation[] heartbeat;
	
	static LIGui gui;
	static {
		gui = CGUIDocLoader.load(new ResourceLocation("dawn47:guis/hud.xml"));
		
		numbers = DWResources.getTextures("hud/numbers/", 10);
		heartbeat = DWResources.getTextures("hud/heartbeat/", 12);
	}
	
	@RegAuxGui
	public static HudGui instance = new HudGui();
	
	int curAmmo, maxAmmo;
	int curHealth;
	int curMedkit;

	HudGui() {
		EventLoader.load(gui, this);
	}

	@Override
	public boolean isForeground() {
		return false;
	}
	
	@Override
	public void draw(ScaledResolution sr) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ItemStack stack = player.getCurrentEquippedItem();
		if(stack != null && stack.getItem() instanceof WeaponClassic) {
			WeaponClassic weapon = (WeaponClassic) stack.getItem();
			curAmmo = weapon.ammoStrategy.getAmmo(stack);
			maxAmmo = weapon.ammoStrategy.getMaxAmmo(stack);
		} else {
			curAmmo = maxAmmo = -1;
		}
		
		curHealth = (int) (player.getHealth() * 5);
		
		curMedkit = BlockMedkit.getMedkitCount(player);
		
		gui.resize(sr.getScaledWidth_double(), sr.getScaledHeight_double());
		gui.draw(0, 0);
	}
	
	@GuiCallback("main/area")
	public void updateHealth(Widget w, FrameEvent event) {
		DrawTexture drawer = DrawTexture.get(w);
		float speedMul;
		if(curHealth > 70) {
			drawer.color.setColor4i(50, 255, 50, 255);
			speedMul = 2.0f;
		} else if(curHealth > 30) {
			drawer.color.setColor4i(255, 216, 50, 255);
			speedMul = 3.0f;
		} else {
			drawer.color.setColor4i(255, 50, 50, 255);
			speedMul = 5.0f;
		}
		
		drawer.texture = heartbeat[
		    ((int)(Minecraft.getMinecraft().getSystemTime() / 200.0 * speedMul)) % 12];
	}
	
	@GuiCallback("main/area_cur")
	public void drawCurrent(Widget w, FrameEvent event) {
		if(curAmmo != -1)
			drawNumber(curAmmo, 14, 20, 1, Align.RIGHT);
	}
	
	@GuiCallback("main/area_max")
	public void drawMax(Widget w, FrameEvent event) {
		if(maxAmmo != -1)
			drawNumber(maxAmmo, 15, 0, 1, Align.LEFT);
	}
	
	@GuiCallback("main/area_medkit")
	public void drawMedkit(Widget w, FrameEvent event) {
		drawNumber(curMedkit, 12, 5, 0, Align.CENTER);
	}
	
	private void drawNumber(int num, double size, double x, double y, Align align) {
		double x0 = 0;
		double step = 0.7;
		
		GL11.glPushMatrix();
		
		List<Integer> numbers = new ArrayList();
		do {
			numbers.add(num % 10);
			num /= 10;
		} while(num != 0);
		
		double len = step * numbers.size();
		double offset = 0;
		switch(align) {
		case LEFT:
			offset = 0;
			break;
		case CENTER:
			offset = -len / 2;
			break;
		case RIGHT:
			offset = -len;
			break;
		}
		
		GL11.glTranslated(x, y, 0);
		GL11.glScaled(size, size, 1);
		GL11.glTranslated(offset, 0, 0);
		
		for(int i = numbers.size() - 1; i >= 0; --i) {
			GL11.glPushMatrix();
			GL11.glTranslated(x0, 0, 0);
			drawSingleNumber(numbers.get(i));
			GL11.glPopMatrix();
			
			num /= 10;
			x0 += step;
		}
		GL11.glPopMatrix();
	}
	
	/**
	 * Draw a single number at (0, 0) with size 1.
	 */
	private void drawSingleNumber(int num) {
		RenderUtils.loadTexture(numbers[num]);
		HudUtils.rect(0, 0, 1, 1);
	}

}
