package me.moon.sorin.korra.Erosion;

import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AirAbility;

public class Erosion extends AirAbility implements AddonAbility {
	
	private Location loc;
	private Location loc2;
	private Location loc3;
	
	private Player player;
	private Location start;
	private Vector dir;
	private Permission perm;
	private double x;
	private double y;
	private double z;
	
	private double x2;
	private double y2;
	private double z2;
	
	private double curveTime;
	
	private boolean curve;
	
	public Erosion(Player player) {
		super(player);
		this.player = player;
		this.start = player.getLocation();
		this.dir = player.getEyeLocation().getDirection().multiply(2);
		this.loc = player.getLocation();
		
		x = player.getEyeLocation().getX();
		y = player.getLocation().getY();
		z = player.getLocation().getZ();
		
		x2 = player.getEyeLocation().getX();
		y2 = player.getLocation().getY();
		z2 = player.getLocation().getZ();
		
		curve = true;
		
		start();
	}

	@Override
	public long getCooldown() {
		return 25000;
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public String getName() {
		return "Erosion";
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		
		
		
		
		
		this.loc.add(dir);
		this.loc2.add(dir);
		this.loc3.add(dir);
		if(curveTime < 30) {
			curveTime++;
		} else {
			curveTime--;
		}
		
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		
		if (GeneralMethods.isSolid((Block) this.loc.getBlock())) {
			remove();
			return;
		}
		
		if (this.loc.getBlock().isLiquid()) {
			remove();
			return;
		}
		
		if (this.loc.distance(this.start) > (double) 20) {
			remove();
			return;
		}
		
	}

	@Override
	public String getAuthor() {
		return "Moon243";
	}
	
	public void doParticlesLoc() {
		
	}
	
	public void doParticlesLoc2() {
		if(curveTime < 30 && curve) {
			x+= 0.1;
			y+= 0.1;
			z+= 0.1;
		} else if (!(curveTime < 30)) {
			curve = false;
		} else if(!(curveTime <= 0) && !curve) {
			x-= 0.1;
			y-=0.1;
			z-=0.1;
		}
		loc2.add(x, y, z);
	}
	
	public void doParticlesLoc3() {
		if(curveTime < 30 && curve) {
			x2-= 0.1;
			y2-= 0.1;
			z2-= 0.1;
		} else if (!(curveTime < 30)) {
			curve = false;
		} else if(!(curveTime <= 0) && !curve) {
			x2+= 0.1;
			y2+=0.1;
			z2+=0.1;
		}
		loc3.add(x2, y2, z2);
	}
	
	public void doExplosion() {
		
	}
	
	@Override
	public String getVersion() {
		return "v1.0";
	}

	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getLogger().log(Level.INFO, getName() + " " + getVersion() + " Developed By " + getAuthor() + " Has Been Enabled.");
		perm = new Permission("bending.ability.Erosion");
		perm.setDefault(PermissionDefault.TRUE);
		ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new ErosionListener(), ProjectKorra.plugin);
	}
		

	@Override
	public void stop() {
		ProjectKorra.plugin.getServer().getLogger().log(Level.INFO, getName() + " " + getVersion() + " Developed By " + getAuthor() + " Has Been Disabled.");
		ProjectKorra.plugin.getServer().getPluginManager().removePermission(perm);
		super.remove();
	}

}
