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
package cn.weaponry.impl.classic;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cn.liutils.util.helper.Motion3D;
import cn.liutils.util.mc.EntitySelectors;
import cn.liutils.util.raytrace.Raytrace;
import cn.weaponry.api.ItemInfo;
import cn.weaponry.api.action.Action;
import cn.weaponry.api.client.render.PartedModel;
import cn.weaponry.api.client.render.RenderInfo;
import cn.weaponry.api.client.render.RenderInfo.Animation;
import cn.weaponry.api.ctrl.KeyEventType;
import cn.weaponry.api.event.WeaponCallback;
import cn.weaponry.api.event.WpnEventLoader;
import cn.weaponry.api.item.WeaponBase;
import cn.weaponry.api.state.WeaponState;
import cn.weaponry.api.state.WeaponStateMachine;
import cn.weaponry.core.blob.SoundUtils;
import cn.weaponry.impl.classic.ammo.AmmoStrategy;
import cn.weaponry.impl.classic.ammo.ClassicAmmoStrategy;
import cn.weaponry.impl.classic.ammo.ClassicReloadStrategy;
import cn.weaponry.impl.classic.ammo.ReloadStrategy;
import cn.weaponry.impl.classic.client.animation.Muzzleflash;
import cn.weaponry.impl.classic.client.animation.Recoil;
import cn.weaponry.impl.classic.client.animation.ReloadAnimation;
import cn.weaponry.impl.classic.event.ClassicEvents.CanReload;
import cn.weaponry.impl.classic.event.ClassicEvents.CanShoot;
import cn.weaponry.impl.classic.event.ClassicEvents.ReloadEvent;
import cn.weaponry.impl.classic.event.ClassicEvents.ShootEvent;
import cn.weaponry.impl.classic.event.ClassicEvents.StartReloadEvent;
import cn.weaponry.impl.generic.action.ScatterUpdater;
import cn.weaponry.impl.generic.action.ScreenUplift;
import cn.weaponry.impl.generic.action.SwingSilencer;
import cn.weaponry.impl.generic.entity.EntityBullet;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * <code>WeaponClassic</code> provides a schema for a Half-Life like or CS-like weapon. <br/>
 * It has 3 fixed states:<br/>
 *  * shooting<br/>
 *  * reloading<br/>
 *  * idle<br/>
 * and a optional state: <br/>
 *  * action.(mouse right click to switch)
 * <br/><br/>
 * State diagram is given in state classes' descriptions.
 * <br/>
 * The ammoTyped field must be initialized before use, or it will crash MC when reloading.
 * <br/>
 * WARNING: This is a data heavy class. You would probably want to use a json loader to load its instances.
 * @author WeAthFolD
 * @see cn.weaponry.impl.classic.loading
 */
public class WeaponClassic extends WeaponBase {
	
	//Weapon basic info
	public int maxAmmo = 30;
	public Item ammoType;
	
	//Shooting
	public int shootInterval = 5;
	public int shootDamage = 10; //Per bullet
	public int shootBucks = 1; //How many bullets per shoot
	public String shootSound;
	public boolean isAutomatic = true;
	
	public double shootScatterMin = 0.2;
	public double shootScatterIncrement = 0.5;
	public double shootScatterStability = 1;
	public double movingScatterIncrement = 0.5;
	
	//Reloading
	public int reloadTime = 20;
	//Is the reloading state a 'reload one at a time' reloading style. (Used for stuffs like shotguns)
	public boolean isBuckReload;
	public String reloadStartSound;
	public String reloadEndSound;
	public String reloadAbortSound;
	
	//Misc
	public String jamSound;
	
	//Ammo strategies
	@LoaderExclude
	public AmmoStrategy ammoStrategy;
	@LoaderExclude
	public ReloadStrategy reloadStrategy;
	
	//Render data
	@SideOnly(Side.CLIENT)
	public ScreenUplift screenUplift;
	@SideOnly(Side.CLIENT)
	public Muzzleflash animMuzzleflash;
	@SideOnly(Side.CLIENT)
	public ReloadAnimation reloadAnim;
	@SideOnly(Side.CLIENT)
	public Recoil recoilAnim;
	
