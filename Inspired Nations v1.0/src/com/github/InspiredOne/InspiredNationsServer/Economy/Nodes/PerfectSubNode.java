package com.github.InspiredOne.InspiredNationsServer.Economy.Nodes;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.github.InspiredOne.InspiredNationsServer.Debug;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.NPC;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedMap;

public class PerfectSubNode extends Node {

	double[] coefs;
	Node choice = null;
	
	public PerfectSubNode(double[] coefs, Node[] elems) {
		super(elems);
		this.coefs = coefs;
	}

	@Override
	public double getCoef(NPC npc) {
		String output = "Min(";
		Node.tier++;
		double coefTemp = 0;
		IndexedMap<Node,Double> ids = new IndexedMap<Node, Double>();
		for(int i = 0; i < elems.length; i++) {
			double holder = elems[i].getCoef(npc)*coefs[i];
			output = output.concat(holder + "*X" + i + ", ");
			if(holder > coefTemp) {
				ids.clear();
				ids.put(elems[i], holder);
				coefTemp = holder;
			}
			else if(holder == coefTemp) {
				ids.put(elems[i],holder);
			}
		}
		if(ids.size() >= 1) {
			int rand = ids.size();
			rand = (int) Math.floor(Math.random()*ids.size());
			choice = ids.getIndex(rand);
			coefTemp = ids.get(choice);
		}
		else {
			coefTemp = 0;
		}
		if(output.length() > 2) {
			Debug.node("PerfectSubNode: " + output.substring(0, output.length()-2));
		}
		else {
			Debug.node("PerfectSubNode: No Subnodes");
		}
		return coefTemp;
	}

	@Override
	public void buy(BigDecimal amount, Currency curren, NPC npc) throws RemoteException {
		if(choice == null) {
			this.getCoef(npc);
		}
		choice.buy(amount, curren, npc);
	}

	@Override
	public void writeToConfig(String addr, YamlConfiguration output) {
		int id = 0;
		for(Node elem:this.elems) {
			elem.writeToConfig(addr +"PerfectSub." + id, output);
			id++;
		}
		
	}
}
