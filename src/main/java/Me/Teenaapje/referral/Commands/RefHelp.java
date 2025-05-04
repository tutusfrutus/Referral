package me.teenaapje.referral.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.teenaapje.referral.utils.Utils;

public class RefHelp extends CommandBase {
	public RefHelp() {
		 permission = "";
		 command = "Help";
		 forPlayerOnly = false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Utils.sendMessage(sender, "&6== &7Referral Help &6==");
		Utils.sendMessage(sender, "&7/Ref <Player> - &6Referrals a players and gives rewards");
		Utils.sendMessage(sender, "&7/Ref Total - &6total refers of self");
		Utils.sendMessage(sender, "&7/Ref Total <Player> - &6total refers of a player");
		Utils.sendMessage(sender, "&7/Ref Accept <Player> - &6Accept a players refer");
		Utils.sendMessage(sender, "&7/Ref Reject <Player> - &6reject a players refer");

		// check if has perm or console
		if ((sender instanceof Player) && !sender.hasPermission("Referral.Admin")) {
			return true;
		}
		
		Utils.sendMessage(sender, "&7/Ref Admin Reset <Player> - &6Resets player referral in database");
		Utils.sendMessage(sender, "&7/Ref Admin Remove <Player> - &6Removes player referral in database");
		return true;
	}
}