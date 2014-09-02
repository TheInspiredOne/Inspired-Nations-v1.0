package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.MainHud;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNationsServer.Debug;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;

public class ManageMoney extends OptionMenu {
	//private MathContext mcup = new MathContext(5, RoundingMode.UP);//this is temporary

	public ManageMoney(PlayerDataPortal PDI) throws RemoteException {
		super(PDI);
	}

	@Override
	public String getPreOptionText() throws RemoteException {
		Debug.print("inside getPreoptionText 0");
		String output = MenuTools.oneLineWallet("", PDI, PDI.getAccounts());
		Debug.print("inside getPreoptionText 1");
		output = output.concat(PDI.getAccounts().getTaxesText(PDI));
		Debug.print("inside getPreoptionText 2");
		return output; 
	}

	@Override
	public boolean getPassBy() {
		return false;
	}

	@Override
	public Menu getPassTo() {
		return null;
	}

	@Override
	public String getHeader() {
		return "Manage Money";
	}

	@Override
	public Menu getPreviousMenu() throws RemoteException {
		return new MainHud(PDI);
	}

	@Override
	public void addOptions() throws RemoteException {
		Debug.print("inside addOptions 1");
		this.options.add(new PromptOption(this, "Pay", new PayNav(PDI, this, PDI)));
		Debug.print("inside addOptions 2");
		this.options.add(new PromptOption(this, "Manage Accounts", new ManageAccounts(PDI, this, PDI.getAccounts())));
		Debug.print("inside addOptions 3");
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

}
