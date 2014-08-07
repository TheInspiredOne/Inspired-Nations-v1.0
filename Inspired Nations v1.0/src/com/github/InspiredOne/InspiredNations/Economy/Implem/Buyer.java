package com.github.InspiredOne.InspiredNations.Economy.Implem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNations.ToolBox.Payable;
import com.github.InspiredOne.InspiredNations.ToolBox.Point3D;

public interface Buyer extends Nameable, Payable {
	public Point3D getLocation() throws RemoteException, NotBoundException;
	public Currency getCurrency();
}
