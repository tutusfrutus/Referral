package me.teenaapje.referral;

import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.entity.Player;

import me.teenaapje.referral.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class ReferralInvites {
	// list of invites
	ArrayList<Refer> referInvites;
	// the core
	ReferralCore core = ReferralCore.core;
	
	// init ReferralInvites
	public ReferralInvites () {
		referInvites = new ArrayList<>();
	}
	
	// add and send invite
	public void addToList(String to, String from) {
		Player fromPlayer = core.getPlayer(from);
		
		// check if already exists
		if (isInList(to, from)) {
			Utils.sendMessage(fromPlayer, core.config.alreadySendRef);
			return;
		}
		
		// check if it is the same player
		Player toPlayer = core.getPlayer(to);
		if (fromPlayer == toPlayer) {
			//refp.sendMessage(Utils.chatConsole(main.util.referSelf));
			Utils.sendMessage(fromPlayer, core.config.alreadySendRef);
			return;
		}
		
		// add to list
		referInvites.add(new Refer(to, from));
		
		// get the buttons
		TextComponent accept  = Utils.createTextComponent(core.config.accept, ChatColor.GREEN, true, ClickEvent.Action.RUN_COMMAND, "/ref accept " + from);
		TextComponent decline = Utils.createTextComponent(core.config.decline,  ChatColor.RED, true, ClickEvent.Action.RUN_COMMAND, "/ref reject " + from);
		
		// send invite
		Utils.sendMessage(toPlayer, core.config.youGotRefer, fromPlayer);
		toPlayer.spigot().sendMessage(accept, decline);
		
		// notify that it has been sned
		Utils.sendMessage(fromPlayer, core.config.youSendRequest, toPlayer);

	}
	
	// remove from list
	public void removeFromList(String ref, String refer) {
		Iterator<Refer> itr = referInvites.iterator();  
        while(itr.hasNext()){  
        	Refer st= itr.next();
            if (st.ref.contains(ref) && st.refer.contains(refer)) {
            	itr.remove();
            	return;
			}
        }

	}
	
	// is in list
	public boolean isInList(String ref, String refer) {
        for (Refer st : referInvites) {
            if (st.ref.toLowerCase().compareTo(ref.toLowerCase()) == 0 && st.refer.toLowerCase().compareTo(refer.toLowerCase()) == 0) {
                return true;
            }
        }     
        return false;
	}
}


class Refer {
	String ref;
	String refer;
	
	Refer (String _ref, String _refer) {
		this.ref = _ref;
		this.refer = _refer;
	}
}
