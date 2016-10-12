/**
 * Copyright (c) Lambda Innovation, 2013-2015
 * 本作品版权由Lambda Innovation所有。
 * http://www.li-dev.cn/
 *
 * This project is open-source, and it is distributed under  
 * the terms of GNU General Public License. You can modify
 * and distribute freely as long as you follow the license.
 * 本项目是一个开源项目，且遵循GNU通用公共授权协议。
 * 在遵照该协议的情况下，您可以自由传播和修改。
 * http://www.gnu.org/licenses/gpl.html
 */
package cn.weaponry.api.state;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.ctrl.KeyEventType;
import cn.weaponry.api.item.WeaponBase;

/**
 * @author WeAthFolD
 */
public abstract class WeaponState {
	
	/**
	 * instance injected when added into WSM.
	 */
	protected WeaponStateMachine machine;
	
	public WeaponState() {}
	
	//---Event---
	public void enterState() {}
	
	public void leaveState() {}
	
	public void tickState(int tick) {}
	
	public void onCtrl(int key, KeyEventType type) {}
	
	//---Sandbox---

	public void transitState(String stateName) {
		machine.transitState(stateName);
	}
	
	public WeaponStateMachine stateMachine() {
		return machine;
	}
	
	public EntityPlayer getPlayer() {
		return machine.getPlayer();
	}

	public ItemStack getStack() {
		return machine.getStack();
	}
	
	public ItemInfo getItem() {
		return machine.itemInfo;
	}
	
	public World getWorld() {
		return machine.getWorld();
	}
	
	public <T extends WeaponBase> T getWeapon() {
		return (T) getStack().getItem();
	}
	
	public boolean isRemote() {
		return machine.isRemote();
	}
	
}
