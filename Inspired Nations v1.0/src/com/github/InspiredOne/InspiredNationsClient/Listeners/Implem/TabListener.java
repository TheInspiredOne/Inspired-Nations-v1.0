package com.github.InspiredOne.InspiredNationsClient.Listeners.Implem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNationsClient.Listeners.InspiredListener;
import com.github.InspiredOne.InspiredNationsClient.Listeners.TabManager;

public class TabListener<T extends TabManager<?>> extends InspiredListener<T> {

	public TabListener(T manager) {
		super(manager);
		
	}
	
	@EventHandler
	public void onChatTabPress(PlayerChatTabCompleteEvent event) {
		if(!this.getPlayerData().getPlayerID().equals(new PlayerID(event.getPlayer()))) {
			return;
		}
		event.getTabCompletions().clear();
		this.getManager().preTabEntry = event.getLastToken();
		this.getManager().Update();

		
	}
	
}
