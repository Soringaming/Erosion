package me.moon.korra.Erosion;

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
	private Player player;
	private Location start;
	private Vector dir;
	private Permission perm;
	
	public Erosion(Player player) {
		super(player);
		this.player = player;
		this.start = player.getLocation();
		this.dir = player.getEyeLocation().getDirection().multiply(2);
		this.loc = player.getLocation();
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
	
	public void doParticles() {
		
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
