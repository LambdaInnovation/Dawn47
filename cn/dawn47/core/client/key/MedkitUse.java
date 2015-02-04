package cn.dawn47.core.client.key;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cn.dawn47.Dawn47;
import cn.dawn47.core.network.MsgMedkitUse;
import cn.liutils.api.key.IKeyHandler;

public class MedkitUse implements IKeyHandler {

  @Override
  public void onKeyDown(int keyCode, boolean isEnd) {
      if(isEnd)
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