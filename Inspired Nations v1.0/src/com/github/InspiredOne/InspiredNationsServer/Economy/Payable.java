package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortal;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Notifyable;

public interface Payable extends Nameable, Notifyable, Remote{
	
	public void transferMoney(BigDecimal amount, CurrencyPortal monType, Payable target) throws BalanceOutOfBoundsException, NegativeMoneyTransferException, RemoteException;
	public void addMoney(BigDecimal amount, CurrencyPortal monType) throws NegativeMoneyTransferException, RemoteException;
	public BigDecimal getTotalMoney(CurrencyPortal valueType, MathContext round) throws RemoteException;
}
