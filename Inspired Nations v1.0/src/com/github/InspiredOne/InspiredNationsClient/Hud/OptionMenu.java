package com.github.InspiredOne.InspiredNationsClient.Hud;

import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Listeners.ActionManager;
import com.github.InspiredOne.InspiredNationsClient.Listeners.Implem.MenuUpdateManager;

public abstract class OptionMenu extends ActionMenu {

	public List<Option> options;
	
	public OptionMenu(PlayerData PDI) {
		super(PDI);
	}

/*	@SuppressWarnings("unchecked")
	@Override
	public <T extends Menu> T getSelf(T self) {
		Debug.print("Is self null? " + this.self == null);
		Debug.print(self);
		OptionMenu output = (OptionMenu) this.self;
		output.options = new ArrayList<Option>();
		output.initialized = false;
		return (T) this.self;
	}*/
	
	@Override
	public final String getFiller() {
		String output = "";
		if(!this.getPreOptionText().isEmpty()) {
			output = output.concat(TextColor.INSTRUCTION(PDI) + this.getPreOptionText() + "\n");
			if(!optionsToText(options, PDI).isEmpty()) {
				output = MenuTools.addDivider(output, PDI);
			}
		}
		output = output.concat(optionsToText(options, PDI));
		return output;
	}
	
	@Override
	public final Menu getNextMenu(String arg) {
		int answer;
		try {
			String[] args = arg.split(" ");
			answer = Integer.parseInt(args[0]);
			if(answer > options.size()) {
				this.setError(MenuError.OUT_OF_RANGE_NUMBER_INPUT(PDI));
				return this.getSelfPersist();
			}
			else {
				Option option = options.get(answer - 1);
				if(option.isAvailable()) {
					return option.response(arg.substring(args[0].length()).trim());
				}
				else {
					this.setError(MenuError.makeMessage(options.get(answer - 1).getUnvailReason(), PDI));
					return this.getSelfPersist();
				}
			}
		}
		catch (Exception ex) {
				//ex.printStackTrace();
				this.setError(MenuError.INVALID_NUMBER_INPUT(PDI));
				return this.getSelfPersist();
		}
	}
	
	public static String optionsToText(List<Option> options, PlayerData PDI) {
		String output = "";
		int iter = 1;
		
		for(Option option:options)  {
			if(option.isAvailable()) {
				output = output.concat(TextColor.OPTION(PDI) + "(" + TextColor.OPTIONNUMBER(PDI) + iter + TextColor.OPTION(PDI) + ") "
			+ option.getName() + TextColor.OPTIONDESCRIP(PDI) + " " + option.getDescription() + "\n");
			}
			else {
				output = output.concat(TextColor.UNAVAILABLE(PDI) + "(" + TextColor.UNAVAILREASON(PDI) + iter + TextColor.UNAVAILABLE(PDI) + ") " + option.getName() +
					TextColor.UNAVAILREASON(PDI) + ": " + option.getUnvailReason() + "\n");
			}
			iter ++;
		}
		
		return output;
	}
	
	/**
	 * Used to get the options.
	 * @return	the options for this menu
	 */
	public final List<Option> getOptions() {
		this.Initialize();
		return this.options;
	}
	/**
	 * Used to add text before the list of options.
	 * @return	the text used before the options
	 */
	public abstract String getPreOptionText();

	@Override
	public void actionResponse() {
		
	}
	
	public abstract void addOptions();
	
	// These methods are overridden by all the super classes. I wish there were a better
	// way I could do this. Until then, ctrl-c and ctrl-v.
	@Override
	public void menuPersistent() {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}

		this.getActionManager().clear();
		this.getActionManager().add(new TaxTimerManager<ActionMenu>(this));
		this.getActionManager().add(new MenuUpdateManager<ActionMenu>(this));
		this.addActionManagers();
		
	}

	@Override
	public void nonPersistent() {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.startListening();
		}
		this.addOptions();
	}

	@Override
	public void unloadNonPersist() {
		for(ActionManager<?> manager:this.getActionManager()) {
			manager.stopListening();
		}
		this.options = new ArrayList<Option>();
	}

}
