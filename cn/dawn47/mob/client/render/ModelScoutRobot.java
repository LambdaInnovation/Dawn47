/**
 * 
 */
package cn.dawn47.mob.client.render;

import java.util.regex.Pattern;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.IModelCustom;
import cn.dawn47.core.proxy.DWClientProps;
import cn.liutils.api.client.model.ModelBaseCustom;
import cn.liutils.api.client.model.ModelPart;

/**
 * @author WeathFolD
 *
 */
public class ModelScoutRobot extends ModelBaseCustom {
	
	ModelPart
		main,
		arms,
		lleg,
		rleg;
	
	public static final float 
		CYCLE = (float) (18 / Math.PI),
		LEG_AMP = 40,
		MAX_HEIGHT = 9;

	/**
	 * @param model
	 */
	public ModelScoutRobot() {
		super(DWClientProps.MDL_SCOUT_ROBOT);
		main = new ModelPart(theModel, "main");
		arms = new ModelPart(theModel, "arm");
		lleg = new ModelPart(theModel, "lleg", 0, 71.382, 0);
		rleg = new ModelPart(theModel, "rleg", 0, 71.382, 0);
		setScale(0.03F);
		setOffset(0, 1.5, 0);
	}
	
	@Override
    public void doRenderModel(float amp, float head) {
    	double height = 0.0;
    	
    	GL11.glPushMatrix(); {
    		adjustRotations(amp, head);
    		GL11.glTranslated(0, height, 0);
    		
    		main.render();
    		arms.render();
    		lleg.render();
    		rleg.render();
    		
    	} GL11.glPopMatrix();
    }
	
	
	private void adjustRotations(float amp, float head) {
		long time = Minecraft.getSystemTime() / 50;
		System.out.println("amp:" + amp);
		double height;
		height = 0.5 * MAX_HEIGHT * (MathHelper.cos(time / CYCLE / 2));
		GL11.glTranslated(0, height, 0);
		
		lleg.rotationZ = amp * LEG_AMP * Math.sin(time / CYCLE) + 10F;
		rleg.rotationZ = amp * LEG_AMP * Math.cos(time / CYCLE) + 10F;
		//rleg.rotationZ = 40 * Math.cos((double)time / CYCLE * 5);
		//System.out.println(time / CYCLE);
		arms.rotationY = head / (180F / (float) Math.PI);
	}

}
