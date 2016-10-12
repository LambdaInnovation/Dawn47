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

import java.util.HashMap;
import java.util.Map;

import cn.weaponry.api.action.Action;
import cn.weaponry.api.ctrl.KeyEventType;
import cn.weaponry.core.Weaponry;

/**
 * @author WeAthFolD
 */
public class WeaponStateMachine extends Action {
	
	Map<String, WeaponState> states = new HashMap();
	
	int startTick = 0;
	
	WeaponState currentState = null;
	boolean init = false;

	public void transitState(String name) {
		currentState.leaveState();
		
		currentState = getState(name);
		init = false;
		startTick = this.getTick();
		//Weaponry.log.info("S->" + name + " #" + isRemote());
	}
	
	public void addState(String name, WeaponState state) {
		if(states.containsKey(name)) {
			throw new RuntimeException("Duplicate state name " + name);
		}
		
		if(currentState == null) {
			currentState = state;
		}
		state.machine = this;
		states.put(name, state);
	}
	
	/**
	 * Must be called AFTER all state were loaded.
	 */
	public void setInitState(String n) {
		currentState = getState(n);
	}
	
	public WeaponState getState(String name) {
		return states.get(name);
	}
	
	public boolean hasState(String name) {
		return states.containsKey(name);
	}
	
	public void onCtrl(int keyID, KeyEventType type) {
		//System.out.println("onCtrl[" + keyID + "] " + type);
		currentState.onCtrl(keyID, type);
	}
	
	@Override
	public void onTick(int tick) {
		if(!init) {
			currentState.enterState();
			startTick = tick;
			init  = true;
		}
		currentState.tickState(tick - startTick);
	}
	
	@Override
	public void onFinalize() {
		currentState.leaveState();
	}
	
	public WeaponState getCurrentState() {
		return currentState;
	}

	@Override
	public String getName() {
		return "StateMachine";
	}
	
}
