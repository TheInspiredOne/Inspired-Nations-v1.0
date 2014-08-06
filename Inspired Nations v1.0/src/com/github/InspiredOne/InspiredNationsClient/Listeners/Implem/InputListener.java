package com.github.InspiredOne.InspiredNationsClient.Listeners.Implem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import com.github.InspiredOne.InspiredNationsClient.Listeners.InspiredListener;

public class InputListener<T extends InputManager<?>> extends InspiredListener<T> {

	public InputListener(T manager) {
		super(manager);
	}
	
	@EventHandler
	public void onPlayerTabComplete(PlayerChatTabCompleteEvent event) {
		if(this.getManager().getTabOptions().size() > 0) {
			event.getTabCompletions().clear();
			event.getTabCompletions().addAll(this.getManager().getTabOptions());
			this.getManager().Update();
		}
	}
	
}
