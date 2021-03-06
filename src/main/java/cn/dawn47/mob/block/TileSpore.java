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
package cn.dawn47.mob.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegInit;
import cn.annoreg.mc.RegTileEntity;
import cn.annoreg.mc.network.RegNetworkCall;
import cn.annoreg.mc.s11n.StorageOption;
import cn.annoreg.mc.s11n.StorageOption.Data;
import cn.dawn47.core.proxy.DWResources;
import cn.dawn47.mob.client.render.RendererSpore;
import cn.dawn47.mob.entity.EntityDrone;
import cn.liutils.entityx.handlers.Rigidbody;
import cn.liutils.render.particle.Particle;
import cn.liutils.render.particle.ParticleFactory;
import cn.liutils.util.generic.RandUtils;
import cn.liutils.util.generic.VecUtils;
import cn.liutils.util.helper.BlockPos;
import cn.liutils.util.mc.EntitySelectors;
import cn.liutils.util.mc.IBlockFilter;
import cn.liutils.util.mc.WorldUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
@Registrant
@RegInit(side = RegInit.Side.CLIENT_ONLY)
@RegTileEntity
@RegTileEntity.HasRender
public class TileSpore extends TileEntity {
    
    public int textureID;
    public float rot1, rot2;
    
    private boolean released;
    private boolean toRelease;
    
    private int ticker = 10;
    private int triggerTicker = 3;
    private int syncTicker;
    
    static Random r = new Random();
    
    @SideOnly(Side.CLIENT)
    static ParticleFactory sporeFactory;
    
    static IBlockFilter sporeFilter = new IBlockFilter() {

        @Override
        public boolean accepts(World world, int x, int y, int z, Block block) {
            return world.getBlock(x, y, z) instanceof BlockSpore;
        }
        
    };
    
    @SideOnly(Side.CLIENT)
    public static void init() {
        Particle template = new Particle();
        template.hasLight = true;
        template.texture = DWResources.entityTexture("spore");
        template.size = 0.06f;
        
        sporeFactory = new ParticleFactory(template);
    }
    
    public TileSpore() {
        rot1 = RandUtils.rangef(0, 360);
        rot2 = RandUtils.rangef(0, 360);
        textureID = r.nextInt(2);
    }
    
    private void release() {
        released = true;
        World world = getWorldObj();
        
        if(!world.isRemote) {
            double spawnX = xCoord + 0.5,
                    spawnY = yCoord + 1,
                    spawnZ = zCoord + 0.5;
            
            EntityDrone drone = new EntityDrone(world);
            
            drone.setPosition(spawnX, spawnY, spawnZ);
            world.spawnEntityInWorld(drone);
            
            worldObj.playSoundEffect(spawnX, spawnY, spawnZ, "dawn47:entities.spore", 0.5f, 1.0f);
            
            doClientRelease();
            
            if(!toRelease) {
                double range = 3.5;
                List<BlockPos> list = WorldUtils.getBlocksWithin(world, spawnX, spawnY, spawnZ, range, 100, sporeFilter);
                for(BlockPos bp : list) {
                    TileEntity te = world.getTileEntity(bp.x, bp.y, bp.z);
                    if(te instanceof TileSpore) {
                        ((TileSpore) te).triggerRelease();
                    }
                }
            }
        } else {
            releaseClient();
        }
    }
    
    @SideOnly(Side.CLIENT)
    private void releaseClient() {
        World world = getWorldObj();
        int n = RandUtils.rangei(20, 30);
        
        for(int i = 0; i < n; ++i) {
            double yvel = RandUtils.ranged(0.32, 0.48);
            double xzvel = RandUtils.ranged(0.08, 0.12);
            double theta = RandUtils.ranged(0, Math.PI * 2);
            
            sporeFactory.setPosition(
                xCoord + 0.5 + RandUtils.ranged(-0.2, 0.2), 
                yCoord + 0.2 + RandUtils.ranged(0.1, 0.3),
                zCoord + 0.5 + RandUtils.ranged(-0.2, 0.2));
            sporeFactory.setVelocity(xzvel * Math.sin(theta), yvel, xzvel * Math.cos(theta));
            
            Particle p = sporeFactory.next(world);
            
            Rigidbody rb = new Rigidbody();
            rb.gravity = 0.035;
            p.addMotionHandler(rb);
            
            world.spawnEntityInWorld(p);
        }
    }
    
    public boolean isReleased() {
        return released;
    }
    
    public void triggerRelease() {
        toRelease = true;
    }
    
    @Override
    public void updateEntity() {
        
        if(--ticker <= 0) {
            ticker = 10;
            
            doReleaseCheck();
        }
        
        if(!worldObj.isRemote) {
            if(++syncTicker == 10) {
                syncTicker = 0;
                this.receiveUpdate(released);
            }
        }
        
        if(!released && toRelease && --triggerTicker == 0) {
            release();
        }
    }
    
    private void doReleaseCheck() {
        if(!released) {
            List<Entity> list = WorldUtils.getEntities(worldObj, 
                xCoord + .5, yCoord + .7, zCoord + .5, 
                5, EntitySelectors.survivalPlayer);
            if(!list.isEmpty()) {
                release();
            }
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        
        released = tag.getBoolean("released");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        
        tag.setBoolean("released", released);
    }
    
    @RegNetworkCall(side = Side.CLIENT, thisStorage = StorageOption.Option.INSTANCE)
    public void doClientRelease() {
        if(this != null) {
            if(!this.released) {
                release();
            }
        }
    }
    
    @RegNetworkCall(side = Side.CLIENT, thisStorage = StorageOption.Option.INSTANCE)
    public void receiveUpdate(@Data Boolean release) {
        if(this != null) {
            this.released = release;
        }
    }

    @RegTileEntity.Render
    @SideOnly(Side.CLIENT)
    public static RendererSpore renderer;
    
}
