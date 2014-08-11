package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Player;

import java.rmi.RemoteException;

import org.bukkit.ChatColor;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.HUD.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Player.CustomTheme.TextCatagory;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;

public class CustomTheme extends TabSelectOptionMenu<TextCatagory> {

	public CustomTheme(PlayerDataPortal PDI) throws RemoteException {
		super(PDI);
	}

	@Override
	public Menu getPreviousPrompt() throws RemoteException {
		return new ColorMenu(PDI);
	}

	@Override
	public String postTabListPreOptionsText() {
		return "Tab to select color";
	}

	@Override
	public void addTabOptions() {
		
		//HEADER
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Header";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.HEADER() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setHEADER(color + ChatColor.BOLD);
					} 
					
				});
		
		//SUBHEADER
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Sub-Header";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.SUBHEADER() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setSUBHEADER(color + ChatColor.ITALIC + "" + ChatColor.BOLD);
					} 
					
				});
		//OPTION NUMBERS
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Option Numbers (#)";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.OPTIONNUMBER() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setOPTIONNUMBER(color);
					} 
					
				});
		
		//OPTION
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Options";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.OPTION() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setOPTION(color);
					} 
					
				});
		
		//LABEL
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Labels";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.LABEL() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setLABEL(color);;
					} 
					
				});
		
		//VALUE
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Values";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.VALUE()+ this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setLABEL(color);;
					} 
					
				});
		//VALUEDESCRI
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Value Description";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.VALUEDESCRI() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setVALUEDESCRI(color);;
					} 
					
				});
		//DIVIDER
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Divider";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.DIVIDER() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setDIVIDER(color);;
					} 
					
				});
		//OPTIONDESCRI
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Option Descriptions";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.OPTIONDESCRIP() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setOPTIONDESCRI(color);;
					} 
					
				});
		
		//UNAVAILABLE
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Unavailable Options";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.UNAVAILABLE() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setUNAVAILABLE(color);
					} 
					
				});
		//UNAVAILREASON
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Unavailable Reason";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.UNAVAILREASON() + this.getName();
					}
					

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setUNAVAILREASON(color);;
					} 
					
				});
		//INSTRUCTION
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Instruction";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.INSTRUCTION() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setINSTRUCTION(color);
					} 
					
				});
		//ERROR
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Error Messages";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.ERROR() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setERROR(color);
					} 
					
				});
		//ALERT
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Alert Messages";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.ALERT() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setALERT(color);
					} 
					
				});
		//UNIT
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Units";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.UNIT() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setUNIT(color);
					} 
					
				});
		//ENDINSTRUCTION
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Bottom Instructions";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.ENDINSTRU() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setENDINSTRUCT(color);
					} 
				});
		//ENEMY
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Enemies";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.ENEMY() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setENEMY(color);
					} 
				});
		//NEUTRAL
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Neutrals";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.NEUTRAL() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setNEUTRAL(color);
					} 
				});
		//ALLY
		this.taboptions.add(
				new TextCatagory(PDI) {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "ALLIES";
					}

					@Override
					public String getDisplayName(PlayerID viewer) throws RemoteException {
						// TODO Auto-generated method stub
						return PDI.ALLY() + this.getName();
					}

					@Override
					void changeColor(String color) throws RemoteException {
						// TODO Auto-generated method stub
						PDI.getTheme().setALLY(color);
					} 
				});
	}

	@Override
	public void addOptions() throws RemoteException {
		//PICK COLOR
		this.options.add(new PromptOption(this, "Color Selection", new ColorOptions(PDI, this.getData())));
		
	}

	@Override
	public void addActionManagers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return "Choose which section you want to change:";
	}
	
	public abstract class TextCatagory implements Nameable {
		
		PlayerDataPortal PDI;
		
		public TextCatagory(PlayerDataPortal PDI) {
			this.PDI = PDI;
		}

		@Override
		public void setName(String name) throws NameAlreadyTakenException {
			// TODO Auto-generated method stub
			
		}
		
		abstract void changeColor(String color) throws RemoteException;
		
	}

}