	/**
	 * This ctor is used for item loader. When use this explicitly call finishInit().
	 */
	public WeaponClassic() {
		ammoStrategy = new ClassicAmmoStrategy(this);
		reloadStrategy = new ClassicReloadStrategy(this);
		
		WpnEventLoader.load(this);
		
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			screenUplift = new ScreenUplift();
			animMuzzleflash = new Muzzleflash();
			reloadAnim = new ReloadAnimation();
			recoilAnim = new Recoil();
		}
	}
	
	public WeaponClassic(Item ammoType, int maxAmmo) {
		this.ammoType = ammoType;
		this.maxAmmo = maxAmmo;
	}
	
	@WeaponCallback(side = Side.CLIENT)
	@SideOnly(Side.CLIENT)
	public void onShootCli(ItemInfo item, ShootEvent event) {
		item.addAction(screenUplift.copy());
		RenderInfo.get(item).addAnimation(recoilAnim.copy());
		RenderInfo.get(item).addAnimation(animMuzzleflash.copy());
	}
	
	@WeaponCallback(side = Side.SERVER)
	public void onShootSvr(ItemInfo info, ShootEvent event) {
		World world = info.getWorld();
		EntityPlayer player = info.getPlayer();
		//System.out.println("OnShootSvr called");
		for(int i = 0; i < shootBucks; ++i) {
			Motion3D mo = new Motion3D(player, true);
			((ScatterUpdater)info.getAction("ScatterUpdater")).callShoot();
			double scatter = ((ScatterUpdater)info.getAction("ScatterUpdater")).getCurrentScatter();
			//System.out.println("callShoot" + scatter);
			mo.setMotionOffset(scatter);
			
			Vec3 start = mo.getPosVec(), end = mo.move(108).getPosVec();
			MovingObjectPosition trace = Raytrace.perform(world, start, end, EntitySelectors.excludeOf(player));
			if(trace != null && trace.typeOfHit == MovingObjectType.ENTITY) {
				trace.entityHit.hurtResistantTime = -1;
				trace.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), shootDamage);
			}
		}
	}
	
	@WeaponCallback(side = Side.CLIENT)
	@SideOnly(Side.CLIENT)
	public void spawnClientBullet(ItemInfo info, ShootEvent event) {
		World world = info.getWorld();
		for(int i = 0; i < shootBucks; ++i) {
			Entity spawn = createBulletEffect(info);
			if(spawn != null) {
				world.spawnEntityInWorld(spawn);
			}
		}
	}
	
	@WeaponCallback(side = Side.CLIENT)
	@SideOnly(Side.CLIENT)
	public void onReload(ItemInfo item, StartReloadEvent event) {
		RenderInfo.get(item).addAnimation(reloadAnim.copy());
	}


	@Override
	public void onInfoStart(ItemInfo info) {
		super.onInfoStart(info);
		info.addAction(new SwingSilencer());
		info.addAction(new ScatterUpdater());
		info.addAction(new Action() {

			@Override
			public void onTick(int tick) {
				int shootCount = itemInfo.dataTag().getInteger("shootCount");
				if(shootCount > 0)
					shootCount--;
				itemInfo.dataTag().setInteger("shootCount", shootCount);
			}
			
			@Override
			public String getName() {
				return "Miscs";
			}
			
		});
	}
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean idk) {
    	list.add(ammoStrategy.getDescription(stack));
    }

	@Override
	public void initStates(WeaponStateMachine machine) {
		machine.addState("idle", new StateIdle());
		machine.addState("reload", isBuckReload ? new StateBuckReload() : new StateReload());
		machine.addState("shoot", new StateShoot());
	}
	
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs cct, List list) {
    	ItemStack add = new ItemStack(item, 1, 0);
    	ammoStrategy.setAmmo(add, ammoStrategy.getMaxAmmo(add));
        list.add(add);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initDefaultAnims(RenderInfo render) {
		// An idle-swing effect
		render.addAnimation(new Animation() {
			@Override
			public void render(ItemInfo info, PartedModel model, boolean firstPerson) {
				long time = Minecraft.getSystemTime();
				double dx = 0.005 * Math.sin(time / 1000.0),
						dy = 0.01 * Math.sin(time / 700.0),
						dz = 0.012 * Math.sin(time / 1400.0);
				//System.out.println("Translate");
				GL11.glTranslated(dx, dy, dz);
			}
			
			@Override
			public boolean shouldRenderInPass(int pass) {
				return pass == 0;
			}
		});
		
		
	}
	
	/**
	 * Create the shooting entity to be spawned. The spawn is only down in client side.
	 */
	public Entity createBulletEffect(ItemInfo item) {
		((ScatterUpdater)item.getAction("ScatterUpdater")).callShoot();
		double scatter = ((ScatterUpdater)item.getAction("ScatterUpdater")).getCurrentScatter();
		return new EntityBullet(item.getPlayer(), scatter);
	}
	
	public class StateIdle extends WeaponState {
		//Idle->(Hold ML)->Shoot
		//Idle->(R)->Reload
		//Idle->(MR)->Action, if that state exists
		
		@Override
		public void onCtrl(int key, KeyEventType type) {
			if(key == 0 && (type == KeyEventType.DOWN || type == KeyEventType.TICK) ) {
				if(ammoStrategy.canConsume(getPlayer(), getStack(), 1)) {
					transitState("shoot");
				} else {
					SoundUtils.playBothSideSound(getPlayer(), jamSound);
				}
			} else if(key == 1 && type == KeyEventType.DOWN) {
				if(machine.hasState("action")) {
					transitState("action");
				}
			} else if(key == 2 && type == KeyEventType.DOWN) {
				transitState("reload");
			}
		}
	}
	
	public class StateReload extends WeaponState {
		//Solely a handler of ClassicReload action.
		//TODO: Wasty code?
		//Reload->(Any)->Idle
		
		@Override
		public void enterState() {
			WeaponClassic weapon = getWeapon();
			ReloadStrategy rs = weapon.reloadStrategy;
			if(post(getItem(), new CanReload())) {
				if(rs.canReload(getPlayer(), getStack())) {
					post(getItem(), new StartReloadEvent());
					SoundUtils.playBothSideSound(getPlayer(), reloadStartSound);
				} else {
					SoundUtils.playBothSideSound(getPlayer(), reloadAbortSound);
					transitState("idle");
				}
			} else {
				transitState("idle");
			}
		}
		
		@Override
		public void tickState(int tick) {
			if(tick == reloadTime) {
				if(post(getItem(), new CanReload())) {
					post(getItem(), new ReloadEvent());
					reloadStrategy.doReload(getPlayer(), getStack());
					SoundUtils.playBothSideSound(getPlayer(), reloadEndSound);
				}
				transitState("idle");
			}
		}
		
		@Override
		public void leaveState() {}
		
		@Override
		public void onCtrl(int key, KeyEventType type) {
			if(key != 2 && type == KeyEventType.DOWN) {
				transitState("idle");
			}
		}
	}
	
	public class StateBuckReload extends WeaponState {
		
		@Override
		public void tickState(int tick) {
			if(tick % reloadTime == 0) {
				// Load one buck
				boolean quit = false;
				if(post(getItem(), new CanReload())) {
					post(getItem(), new ReloadEvent());
					if(ammoStrategy.getAmmo(getStack()) == ammoStrategy.getMaxAmmo(getStack())) {
						quit = true;
					} else {
						if(!isRemote()) {
							reloadStrategy.doReload(getPlayer(), getStack());
						}
						SoundUtils.playBothSideSound(getPlayer(), reloadEndSound);
					}
				} else
					quit = true;
				
				if(quit)
					transitState("idle");
			}
		}
	}
	
	public class StateShoot extends WeaponState {
		//Shoot->(Release ML)->Idle
		
		@Override
		public void onCtrl(int key, KeyEventType type) {
			if(key == 0 && type == KeyEventType.UP) {
				transitState("idle");
			}
		}
		
		@Override
		public void enterState() {
		}
		
		@Override
		public void tickState(int tick) {
			int shootTick = getItem().dataTag().getInteger("shootTick");
			int time = machine.getTick();
			
			if(time - shootTick >= shootInterval) {
				if(tryShoot()) {
					getItem().dataTag().setInteger("shootTick", time);
				} else {
					transitState("idle");
				}
				if(!isAutomatic) {
					transitState("idle");
				}
			}
		}
		
		private boolean tryShoot() {
			if(!post(getItem(), new CanShoot()) || !ammoStrategy.canConsume(getPlayer(), getStack(), 1)) {
				return false;
			}
			
			post(getItem(), new ShootEvent());
			SoundUtils.playBothSideSound(getPlayer(), shootSound);
			if(!isRemote())
				ammoStrategy.consumeAmmo(getPlayer(), getStack(), 1);
			
			return true;
		}
	}

}
