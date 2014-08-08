package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public interface PlayerDataInter extends Remote, Nameable, Serializable {
	
	public PlayerID getPlayerID() throws RemoteException;
	public MessageManagerInter getMsg() throws RemoteException;
	public void setKill(boolean kill) throws RemoteException;
	public boolean getKill() throws RemoteException;
	
	/**
	 * Used exclusively for the menu header.
	 */
	public String HEADER() throws RemoteException;
	/**
	 * Subheader
	 */
	public String SUBHEADER() throws RemoteException;
	/**
	 * Used to say what the proceeding value is.
	 */
	public String LABEL() throws RemoteException;
	/**
	 * Anything that is calculated information displayed for the viewer 
	 */
	public String VALUE() throws RemoteException;
	/**
	 * Additional text describing the value
	 */
	public String VALUEDESCRI() throws RemoteException;
	/**
	 * Used for the divider between sections of the HUD
	 */
	public String DIVIDER() throws RemoteException;
	/**
	 * The color of the options
	 */
	public String OPTION() throws RemoteException;
	/**
	 * The color of the number correlating to the option
	 */
	public String OPTIONNUMBER() throws RemoteException;
	/**
	 * Used for text that describes an option
	 */
	public String OPTIONDESCRIP() throws RemoteException;
	/**
	 * Color to be used for an unavailable option
	 */
	public String UNAVAILABLE() throws RemoteException;
	/**
	 * Color of text next to unavailable option describing why it's unavailable
	 */
	public String UNAVAILREASON() throws RemoteException;
	/**
	 * Used to give instructions
	 */
	public String INSTRUCTION() throws RemoteException;
	/**
	 * Used for error messages through out the plugin
	 */
	public String ERROR() throws RemoteException;
	/**
	 * Used for alert messages through out the plugin
	 */
	public  String ALERT() throws RemoteException;
	/**
	 * Used for all units mentioned in the HUD
	 */
	public String UNIT() throws RemoteException;
	/**
	 * Used for the line of text below the HUD
	 */
	public String ENDINSTRU() throws RemoteException;
	/**
	 * Used for Enemy Nations and players
	 */
	public String ENEMY() throws RemoteException;
	/**
	 * Used for Neutral Nations and players
	 */
	public String NEUTRAL() throws RemoteException;
	/**
	 * Used for Ally Nations and players
	 */
	public String ALLY() throws RemoteException;
}
