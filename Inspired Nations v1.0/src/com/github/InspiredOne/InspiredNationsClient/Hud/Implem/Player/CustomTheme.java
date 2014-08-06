package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Player;

import org.bukkit.ChatColor;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.PromptOption;
import com.github.InspiredOne.InspiredNationsClient.Hud.TabSelectOptionMenu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Player.CustomTheme.TextCatagory;

public class CustomTheme extends TabSelectOptionMenu<TextCatagory> {

	public CustomTheme(PlayerData PDI) {
		super(PDI);
	}

	@Override
	public Menu getPreviousPrompt() {
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return viewer.theme.HEADER() + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setHEADER(color + ChatColor.BOLD);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return viewer.theme.SUBHEADER() + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setSUBHEADER(color + ChatColor.ITALIC + "" + ChatColor.BOLD);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return viewer.theme.OPTIONNUMBER() + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setOPTIONNUMBER(color);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return viewer.theme.OPTION() + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setOPTION(color);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.LABEL(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setLABEL(color);;
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.VALUE(viewer)+ this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setLABEL(color);;
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.VALUEDESCRI(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setVALUEDESCRI(color);;
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.DIVIDER(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setDIVIDER(color);;
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.OPTIONDESCRIP(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setOPTIONDESCRI(color);;
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.UNAVAILABLE(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setUNAVAILABLE(color);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.UNAVAILREASON(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setUNAVAILREASON(color);;
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.INSTRUCTION(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setINSTRUCTION(color);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.ERROR(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setERROR(color);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.ALERT(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setALERT(color);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.UNIT(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setUNIT(color);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.ENDINSTRU(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setENDINSTRUCT(color);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.ENEMY(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setENEMY(color);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.NEUTRAL(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setNEUTRAL(color);
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
					public String getDisplayName(PlayerData viewer) {
						// TODO Auto-generated method stub
						return TextColor.ALLY(viewer) + this.getName();
					}

					@Override
					void changeColor(String color) {
						// TODO Auto-generated method stub
						PDI.theme.setALLY(color);
					} 
				});
	}

	@Override
	public void addOptions() {
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
		
		PlayerData PDI;
		
		public TextCatagory(PlayerData PDI) {
			this.PDI = PDI;
		}

		@Override
		public void setName(String name) throws NameAlreadyTakenException {
			// TODO Auto-generated method stub
			
		}
		
		abstract void changeColor(String color);
		
	}

}
