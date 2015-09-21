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
package cn.dawn47.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import cn.dawn47.Dawn47;
import cn.liutils.util.generic.VecUtils;
import cn.liutils.util.mc.EntitySelectors;
import cn.liutils.util.raytrace.Raytrace;
import cn.weaponry.api.ctrl.KeyEventType;
import cn.weaponry.api.state.WeaponState;
import cn.weaponry.api.state.WeaponStateMachine;
import cn.weaponry.impl.classic.WeaponClassic;
import cn.weaponry.impl.generic.action.SwingSilencer;

/**
 * @author WeAthFolD
 *
 */
public class DawnWeapon extends WeaponClassic {
	
	public float stockDamage = 3.0f;

	public DawnWeapon() {
		super();
		setCreativeTab(Dawn47.cct);
	}
	
	@Override
	public void initStates(WeaponStateMachine machine) {
		super.initStates(machine);
		machine.addState("action", new StateStockAttack());
	}
	
	public class StateStockAttack extends WeaponState {
		
		public void enterState() {
			SwingSilencer silencer = getItem().getAction("SwingSilencer");
			if(silencer != null) {
				silencer.active = false;
			}
			EntityPlayer player = getPlayer();
			player.swingItem();
			
			if(!isRemote()) {
				Vec3 vec1 =	Vec3.createVectorHelper(player.posX, player.posY + player.eyeHeight, player.posZ);
				Vec3 vec2 = VecUtils.add(vec1, VecUtils.multiply(player.getLookVec(), 1.5));
				MovingObjectPosition ret = Raytrace.perform(player.worldObj, vec1, vec2, EntitySelectors.excludeOf(player));
				if(ret != null && ret.typeOfHit == MovingObjectType.ENTITY) {
					ret.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), stockDamage);
					player.worldObj.playSoundAtEntity(player, "dawn47:weapons.stock_attack", 0.5f, 1.0f);
				} else
					player.worldObj.playSoundAtEntity(player, "dawn47:weapons.stock_swing", 0.5f, 1.0f);
			}
		}
		
		@Override
		public void onCtrl(int key, KeyEventType type) {
			//transitState("idle");
		}
		
		@Override
		public void tickState(int ticks) {
			if(ticks == 15) {
				transitState("idle");
			}
		}
		
 		public void leaveState() {
			SwingSilencer silencer = getItem().getAction("SwingSilencer");
			if(silencer != null) {
				silencer.active = true;
			}
		}
 		
	}

}
