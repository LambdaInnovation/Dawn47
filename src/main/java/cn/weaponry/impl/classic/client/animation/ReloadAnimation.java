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
import cn.liutils.util.client.RenderUtils;
import cn.liutils.util.generic.VecUtils;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.client.render.PartedModel;
import cn.weaponry.api.client.render.RenderInfo.Animation;
import cn.weaponry.impl.classic.WeaponClassic;

/**
 * @author WeAthFolD
 */
public class ReloadAnimation extends Animation {

	public Vec3 rotationAxis = Vec3.createVectorHelper(0, 0.8, -0.2);
	
	public Vec3 pivotPt = Vec3.createVectorHelper(-0.3, 0, 0);
	
	public double maxAngle = 40;
	
	public double blendSpeed = 130; //Per second!
	
	private double progress = 0.0;
	
	private long blendTime;
	
	@Override
	public void start(ItemInfo info) {
		WeaponClassic weapon = info.getItemType();
		
		blendTime = (long) (maxAngle / blendSpeed  * 1000);
		this.setLifetime(weapon.reloadTime * 50 + blendTime);
	}
	
	@Override
	public void render(ItemInfo info, PartedModel model, boolean firstPerson) {
		long time = getTime(), dt = info.getDeltaTime();
		if(time < blendTime) {
			progress = time * blendSpeed / 1000.0;
		} else if(time > lifeTime - blendTime) {
			progress = (lifeTime - time) * blendSpeed / 1000.0;
		} else {
			progress = maxAngle;
		}
		
		GL11.glTranslated(0, -progress / maxAngle * 0.15, progress / maxAngle * 0.1);
		
		RenderUtils.glTranslate(pivotPt);
		RenderUtils.glRotate(progress, rotationAxis);
		RenderUtils.glTranslate(VecUtils.neg(pivotPt));
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 0;
	}
	
	@Override
	public void load(ObjectNamespace ns) {
		Double t = ns.getDouble("render", "reload", "maxAngle");
		if(t != null) {
			maxAngle = t;
		}
		
		t = ns.getDouble("render", "reload", "blendSpeed");
		if(t != null) {
			blendSpeed = t;
		}
		
		Vec3 axis = LoaderUtils.loadVec3(ns, "render", "reload", "axis");
		if(axis != null) {
			rotationAxis = axis;
		}
	}
	
}
