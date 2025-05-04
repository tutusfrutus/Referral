package me.teenaapje.referral.utils;

import me.teenaapje.referral.ReferralCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import me.clip.placeholderapi.PlaceholderAPI;

public class Utils {	
	// send message to player
	public static boolean sendMessage(CommandSender sendTo, String text) {
		return sendMessage(sendTo, text, null);
	}
	public static boolean sendMessage(CommandSender sendTo, String text, Player playerPlaceholder) {
		try {
			// get placeholder of other player
			if (playerPlaceholder != null) {
				text = setPlaceholders(playerPlaceholder, text);
			}
			
			// colorcode and send
			sendTo.sendMessage(colorCode(text));
			
			return true;
		} catch (Exception e) {
			Bukkit.getLogger().warning("Something went wrong sending message to player with exception: " + e);
			return false;
		}
	}
	
	public static boolean sendMessage(Player player, String text) {
		return sendMessage(player, text, null);
	}
	public static boolean sendMessage(Player player, String text, Player playerPlaceholder) {
		try {
			// get placeholder of other player
			if (playerPlaceholder != null) {
				text = setPlaceholders(playerPlaceholder, text);
			} else {
				text = setPlaceholders(player, text);
			}
			
			// colorcode and send
			player.sendMessage(colorCode(text));
			
			return true;
		} catch (Exception e) {
			Bukkit.getLogger().warning("Something went wrong sending message to player with exception: " + e);
			return false;
		}
	}
	
	public static String setPlaceholders(Player player, String text) {
		if (ConfigManager.placeholderAPIEnabled) {
			return PlaceholderAPI.setPlaceholders(player, text);
		}
		
		return text.replace("%referral_total%", Integer.toString(ReferralCore.core.db.getReferrals(player.getUniqueId().toString(), player.getName())))
				   .replace("%referral_refed%", String.valueOf(ReferralCore.core.db.playerReferred(player.getUniqueId().toString(), player.getName())))
				   .replace("%referral_referred_by%",ReferralCore.core.db.playerReferredByName(player.getUniqueId().toString()));
	}
	
	/// Color code text
	public static String colorCode(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	// create text component
	public static TextComponent createTextComponent(String text, ChatColor color, boolean bold, ClickEvent.Action action, String runCommand) {
		TextComponent component = new TextComponent(text);
		component.setColor(color);
		component.setBold(bold);
		component.setClickEvent(new ClickEvent(action, runCommand));
		return component;
	}
	
	// check if its the same player
	public static boolean isPlayerSelf(Player player, String name) {
		return player.getName().toLowerCase().compareTo(name.toLowerCase()) == 0;
	}
	public static boolean isPlayerSelf(Player a, Player b) {
		return a == b;
	}
	
	public static boolean isConsole(CommandSender sender) {
		return !(sender instanceof Player);
	}

	public static void logInfo(String text) {
		Bukkit.getLogger().info("[Referral] - " + text);
	}

	public static void logInfo(String text, String exception) {
		Bukkit.getLogger().info("[Referral] - " + text + " throws the following stacktrace: " + exception);
	}

	public static void logError(String text) {
		Bukkit.getLogger().warning("[Referral] - " + text);
	}

	public static void logError(String text, String exception) {
		Bukkit.getLogger().info("[Referral] - " + text + " throws the following stacktrace: " + exception);
	}

}
