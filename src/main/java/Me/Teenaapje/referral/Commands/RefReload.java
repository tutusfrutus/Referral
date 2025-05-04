package me.teenaapje.referral.commands;

import me.teenaapje.referral.database.Database;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.teenaapje.referral.ReferralInvites;
import me.teenaapje.referral.utils.ConfigManager;
import me.teenaapje.referral.utils.Utils;

public class RefReload extends CommandBase {
	// init class
	public RefReload() {
		 permission = "RefReload";
		 command = "Reload";
		 forPlayerOnly = false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// reload config
		core.reloadConfig();
		core.milestone.loadRewards();
		
		// reload things
		core.db.closeConnection();
		core.db = new Database();
		core.rInvites = new ReferralInvites();		
		core.config = new ConfigManager();
				
		Utils.sendMessage(sender, "&6[Referral] &fReloaded");
		return true;
	}
}