package cn.dawn47.core.client.key;

import org.lwjgl.input.Keyboard;

import cn.annoreg.core.Registrant;
import cn.dawn47.Dawn47;
import cn.dawn47.core.network.MsgMedkitUse;
import cn.liutils.registry.KeyHandlerRegistration.RegKeyHandler;
import cn.liutils.util.helper.KeyHandler;

@Registrant
public class MedkitUse extends KeyHandler {
	
	@RegKeyHandler(name = "dw_useMedkit", keyID = Keyboard.KEY_B)
	public static MedkitUse instance;

  @Override
  public void onKeyDown() {
      Dawn47.netHandler.sendToServer(new MsgMedkitUse());
  }
  
}