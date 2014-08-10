package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.Economy.Account;
import com.github.InspiredOne.InspiredNationsServer.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Notifyable;

public interface AccountCollectionPortalInter extends Remote,  Payable, 
Notifyable, Iterable<Account> {
	
	public BigDecimal getTotalMoney(CurrencyPortalInter valueType, MathContext round) throws RemoteException;
	public AccountCollection getSelf() throws RemoteException;
	public void transferMoney(BigDecimal amount, CurrencyPortalInter monType, Payable accountTo) throws BalanceOutOfBoundsException, NegativeMoneyTransferException, RemoteException;
	public void addMoney(BigDecimal amount, CurrencyPortalInter monType) throws NegativeMoneyTransferException, RemoteException;
	public String getName() throws RemoteException;
	public void setName(String name) throws RemoteException;
	public String getDisplayName(PlayerID PID) throws RemoteException;
	public boolean isLinked() throws RemoteException;
	public void sendNotification(AlertPortalInter msg) throws RemoteException;
	public void addAccount(Account account) throws RemoteException;
}
