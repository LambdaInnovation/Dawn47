package cn.dawn47.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import cn.annoreg.core.Registrant;
import cn.annoreg.mc.RegMessageHandler;
import cn.annoreg.mc.RegMessageHandler.Side;
import cn.dawn47.core.entitis.ExtendedPlayer;
import cn.liutils.template.command.LICommandBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

@Registrant
public class MsgMedkitUse implements IMessage {

  private float containHP = 10;

  public MsgMedkitUse() {

  }

  @Override
  public void fromBytes(ByteBuf buf) {

  }

  @Override
  public void toBytes(ByteBuf buf) {

  }
  
  @RegMessageHandler(msg = MsgMedkitUse.class, side = Side.SERVER)
  public static class Handler implements IMessageHandler<MsgMedkitUse, IMessage> {

    @Override
    public IMessage onMessage(MsgMedkitUse msg, MessageContext ctx) {
      EntityPlayer thePlayer = ctx.getServerHandler().playerEntity;

      ExtendedPlayer props = ExtendedPlayer.get(thePlayer);
      int medkitCount = props.getMedkitCount();

      if (medkitCount > 0) {
        if (thePlayer.getHealth() < thePlayer.getMaxHealth()) {
          thePlayer.setHealth(thePlayer.getHealth() + msg.containHP);
          props.setMedkitCount(medkitCount - 1);
        }
      } else {
        LICommandBase.sendChat(thePlayer, "Insufficient Medkit!!");
      }

      return null;
    }

  }

}
