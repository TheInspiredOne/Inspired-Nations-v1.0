package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.Economy.Account;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Alert;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Notifyable;

public interface AccountCollectionPortalInter extends Remote,  Payable, 
Notifyable, Iterable<Account> {
	
	public BigDecimal getTotalMoney(Currency valueType, MathContext round) throws RemoteException;

	public void transferMoney(BigDecimal amount, Currency monType, Payable accountTo) throws BalanceOutOfBoundsException, NegativeMoneyTransferException, RemoteException;

	public void addMoney(BigDecimal amount, Currency monType) throws NegativeMoneyTransferException, RemoteException;
	public void addMoney(BigDecimal amount, CurrencyPortalInter monType) throws NegativeMoneyTransferException, RemoteException;
	public String getName() throws RemoteException;
	public void setName(String name) throws RemoteException;
	public String getDisplayName(PlayerID PID) throws RemoteException;
	public boolean isLinked() throws RemoteException;
	public void sendNotification(Alert msg) throws RemoteException;
	public void sendNotification(AlertPortalInter msg) throws RemoteException;
}
