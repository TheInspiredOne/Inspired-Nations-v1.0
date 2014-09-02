package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AlertPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyAccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Tools;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Alert;

public class CurrencyAccount implements CurrencyAccountPortal, Cloneable {

	/**
	 * Currency Discipline
	 * 
	 * 1. Every money value must be paired with a money type.
	 * 2. Negative transfers are never allowed.
	 * 3. Any money you receive is rounded up. Any money you give is rounded down.
	 */
	private static final long serialVersionUID = 6553887792904870042L;
	private Currency curren;
	private BigDecimal amount;
	private static int currencyAccountID = 0;
	private final int id;
	
	public CurrencyAccount(Currency curren) throws RemoteException {
		this.curren = curren;
		this.amount = BigDecimal.ZERO;
		UnicastRemoteObject.exportObject(this, InspiredNationsServer.port);
		id = CurrencyAccount.currencyAccountID;
		CurrencyAccount.currencyAccountID++;
	}
	public CurrencyAccount(Currency curren2, BigDecimal amount) throws RemoteException {
		this.curren = curren2;
		this.amount = amount;
		id = CurrencyAccount.currencyAccountID;
		CurrencyAccount.currencyAccountID++;
	}
	public CurrencyAccount(CurrencyPortal curren2, BigDecimal amount) throws RemoteException {
		this.curren = Currency.resolve(curren2);
		this.amount = amount;
		id = CurrencyAccount.currencyAccountID;
		CurrencyAccount.currencyAccountID++;
	}
	
	public static CurrencyAccount resolve(CurrencyAccountPortal portal) throws RemoteException {
		for(PlayerData player:InspiredNationsServer.playerdata.values()) {
			for(Account account:player.getAccounts()) {
				for(int i = 0;i<account.getCurrencySize(); i++) {
					if(account.getCurrency(i).getID() == portal.getID()) {
						return account.money.get(i);
					}
				}
			}
			for(NPC npc:player.npcs) {
				for(Account account:npc.accounts) {
					for(int i = 0;i<account.getCurrencySize(); i++) {
						if(account.getCurrency(i).getID() == portal.getID()) {
							return account.money.get(i);
						}
					}
				}
			}
		}
		for(InspiredGov gov:InspiredNationsServer.regiondata) {
			for(Account account:gov.getAccounts()) {
				for(int i = 0;i<account.getCurrencySize(); i++) {
					if(account.getCurrency(i).getID() == portal.getID()) {
						return account.money.get(i);
					}
				}
			}
		}
		return null;
	}
	
	public CurrencyAccount clone() {
		CurrencyAccount output = null;
		try {
			output = new CurrencyAccount(this.curren, this.amount);
		} catch (RemoteException e) {
			//TODO KILL the plugin
			e.printStackTrace();
		}
		return output;
	}
	
	public Currency getCurrency() {
		return curren;
	}
	public void setCurrency(CurrencyPortal curren) throws RemoteException { 
		this.curren = Currency.resolve(curren);
	}
	
	@Override
	public String getName() {
		return curren.getName();
	}

	@Override
	public void setName(String name) throws NameAlreadyTakenException, RemoteException {
		curren.setName(name);
	}

	@Override
	public String getDisplayName(PlayerID viewerID) {
		PlayerData viewer = InspiredNationsServer.playerdata.get(viewerID);
		return curren.getName() + " (" + Tools.cut(this.getTotalMoney(this.curren, InspiredNationsServer.Exchange.mcdown)) + "~"
				+ Tools.cut(InspiredNationsServer.Exchange.getExchangeValue(amount, curren, viewer.getCurrency()))
				+ " " + viewer.getCurrency() + ":1.00 " + "~" + Tools.cut(InspiredNationsServer.Exchange.getExchangeValue(BigDecimal.ONE, curren, viewer.getCurrency()))
				+ " " + viewer.getCurrency() + ")";
	}

	@Override
	public void transferMoney(BigDecimal amountTake, CurrencyPortal monType,
			Payable target) throws BalanceOutOfBoundsException,
			NegativeMoneyTransferException, RemoteException {
		
		BigDecimal amountTempup = InspiredNationsServer.Exchange.getTransferValue(amountTake, monType, curren, InspiredNationsServer.Exchange.mcup);
		BigDecimal amountTempdown = InspiredNationsServer.Exchange.getTransferValue(amountTake, monType, curren, InspiredNationsServer.Exchange.mcdown);
		if(amountTempdown.compareTo(BigDecimal.ZERO) < 0 ) {
			throw new NegativeMoneyTransferException();
		}
		else if(amount.compareTo(amountTempdown) < 0) {
			throw new BalanceOutOfBoundsException();
		}
		else {
			this.amount = amount.subtract(amountTempdown);
			target.addMoney(amountTempup, curren);
		}
	}

	@Override
	public void addMoney(BigDecimal amountGive, CurrencyPortal monType)
			throws NegativeMoneyTransferException, RemoteException {
		if(amountGive.compareTo(BigDecimal.ZERO) < 0 ) {
			throw new NegativeMoneyTransferException();
		}
		else {
			this.amount = this.amount.add(InspiredNationsServer.Exchange.exchange(amountGive, monType, this.curren));
		}
	}

	@Override
	public BigDecimal getTotalMoney(CurrencyPortal getType, MathContext round) {
		return InspiredNationsServer.Exchange.getExchangeValue(amount, curren, getType, InspiredNationsServer.Exchange.mcdown);
	}
	
	@Override
	public void sendNotification(AlertPortal msg) throws RemoteException {
		for(PlayerData player:InspiredNationsServer.playerdata.values()) {
			for(Account account:player.getAccounts()) {
				if(account.getMoney().contains(this)) {
					account.sendNotification(msg);
					break;
				}
			}
		}
	}
	@Override
	public int getID() throws RemoteException {
		return this.id;
	}
}
