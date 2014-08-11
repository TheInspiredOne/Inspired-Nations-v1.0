package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Economy.Account;
import com.github.InspiredOne.InspiredNationsServer.Economy.CurrencyAccount;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;


public interface AccountPortal extends Serializable, Nameable, Payable, Cloneable, Remote {

	public String getTypeName() throws RemoteException;
	public void addCurrencyAccount(CurrencyAccountPortal account) throws RemoteException;
	/**
	 * Sets the HashMap of all the money values
	 * @param money	the HashMap to replace the current one
	 */
	public Account getSelf() throws RemoteException;
	public void setMoney(ArrayList<CurrencyAccount> money) throws RemoteException;
	public BigDecimal getMoney(CurrencyPortal getType, CurrencyPortal valueType) throws RemoteException;
	public BigDecimal getTotalMoney(CurrencyPortal valueType, MathContext round) throws RemoteException;
	public void addMoney(BigDecimal mon, CurrencyPortal monType) throws NegativeMoneyTransferException, RemoteException;
	public void transferMoney(BigDecimal mon, CurrencyPortal monType, Payable accountTo) throws BalanceOutOfBoundsException, NegativeMoneyTransferException, RemoteException ;
	CurrencyAccountPortal getCurrenAccount(CurrencyPortal monType) throws RemoteException;
	/**
	 * Does this account auto exchange it's currencies when adding more money?
	 * @return
	 */
	public boolean isAutoExchange() throws RemoteException;
	/**
	 * Sets the auto exchange policy
	 * @param autoExchange
	 */
	public void setAutoExchange(boolean autoExchange) throws RemoteException;
	public String getName() throws RemoteException;
	public void setName(String name) throws RemoteException;
	boolean isShared() throws RemoteException;
	public boolean containsCurrency(CurrencyPortal curren) throws RemoteException;
	public String getDisplayName(PlayerID PDI) throws RemoteException;
	public void sendNotification(AlertPortal msg) throws RemoteException;
}
