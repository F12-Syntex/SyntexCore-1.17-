package com.base.cooldowns;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

public class CooldownManager {

	private List<Cooldown> cooldowns;
	
	public CooldownManager() {
		this.cooldowns = new ArrayList<>();
	}
	
	public void registerCooldownEntity(Cooldown cooldown) {
		this.cooldowns.add(cooldown);
	}
	
	public Cooldown getCooldown(UUID player, String key) {
		return this.cooldowns.stream().filter(i -> i.getKey(player).equals(key)).findFirst().get();
	}
	
	public Cooldown getCooldown(Player player, String key) {
		return this.cooldowns.stream().filter(i -> i.getKey(player.getUniqueId()).equals(key)).findFirst().get();
	}
	
}
