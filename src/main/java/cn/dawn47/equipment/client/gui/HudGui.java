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

import org.lwjgl.opengl.GL11;

import cn.annoreg.core.Registrant;
import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.equipment.block.BlockMedkit;
import cn.dawn47.equipment.data.ShieldData;
import cn.dawn47.equipment.event.ShieldAttackEvent;
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
import cn.liutils.util.helper.GameTimer;
import cn.liutils.vis.animation.CubicSplineCurve;
import cn.liutils.vis.animation.LineInterpCurve;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.ItemInfoProxy;
import cn.weaponry.impl.classic.WeaponClassic;
import cn.weaponry.impl.generic.action.ScatterUpdater;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author WeAthFolD
 */
@Registrant
public class HudGui extends AuxGui {
    
    static double sin41 = Math.sin(41.0 / 180 * Math.PI);
    private enum Align { LEFT, CENTER, RIGHT };
    
    static ResourceLocation[] numbers;
    static ResourceLocation[] heartbeat;
    static ResourceLocation shieldEnergyBar;
    static ResourceLocation coverAttacked;
    static ResourceLocation coverDepleted;
    
    static LIGui gui;
    static double mainInitY;
    static {
        gui = CGUIDocLoader.load(new ResourceLocation("dawn47:guis/hud.xml"));
        
        numbers = DWResources.getTextures("hud/numbers/", 10);
        heartbeat = DWResources.getTextures("hud/heartbeat/", 12);
        
        shieldEnergyBar = DWResources.texture("hud/shield_energy");
        coverAttacked = DWResources.texture("hud/shield_attacked");
        coverDepleted = DWResources.texture("hud/shield_depleted");
        
        mainInitY = gui.getWidget("main").transform.y;
    }
    
    @RegAuxGui
    public static HudGui instance = new HudGui();
    
    float shieldProg;
    boolean shieldActivated, shieldCooldown;
    int attackCooldown;
    
    int curAmmo, maxAmmo;
    int curHealth;
    int curMedkit;
    
    long startShieldTime;
    
    CubicSplineCurve curveAlpha = new CubicSplineCurve();
    LineInterpCurve curveScale = new LineInterpCurve();
    
