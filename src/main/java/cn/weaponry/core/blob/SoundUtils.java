package cn.weaponry.core.blob;

import net.minecraft.entity.player.EntityPlayer;

public class SoundUtils {
    
    /**
     * A playing strategy that play the sound at both sides and make the effect good
     */
    public static void playBothSideSound(EntityPlayer player, String snd) {
        if(snd != null) {
            player.playSound(snd, 0.5f, 1.0f);
            if(player.worldObj.isRemote) {
                player.worldObj.playSound(player.posX, player.posY, player.posZ, snd, 0.5f, 1.0f, false);
            }
        }
    }
    
}
