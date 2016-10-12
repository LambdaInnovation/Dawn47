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
package cn.weaponry.impl.classic.loading;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelFormatException;
import net.minecraftforge.client.model.obj.WavefrontObject;
import cn.liutils.loading.Loader.ObjectNamespace;
import cn.liutils.loading.item.ItemLoadRule;
import cn.weaponry.api.client.render.CompTransform;
import cn.weaponry.api.client.render.PartedObjModel;
import cn.weaponry.api.client.render.RendererWeapon;
import cn.weaponry.core.Weaponry;
import cn.weaponry.impl.classic.WeaponClassic;
import cn.weaponry.impl.classic.client.animation.Muzzleflash;
import cn.weaponry.impl.classic.client.animation.Recoil;
import cn.weaponry.impl.classic.client.animation.ReloadAnimation;
import cn.weaponry.impl.generic.action.ScreenUplift;

/**
 * Provided a chance for subclasses to redirect the searching.
 * @author WeAthFolD
 */
public class ClassicRenderRule extends ItemLoadRule<WeaponClassic> {
	
	protected WeaponClassic item;
	protected ObjectNamespace ns;
	protected String name;

	@Override
	public void load(WeaponClassic item, ObjectNamespace ns, String name)
			throws Exception {
		this.item = item;
		this.ns = ns;
		this.name = name;
		
		WavefrontObject obj = loadModel(); //Currently just support the .obj
		if(obj != null) {
			//Create the renderer and bind it!
			RendererWeapon render = new RendererWeapon(new PartedObjModel(obj), loadTexture());
			//Load the comp transform
			lookComp(render, render.stdTransform, "t_std");
			lookComp(render, render.fpTransform, "t_firstPerson");
			lookComp(render, render.tpTransform, "t_thirdPerson");
			lookComp(render, render.entityItemTransform, "t_entityItem");
			
			item.reloadAnim.load(ns);
			
			MinecraftForgeClient.registerItemRenderer(item, render);
		} else {
			Weaponry.log.error("WeaponClassic Render Rule: Model lookup failed for " + name);
		}
		
		//Set up muzzleflash
		Muzzleflash mf = item.animMuzzleflash;
		mf.load(ns);
		
		ReloadAnimation ra = item.reloadAnim;
		ra.load(ns);
		
		Recoil r = item.recoilAnim;
		r.load(ns);
		
		String[] ul = { "upliftRadius", "upliftSpeed", "recoverSpeed", "degreeFrom", "degreeTo" };
		for(String prop : ul) {
			Double d = ns.getDouble("weapon", "uplift", prop);
			if(d != null) {
				//System.out.println(String.format("[%s]Updated field uplift.%s", prop, name));
				ScreenUplift.class.getField(prop).set(item.screenUplift, d);
			}
		}
	}
	
	protected WavefrontObject loadModel() {
		try {
			return new WavefrontObject(new ResourceLocation(ns.getString("render", "model")));
		} catch(ModelFormatException e) {
			return null;
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	protected ResourceLocation loadTexture() {
		try {
			return new ResourceLocation(ns.getString("render", "texture"));
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	protected void lookComp(RendererWeapon render, CompTransform ct, String compName) {
		// name/render/<compName>/
		// transform
		// pivot->pivotPt
		// rotation
		Vec3 vec;
		
		vec = lookVector("render", compName, "transform");
		//System.out.println(vec);
		if(vec != null) ct.transform = vec;
		
		vec = lookVector("render", compName, "pivot");
		if(vec != null) ct.pivotPt = vec;
		
		vec = lookVector("render", compName, "rotation");
		if(vec != null) ct.rotation = vec;
		
		Double scale = ns.getDouble("render", compName, "scale");
		if(scale != null) ct.scale = scale;
	}
	
	private Vec3 lookVector(String ...base) {
		Object[] look = new Object[base.length + 1];
		System.arraycopy(base, 0, look, 0, base.length);
		Double x, y, z;
		
		look[base.length] = 0;
		x = ns.getDouble(look);
		
		look[base.length] = 1;
		y = ns.getDouble(look);
		
		look[base.length] = 2;
		z = ns.getDouble(look);
		
		if(x != null && y != null && z != null) {
			//System.out.println("Located vec " + name + "/" + DebugUtils.formatArray((Object[])base));
			return Vec3.createVectorHelper(x, y, z);
		} else {
			//System.out.println("Not locate vec " + name + "/" + DebugUtils.formatArray((Object[])base));
		}
		return null;
	}

}