    HudGui() {
        EventLoader.load(gui, this);
        
        curveScale.addPoint(0.0, 1.1);
        curveScale.addPoint(0.05, 1.3);
        curveScale.addPoint(0.2, 1.0);
        curveScale.addPoint(0.6, 1.0);
        curveScale.addPoint(1, 1.0);
        
        curveAlpha.addPoint(0, 0.0);
        curveAlpha.addPoint(0.1, 1);
        curveAlpha.addPoint(0.7, 1);
        curveAlpha.addPoint(1, 0);
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isForeground() {
        return false;
    }
    
    @Override
    public void draw(ScaledResolution sr) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ShieldData sd = ShieldData.get(player);
        double width = sr.getScaledWidth_double(), height = sr.getScaledHeight_double();
        
        long time = GameTimer.getTime();
        
        shieldActivated = sd.isActivated();
        shieldProg = sd.getEnergy() / sd.getMaxEnergy();
        attackCooldown = sd.getAttackCooldown();
        shieldCooldown = !sd.canRecover();
        
        gui.getWidget("main/shield").transform.doesDraw = shieldActivated;
        
        Widget cross1 = gui.getWidget("crosshair/cross_1");
        Widget cross2 = gui.getWidget("crosshair/cross_2");
        Widget cross3 = gui.getWidget("crosshair/cross_3");
        Widget cross4 = gui.getWidget("crosshair/cross_4");
        
        ItemStack stack = player.getCurrentEquippedItem();
        if(stack != null && stack.getItem() instanceof WeaponClassic) {
            WeaponClassic weapon = (WeaponClassic) stack.getItem();
            curAmmo = weapon.ammoStrategy.getAmmo(stack);
            maxAmmo = weapon.ammoStrategy.getMaxAmmo(stack);
            //crosshair
            ItemInfo info = ItemInfoProxy.getInfo(player);
            //double scatter = ((ScatterUpdater)info.getAction("ScatterUpdater")).getCurrentScatter();
            double scatter = ((ScatterUpdater)info.getAction("ScatterUpdater")).getRenderScatter();
            double crossfix = 50;
            cross1.visible = true;
            cross2.visible = true;
            cross3.visible = true;
            cross4.visible = true;
            cross1.transform.pivotX = scatter * crossfix;
            cross2.transform.pivotY = scatter * crossfix;
            cross3.transform.pivotX = -scatter * crossfix;
            cross4.transform.pivotY = -scatter * crossfix;
        } else {
            cross1.visible = false;
            cross2.visible = false;
            cross3.visible = false;
            cross4.visible = false;
            curAmmo = maxAmmo = -1;
        }
        
        curHealth = (int) (player.getHealth() * 5);
        
        curMedkit = BlockMedkit.getMedkitCount(player);
        
        /* Shield attacked effect */ 
        GL11.glPushMatrix(); {
            long dt = time - startShieldTime;
            final long BLEND_TIME = 2400;
            if(dt >= 0 && dt < BLEND_TIME) {
                double alpha = curveAlpha.valueAt((double)dt / BLEND_TIME);
                double scale = curveScale.valueAt((double)dt / BLEND_TIME);
                // System.out.println("Drawing " + dt + "/" + alpha + "/" + scale);
                
                GL11.glColor4d(1, 1, 1, alpha);
                
                GL11.glTranslated(width / 2, height / 2, 0);
                GL11.glScaled(scale, scale, 1);
                GL11.glTranslated(-width / 2, -height / 2, 0);
                
                if(shieldProg == 0.0f) { //Depleted
                    RenderUtils.loadTexture(coverDepleted);
                } else {
                    RenderUtils.loadTexture(coverAttacked);
                }
                
                HudUtils.rect(width, height);
            }
        } GL11.glPopMatrix();
        
        gui.resize(sr.getScaledWidth_double(), sr.getScaledHeight_double());
        GL11.glPushMatrix();
        gui.draw(0, 0);
        GL11.glPopMatrix();
    }
    
    @GuiCallback("main")
    public void udpateMainPos(Widget w, FrameEvent event) {
        double prevY = w.transform.y;
        if(shieldActivated) {
            w.transform.y = mainInitY;
        } else {
            w.transform.y = mainInitY + 20;
        }
        if(prevY != w.transform.y)
            w.dirty = true;
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
    
    @GuiCallback("main/shield")
    public void drawShieldEnergy(Widget w, FrameEvent event) {
        float prog = shieldProg;
        
        RenderUtils.loadTexture(shieldEnergyBar);
        
        //We need a cut-angle effect so this must be done manually
        if(shieldCooldown)
            GL11.glColor4d(1, 1, 1, 0.4 + 0.3 * (1 + Math.sin(GameTimer.getAbsTime() / 60.0)));
        else
            GL11.glColor4d(1, 1, 1, 1);
        
        final double X0 = 64, Y0 = 37, WIDTH = 104, HEIGHT = 10, OFF = HEIGHT * sin41;
        Tessellator t = Tessellator.instance;
        double len = WIDTH * prog, len2 = len - OFF;
        
        GL11.glCullFace(GL11.GL_BACK);
        
        t.startDrawingQuads();
        addVertex(X0 + OFF, Y0);
        addVertex(X0, Y0 + HEIGHT);
        addVertex(X0 + len2, Y0 + HEIGHT);
        addVertex(X0 + len, Y0);
        t.draw();
        
        GL11.glCullFace(GL11.GL_BACK);
    }
    
    private void addVertex(double x, double y) {
        double width = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH),
                height = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
        Tessellator.instance.addVertexWithUV(x, y, 0, x / width, y / height);
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
    
    @SubscribeEvent
    public void onShieldAttack(ShieldAttackEvent event) {
        if(event.player.worldObj.isRemote)
            startShieldTime = GameTimer.getTime();
        System.out.println("OnShieldAttack");
    }

}
