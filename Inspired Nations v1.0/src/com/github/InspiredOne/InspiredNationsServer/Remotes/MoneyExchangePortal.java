package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;

public interface MoneyExchangePortal extends Remote {
	/**
	 * Finds the value of money required in valueType that would yeild mon in montype if exchanged.
	 * The reason getExchangeValue does not work in a transfer context is that getExchangeValue
	 * assumes that monType depreciates when exchanged. Because in a transfer there is no exchange
	 * happening, the monType does not depreciate and therefore more valueType is required than
	 * what is found using getExchangeValue.
	 * @param mon		the amount of mon you're trying to get
	 * @param monType	the type of mon
	 * @param valueType	the type of currency you're using to get mon
	 * @return
	 */
	public BigDecimal getTransferValue(BigDecimal mon, CurrencyPortal monType, Currency valueType, MathContext round) throws RemoteException;
	
	/**
	 * Gets the total amount of valueType you would recieve if you exchanged mon amount of monType
	 * @param mon
	 * @param getType
	 * @param valueType
	 * @return
	 */
	public BigDecimal getExchangeValue(BigDecimal mon, CurrencyPortal getType, CurrencyPortal valueType) throws RemoteException;
	
	public BigDecimal getExchangeValue(BigDecimal mon, CurrencyPortal monType, CurrencyPortal getType, MathContext round) throws RemoteException;
	
	public BigDecimal exchange(BigDecimal mon, CurrencyPortal monType, CurrencyPortal valueType) throws RemoteException;
	
	public int getSize() throws RemoteException;
	
	public CurrencyPortal getCurrency(int index) throws RemoteException;
}