package cn.dawn47.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cn.annoreg.core.RegistrationClass;
import cn.annoreg.mc.RegMessageHandler;
import cn.dawn47.core.entitis.ExtendedPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@RegistrationClass
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
  
  @RegMessageHandler(msg = MsgSyncPlayerProps.class, side = RegMessageHandler.Side.CLIENT)
  public static class Handler implements IMessageHandler<MsgSyncPlayerProps, IMessage> {
      @Override
      @SideOnly(Side.CLIENT)
      public IMessage onMessage(MsgSyncPlayerProps message, MessageContext ctx) {
          EntityPlayer player = Minecraft.getMinecraft().thePlayer;
          ExtendedPlayer.get(player).loadNBTData(message.data);
          return null;
      }
  }
}