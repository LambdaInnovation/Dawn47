/**
 * 
 */
package cn.dawn47.misc.client;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.misc.entity.EntityPoster;
import cn.liutils.util.client.RenderUtils;
import cn.liutils.util.generic.VecUtils;

/**
 * @author WeathFolD
 *
 */
public class RendererPoster extends RenderEntity {
	
	final Vec3 vecs[] = {
		VecUtils.vec(0, 0, 0),
		VecUtils.vec(1, 0, 0),
		VecUtils.vec(1, 1, 0),
		VecUtils.vec(0, 1, 0)
	};
	double uvs[][] = {
			{0, 1},
			{1, 1},
			{1, 0},
			{0, 0}
	};

	public RendererPoster() {
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.renderer.entity.Render#doRender(net.minecraft.entity.Entity, double, double, double, float, float)
	 */
	@Override
	public void doRender(Entity var1, double var2, double var4, double var6,
			float var8, float var9) {
		EntityPoster poster = (EntityPoster) var1;
		Tessellator t = Tessellator.instance;
		//super.doRender(var1, var2, var4, var6, var8, var9);
		
		GL11.glPushMatrix(); {
			GL11.glTranslated(var2, var4, var6);
			RenderUtils.loadTexture(DWResources.POSTER_TEXTURE_PATH[poster.posterID]);
			
			ForgeDirection dir = poster.attachedSide;
			int side = poster.attachedSide.ordinal();
			//x和z坐标是否需要互换（旋转90度）
			boolean swap = side == 4 || side == 5;
			//是否需要渲染反面
			boolean reverse = side == 2 || side == 5;
			
			if(reverse)
				GL11.glTranslated(-poster.attachedSide.offsetZ, 0, poster.attachedSide.offsetX);
			
			t.startDrawingQuads();
			t.setNormal(dir.offsetX, 0, dir.offsetZ);
			for(int i = 0; i < 4; ++i) {
				Vec3 vec = vecs[i].addVector(0, 0, 0);
				vec.xCoord *= EntityPoster.infs[poster.posterID].width * (reverse ? -1 : 1);
				vec.yCoord *= EntityPoster.infs[poster.posterID].height;
				
				if(swap) {
					double tmp = vec.xCoord;
					vec.xCoord = vec.zCoord;
					vec.zCoord = tmp;
				}
				RenderUtils.addVertex(vec, uvs[i][0], uvs[i][1]);
			}
			t.draw();
		} GL11.glPopMatrix();
		//super.renderOffsetAABB(var1.boundingBox, var2, var4, var6);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return null;
	}

}
