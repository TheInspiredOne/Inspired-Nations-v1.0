package com.github.InspiredOne.InspiredNationsClient.Hud.Implem.Money;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuAlert;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNations.ToolBox.Payable;
import com.github.InspiredOne.InspiredNationsClient.Hud.Menu;
import com.github.InspiredOne.InspiredNationsClient.Hud.Option;
import com.github.InspiredOne.InspiredNationsClient.Hud.OptionMenu;

public class PayAccountOption extends Option {

	Payable accountsFrom;
	Payable accountTo;
	PlayerData PDI;
	Nameable sender;
	
	public PayAccountOption(PlayerData PDI, OptionMenu menu, String label, Payable accountsFrom, Payable accountTo, Nameable sender) {
		super(menu, label);
		this.accountsFrom = accountsFrom;
		this.accountTo = accountTo;
		this.PDI = PDI;
		this.sender = sender;
	}

	@Override
	public Menu response(String input) {
		String[] args = input.split(" ");
		try {
			BigDecimal amount = new BigDecimal(args[0]);
	
			try {
				accountsFrom.transferMoney(amount, PDI.getCurrency(), accountTo);
				
				accountTo.sendNotification(MenuAlert.RECEIVED_MONEY(amount, PDI.getCurrency(), sender));
				accountsFrom.sendNotification(MenuAlert.TRANSFER_SUCCESSFUL(amount, PDI.getCurrency(), sender, accountTo));
				//accountTo.sendNotification(MenuAlert.RECEIVED_MONEY(amount, PDI.getCurrency(), PDI));
			} catch (BalanceOutOfBoundsException e) {
				e.printStackTrace();
				menu.setError(MenuError.NOT_ENOUGH_MONEY(this.PDI));
			} catch (NegativeMoneyTransferException e) {
				e.printStackTrace();
				menu.setError(MenuError.NEGATIVE_AMOUNTS_NOT_ALLOWED(amount, this.PDI));
			}

			return menu;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return menu.setError(MenuError.INVALID_NUMBER_INPUT(this.PDI));
		}
	}
}
