package cn.weaponry.api.client.render;

import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.liutils.util.client.RenderUtils;
import cn.liutils.util.generic.VecUtils;

public class CompTransform {
    
    public Vec3 transform = Vec3.createVectorHelper(0, 0, 0);
    
    public Vec3 pivotPt = Vec3.createVectorHelper(0, 0, 0);
    
    public Vec3 rotation = Vec3.createVectorHelper(0, 0, 0);
    
    public double scale = 1.0;
    
    private CompTransform stack;
    
    public void setPivotPt(double x, double y, double z) {
        pivotPt.xCoord = x;
        pivotPt.yCoord = y;
        pivotPt.zCoord = z;
    }
    
    public void doTransform() {
        RenderUtils.glTranslate(VecUtils.add(transform, pivotPt));
        
        GL11.glRotated(rotation.zCoord, 0, 0, 1);
        GL11.glRotated(rotation.yCoord, 0, 1, 0);
        GL11.glRotated(rotation.xCoord, 1, 0, 0);
        
        GL11.glScaled(scale, scale, scale);
        
        RenderUtils.glTranslate(VecUtils.neg(pivotPt));
    }
    
    public void store() {
        if(stack == null) 
            stack = new CompTransform();
        VecUtils.copy(transform, stack.transform);
        VecUtils.copy(pivotPt, stack.pivotPt);
        VecUtils.copy(rotation, stack.rotation);
        stack.scale = scale;
    }
    
    public void restore() {
        VecUtils.copy(stack.transform, transform);
        VecUtils.copy(stack.pivotPt, pivotPt);
        VecUtils.copy(stack.rotation, rotation);
        scale = stack.scale;
    }
    
}
