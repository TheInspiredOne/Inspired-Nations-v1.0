package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.github.InspiredOne.InspiredNationsServer.Debug;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.Governments.GovFactory;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountCollectionPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AlertPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedMap;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedSet;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Tools;


public class AccountCollection extends IndexedSet<Account> implements AccountCollectionPortal, Iterable<Account> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4596555858007834733L;
	private String name;
	
	public AccountCollection(String name) throws RemoteException {
		this.name = name;
		this.add(new Account());
		UnicastRemoteObject.exportObject(this, InspiredNationsServer.port);
	}
	
	public AccountCollection clone() {
		AccountCollection output = null;
		try {
			output = new AccountCollection(name);
		} catch (RemoteException e) {
			// TODO Kill the plugin
			e.printStackTrace();
		}
		for(Account acc:this) {
			output.add(acc);
		}
		return output;	
	}
	
	@Override
	public BigDecimal getTotalMoney(CurrencyPortal valueType, MathContext round) {
		BigDecimal output = BigDecimal.ZERO;
		for(Account valueCheck:this) {
			output = output.add(valueCheck.getTotalMoney(valueType, round));
		}
		return output;
	}

	public void transferMoney(BigDecimal amount, CurrencyPortal monType, Payable accountTo) throws RemoteException, BalanceOutOfBoundsException, NegativeMoneyTransferException {
		
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
	public void addMoney(BigDecimal amount, CurrencyPortal monType) throws NegativeMoneyTransferException, RemoteException {
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
	public void sendNotification(AlertPortal msg) throws RemoteException {
		for(PlayerData player:InspiredNationsServer.playerdata.values()) {
			if(player.getAccounts().equals(this)) {
				player.sendNotification(msg);
			}
		}
	}
	
	
	public IndexedMap<Class<? extends InspiredGov>, BigDecimal> getTaxes(CurrencyPortal portal) throws RemoteException {
		Debug.print("Inside GetTaxes 1");
		Currency curren = Currency.resolve(portal);
		Debug.print("Inside GetTaxes 2");
		IndexedMap<Class<? extends InspiredGov>, BigDecimal> output = new IndexedMap<Class<? extends InspiredGov>, BigDecimal>();
		Debug.print("Inside GetTaxes 3");
		Debug.print("regiondata null?: " + (InspiredNationsServer.regiondata==null));
		for(InspiredGov gov:InspiredNationsServer.regiondata) {
			Debug.print("inside the forloop of getTaxes 1" );
			if(gov.getAccounts() == (this)) {
				Debug.print("inside the forloop of getTaxes 2" );
				if(output.containsKey(gov.getClass())) {
					Debug.print("inside the forloop of getTaxes 3" );
					output.put(gov.getClass(), output.get(gov.getClass()).add(gov.currentTaxCycleValue(curren)));
					Debug.print("inside the forloop of getTaxes 5" );
				}
				else {
					Debug.print("inside the forloop of getTaxes 4" );
					output.put(gov.getClass(), gov.currentTaxCycleValue(curren));
					Debug.print("inside the forloop of getTaxes 6" );
				}
			}
		}
		Debug.print("Inside GetTaxes 4");
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

	@Override
	public int getSizeOf() throws RemoteException {
		return this.size();
	}

	@Override
	public void add(AccountPortal account) throws RemoteException {
		this.add(Account.resolve(account));
	}

	@Override
	public void removeAccount(AccountPortal account) throws RemoteException {
		this.remove(Account.resolve(account));
		try {
			account.transferMoney(account.getTotalMoney(Currency.DEFAULT, InspiredNationsServer.Exchange.mcdown), Currency.DEFAULT, this);
		} catch (BalanceOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NegativeMoneyTransferException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public String getTaxesText(PlayerDataPortal player) throws RemoteException {
		Debug.print("Inside getTaxesText 1");
		PlayerData PDI = PlayerData.resolve(player);
		Debug.print("Inside getTaxesText 2");
		String output = "";
		
		IndexedMap<Class<? extends InspiredGov>, BigDecimal> taxmap = PDI.getAccounts().getTaxes(PDI.getCurrency());
		Debug.print("Inside getTaxesText 3");
		if(!taxmap.isEmpty()) {
			output = output.concat(PDI.SUBHEADER() + "Taxes\n");
		}
		Debug.print("Inside getTaxesText 4");
		for(Class<? extends InspiredGov> govtype:taxmap) {
			InspiredGov gov = GovFactory.getGovInstance(govtype);
			output = output.concat(PDI.VALUEDESCRI() + gov.getTypeName() + ": " + PDI.VALUE() +
					Tools.cut(taxmap.get(govtype))) + PDI.UNIT() + " " + PDI.getCurrency() +"\n";
		}
		Debug.print("Inside getTaxesText 4");
		return output;
		
	}

	@Override
	public int moveUp(int start) throws RemoteException {
		Account theOption = this.get(start);
		int position = start;
		int size = this.size();
		if(size == 0) {
			return 0;
		}
		else {
			int newpos = Tools.newPosition(position - 1, size);
			this.remove(position);
			this.add(newpos, theOption);
			return newpos;
		}
	}
	@Override
	public int moveDown(int start) throws RemoteException {
		Account theOption =this.get(start);
		int position = start;
		int size = this.size();
		if(size == 0) {
			return 0;
		}
		else {
			int newpos = Tools.newPosition(position + 1, size);
			this.remove(position);
			this.add(newpos,  theOption);
			return newpos;
		}		
	}
}