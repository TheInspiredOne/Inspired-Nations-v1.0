package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AlertPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyAccountPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortalInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Tools;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Alert;


public class Account implements AccountPortalInter {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7022565910007118461L;
	private static final String typeName = "Money";
	private String name = "Money";
	private ArrayList<CurrencyAccount> money = new ArrayList<CurrencyAccount>();
	private boolean AutoExchange = true;

	
	public Account() {
		//TODO remove these for later. figure out how to handle when a player is first joining a server with no
		// currencies already
	}
	public Account(String name) {
		this.setName(name);
	}
	
	public Account getSelf() {
		return this;
	}

	public String getTypeName() {
		return typeName;
	}
	public Account clone() {
		Account output = new Account();
		output.name = this.name;
		output.AutoExchange = this.AutoExchange;
		ArrayList<CurrencyAccount> currenaccount = new ArrayList<CurrencyAccount>();
		for(CurrencyAccount acc:this.money){
			currenaccount.add(acc.clone());
		}
		return output;
	}
	/**
	 * @return	the <code>HashMap</code> with all the money values in it
	 */
	public final ArrayList<CurrencyAccount> getMoney() {
		return money;
	}
	/**
	 * Sets the HashMap of all the money values
	 * @param money	the HashMap to replace the current one
	 */
	public final void setMoney(ArrayList<CurrencyAccount> money) {
		this.money = money;
	}

	public final BigDecimal getMoney(CurrencyPortalInter getType, CurrencyPortalInter valueType) throws RemoteException {
		MoneyExchange exch = InspiredNationsServer.Exchange;
		CurrencyAccount curren = getCurrenAccount(getType);
		boolean contains = curren != null;
		
		if(contains) {
			return exch.getExchangeValue(curren.getTotalMoney(getType, InspiredNationsServer.Exchange.mcdown), getType, valueType);
		}
		else {
			money.add(new CurrencyAccount(getType.getSelf(), BigDecimal.ZERO));
			return this.getMoney(getType, valueType);
		}
	}
	
	public final BigDecimal getTotalMoney(CurrencyPortalInter valueType, MathContext round) {
		BigDecimal output = BigDecimal.ZERO;
		for(CurrencyAccount curren:money) {
			output = output.add(curren.getTotalMoney(valueType, round));
		}
		return output;
	}
	
	public final void addMoney(BigDecimal mon, CurrencyPortalInter monType) throws NegativeMoneyTransferException, RemoteException {
		if(mon.compareTo(BigDecimal.ZERO) < 0) {
			throw new NegativeMoneyTransferException();
		}


		
		if(this.AutoExchange) {
			if(this.money.isEmpty()) {
				this.money.add(new CurrencyAccount(Currency.DEFAULT, BigDecimal.ZERO));
			}
			money.get(0).addMoney(mon, monType);
		}
		else {
			// looks to see if accountCollection has MonType
			CurrencyAccount account = getCurrenAccount(monType.getSelf());
			account.addMoney(mon, monType);
		}
	}
	
	public final void transferMoney(BigDecimal mon, CurrencyPortalInter monType, Payable accountTo) throws BalanceOutOfBoundsException, NegativeMoneyTransferException, RemoteException {
		if(getTotalMoney(monType, InspiredNationsServer.Exchange.mcdown).compareTo(mon) < 0) {
			throw new BalanceOutOfBoundsException();
		}
		else {
			for(CurrencyAccount curren:money) {
				//BigDecimal amountup = curren.getTotalMoney(monType, InspiredNations.Exchange.mcup);
				BigDecimal amountdown = curren.getTotalMoney(monType, InspiredNationsServer.Exchange.mcdown);
				
				if(mon.compareTo(amountdown) >= 0) {
					money.set(money.indexOf(curren), new CurrencyAccount(curren.getCurrency()));
					curren.transferMoney(amountdown, monType, accountTo);
					mon = mon.subtract(amountdown);
				}
				else {
					curren.transferMoney(mon, monType, accountTo);
					break;
				}
			}
		}
	}
	
	public CurrencyAccount getCurrenAccount(CurrencyPortalInter monType) throws RemoteException {
		CurrencyAccount account = new CurrencyAccount(monType.getSelf());
		for(CurrencyAccount curren:this.money) {
			if(curren.getCurrency().equals(monType)) {
				return curren;
			}
		}
		this.money.add(account);
		return account;
	}
	
	/**
	 * Does this account auto exchange it's currencies when adding more money?
	 * @return
	 */
	public boolean isAutoExchange() {
		return AutoExchange;
	}
	/**
	 * Sets the auto exchange policy
	 * @param autoExchange
	 */
	public void setAutoExchange(boolean autoExchange) {
		AutoExchange = autoExchange;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isShared() {
		boolean foundOne = false;
		for(InspiredGov gov:InspiredNationsServer.regiondata) {
			if(gov.getAccounts().contains(this)) {
				if(foundOne) {
					return true;
				}
				else {
					foundOne = true;
				}
			}
		}
		for(PlayerData player:InspiredNationsServer.playerdata.values()) {
			if(player.getAccounts().contains(this)) {
				if(foundOne) {
					return true;
				}
				else {
					foundOne = true;
				}
			}
		}
		return false;
	}
	
	public boolean containsCurrency(CurrencyPortalInter curren) {
		for(CurrencyAccount acc:this.money) {
			if(acc.getCurrency().equals(curren)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getDisplayName(PlayerID PID) {
	
		PlayerData PDI = InspiredNationsServer.playerdata.get(PID);
		
		if(isShared()){
			return this.getName() + " (" + Tools.cut(this.getTotalMoney(PDI.getCurrency(), InspiredNationsServer.Exchange.mcdown)) +" " + PDI.getCurrency() + ") Shared";
		}
		else {
			return this.getName() + " (" + Tools.cut(this.getTotalMoney(PDI.getCurrency(), InspiredNationsServer.Exchange.mcdown)) +" " + PDI.getCurrency() + ")";
		}
	}
	
	@Override
	public void sendNotification(AlertPortalInter msg) throws RemoteException {
		for(PlayerData player:InspiredNationsServer.playerdata.values()) {
			for(Account account: player.getAccounts()) {
				if(account.equals(this)) {
					player.sendNotification(msg);
				}
			}
		}
	}
	@Override
	public void addCurrencyAccount(CurrencyAccountPortalInter account)
			throws RemoteException {
		this.getMoney().add(account.getSelf());
	}
}
