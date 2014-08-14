package cn.dawn47.weapon.dual;

import cn.dawn47.core.register.DWItems;

public class Dual_Handgun extends DWGeneralDualWield{

	public Dual_Handgun(int par1) {
		super(par1, DWItems.handgun_ammo.itemID);
		setIAndU("handgun_dual");
		iconName = "none";
		setLiftProps(5F, 0.7F);
		this.reloadTime = 45;
		setCapacityPerWeapon(8);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getOffset(boolean)
	 */
	@Override
	public int getOffset(boolean left) {
		return 4;
	}
	
	@Override
	public int getWeaponDamage(boolean left) {
		return 4;
	}
	
	/**
	 * Get the shoot time corresponding to the mode.
	 * 
	 * @param mode
	 * @return shoot time
	 */
	public int getShootTime(boolean left) {
		return 4;
	}
	
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public String getSoundShoot(boolean left) {
		return "dawn47:weapons.glock.glock_fire" ;
	}
	
	/**
	 * Get the reload sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	@Override
	public String getSoundReload() {
		return "dawn47:weapons.glock.glock_magout";
	}
	
	@Override
	public String getSoundReloadFinish() {
		return "dawn47:weapons.glock.glock_magin";
	}

	
}
