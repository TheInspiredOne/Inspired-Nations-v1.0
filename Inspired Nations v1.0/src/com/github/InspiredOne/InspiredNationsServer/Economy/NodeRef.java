package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNationsServer.Debug;
import com.github.InspiredOne.InspiredNationsServer.Economy.Nodes.CobDugNode;
import com.github.InspiredOne.InspiredNationsServer.Economy.Nodes.ItemNode;
import com.github.InspiredOne.InspiredNationsServer.Economy.Nodes.Node;
import com.github.InspiredOne.InspiredNationsServer.Economy.Nodes.PerfectCompNode;
import com.github.InspiredOne.InspiredNationsServer.Economy.Nodes.PerfectSubNode;

public class NodeRef {
	HashMap<Integer, Node> ref = new HashMap<Integer, Node>();
	ItemRefTrees item;
	
	public Node Begin;
	Node Smelt;
	
	public NodeRef() {
		item = new ItemRefTrees();
		
		Begin = new CobDugNode( new double[] {1,1}, new Node[] {
			// { Armor
				item.armor,
			// }
			// { Tools
				item.tools
			// }
			// { Weapons
				// { Potions
						
				// }
				// { Hand-held Weapons

				// }
			// }
			// { Food
				
			// }
		});
	}
	
	public void allocateMoney(NPC npc) throws RemoteException { 
		BigDecimal money = npc.getTotalUnallocatedMoney(npc.getCurrency()).divide(new BigDecimal(5));
		Debug.info(money.toString() + npc.getCurrency());
		Debug.info("///////////////////////////New NPC\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
		this.Begin.buy(money, npc.getCurrency(), npc);
	}
	
	public Node get(int id) {
		return ref.get(id);
	}
	
	public void put(int id, Node node) {
		ref.put(id, node);
	}
}
