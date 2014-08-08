package com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPlayerDataInter;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Log;
import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.Remotes.MessageManagerInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;

public class MessageManager implements Serializable, MessageManagerInter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5200407053459680313L;
	
	
	private ArrayList<Alert> messages = new ArrayList<Alert>();
	private PlayerData PDI;
	transient BukkitRunnable Timer;
	private int histLength = 50;
	private int missedSize = 0;
	public MessageManager(PlayerData PDI) {
		this.PDI = PDI;
	}
	
	public ArrayList<Alert> getMessages() {
		return messages;
	}
	
	public void receiveAlert(Alert alert, boolean refresh) throws RemoteException {
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
	public void receiveError(final String msg) throws RemoteException {
		Error error = new Error() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 9202786487465493637L;

			@Override
			public String getMessage(PlayerDataInter receiver) {
				return msg;
			}

		};
		if(!msg.isEmpty()) {
			this.receiveAlert(error, false);
		}
	}
	
	@Override
	public String pushMessageContent() throws RemoteException {
		String output = "";
		try {
			ClientPlayerDataInter player = PDI.getPlayer();
			
			for(Alert alert:messages) {
				if((alert.menuVisible && !alert.expired()) || !player.isConversing()) {
					output = output.concat(alert.getDisplayName(PDI.getId()) + "\n");
				}
			}

		} catch (PlayerOfflineException e) {
 
		}
		return output;
	}
	
	public void pushMessage(boolean refresh) {
		try {
			String output = messages.get(messages.size() -1).getDisplayName(PDI.getId());
			if(PDI.getPlayer().isConversing()) {
				output = this.pushMessageContent();
				this.missedSize++;
			}
			if(!output.isEmpty()) {
				PDI.getPlayer().sendMessage(output);
				if(refresh) {
//					MenuUpdateEvent event = new MenuUpdateEvent(PDI.getPlayerID());
//					Bukkit.getServer().getPluginManager().callEvent(event);
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendChatMessage(String msg) throws RemoteException {
		Log.info(this.PDI.getName() + ": " + msg);
		for(PlayerData pdi:InspiredNationsServer.playerdata.values()) {
			try {
				@SuppressWarnings("unused")
				ClientPlayerDataInter player = pdi.getPlayer();
				if(pdi.equals(PDI)) {
					pdi.getMsg().receiveAlert(new Message(true, PDI, msg), true);
				}
				else {
					pdi.getMsg().receiveAlert(new Message(pdi.chatState, PDI, msg), true);
				}
			}
			catch (PlayerOfflineException ex) {
				
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

	@Override
	public int getMissedSize() throws RemoteException {
		return this.missedSize;
	}

	@Override
	public void setMissedSize(int size) throws RemoteException {
		this.missedSize = size;
	}
}
