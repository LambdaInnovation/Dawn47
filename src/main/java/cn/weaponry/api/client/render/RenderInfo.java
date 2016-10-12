/**
 * Copyright (c) Lambda Innovation, 2013-2015
 * 譛ｬ菴懷刀迚域揀逕ｱLambda Innovation謇�譛峨��
 * http://www.li-dev.cn/
 *
 * This project is open-source, and it is distributed under  
 * the terms of GNU General Public License. You can modify
 * and distribute freely as long as you follow the license.
 * 譛ｬ鬘ｹ逶ｮ譏ｯ荳�荳ｪ蠑�貅宣｡ｹ逶ｮ�ｼ御ｸ秘�ｵ蠕ｪGNU騾夂畑蜈ｬ蜈ｱ謗域揀蜊剰ｮｮ縲�
 * 蝨ｨ驕ｵ辣ｧ隸･蜊剰ｮｮ逧�諠�蜀ｵ荳具ｼ梧お蜿ｯ莉･閾ｪ逕ｱ莨�謦ｭ蜥御ｿｮ謾ｹ縲�
 * http://www.gnu.org/licenses/gpl.html
 */
package cn.weaponry.api.client.render;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.client.Minecraft;
import cn.liutils.loading.Loader.ObjectNamespace;
import cn.liutils.util.generic.RegistryUtils;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.action.Action;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Runtime weapon rendering info. Used to dispatch render events and enables cool stuffs such as muzzle flash + particle effects.
 * @author WeAthFolD
 */
public class RenderInfo extends Action {
	
	Map<String, Animation> callbacks = new HashMap();
	
	public void addAnimation(String name, Animation cb) {
		if(callbacks.containsKey(name)) {
			return;
			//throw new IllegalStateException("Duplicate name " + name);
		}
		callbacks.put(name, cb);
		cb.onStart(itemInfo);
	}
	
	public void addAnimation(Animation cb) {
		addAnimation(nextFreeName(), cb);
	}
	
	private String nextFreeName() {
		String str = "un";
		String res = str + 0;
		for(int i = 1; callbacks.containsKey(res);++i) {
			res = str + i;
		}
		return res;
	}
	
	public void removeCallback(String name) {
		Animation t = callbacks.get(name);
		if(t != null)
			t.disposed = true;
	}
	
	@Override
	public void onTick(int tick) {
		Iterator<Animation> iter = callbacks.values().iterator();
		while(iter.hasNext()) {
			Animation irc = iter.next();
			if(irc.disposed) {
				iter.remove();
			}
		}
	}
	
//	public Collection<Animation> getCallbacks() {
//		return callbacks.values();
//	}
	
	public void renderAll(PartedModel model, boolean firstPerson, int pass) {
		for(Animation a : callbacks.values()) {
			if(!a.disposed && a.shouldRenderInPass(pass)) {
				a.onRender(itemInfo, model, firstPerson);
			}
		}
	}
	
	private String nextName() {
		String n;
		int i = 0;
		do {
			n = String.valueOf("u" + i);
		} while(!callbacks.containsKey(n));
		return n;
	}

	@Override
	public String getName() {
		return "RenderInfo";
	}
	
	public static RenderInfo get(ItemInfo ii) {
		return ii.getAction("RenderInfo");
	}
	
	/**
	 * Each animation is simply a callback that is called
	 * each render tick when rendering weapon. You can either draw stuffs
	 * or do translation in it.
	 * When draw stuffs, use pass 1; When translating, use pass 0.
	 * @author WeAthFolD
	 *
	 */
	@SideOnly(Side.CLIENT)
	public static abstract class Animation {
		
		public boolean disposed = false;
		
		long beginTime;
		
		protected long lifeTime = Long.MAX_VALUE;
		
		public Animation() {}
		
		//Sets
		public Animation setLifetime(long time) {
			lifeTime = time;
			return this;
		}
		
		//Callback events
		public void start(ItemInfo info) {}
		
		public void render(ItemInfo info, PartedModel model, boolean firstPerson) {}
		
		//Driven events
		public final void onStart(ItemInfo info) {
			beginTime = Minecraft.getSystemTime();
			start(info);
		}
		
		public final void onRender(ItemInfo info, PartedModel model, boolean firstPerson) {
			if(getTime() > lifeTime) {
				disposed = true;
			} else {
				render(info, model, firstPerson);
			}
		}
		
		//Utils
		public long getTime() {
			return Minecraft.getSystemTime() - beginTime;
		}
		
		public boolean shouldRenderInPass(int pass) {
			return pass == 1;
		}
		
		public <T extends Animation> T copy() {
			try {
				return (T) RegistryUtils.copy(this);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public void load(ObjectNamespace ns) {
			throw new RuntimeException("Can't load this animation");
		}
		
	}

}
