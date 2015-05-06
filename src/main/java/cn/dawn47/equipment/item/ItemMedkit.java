package cn.dawn47.equipment.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.input.Keyboard;

import cn.annoreg.mc.RegSubmoduleInit;
import cn.dawn47.Dawn47;
import cn.dawn47.core.item.DWGenericItem;
import cn.dawn47.core.network.MsgMedkitUse;
import cn.dawn47.equipment.entities.EntityMedkit;
import cn.liutils.api.key.IKeyHandler;
import cn.liutils.api.key.LIKeyProcess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@RegSubmoduleInit(side = RegSubmoduleInit.Side.CLIENT_ONLY)
public class ItemMedkit extends DWGenericItem {

	public static int keyID = Keyboard.KEY_N;
	
	public ItemMedkit() {
		super();
		setIAndU("medkit");
		setMaxStackSize(1);
		setMaxDamage(1);
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4X,
			int par5Y, int par6Z, int par7Side, float par8, float par9,
			float par10) {

		if (!par3World.isRemote) {
			if (canSpawnMedkit(par3World, par4X, par5Y, par6Z, par7Side)) {
				double x = par4X + 0.5;
				double y = par5Y + 0.5;
				double z = par6Z + 0.5;
				if (par7Side == 0) {
					// return;
				} else if (par7Side == 1) {
					y += 0.8;
				} else if (par7Side == 2) {
					z -= 0.8;
				} else if (par7Side == 3) {
					z += 0.8;
				} else if (par7Side == 4) {
					x -= 0.8;
				} else if (par7Side == 5) {
					x += 0.8;
				}
				EntityMedkit entity = new EntityMedkit(par3World, x, y, z,
						par7Side);
				par3World.spawnEntityInWorld(entity);
				return true;
			}
		}
		return false;
	}

	private boolean canSpawnMedkit(World world, int x, int y, int z, int side) {
		ForgeDirection direction = ForgeDirection.getOrientation(side);
		ForgeDirection opposite = direction.getOpposite();

		if (direction == ForgeDirection.UP
				|| direction == ForgeDirection.UNKNOWN) {
			return false;
		}
		return world.isSideSolid(x, y, z, opposite);
	}

	@SideOnly(Side.CLIENT)
	public static void init() {
		LIKeyProcess.instance.addKey("dw_medkit", keyID, false, new MedkitUse());
	}
	
	private static class MedkitUse implements IKeyHandler {

		@Override
		public void onKeyDown(int keyCode, boolean isEnd) {
			if (isEnd)
				return;
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			if (player == null)
				return;
			Dawn47.netHandler.sendToServer(new MsgMedkitUse());
		}

		@Override
		public void onKeyUp(int keyCode, boolean isEnd) {
		}

		@Override
		public void onKeyTick(int keyCode, boolean tickEnd) {
		}

	}

}
