package me.teenaapje.referral;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class ReferralMilestone {
	ReferralCore core = ReferralCore.core;
	
	public List<Reward> rewards;

	public ReferralMilestone() {
		rewards = new ArrayList<>();
		loadRewards();
	}
	
	public void loadRewards() {
		rewards.clear();
		
		for (String key : core.config.rewards.getKeys(false)) {
			Reward reward = new Reward (
				core.config.config.getInt("rewards."+key+".min"),
				core.config.config.getStringList("rewards."+key+".commands")
			);
			rewards.add(reward);
		}
	}
	
	public boolean hasReward(int lastReward, int referred) {
		for (Reward reward : rewards) {
			if (reward.min == referred && lastReward < referred) {
				return true;
			} else if (reward.min == referred) {
				return false;
			}
		}		
		return false;
	}
	
	public List<String> getRewards(int referTotal) {
		for (Reward reward : rewards) {
			if (reward.min == referTotal) {
				return reward.commands;
			}
		}
		// this should not be able to return null if so the HasAReward function isnt working correct
		Bukkit.getLogger().warning("[Referral] Rewards are Null!");
		return null;
	}
}

class Reward {
    public int min;
	public List<String> commands;
 
	public Reward (int min,	List<String> commands){
        this.min = min;
        this.commands = commands;
    }
}