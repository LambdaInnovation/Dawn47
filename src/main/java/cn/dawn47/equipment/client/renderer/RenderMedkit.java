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
package cn.dawn47.equipment.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cn.annoreg.core.Registrant;
import cn.dawn47.core.proxy.DWResources;
import cn.liutils.util.client.RenderUtils;

/**
 * @author WeAthFolD
 */
@Registrant
public class RenderMedkit extends TileEntitySpecialRenderer {
	
	IModelCustom model;
	ResourceLocation texture;

	public RenderMedkit() {
		model = DWResources.loadModel("medkit");
		texture = DWResources.getMdlTexture("medkit");	
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x,
			double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		float scale = 0.4f;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90 + 90 * te.getBlockMetadata(), 0, 1, 0);
		
		RenderUtils.loadTexture(texture);
		model.renderAll();
		
		GL11.glPopMatrix();
	}

}
