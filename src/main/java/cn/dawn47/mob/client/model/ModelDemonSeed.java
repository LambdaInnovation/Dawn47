// Date: 2014/10/18 16:41:53
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package cn.dawn47.mob.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDemonSeed extends ModelBase {
    
    ModelRenderer head;
    ModelRenderer te[] = new ModelRenderer[7];
    
    int dirMap[] = {
        4, 5, 0, 1, 0, 5, 5
    };
    float initRotations[] = new float[7];

    public ModelDemonSeed() {
        textureWidth = 128;
        textureHeight = 256;

        head = new ModelRenderer(this, 44, 0);
        head.addBox(0F, 0F, 0F, 8, 8, 11);
        head.setRotationPoint(-4F, 11F, -8F);
        head.setTextureSize(128, 256);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        
        te[0] = new ModelRenderer(this, 0, 0);
        te[0].addBox(0F, 0F, 0F, 2, 1, 16);
        te[0].setRotationPoint(0F, 12F, 2F);
        te[0].setTextureSize(128, 256);
        te[0].mirror = true;
        setRotation(te[0], 0.3346075F, 0F, 0F);
        initRotations[0] = te[0].rotateAngleX;
        
        te[1] = new ModelRenderer(this, 0, 0);
        te[1].addBox(0F, 0F, 0F, 2, 1, 16);
        te[1].setRotationPoint(-3F, 17F, 2F);
        te[1].setTextureSize(128, 256);
        te[1].mirror = true;
        setRotation(te[1], -0.2974289F, -0.5576792F, 0F);
        initRotations[1] = te[1].rotateAngleX;
        
        te[2] = new ModelRenderer(this, 0, 0);
        te[2].addBox(0F, 0F, 0F, 2, 1, 16);
        te[2].setRotationPoint(1F, 17F, 2F);
        te[2].setTextureSize(128, 256);
        te[2].mirror = true;
        setRotation(te[2], -0.0743572F, 0.3717861F, 0F);
        initRotations[2] = te[2].rotateAngleY;
        
        te[3] = new ModelRenderer(this, 0, 0);
        te[3].addBox(0F, 0F, 0F, 2, 1, 16);
        te[3].setRotationPoint(-3F, 16F, 2F);
        te[3].setTextureSize(128, 256);
        te[3].mirror = true;
        initRotations[3] = te[3].rotateAngleY;
        
        setRotation(te[3], 0.0743572F, -0.2974289F, -0.1487144F);
        te[4] = new ModelRenderer(this, 0, 0);
        te[4].addBox(0F, 0F, 0F, 2, 1, 16);
        te[4].setRotationPoint(0F, 14F, 2F);
        te[4].setTextureSize(128, 256);
        te[4].mirror = true;
        setRotation(te[4], 0.1115358F, 0.4461433F, 0F);
        initRotations[4] = te[4].rotateAngleY;
        
        te[5] = new ModelRenderer(this, 0, 0);
        te[5].addBox(0F, 0F, 0F, 2, 1, 16);
        te[5].setRotationPoint(0F, 16F, -3F);
        te[5].setTextureSize(128, 256);
        te[5].mirror = true;
        setRotation(te[5], -0.2602503F, 0F, 0F);
        initRotations[5] = te[5].rotateAngleX;
        
        te[6] = new ModelRenderer(this, 0, 0);
        te[6].addBox(0F, 0F, 0F, 2, 1, 16);
        te[6].setRotationPoint(-2F, 14F, 0F);
        te[6].setTextureSize(128, 256);
        te[6].mirror = true;
        setRotation(te[6], 0.2230717F, -0.2230717F, -0.1487144F);
        initRotations[6] = te[6].rotateAngleX;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3,
            float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        GL11.glPushMatrix(); {
            GL11.glTranslated(0, 0.5, 0);
            
            double mx = entity.motionX, my = entity.motionY, mz = entity.motionZ;
            Double rotationPitch = Math.atan2(my, Math.sqrt(mx * mx + my + my + mz * mz)) * 180 / Math.PI;
            if(!rotationPitch.equals(Double.NaN))
                GL11.glRotated(rotationPitch, -1, 0, 0);
            
            //Back at the origin
            GL11.glTranslated(0, -0.5, 0);
            
            head.render(f5);
            te[0].render(f5);
            te[1].render(f5);
            te[2].render(f5);
            te[3].render(f5);
            te[4].render(f5);
            te[5].render(f5);
            te[6].render(f5);
        } GL11.glPopMatrix();
    }
    
    private void applyTentacleRotation(int id, float rotation) {
        if(dirMap[id] == 4 || dirMap[id] == 5) {
            te[id].rotateAngleX = (dirMap[id] == 5 ? 1 : -1) * rotation + initRotations[id];
        } else {
            te[id].rotateAngleY = (dirMap[id] == 1 ? 1 : -1) * rotation + initRotations[id];
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3,
            float f4, float f5, Entity e) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
        float rotation = (float) (15 * f1 * Math.sin(e.ticksExisted * 0.14));
        for(int i = 0; i < 7; ++i) {
            applyTentacleRotation(i, (float) (rotation / 180 * Math.PI));
        }
    }

}
