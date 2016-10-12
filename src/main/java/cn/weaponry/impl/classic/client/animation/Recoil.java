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
package cn.weaponry.impl.classic.client.animation;

import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.liutils.loading.Loader.ObjectNamespace;
import cn.liutils.loading.item.LoaderUtils;
import cn.liutils.util.generic.VecUtils;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.client.render.PartedModel;
import cn.weaponry.api.client.render.RenderInfo.Animation;

/**
 * @author WeAthFolD
 */
public class Recoil extends Animation {

	public int recoilTime = 400;
	
	public Vec3 recoilVec = VecUtils.vec(-1, 0.4, 0).normalize();
	
	public double wiggleRadius = 0.12;
	
	@Override
	public void start(ItemInfo info) {
		setLifetime(recoilTime);
	}
	
	@Override
	public void render(ItemInfo info, PartedModel model, boolean firstPerson) {
		long time = getTime();
		double phase = time * 2 * Math.PI / recoilTime;
		double progress = (double)time / recoilTime;
		double offset = Math.sin(phase) * wiggleRadius * 1 / (1 + 8 * progress * progress * progress);
		
		GL11.glTranslated(recoilVec.xCoord * offset, recoilVec.yCoord * offset, 0);
	}
	
	@Override
	public void load(ObjectNamespace ns) {
		Vec3 v = LoaderUtils.loadVec3(ns, "render", "recoil", "direction");
		if(v != null) {
			recoilVec = v.normalize();
		}
		
		Integer time = ns.getInt("render", "recoil", "time");
		if(time != null) {
			recoilTime = time;
		}
		
		Double radius = ns.getDouble("render", "recoil", "radius");
		if(radius != null) {
			wiggleRadius = radius;
		}
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 0;
	}
	
}
