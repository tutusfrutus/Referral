package me.teenaapje.referral.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teenaapje.referral.utils.Utils;

public class RefReject extends CommandBase {
	public RefReject() {
		 permission = "RefReject";
		 command = "Reject";
		 forPlayerOnly = true;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// check arguments
		if (args.length > 2) {
	        Utils.sendMessage(sender, core.config.tooManyArgs);
	        return false;
	    } else if (args.length < 2) {
	        Utils.sendMessage(sender, core.config.missingPlayer);
	        return false;
	    } 
		
		// check if the player wants to reject him self
		if (Utils.isPlayerSelf((Player)sender, args[1])) {
	        Utils.sendMessage(sender, core.config.rejectSomeone); // no need to reject yourself
	        return false;
		}
		
		Player reject = core.getPlayer(args[1]);
		
		// check if is in list
		if (!core.rInvites.isInList(sender.getName(), args[1])) {
	        Utils.sendMessage(sender, core.config.didntRef, reject);
			return false;
		}
		
		if (reject.isOnline()) {
			Utils.sendMessage(reject, core.config.playerGotRej, (Player)sender);
		}
		
		// remove from list
		core.rInvites.removeFromList(sender.getName(), args[1]);
				
		Utils.sendMessage(sender, core.config.playerRej, reject);
		
		return true;
	}
}