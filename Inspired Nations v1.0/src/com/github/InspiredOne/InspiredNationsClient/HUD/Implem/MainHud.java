package com.github.InspiredOne.InspiredNationsClient.HUD.Implem;

import java.rmi.RemoteException;
import java.util.List;

import com.github.InspiredOne.InspiredNationsClient.HUD.HelpMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;

public class MainHud extends OptionMenu {
	

	public MainHud(PlayerDataInter PDI) throws RemoteException {
		super(PDI);
		help = new HelpMenu(PDI, this).addPage(
				"Welcome to the main HUD. This documentation will help get you "
				+ "familiar with how to use the HUD. You can navigate these help "
				+ "docs by typing a page number in chat and hitting " + PDI.VALUE()
				+ "Enter" + PDI.INSTRUCTION() +".\n \nThe HUD is the source of all your "
				+ "information. It gives you access to "
				+ "your money, government, and other players. You can even make purchases "
				+ "through the HUD!\n \nTo learn how to use the HUD and about its features, "
				+ "read on to page two of the help docs."
				);
		help.addPage(
				"\tThe HUD operates entirely through the chat. You write commands and it "
				+ "executes them. Typically you are presented with a list of numbered "
				+ "options. To select an option, you just type the number that appears "
				+ "beside it. For instance:\n"
				+ PDI.OPTION() + ""
				);
		
	}

	@Override
	public String getPreOptionText() {
		return "";
	}

	@Override
	public String getHeader() {
		return "Welcome to the HUD! Enter an option number.";
	}

	@Override
	public Menu getPreviousMenu() {
		return this.getSelfPersist();
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
	public void addOptions() {
/*		this.options.add(new PromptOption(this, "Map", new Map(PDI)));
		
		boolean showMarket = false;
		for(MarketPlace<?> mark:InspiredNations.Markets) {
			if(!mark.getSales(PDI).isEmpty()) {
				showMarket = true;
			}
		}
		
		if(!InspiredNations.Markets.isEmpty()) {
			if(showMarket) {
				this.options.add(new PromptOption(this, "Market", new PickMarketplace(PDI)));
			}
		}
		

		this.options.add(new PromptOption(this, "Player Directory", new PlayerDirectory(PDI)));
		this.options.add(new PromptOption(this, "Citizenship", new PlayerCitizenship(PDI)));
		this.options.add(new PromptOption(this, "Manage Money", new ManageMoney(PDI),
				"(" + Tools.cut(PDI.getTotalMoney(PDI.getCurrency(), InspiredNations.Exchange.mcdown)) + " "+ PDI.getCurrency() + ")"));
		
		//this.options.add(new getMoneyOption(this, "Get 1000 " + PDI.getCurrency().getName(), PDI));

		
		
		List<Class<? extends OwnerGov>> array = InspiredNations.global.getAllSubGovs();
		array.remove(InspiredNations.global.getClass());
		for(Class<? extends OwnerGov> gov:array) {
			OwnerGov govobj = (OwnerGov) GovFactory.getGovInstance(gov);
			if(!PDI.getOwnership(gov).isEmpty()) {
				this.options.add(new PromptOption(this, "Manage Government", new ManageAll(PDI, this)));
				break;
			}
		}
	
		for(Class<? extends OwnerGov> gov:array) {
			OwnerGov govobj = (OwnerGov) GovFactory.getGovInstance(gov);
			if(!PDI.getCitizenship(govobj.getSuperGov()).isEmpty()) {
				this.options.add(new PromptOption(this, "New Government", new NewAll(PDI, this)));
				break;
			}
		}*/
	}

	@Override
	public void addActionManagers() {
		
	}
}
