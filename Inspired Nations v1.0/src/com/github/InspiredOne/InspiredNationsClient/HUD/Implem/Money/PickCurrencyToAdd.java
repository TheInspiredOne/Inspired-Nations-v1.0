package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.MenuLoops.FindAddress.PickCurrencyGeneral;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AccountPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public class PickCurrencyToAdd extends PickCurrencyGeneral {

	AccountPortal account;
	OptionMenu previous;
	
	public PickCurrencyToAdd(PlayerDataPortal PDI, OptionMenu previous, AccountPortal account) throws RemoteException {
		super(PDI, previous);
		this.account = account;
		this.previous = previous;
	}

	@Override
	public boolean check(CurrencyPortal curren) throws RemoteException {
		if(account.containsCurrency(curren)) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void addOptions() {
		if(this.getTabOptions().size() >= 2) {
			this.options.add(new AddCurrencyToAccountOption(this,"Add " + this.getData() + " To Your Account",account,this.getData()));
		}
		else if (this.getTabOptions().size() == 1) {
			this.options.add(new AddCurrencyToAccountOption(previous,"Add " + this.getData() + " To Your Account",account,this.getData()));
		}
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
