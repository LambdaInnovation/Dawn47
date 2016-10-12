package cn.weaponry.core.ctrl;

import cn.weaponry.api.item.WeaponBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * This class prevents block breaking from happening when player is wielding a weapon.
 */
public class HarvestBreaker {

    /**
     * Note: this is very hacky and relies on the call order of {@link Minecraft#runTick()}. By setting objectMouseOver
     *  to null this time, it prevents any interaction for blocks/entities to happen.
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent evt) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ItemStack stack = player.getCurrentEquippedItem();
        if (stack != null && stack.getItem() instanceof WeaponBase) {
            Minecraft.getMinecraft().objectMouseOver = null;
        }
    }

}
