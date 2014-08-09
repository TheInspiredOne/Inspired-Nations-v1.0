package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Notifyable;

public interface Payable extends Nameable, Notifyable, Remote{
	
	public void transferMoney(BigDecimal amount, Currency monType, Payable target) throws BalanceOutOfBoundsException, NegativeMoneyTransferException, RemoteException;
	public void addMoney(BigDecimal amount, Currency monType) throws NegativeMoneyTransferException, RemoteException;
	public BigDecimal getTotalMoney(Currency valueType, MathContext round) throws RemoteException;
}
