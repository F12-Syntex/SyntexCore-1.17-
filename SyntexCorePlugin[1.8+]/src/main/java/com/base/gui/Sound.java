package com.base.gui;

public class Sound {

	private org.bukkit.Sound sound;
	private float pitch;
	private float volume;
	
	public Sound(org.bukkit.Sound sound, float pitch, float volume) {
		this.sound = sound;
		this.pitch = pitch;
		this.volume = volume;
	}
	
	public org.bukkit.Sound getSound() {
		return sound;
	}
	public void setSound(org.bukkit.Sound sound) {
		this.sound = sound;
	}
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
}
