package cn.dawn47.core.item;

import net.minecraft.item.Item;
import cn.dawn47.Dawn47;

public class DWGenericItem extends Item {

    public DWGenericItem() {
        super();
        setCreativeTab(Dawn47.cct);
    }

    public DWGenericItem setIAndU(String name) {
        setTextureName("dawn47:" + name);
        setUnlocalizedName(name);
        return this;
    }

    public DWGenericItem setStackAndDamage(int stack, int damage) {
        setMaxStackSize(stack);
        setMaxDamage(damage);
        return this;
    }
    

}
