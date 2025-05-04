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
		
		Utils.logInfo("[Referral] Initialized");
	}
	
	public void onDisable() {
		db.closeConnection();
	}
	
	
	@SuppressWarnings("deprecation")
	public Player getPlayer(String name) {
		Player player = this.getServer().getPlayer(name);
		if (player != null) {
			return player;
		}
		
		return getServer().getOfflinePlayer(name).getPlayer();
	}
	
	public void useCommands(List<?> commands, Player player) {
        for (Object o : commands) {
            String command = (String) o;
            getServer().getScheduler().runTask(this, () ->
					getServer().dispatchCommand(getServer().getConsoleSender(),
							command.replace("<player>", player.getName())));
        }
	}
}
