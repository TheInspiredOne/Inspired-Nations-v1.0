package com.github.InspiredOne.InspiredNationsClient.HUD;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.MenuUpdateManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.TabScrollManager;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Datable;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;

/**
 * A menu where the options are listed in plain font except the one that is selected (it's bold).
 * The related options to the selection are listed in the options section of the menu. The data is null until after Init()
 * is run, so in order to do anything with it in the subclass, Init() must be run. This is because Init() is used for
 * putting options into the tab menu, and data is dependent on the list of tab options.
 * 
 * @author Jedidiah Phillips
 *
 * @param <E>
 */
public abstract class TabSelectOptionMenu<E extends Nameable> extends OptionMenu implements Datable<E> {

	protected int tabcnt = 0;
	protected int sort = 0;
	private String filterword = "";
	private int rangeBottom = maxLines;
	private static final int maxLines = 7;
	public int linesperitem = 1;
	protected ArrayList<E> taboptions = new ArrayList<E>();
	public ArrayList<E> filteredoptions = new ArrayList<E>();
	private E data;
	private TabScrollManager<TabSelectOptionMenu<E>> manager;
	public TabSelectOptionMenu(PlayerDataInter PDI) throws RemoteException {
		super(PDI);
	}

	public List<E> getTabOptions() {
		return this.taboptions;
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
	public String getPreOptionText() throws RemoteException {
		String output = "";
		if(!tabOptionsToText(taboptions, tabcnt).isEmpty()) {
			output = output.concat(tabOptionsToText(taboptions, tabcnt));
			output = MenuTools.addDivider(output, this.getPlayerData());
			output = output.concat(this.getPlayerData().INSTRUCTION() + "Press '+' + TAB or '-' + TAB to cycle through the list.");
		}
		if(!this.postTabListPreOptionsText().isEmpty()) {
			output = MenuTools.addDivider(output.concat("\n"),this.getPlayerData());
			output = output.concat(this.postTabListPreOptionsText() + "\n");
		}
		return output;
	}
	
	public String tabOptionsToText(List<? extends Nameable> taboptions, int tabcnt) throws RemoteException {
		String output = "";
		int realmaxLines=maxLines/linesperitem;
		//int iter = 0; // Used to identify which option to highlight

		// loop to set range that is displayed
		while(tabcnt >= rangeBottom || tabcnt < rangeBottom - realmaxLines) {
			if(tabcnt >= rangeBottom) {
				rangeBottom++;
				continue;
			}
			if(tabcnt < rangeBottom - realmaxLines) {
				rangeBottom--;
				continue;
			}
		}
		// write the text
		for(int iter = 0; iter<filteredoptions.size(); iter++) {
			output = output.concat(ChatColor.RESET + "");
			Nameable option = filteredoptions.get(iter);
			if(iter >= rangeBottom - realmaxLines && iter < rangeBottom) {
				if(tabcnt == iter) {
					output = output.concat(this.getPlayerData().LABEL() + "=> " + option.getDisplayName(this.PDI.getPlayerID()) + "\n");
				}
				else {
					output = output.concat(this.getPlayerData().LABEL() + option.getDisplayName(this.PDI.getPlayerID()) + "\n");
				}
			}
		}
		return output;
	}

	@Override
	public void actionResponse() throws RemoteException {
		int tabsize = this.filteredoptions.size();
		if(this.manager.updateFromTabScroll) {
			this.manager.updateFromTabScroll = false;
			this.PDI.getMsg().clearMenuVisible();
			if(manager.preTabEntry.equalsIgnoreCase("+") && tabsize > 0) {
				this.setTabcnt(((this.getTabcnt() - 1) + tabsize) % tabsize);
			}
			else if(manager.preTabEntry.equalsIgnoreCase("-") && tabsize > 0) {
				this.setTabcnt((this.getTabcnt() + 1) % tabsize);
			}
			else if(manager.preTabEntry.equalsIgnoreCase("=")) {
				sort++;
			}
			else if(!manager.preTabEntry.isEmpty()) {
				this.filterword = manager.preTabEntry;
				ArrayList<E> tempOptions = MenuTools.filter(filterword, this.taboptions);
				if(tempOptions.size() <= 0) {
					this.setError(MenuError.NO_MATCHES_FOUND(this.getPlayerData()));
					return;
				}
				else {
					this.filteredoptions = tempOptions;
					this.setTabcnt(0);
				}
			}
			else {
				return;
			}
			this.setData(this.filteredoptions.get(tabcnt));
		}
	}
	
	public int getTabcnt() {
		return tabcnt;
	}

	public void setTabcnt(int tabcnt) {
		if(this.tabcnt > 1024) {
			// Because trolls and memory leaks t(<.<)t
			//this.tabcnt = 0;
			this.tabcnt = tabcnt; // delete this and use the top one if you think it's correct
		}
		else {
			this.tabcnt = tabcnt;
		}
	}
	@Override
	public final Menu getPreviousMenu() throws RemoteException {
		if(!this.filteredoptions.containsAll(taboptions)) {
			filterword = "";
			return this.getSelfPersist();
		}
		else {
			return getPreviousPrompt();
		}
	}
	
	public void setData(E data) {
		this.data = data;
	}
	
	@Override
	public E getData() {
		
		while(data==null && tabcnt >=0) {
			try {
				this.data = this.filteredoptions.get(tabcnt);
			}
			catch (Exception ex) {
				tabcnt--;
			}
		}
		try{
			this.data = this.filteredoptions.get(tabcnt);
		}
		catch (Exception ex) {
			return null;
		}
		
		return this.data;
	}
	
	/**
	 * Gets the previous menu
	 * @return	 the previous menu
	 * @throws RemoteException 
	 */
	public abstract Menu getPreviousPrompt() throws RemoteException;
	/**
	 * Used to insert text between tab options and input options
	 * @return	the text to be inserted
	 */
	public abstract String postTabListPreOptionsText();
	
	public abstract void addTabOptions() throws RemoteException;
	
	// These methods are overridden by all the super classes. I wish there were a better
	// way I could do this. Until then, ctrl-c and ctrl-v.
	@Override
	public void menuPersistent() throws RemoteException {
		this.setError(MenuError.NO_ERROR());
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		this.getActionManager().clear();
//		this.getActionManager().add(new TaxTimerManager<ActionMenu>(this));
		this.getActionManager().add(new MenuUpdateManager<ActionMenu>(this));
		manager = new TabScrollManager<TabSelectOptionMenu<E>>(this);
		this.getActionManager().add(manager);
		this.addActionManagers();
	}

	@Override
	public void nonPersistent() throws RemoteException {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.startListening();
		}
		this.addTabOptions();
		this.filteredoptions = MenuTools.filter(filterword, this.taboptions);
		if(this.filteredoptions.size() == 0 && this.taboptions.size() != 0) {
			//this.setError(MenuError.NO_MATCHES_FOUND(this.getPlayerData()));
			return;
		}
		else if(this.filteredoptions.size() != 0) {
			this.data = this.getData();
		}
		this.addOptions();
	}

	@Override
	public void unloadNonPersist() throws RemoteException {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		this.options = new ArrayList<Option>();
		this.taboptions = new ArrayList<E>();
		this.filteredoptions = new ArrayList<E>();
	}

	@Override
	public void unloadPersist() throws RemoteException {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		this.getActionManager().clear();
	}
}
