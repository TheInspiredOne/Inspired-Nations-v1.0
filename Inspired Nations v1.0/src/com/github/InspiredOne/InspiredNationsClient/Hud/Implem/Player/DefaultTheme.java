package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Player;

import org.bukkit.ChatColor;

import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;


public class DefaultTheme extends ThemeOption {

	public DefaultTheme(OptionMenu menu, String label) {
		super(menu, label);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String HEADER() {
		// TODO Auto-generated method stub
		return ChatColor.BLUE + "" + ChatColor.BOLD;
	}

	@Override
	public String SUBHEADER() {
		// TODO Auto-generated method stub
		return ChatColor.YELLOW + "" + ChatColor.ITALIC + "" + ChatColor.BOLD;
	}

	@Override
	public String LABEL() {
		// TODO Auto-generated method stub
		return ChatColor.RED + "";
	}

	@Override
	public String VALUE() {
		// TODO Auto-generated method stub
		return ChatColor.GOLD + "";
	}

	@Override
	public String VALUEDESCRI() {
		// TODO Auto-generated method stub
		return ChatColor.RED + "";
	}

	@Override
	public String DIVIDER() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_AQUA + "";
	}

	@Override
	public String OPTION() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_GREEN + "";
	}

	@Override
	public String OPTIONNUMBER() {
		// TODO Auto-generated method stub
		return ChatColor.YELLOW + "";
	}

	@Override
	public String OPTIONDESCRIP() {
		// TODO Auto-generated method stub
		return ChatColor.GRAY + "";
	}

	@Override
	public String UNAVAILABLE() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_GRAY + "";
	}

	@Override
	public String UNAVAILREASON() {
		// TODO Auto-generated method stub
		return ChatColor.GRAY + "";
	}

	@Override
	public String INSTRUCTION() {
		// TODO Auto-generated method stub
		return ChatColor.YELLOW + "";
	}

	@Override
	public String ERROR() {
		// TODO Auto-generated method stub
		return ChatColor.RED + "";
	}

	@Override
	public String ALERT() {
		// TODO Auto-generated method stub
		return ChatColor.YELLOW + "";
	}

	@Override
	public String UNIT() {
		// TODO Auto-generated method stub
		return ChatColor.YELLOW + "";
	}

	@Override
	public String ENDINSTRU() {
		// TODO Auto-generated method stub
		return ChatColor.AQUA + "";
	}
	

}
