/**
 * 
 */
package cn.dawn47.misc.client;

import org.lwjgl.opengl.GL11;

import cn.dawn47.core.proxy.DWClientProps;
import cn.dawn47.misc.entity.EntityPoster;
import cn.dawn47.misc.entity.PosterInfo;
import cn.liutils.api.client.util.RenderUtils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

/**
 * @author WeathFolD
 *
 */
public class RendererPoster extends Render {
	
	final Vec3 vecs[] = {
		RenderUtils.newV3(0, 0, 0),
		RenderUtils.newV3(1, 0, 0),
		RenderUtils.newV3(1, 1, 0),
		RenderUtils.newV3(0, 1, 0)
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
		
		GL11.glPushMatrix(); {
			GL11.glTranslated(var2, var4, var6);
			RenderUtils.loadTexture(DWClientProps.POSTER_TEXTURE_PATH[poster.posterID]);
			
			int side = poster.attachedSide.ordinal();
			//x和z坐标是否需要互换（旋转90度）
			boolean swap = side == 4 || side == 5;
			//是否需要渲染反面
			boolean reverse = side == 2 || side == 5;
			
			if(reverse)
				GL11.glTranslated(-poster.attachedSide.offsetZ, 0, poster.attachedSide.offsetX);
			
			t.startDrawingQuads();
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
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return null;
	}

}
