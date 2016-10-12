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
package cn.dawn47.equipment.data;

import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegEventHandler;
import cn.annoreg.mc.RegEventHandler.Bus;
import cn.annoreg.mc.RegInit;
import cn.annoreg.mc.network.RegNetworkCall;
import cn.annoreg.mc.s11n.StorageOption.Instance;
import cn.annoreg.mc.s11n.StorageOption.Target;
import cn.dawn47.Dawn47;
import cn.dawn47.core.register.DWItems;
import cn.dawn47.equipment.event.ShieldAttackEvent;
import cn.liutils.registry.RegDataPart;
import cn.liutils.ripple.ScriptNamespace;
import cn.liutils.util.helper.DataPart;
import cn.liutils.util.helper.PlayerData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * @author WeAthFolD
 */
@Registrant
@RegInit
@RegDataPart("dw_Shield")
public class ShieldData extends DataPart {
    
    public ShieldData() {}
    
    // CONSTANTS
    static int 
        ATTACKED_COOLDOWN,
        DEPLETED_COOLDOWN;
    
    static float
        ABSORB_RATE;
    
    static float MAX_ENERGY;
    
    static float RECOVER_PER_TICK;
    
    static final int ATTACK_TIME_CD = 20;
    
    public static void init() {
        ScriptNamespace ns = Dawn47.script.at("dawn47.shield");
        ATTACKED_COOLDOWN = ns.getInteger("attacked_cooldown");
        DEPLETED_COOLDOWN = ns.getInteger("depleted_cooldown");
        ABSORB_RATE = ns.getFloat("absorb_rate");
        MAX_ENERGY = ns.getFloat("max_energy");
        RECOVER_PER_TICK = ns.getFloat("recover_per_tick");
    }
    
    float energy;
    int recoverCooldown = 0;
    int attackCooldown = 0;
    
    int untilSync = 10;
    
    public static ShieldData get(EntityPlayer player) {
        return PlayerData.get(player).getPart(ShieldData.class);
    }
    
    public void tick() {
        if(!isRemote()) {
            if(--untilSync == 0) {
                untilSync = 20;
                sync();
            }
        }
        
        if(recoverCooldown > 0) {
            recoverCooldown--;
        }
        
        if(attackCooldown > 0) {
            attackCooldown--;
        }
        
        // Recover
        if(canRecover()) {
            float rec = Math.min(getMaxEnergy() - energy, RECOVER_PER_TICK);
            
            energy += rec;
        }
    }

    @Override
    public void fromNBT(NBTTagCompound tag) {
        energy = tag.getFloat("e");
        recoverCooldown = tag.getInteger("cd");
        attackCooldown = tag.getInteger("c2");
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound ret = new NBTTagCompound();
        
        ret.setFloat("e", energy);
        ret.setInteger("cd", recoverCooldown);
        ret.setInteger("c2", attackCooldown);
        
        return ret;
    }
    
    public boolean isActivated() {
        for(ItemStack stack : getPlayer().inventory.mainInventory) {
            if(stack != null && stack.getItem() == DWItems.electronicBalancer) {
                return true;
            }
        }
        return false;
    }
    
    public float applyDamage(float original) {
        float canAbsorb = Math.min(original, energy);
        
        energy -= canAbsorb;
        
        if(energy == 0) {
            recoverCooldown = Math.max(DEPLETED_COOLDOWN, recoverCooldown);
        } else {
            recoverCooldown = Math.max(ATTACKED_COOLDOWN, recoverCooldown);
        }
        
        attackCooldown = ATTACK_TIME_CD;
        
        
        if(!isRemote()) {
            sync();
        }
        
        return original - canAbsorb * ABSORB_RATE;
    }
    
    public int getAttackCooldown() {
        return ATTACK_TIME_CD - attackCooldown;
    }
    
    public boolean canRecover() {
        return recoverCooldown == 0;
    }
    
    public float getEnergy() {
        return energy;
    }
    
    public float getMaxEnergy() {
        return MAX_ENERGY;
    }
    
    @RegEventHandler(Bus.Forge)
    public static class Events {
        
        @SubscribeEvent
        public void onLivingHurt(LivingHurtEvent event) {
            if(event.entityLiving instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entityLiving;
                ShieldData sdata = ShieldData.get(player);
                if(sdata.isActivated()) {
                    event.ammount = sdata.applyDamage(event.ammount);
                    
                    MinecraftForge.EVENT_BUS.post(new ShieldAttackEvent(player));
                    if(!player.worldObj.isRemote)
                        postEvent(player, player);
                }
            }
        }
        
    }
    
    @RegNetworkCall(side = Side.CLIENT)
    private static void postEvent(@Target EntityPlayer _player, @Instance EntityPlayer player) {
        MinecraftForge.EVENT_BUS.post(new ShieldAttackEvent(player));
    }

}
