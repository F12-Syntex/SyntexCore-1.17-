package com.base.cooldowns;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import com.base.main.Base;

public class Cooldown {

	protected class Consumer {
		private long timer;
		private UUID user;
		
		public Consumer(long timer, UUID user) {
			super();
			this.timer = timer;
			this.user = user;
		}
		
		public long getTimer() {
			return timer;
		}
		public void setTimer(long timer) {
			this.timer = timer;
		}
		public UUID getUser() {
			return user;
		}
		public void setUser(UUID user) {
			this.user = user;
		}
	}
	
	private String key;
	private final long initialTimer;
	private List<Consumer> consumers;

	private final BukkitRunnable schedular;
	
	public Cooldown(String key, long timer) {
		this.key = key;
		this.initialTimer = timer;
		this.consumers = new ArrayList<>();
		
		this.schedular = new BukkitRunnable() {
			@Override
			public void run() {
				for(Consumer uuid : consumers) {
					Cooldown.this.tick(uuid.getUser());
				}
			}
		};
		
		schedular.runTaskTimerAsynchronously(Base.getInstance(), 0L, 20L);
		
	}
	
	public boolean ready(UUID player) {
		return this.getConsumer(player).timer == 0;
	}
	
	public boolean readyAndResetIfAvailable(UUID player) {
		
		Consumer consumer = this.getConsumer(player);
		
		if(consumer.timer == 0) {
			this.reset(player);
			return true;
		}
		return false;
	}
	
	public void reset(UUID player) {
		getConsumer(player).timer = initialTimer;
	}
	
	public void tick(UUID player) {
		
		Consumer consumer = this.getConsumer(player);
		
		if(consumer.timer == 0) return;
		
		consumer.timer -= 1;
		if(consumer.timer < 0) {
			consumer.timer = 0;
		}
	}
	
	public String getKey(UUID player) {
		return key;
	}

	private boolean containsConsumer(UUID user) {
		return this.consumers.stream().filter(i -> user.compareTo(i.getUser()) == 0).count() > 0;
	}
	
	private void registerConsumer(UUID user) {
		Consumer consumer = new Consumer(initialTimer, user);
		this.consumers.add(consumer);
	}
	
	private Consumer getConsumer(UUID user) {
		if(!this.containsConsumer(user)) {
			this.registerConsumer(user);
		}
		return this.consumers.stream().filter(i -> user.compareTo(i.getUser()) == 0).findFirst().get();
	}
	
	public long getTimer(UUID player) {
		return getConsumer(player).timer;
	}

}
