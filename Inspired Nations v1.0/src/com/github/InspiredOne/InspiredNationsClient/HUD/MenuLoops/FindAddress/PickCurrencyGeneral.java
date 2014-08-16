package com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.MoneyExchangePortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public abstract class PickCurrencyGeneral extends TabSelectOptionMenu<CurrencyPortal> {

	Menu previous;
	public PickCurrencyGeneral(PlayerDataPortal PDI, Menu previous) throws RemoteException {
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

	/**
	 * 
	 * @param curren
	 * @return	<code>true</code> if the currency should be added
	 * @throws RemoteException 
	 */
	public abstract boolean check(CurrencyPortal curren) throws RemoteException;

	@Override
	public String getHeader() {
		return "Select Currency";
	}

	@Override
	public void addTabOptions() throws RemoteException {
		MoneyExchangePortal exchange = InspiredNationsClient.server.getExchange();
		for(int i = 0; i < exchange.getSize(); i++) {
			CurrencyPortal curren = exchange.getCurrency(i);
			if(check(curren)) {
				this.taboptions.add(curren);
			}
		}
	}
}
