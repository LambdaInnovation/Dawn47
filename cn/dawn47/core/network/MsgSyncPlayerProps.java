package cn.dawn47.core.network;

import cn.dawn47.core.entitis.ExtendedPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;


public class MsgSyncPlayerProps implements IMessage
{

  private NBTTagCompound data;

  public MsgSyncPlayerProps() {}

  public MsgSyncPlayerProps(EntityPlayer player) {
      data = new NBTTagCompound();
      ExtendedPlayer.get(player).saveNBTData(data);
  }

  @Override
  public void fromBytes(ByteBuf buffer) {
      data = ByteBufUtils.readTag(buffer);
  }

  @Override
  public void toBytes(ByteBuf buffer) {
      ByteBufUtils.writeTag(buffer, data);
  }
  
  public static class Handler implements IMessageHandler<MsgSyncPlayerProps, IMessage> {
      @Override
      public IMessage onMessage(MsgSyncPlayerProps message, MessageContext ctx) {
          EntityPlayer player = ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer :  ctx.getServerHandler().playerEntity;
          ExtendedPlayer.get(player).loadNBTData(message.data);
          return null;
      }
  }
}