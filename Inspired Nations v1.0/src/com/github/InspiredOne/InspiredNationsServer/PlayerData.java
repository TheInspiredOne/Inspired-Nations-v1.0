package com.github.InspiredOne.InspiredNationsServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNationsClient.Exceptions.PlayerOfflineException;
import com.github.InspiredOne.InspiredNationsClient.Remotes.ClientPlayerDataInter;
import com.github.InspiredOne.InspiredNationsServer.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.NPC;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.ClientID;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Theme;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Alert;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.MessageManager;

public class PlayerData extends UnicastRemoteObject implements PlayerDataInter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1358248142526827092L;
	private PlayerID id;
	private AccountCollection accounts;
	private Currency currency;
	private String name;
	private Theme theme = new Theme();
	private MessageManager msg;
	private boolean kill = false;
	public List<NPC> npcs = new ArrayList<NPC>();
	public boolean TimerState = false;
	public boolean chatState = true;
	
	public PlayerData(PlayerID id) throws RemoteException {
		this.id = id;
		name = id.getName();
		msg = new MessageManager(this);
	}

	public PlayerID getId() {
		return id;
	}
	@Override
	public void sendNotification(Alert msg) throws RemoteException {
		this.getMsg().receiveAlert(msg, true);
	}
	public AccountCollection getAccounts() {
		return accounts;
	}

	public void setAccounts(AccountCollection accounts) {
		if(this.accounts.isLinked()) {
			// TODO I have to figure out how to deal with linked account collections.
		}
		this.accounts = accounts;
	}
	
	public void setKill(boolean kill) throws RemoteException {
		this.kill = kill;
	}
	
	public boolean getKill() throws RemoteException {
		return kill;
	}
	
	public void sendRawMessage(String msg) throws RemoteException, PlayerOfflineException {
		this.getPlayer().sendRawMessage(msg);
	}
	
	public ClientPlayerDataInter getPlayer() throws PlayerOfflineException {
		ClientPlayerDataInter output = null;
		
		Debug.info("Isid  getPlayer of PlayerData");
		Debug.info("client vecotr is :" + InspiredNationsServer.clients.size());
		
		for(ClientID client:InspiredNationsServer.clients) {
			try {
				Debug.info("Isid  getPlayer of PlayerData 2");
				output = client.getClientPortal().getPlayer(id);
				Debug.info("Isid  getPlayer of PlayerData 3");
			} catch (RemoteException e) {
				Debug.info("there was a remote exception in PlayerData");
			}
		}
		if(output == null) {
			throw new PlayerOfflineException();
		}
		else {
			return output;
		}
	}

	@Override
	public String getName() throws RemoteException {
		for(ClientID client:InspiredNationsServer.clients) {
			try {
				name = client.getClientPortal().getPlayer(id).getName();
			} catch (PlayerOfflineException e) {

			}
		}
		return name;
	}

	@Override
	public void setName(String name) throws NameAlreadyTakenException, RemoteException {
		
	}

	@Override
	public String getDisplayName(PlayerID viewer) throws RemoteException {
		return this.getName();
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public String HEADER() throws RemoteException {
		return theme.HEADER();
	}

	@Override
	public String SUBHEADER() throws RemoteException {
		return theme.SUBHEADER();
	}

	@Override
	public String LABEL() throws RemoteException {
		return theme.LABEL();
	}

	@Override
	public String VALUE() throws RemoteException {
		return theme.VALUE();
	}

	@Override
	public String VALUEDESCRI() throws RemoteException {
		return theme.VALUEDESCRI();
	}

	@Override
	public String DIVIDER() throws RemoteException {
		return theme.DIVIDER();
	}

	@Override
	public String OPTION() throws RemoteException {
		return theme.OPTION();
	}

	@Override
	public String OPTIONNUMBER() throws RemoteException {
		return theme.OPTIONNUMBER();
	}

	@Override
	public String OPTIONDESCRIP() throws RemoteException {
		return theme.OPTIONDESCRI();
	}

	@Override
	public String UNAVAILABLE() throws RemoteException {
		return theme.UNAVAILABLE();
	}

	@Override
	public String UNAVAILREASON() throws RemoteException {
		return theme.UNAVAILREASON();
	}

	@Override
	public String INSTRUCTION() throws RemoteException {
		return theme.INSTRUCTION();
	}

	@Override
	public String ERROR() throws RemoteException {
		return theme.ERROR();
	}

	@Override
	public String ALERT() throws RemoteException {
		return theme.ALERT();
	}

	@Override
	public String UNIT() throws RemoteException {
		return theme.UNIT();
	}

	@Override
	public String ENDINSTRU() throws RemoteException {
		return theme.ENDINSTRUCT();
	}

	@Override
	public String ENEMY() throws RemoteException {
		return theme.ENEMY();
	}

	@Override
	public String NEUTRAL() throws RemoteException {
		return theme.NEUTRAL();
	}

	@Override
	public String ALLY() throws RemoteException {
		return theme.ALLY();
	}

	@Override
	public PlayerID getPlayerID() throws RemoteException {
		return this.id;
	}

	public MessageManager getMsg() {
		return msg;
	}
}
