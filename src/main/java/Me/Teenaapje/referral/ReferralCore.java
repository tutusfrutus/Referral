package me.teenaapje.referral;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.teenaapje.referral.commands.CommandManager;
import me.teenaapje.referral.database.Database;
import me.teenaapje.referral.placeholders.PlaceHolders;
import me.teenaapje.referral.utils.ConfigManager;
import me.teenaapje.referral.utils.Utils;

public class ReferralCore extends JavaPlugin {
	public static ReferralCore core;
	
	public ConfigManager config;
	public ReferralInvites rInvites;
	public ReferralMilestone milestone;
	public Database db;
	
	public void onEnable() {
		saveDefaultConfig();
		// set the plugin
		ReferralCore.core = this;
		
		// get the config
		config 		= new ConfigManager();
		rInvites 	= new ReferralInvites(); 
		milestone 	= new ReferralMilestone();
		db 			= new Database();
		
		// set placeholders if papi is there
		if (ConfigManager.placeholderAPIEnabled) {
			new PlaceHolders().register();
		}
		
		new CommandManager();
		
		new ReferralEvents();
		
		Utils.Console("[Referral] Initialized");
	}
	
	public void onDisable() {
		db.CloseConnection();
	}
	
	
	@SuppressWarnings("deprecation")
	public Player GetPlayer(String name) {
		Player player = this.getServer().getPlayer(name);
		if (player != null) {
			return player;
		}
		
		return getServer().getOfflinePlayer(name).getPlayer();
	}
	
	public void UseCommands(List<?> commands, Player player) {
		for (int i = 0; i < commands.size(); i++) {
			String command = (String) commands.get(i);
									
			getServer().getScheduler().runTask(this, new Runnable() {
				@Override
				public void run() {
					getServer().dispatchCommand(getServer().getConsoleSender(), command.replace("<player>", player.getName()));
				}
		    });
		}
	}
	
}
