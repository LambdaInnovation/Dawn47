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
package cn.dawn47.mob.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.mob.entity.EntityBattleSoldier;

/**
 * @author WeAthFolD
 *
 */
public class RendererBattleSoldier extends RenderBiped {
	
	ResourceLocation[] textures = DWResources.getTextures("entities/mutant", 3);

	public RendererBattleSoldier() {
		super(new ModelBiped(0.0f), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity e) {
		return textures[((EntityBattleSoldier)e).texID];
	}

}
