/**
 * Copyright (C) Lambda-Innovation, 2013-2014
 * This code is open-source. Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 */
package cn.dawn47.action;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.action.Action;
import cn.weaponmod.api.information.InfUtils;
import cn.weaponmod.api.information.InfWeapon;

/**
 * @author WeAthFolD
 *
 */
public class ActionButtAttack extends Action {
	
	private String sound_hit;
	private float damage;

	/**
	 * @param ticks
	 * @param name
	 */
	public ActionButtAttack(String sound, float dmg) {
		super(20, "butt_attack");
		sound_hit = sound;
		damage = dmg;
	}
	
	public boolean onActionBegin(World world, EntityPlayer player, InfWeapon inf) { 
		tickLeft = maxTick;
		
		if(InfUtils.getDeltaTick(inf, "butt") > 10) {
			player.playSound(sound_hit, 0.5F, 1.0F);
			WeaponHelper.doPlayerAttack(player, damage);
			player.swingItem();
			inf.updateTicker("butt");
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.action.Action#getPriority()
	 */
	@Override
	public int getPriority() {
		return 2;
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.action.Action#doesConcurrent(cn.weaponmod.api.action.Action)
	 */
	@Override
	public boolean doesConcurrent(Action other) {
		return false;
	}
	
	@Override
	public boolean needSwing() { 
		return true;
	}

}
