package com.github.InspiredOne.InspiredNationsServer.Economy.Implem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;

public interface Buyer extends Nameable, Payable {
	public Point3D getLocation() throws RemoteException, NotBoundException;
	public Currency getCurrency();
}
