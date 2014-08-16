package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Notifyable;

public interface PlayerDataPortal extends Remote, Nameable, Notifyable, Payable,  Serializable {
	
	public PlayerID getPlayerID() throws RemoteException;
	public MessageManagerPortal getMsg() throws RemoteException;
	public void setKill(boolean kill) throws RemoteException;
	public boolean getKill() throws RemoteException;
	public void sendRawMessage(String msg) throws RemoteException, PlayerOfflineException;
	public CurrencyPortal getCurrency() throws RemoteException;
	public void setCurrency(Currency currency) throws RemoteException;
	public AccountCollectionPortal getAccounts() throws RemoteException;
	public void setAccounts(AccountCollection accounts)throws RemoteException;
	public boolean getTimerState()throws RemoteException;
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
	
	public ThemePortal getTheme() throws RemoteException;
	
	List<OwnerGov> getCitizenship(Class<? extends InspiredGov> govType,
			List<? extends InspiredGov> govDir) throws RemoteException;
	List<OwnerGov> getCitizenship() throws RemoteException;
	List<OwnerGov> getCitizenship(Class<? extends InspiredGov> class1)
			throws RemoteException;
	boolean isTimerState() throws RemoteException;
	void setTimerState(boolean timerState) throws RemoteException;
	Point3D getLastLocation() throws RemoteException, NotBoundException;
}
