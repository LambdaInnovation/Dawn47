package cn.dawn47.core.entitis;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import cn.annoreg.mc.network.RegNetworkCall;
import cn.annoreg.mc.s11n.StorageOption.Data;
import cn.annoreg.mc.s11n.StorageOption.Target;
import cn.dawn47.Dawn47;
import cpw.mods.fml.relauncher.Side;

public class ExtendedPlayer implements IExtendedEntityProperties {

  public final static String EXT_PROP_NAME = "Dawn47ExtendedPlayer";

  private final EntityPlayer player;

  private int medkitCount;

  public ExtendedPlayer(EntityPlayer player) {
    this.player = player;
    medkitCount = 0;
  }

  public int getMedkitCount() {
    return medkitCount;
  }

  public void setMedkitCount(int medkitCount) {
    this.medkitCount = medkitCount;
    sync();
  }

  public static final void register(EntityPlayer player) {
    player.registerExtendedProperties(ExtendedPlayer.EXT_PROP_NAME, new ExtendedPlayer(player));
  }

  public static final ExtendedPlayer get(EntityPlayer player) {
    return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
  }

  @Override
  public void saveNBTData(NBTTagCompound compound) {
    NBTTagCompound properties = new NBTTagCompound();
    properties.setInteger("MedkitCount", medkitCount);
    compound.setTag(EXT_PROP_NAME, properties);

  }

  @Override
  public void loadNBTData(NBTTagCompound compound) {
    NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);

    if (properties == null) {
      properties = new NBTTagCompound();
      compound.setTag(EXT_PROP_NAME, properties);
    }
    medkitCount = properties.getInteger("MedkitCount");
  }

  @Override
  public void init(Entity entity, World world) {}

  public void sync() {
	  NBTTagCompound tag = new NBTTagCompound();
	  saveNBTData(tag);
	  receiveSync(this.player, tag);
  }
  
  @RegNetworkCall(side = Side.CLIENT)
  private static void receiveSync(@Target EntityPlayer player, @Data NBTTagCompound tag) {
	  ExtendedPlayer.get(player).loadNBTData(tag);
  }

}
