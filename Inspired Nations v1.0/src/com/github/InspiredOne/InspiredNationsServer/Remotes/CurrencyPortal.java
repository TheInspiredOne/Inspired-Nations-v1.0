package com.github.InspiredOne.InspiredNationsServer.Remotes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public interface CurrencyPortal extends Remote, Serializable, Nameable {

	public BigDecimal getExchangeRate(CurrencyPortal output) throws RemoteException;
	public String getName() throws RemoteException;
	public void setName(String name) throws NameAlreadyTakenException, RemoteException;
    public String getDisplayName(PlayerID viewer) throws RemoteException;
    public int getID() throws RemoteException;
}
