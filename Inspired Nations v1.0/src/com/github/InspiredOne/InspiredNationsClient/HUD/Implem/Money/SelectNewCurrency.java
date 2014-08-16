package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public class SelectNewCurrency extends TabSelectOptionMenu<CurrencyPortal> {

	Menu previous;
	
	public SelectNewCurrency(PlayerDataPortal PDI, Menu previous) throws RemoteException {
		super(PDI);
		this.previous = previous;
	}

	@Override
	public Menu getPreviousPrompt() {
		return previous;
	}

	@Override
	public String postTabListPreOptionsText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Pick Currency";
	}

	@Override
	public void addTabOptions() throws RemoteException {
		for(int i=0; i < InspiredNationsClient.server.getExchange().getSize(); i++) {
			this.taboptions.add(InspiredNationsClient.server.getExchange().getCurrency(i));
		}
	}

	@Override
	public void addOptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}
}
