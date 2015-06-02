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

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.mob.block.TileSpore;
import cn.liutils.render.material.Material;
import cn.liutils.render.material.SimpleMaterial;
import cn.liutils.render.mesh.Mesh;
import cn.liutils.util.client.RenderUtils;

/**
 * @author WeAthFolD
 *
 */
public class RendererSpore extends TileEntitySpecialRenderer {
	
	IModelCustom model, modelReleased;
	ResourceLocation textures[], textureWeb;
	
	Material materialWeb;
	Mesh ground;
	
	public RendererSpore() {
		model = DWResources.loadModel("spore");
		modelReleased = DWResources.loadModel("spore_released");
		textures = DWResources.getMdlMultiTexture("spore", 2);
		textureWeb = DWResources.getMdlTexture("spore_web");
		materialWeb = new SimpleMaterial(textureWeb);
		
		ground = new Mesh();
		ground.setVertices(new double[][] {
				{0, 0, 0},
				{1, 0, 0},
				{1, 0, 1},
				{0, 0, 1}
		});
		ground.setAllNormals(new float[] { 0, 1, 0 });
		ground.setUVs(new double[][] {
				{0, 0},
				{1, 0},
				{1, 1},
				{0, 1}
		});
		ground.setQuads(new int[] { 3, 2, 1, 0 });
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, 
			double x, double y, double z, float partialTicks) {
		TileSpore spore = (TileSpore) tile;
		
		IModelCustom mdl = spore.isReleased() ? modelReleased : model;
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		GL11.glPushMatrix();
		GL11.glRotated(spore.rot2, 0, 1, 0);
		GL11.glTranslated(-0.5, 0.01, -0.5);
		ground.draw(materialWeb);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		RenderUtils.loadTexture(textures[spore.textureID]);
		GL11.glRotated(spore.rot1, 0, 1, 0);
		mdl.renderAll();
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}

}
