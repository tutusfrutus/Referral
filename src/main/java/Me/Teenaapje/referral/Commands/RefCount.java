package me.teenaapje.referral.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teenaapje.referral.utils.Utils;

public class RefCount extends CommandBase {
	// init class
	public RefCount() {
		 permission = "RefCount";
		 command = "Total";
		 forPlayerOnly = false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// check arguments
		if (args.length > 2) {
	        Utils.sendMessage(sender, core.config.tooManyArgs);
	        return false;
	    } else if (args.length < 2) {
	    	// check if is player
			if (Utils.isConsole(sender)) {
		        Utils.sendMessage(sender, core.config.missingPlayer);
				return false;
			}
			
			Utils.sendMessage((Player)sender, core.config.playerTotal);
	    } else {
	    	// check if the player is online
			Player target = core.getPlayer(args[1]);
	    	
			if (target == null) {
				Utils.sendMessage(sender, core.config.notOnline);
				return false;
			} else {
				Utils.sendMessage(sender, core.config.playerTotal, target);
				
			}
	    }	
		
		return true;
	}
}