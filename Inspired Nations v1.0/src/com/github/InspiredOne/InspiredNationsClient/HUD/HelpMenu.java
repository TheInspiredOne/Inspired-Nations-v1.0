package com.github.InspiredOne.InspiredNationsClient.HUD;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


public class HelpMenu extends InputMenu {

	private int pagenum = 0;
	private ArrayList<String> docs = new ArrayList<String>();
	private Menu previous;
	
	public HelpMenu(PlayerDataPortal PDI, Menu previous) throws RemoteException {
		super(PDI);
		this.previous = previous;
	}

	@Override
	public Menu nextMenu() {
		return this;
	}

	@Override
	public String validate(String input) {
		try{
			int page = Integer.parseInt(input);
			if(page > docs.size() || page < 1) {
				return MenuError.HELP_PAGE_NOT_AVAILABLE(docs.size(), PDI);
			}
			else {
				pagenum = page-1;
				return MenuError.NO_ERROR();
			}
		}
		catch (Exception ex) {
			return MenuError.INVALID_NUMBER_INPUT(PDI);
		}
	}

	@Override
	public void useInput(String input) {
		
	}

	@Override
	public void addTabOptions() {

	}

	@Override
	public String getInstructions() throws RemoteException {
		String output = "Type the page number you want to read. Pages available: \n";
		output = output.concat(pageNumList() + "\n");
		if(docs.size() > 0) {
			output = MenuTools.addDivider(output,PDI);
			output = output.concat(PDI.INSTRUCTION() + docs.get(pagenum));
		}
		return output;
	}
	
	private String pageNumList() throws RemoteException {
		if(docs.size() == 0) {
			return PDI.ERROR() + "There are no help docs for this topic.";
		}
		else {
			String output = "";
			for(int i = 0; i < docs.size(); i++) {
				if(i == pagenum) {
					output = output.concat(PDI.VALUE().toString() + (i + 1) + ", ");
				}
				else {
					output = output.concat(PDI.VALUEDESCRI().toString() + (i + 1) + ", ");
				}
			}
			output = output.substring(0, output.length() - 2);

			return output;
		}

	}

	@Override
	public void addActionManagers() {

	}

	@Override
	public Menu getPreviousMenu() {
		return previous;
	}

	@Override
	public boolean getPassBy() {
		return false;
	}

	@Override
	public String getHeader() {
		return "Help Docs";
	}
	
	public HelpMenu addPage(String text) {
		this.docs.add(text);
		return this;
	}

}
