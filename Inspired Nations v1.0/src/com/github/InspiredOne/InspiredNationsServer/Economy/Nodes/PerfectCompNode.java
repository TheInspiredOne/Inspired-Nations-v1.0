package com.github.InspiredOne.InspiredNationsServer.Economy.Nodes;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.github.InspiredOne.InspiredNationsServer.Debug;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.NPC;

public class PerfectCompNode extends Node {

	double[] ratio;
	
	public PerfectCompNode(double[] ratio, Node[] elems) {
		super(elems);
		this.ratio = ratio;
	
/*		double tot = 0;
		for(double item:ratio) {
			tot += item;
		}
		for(int index = 0;index < ratio.length;index++) {
			if(tot<=0) {
				break;
			}
			ratio[index] = ratio[index]/tot;
		}*/
	}

	@Override
	public double getCoef(NPC npc) {
		String output = "";
		Node.tier++;
		double coeftemp = 0;
		for(int i = 0; i < elems.length;i++) {
			double holder = elems[i].getCoef(npc)*ratio[i];
			output = output.concat(holder + "*X" + i + " + ");
			if(holder >= thresh) {
				coeftemp += holder;
			}
			else {
				coeftemp = 0;
				break;
			}
		}
		if(output.length() > 2) {
			Debug.node("PerfectComp: " + output.substring(0, output.length()-2));
		}
		else {
			Debug.node("PerfectComp: No subnodes");
		}
		return coeftemp;
	}

	@Override
	public void buy(BigDecimal amount, Currency curren, NPC npc) throws RemoteException {
		
		double divisor = 0;
		for(int i = 0; i < elems.length; i++){
			double holder = elems[i].getCoef(npc)*ratio[i];
			divisor += holder;
		}
		if(divisor <= thresh) {
			return;
		}
		
		for(int i = 0; i< elems.length; i++) {
			BigDecimal amountout = amount.multiply(new BigDecimal(elems[i].getCoef(npc)*ratio[i]).divide(new BigDecimal(divisor), InspiredNationsServer.Exchange.mcup));
			
			elems[i].buy(amountout, curren, npc);
		}

	}

	@Override
	public void writeToConfig(String addr, YamlConfiguration output) {
		int id = 0;
		for(Node elem:this.elems) {
			elem.writeToConfig(addr + "PerfectComp." + id, output);
			id++;
		}
	}

}
