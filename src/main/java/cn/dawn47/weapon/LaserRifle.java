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

import net.minecraft.entity.Entity;
import cn.dawn47.weapon.entity.EntityLaserDelayed;
import cn.weaponry.api.ItemInfo;

/**
 * @author WeAthFolD
 *
 */
public class LaserRifle extends DawnWeapon {

	public Entity createShootEntity(ItemInfo item) {
		return new EntityLaserDelayed(item.getPlayer());
	}
	
}
