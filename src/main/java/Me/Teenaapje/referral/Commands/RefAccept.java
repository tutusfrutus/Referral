package me.teenaapje.referral.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teenaapje.referral.utils.ConfigManager;
import me.teenaapje.referral.utils.Utils;

public class RefAccept extends CommandBase {
	public RefAccept() {
		 permission = "RefAccept";
		 command = "Accept";
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
		
		Player player = (Player)sender;
		Player target = core.getPlayer(args[1]);
				
		// is it the same
		if (Utils.isPlayerSelf(player, target)) {
			Utils.sendMessage(player, core.config.acceptSelf);
			return false;
		}
		
		// check if in list
		if (!core.rInvites.isInList(player.getName(), args[1])) {
			Utils.sendMessage(player, core.config.didntRef, target);
			return false;
		}
		
		// check if online
		if (!target.isOnline()) {
			Utils.sendMessage(player, core.config.notOnline, target);
	        return false;
		}	
		
		// Check if player already referred a player 
		if (core.db.playerReferred(target.getUniqueId().toString(), target.getName())) {
			Utils.sendMessage(player, core.config.alreadyRefed);
	        return false;
		}
		
		try { 
	    	core.db.referralPlayer(target, player);
	    	
	    	// send msg to the one that send request
			Utils.sendMessage(player, core.config.playerRef, target);
			
			// send the accept msg
			Utils.sendMessage(target, core.config.playerAcceptedRef, player);

			// give rewards
		    core.useCommands(ConfigManager.playerRefers, player);
		    core.useCommands(ConfigManager.playerReferd, target);

		    // check if the server wants to user milestone rewards
			if (ConfigManager.useMileStoneRewards) {
				// get the players info
				String playerUUID = player.getUniqueId().toString();
				String playerName = player.getName();

				int playerLastReward = core.db.getLastReward(playerUUID, playerName);
				int playerReferrals = core.db.getReferrals(playerUUID, playerName);

				// check if he has a new milestone reward
				if (core.milestone.hasReward(playerLastReward, playerReferrals)) {
					core.useCommands(core.milestone.getRewards(playerReferrals), player);
				}
			}
			core.rInvites.removeFromList(player.getName(), args[1]);
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return true;
	}
}