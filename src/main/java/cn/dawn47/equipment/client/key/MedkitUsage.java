package cn.dawn47.equipment.client.key;

import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import cn.annoreg.core.Registrant;
import cn.annoreg.mc.network.RegNetworkCall;
import cn.annoreg.mc.s11n.StorageOption.Instance;
import cn.dawn47.equipment.block.BlockMedkit;
import cn.liutils.registry.KeyHandlerRegistration.RegKeyHandler;
import cn.liutils.util.helper.KeyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Registrant
public class MedkitUsage {

	@SideOnly(Side.CLIENT)
	@RegKeyHandler(name = "UseMedkit", keyID = Keyboard.KEY_B)
	public static KH keyHandler;
	
	static final float HEAL = 5;
	
	@RegNetworkCall(side = Side.SERVER)
	private static void doHeal(@Instance EntityPlayer player) {
		int mc = BlockMedkit.getMedkitCount(player);
		if(mc > 0) {
			BlockMedkit.setMedkitCount(player, mc - 1);
			
			player.worldObj.playSoundAtEntity(player, "dawn47:medkit", 0.5f, 1.0f);
			player.heal(HEAL);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static class KH extends KeyHandler {
		
		public void onKeyDown() {
			doHeal(getPlayer());
		}
		
	}

}
