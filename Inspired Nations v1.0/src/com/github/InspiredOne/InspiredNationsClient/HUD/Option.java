package com.github.InspiredOne.InspiredNationsClient.HUD;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.OptionUnavail;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public abstract class Option implements Nameable {

	private final String label;
	protected InspiredNationsClient plugin;
	protected OptionMenu menu;
	private String description;
	protected OptionUnavail reason;
	public Option(OptionMenu menu, String label, OptionUnavail reason) {
		this.menu = menu;
		this.label = label;
		this.plugin = this.menu.plugin;
		this.reason = reason;
		this.description = "";
	}
	public Option(OptionMenu menu, String label) {
		this.menu = menu;
		this.label = label;
		this.plugin = this.menu.plugin;
		this.reason = OptionUnavail.NOT_UNAVAILABLE;
		this.description = "";
	}
	public Option(OptionMenu menu, String label, String description) {
		this.menu = menu;
		this.label = label;
		this.plugin = this.menu.plugin;
		this.description = description;
		this.reason = OptionUnavail.NOT_UNAVAILABLE;
	}
	/**
	 * Interprets the input and executes the associated task, then returns
	 * the next Menu for the conversation.
	 * 
	 * @param input	the String to be interpreted for determining the output
	 * @return		the Menu that the conversation should proceed to next
	 * @throws RemoteException 
	 */
	public abstract Menu response(String input) throws RemoteException;
	
	/**
	 * Gets the label that is to be used within the menu.
	 * 
	 * @return	the String as it should appear on the menu
	 */
	@Override
	public String getName() {
		return this.label;
	}
	@Override
	public void setName(String name) {

	}
	
	public boolean isAvailable() {
		switch(reason) {
		case NOT_UNAVAILABLE:
			return true;
		default:
			return false;
		}
	}
	
	public String getUnvailReason() {
		return this.reason.toString();
	}
	public String getDescription() {
		return description;
	}
	
	@Override
	public String getDisplayName(PlayerID PDI) {
		return this.getName();
	}
}
