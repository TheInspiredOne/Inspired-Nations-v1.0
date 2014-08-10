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
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountCollectionPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AlertPortalInter;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortalInter;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedMap;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedSet;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Tools;


public class AccountCollection extends IndexedSet<Account> implements AccountCollectionPortalInter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4596555858007834733L;
	private String name;
	
	public AccountCollection(String name) {
		this.name = name;
		this.add(new Account());
	}
	
	public AccountCollection clone() {
		AccountCollection output = new AccountCollection(name);
		for(Account acc:this) {
			output.add(acc);
		}
		return output;	
	}
	
	@Override
	public BigDecimal getTotalMoney(CurrencyPortalInter valueType, MathContext round) {
		BigDecimal output = BigDecimal.ZERO;
		for(Account valueCheck:this) {
			output = output.add(valueCheck.getTotalMoney(valueType, round));
		}
		return output;
	}

	public void transferMoney(BigDecimal amount, CurrencyPortalInter monType, Payable accountTo) throws RemoteException, BalanceOutOfBoundsException, NegativeMoneyTransferException {
		
		if(this.getTotalMoney(monType, InspiredNationsServer.Exchange.mcdown).compareTo(amount) < 0) {
			throw new BalanceOutOfBoundsException();
		}
		else {
			for(Account handle:this) {
				if(handle.getTotalMoney(monType, InspiredNationsServer.Exchange.mcdown).compareTo(amount) >= 0) {
					handle.transferMoney(amount, monType, accountTo);
					break;
				}
				else {
					amount = amount.subtract(handle.getTotalMoney(monType, InspiredNationsServer.Exchange.mcdown));
					handle.transferMoney(handle.getTotalMoney(monType, InspiredNationsServer.Exchange.mcdown), monType, accountTo);
				}
				
			}
		}
	}

	@Override
	public void addMoney(BigDecimal amount, CurrencyPortalInter monType) throws NegativeMoneyTransferException, RemoteException {
		if(this.isEmpty()) {
			this.add(new Account());
		}
		this.get(0).addMoney(amount, monType);
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDisplayName(PlayerID PID) throws RemoteException {
		PlayerData PDI = InspiredNationsServer.playerdata.get(PID);
		return this.getName() + " (" + PDI.VALUE() + 
				Tools.cut(this.getTotalMoney(PDI.getCurrency(), InspiredNationsServer.Exchange.mcdown)) +PDI.UNIT()
				+ " " + PDI.getCurrency() + ")";
	}
	public boolean isLinked() {
		boolean oneFound = false;
		for(PlayerData player:InspiredNationsServer.playerdata.values()) {
			if(player.getAccounts() == this) {
				if(oneFound) {
					return true;
				}
				else {
					oneFound = true;
				}
			}
		}
		for(InspiredGov gov:InspiredNationsServer.regiondata) {
			if(gov.getAccounts() == this) {
				if(oneFound) {
					return true;
				}
				else {
					oneFound = true;
				}
			}
		}
		return false;
	}
	public ArrayList<InspiredGov> getLinkedGovs() {
		ArrayList<InspiredGov> output = new ArrayList<InspiredGov>();
		for(InspiredGov gov:InspiredNationsServer.regiondata) {
			if(gov.getAccounts() == this) {
				output.add(gov);
			}
		}
		return output;
	}
	@Override
	public void sendNotification(AlertPortalInter msg) throws RemoteException {
		for(PlayerData player:InspiredNationsServer.playerdata.values()) {
			if(player.getAccounts().equals(this)) {
				player.sendNotification(msg);
			}
		}
	}
	
	
	public IndexedMap<Class<? extends InspiredGov>, BigDecimal> getTaxes(CurrencyPortalInter curren) throws RemoteException {
		IndexedMap<Class<? extends InspiredGov>, BigDecimal> output = new IndexedMap<Class<? extends InspiredGov>, BigDecimal>();
		for(InspiredGov gov:InspiredNationsServer.regiondata) {
			if(gov.getAccounts() == (this)) {
				if(output.containsKey(gov.getClass())) {
					output.put(gov.getClass(), output.get(gov.getClass()).add(gov.currentTaxCycleValue(curren.getSelf())));
				}
				else {
					output.put(gov.getClass(), gov.currentTaxCycleValue(curren.getSelf()));
				}
			}
		}
		return output;
	}
	@Override
	public AccountCollection getSelf() throws RemoteException {
		return this;
	}

	@Override
	public void addAccount(Account account) throws RemoteException {
		this.add(account);
	}
}