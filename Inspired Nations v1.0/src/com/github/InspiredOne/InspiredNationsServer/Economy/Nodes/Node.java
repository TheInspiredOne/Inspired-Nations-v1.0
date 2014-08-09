package com.github.InspiredOne.InspiredNationsServer.Economy.Nodes;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.NPC;

public abstract class Node {
	public static int tier = 0;
	
	double thresh = 0.0000001;
	Node[] elems;
	
	public Node(Node[] elems) {
		this.elems = elems;
	}
	
	public abstract double getCoef(NPC npc);
	
	public abstract void buy(BigDecimal amount, Currency montype, NPC npc) throws RemoteException;
	
	public abstract void writeToConfig(String addr, YamlConfiguration output);
}
