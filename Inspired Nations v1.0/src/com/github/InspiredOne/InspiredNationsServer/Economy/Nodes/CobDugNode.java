package com.github.InspiredOne.InspiredNationsServer.Economy.Nodes;

import java.math.BigDecimal;

import org.bukkit.configuration.file.YamlConfiguration;

import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.NPC;

public class CobDugNode extends Node {

	
	double[] power;
	boolean[] available;
	
	public CobDugNode(double[] power, Node[] elems) {
		super(elems);
		this.power = power;
	}

	@Override
	public double getCoef(NPC npc) {
		available = new boolean[elems.length];
		String output = "CobDuglasNode: ";
		Node.tier++;
		double coeftemp = 1;
		boolean atleastone = false;
		
		for(int i = 0; i<elems.length; i++) {
			double holder = Math.pow(elems[i].getCoef(npc), power[i]);
			output = output.concat(holder + "*X" + i + "^" + power[i] + " * ");
			if(holder > 0) {
				coeftemp = coeftemp*holder;
				available[i] = true;
				atleastone =true;
			}
			else {
				available[i] = false;
			}
		}
		if(atleastone) {
			return coeftemp;
		}
		else {
			return 0;
		}
	}

	@Override
	public void buy(BigDecimal amount, Currency curren, NPC npc) {
		double divisor = 0;
		if(available == null) {
			this.getCoef(npc);
		}
		
		for(int i = 0; i < power.length; i++) {
			if(available[i]) {
				divisor += power[i];
			}
		}	
		for(int i = 0; i < elems.length; i++) {
			if(available[i]) {
				BigDecimal amountout = amount.multiply(new BigDecimal(power[i])).divide(new BigDecimal(divisor),
						InspiredNationsServer.Exchange.mcup);
				elems[i].buy(amountout, curren, npc);
				
			}
		}
	}

	@Override
	public void writeToConfig(String addr, YamlConfiguration output) {
		int id = 0;
		for(Node elem:this.elems) {
			elem.writeToConfig(addr + "CobbDug." + id,output);
			id++;
		}
		
	}
}
