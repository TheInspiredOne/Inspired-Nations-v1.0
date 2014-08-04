package com.github.InspiredOne.InspiredNations.ToolBox.Messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNations.Hud.MenuUpdateEvent;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;

public class MessageManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5200407053459680313L;
	
	
	public ArrayList<Alert> messages = new ArrayList<Alert>();
	private PlayerData PDI;
	transient BukkitRunnable Timer;
	private int histLength = 50;
	public int missedSize = 0;
	public MessageManager(PlayerData PDI) {
		this.PDI = PDI;
	}
	
	public void receiveAlert(Alert alert, Boolean refresh) {
		boolean incremented = false;
		for(Alert alertTemp:messages) {
			if(!alertTemp.expired() && alert.getMessage(PDI).equals(alertTemp.getMessage(PDI))) {
				alertTemp.incrementStack();
				incremented = true;
				break;
			}
		}
		if(!incremented) {
			messages.add(alert);
		}
		Collections.sort(messages, Alert.ageSort.getComparator());
		
		while(messages.size() > histLength) {
			messages.remove(0);
		}
		
		pushMessage(refresh);
	}
	public void receiveError(final String msg) {
		Error error = new Error() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 9202786487465493637L;

			@Override
			public String getMessage(PlayerData receiver) {
				return msg;
			}

		};
		if(!msg.isEmpty()) {
			this.receiveAlert(error, false);
		}
	}
	
	public String pushMessageContent() {
		String output = "";
		try {
			Player player = PDI.getPlayer();
			
			for(Alert alert:messages) {
				if((alert.menuVisible && !alert.expired()) || !player.isConversing()) {
					output = output.concat(alert.getDisplayName(PDI) + "\n");
				}
			}

		} catch (PlayerOfflineException e) {
 
		}
		return output;
	}
	
	public void pushMessage(boolean refresh) {
		try {
			String output = messages.get(messages.size() -1).getDisplayName(PDI);
			if(PDI.getPlayer().isConversing()) {
				output = this.pushMessageContent();
				this.missedSize++;
			}
			if(!output.isEmpty()) {
				PDI.getPlayer().sendMessage(output);
				if(refresh) {
					MenuUpdateEvent event = new MenuUpdateEvent(PDI.getPlayerID());
					Bukkit.getServer().getPluginManager().callEvent(event);
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendChatMessage(String msg) {
		InspiredNations.plugin.logger.info(this.PDI.getName() + ": " + msg);
		for(PlayerID playerid:InspiredNations.playerdata.keySet()) {
			try {
				Player player = playerid.getPDI().getPlayer();
				if(playerid.equals(PDI.getPlayerID())) {
					playerid.getPDI().getMsg().receiveAlert(new Message(true, PDI.getPlayerID(), msg), true);
				}
				else {
					playerid.getPDI().getMsg().receiveAlert(new Message(playerid.getPDI().chatState, PDI.getPlayerID(), msg), true);
				}
			}
			catch (Exception ex) {
				
			}
		}
		
	}

	public void clearMenuVisible() {
		List<Error> remove = new ArrayList<Error>();
		for(Alert alert:messages) {
			if(alert.menuVisible) {
				alert.expired = true;
			}
			if(alert instanceof Error) {
				remove.add((Error) alert);
			}
		}
		for(Error error:remove) {
			messages.remove(error);
		}
	}
}
