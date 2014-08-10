package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AlertPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyAccountPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortalInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Tools;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Alert;

public class CurrencyAccount implements CurrencyAccountPortalInter, Cloneable {

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
	
	public CurrencyAccount(Currency curren) {
		this.curren = curren;
		this.amount = BigDecimal.ZERO;
	}
	public CurrencyAccount(Currency curren2, BigDecimal amount) throws RemoteException {
		this.curren = curren2;
		this.amount = amount;
	}
	public CurrencyAccount(CurrencyPortalInter curren2, BigDecimal amount) throws RemoteException {
		this.curren = curren2.getSelf();
		this.amount = amount;
	}
	
	public CurrencyAccount getSelf() {
		return this;
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
	public void setCurrency(CurrencyPortalInter curren) throws RemoteException { 
		this.curren = curren.getSelf();
	}
	
	@Override
	public String getName() {
		return curren.getName();
	}

	@Override
	public void setName(String name) throws NameAlreadyTakenException {
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
	public void transferMoney(BigDecimal amountTake, CurrencyPortalInter monType,
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
	public void addMoney(BigDecimal amountGive, CurrencyPortalInter monType)
			throws NegativeMoneyTransferException, RemoteException {
		if(amountGive.compareTo(BigDecimal.ZERO) < 0 ) {
			throw new NegativeMoneyTransferException();
		}
		else {
			this.amount = this.amount.add(InspiredNationsServer.Exchange.exchange(amountGive, monType, this.curren));
		}
	}

	@Override
	public BigDecimal getTotalMoney(CurrencyPortalInter getType, MathContext round) {
		return InspiredNationsServer.Exchange.getExchangeValue(amount, curren, getType, InspiredNationsServer.Exchange.mcdown);
	}
	
	@Override
	public void sendNotification(AlertPortalInter msg) throws RemoteException {
		for(PlayerData player:InspiredNationsServer.playerdata.values()) {
			for(Account account:player.getAccounts()) {
				if(account.getMoney().contains(this)) {
					account.sendNotification(msg);
					break;
				}
			}
		}
	}

}
