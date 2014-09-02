package com.github.InspiredOne.InspiredNationsClient.HUD.Implem.Money;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import com.github.InspiredOne.InspiredNationsClient.HUD.Menu;
import com.github.InspiredOne.InspiredNationsClient.HUD.Option;
import com.github.InspiredOne.InspiredNationsClient.HUD.OptionMenu;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuAlert;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;


public class PayAccountOption extends Option {

	Payable accountsFrom;
	Payable accountTo;
	PlayerDataPortal PDI;
	Nameable sender;
	
	public PayAccountOption(PlayerDataPortal PDI, OptionMenu menu, String label, Payable accountsFrom, Payable accountTo, Nameable sender) {
		super(menu, label);
		this.accountsFrom = accountsFrom;
		this.accountTo = accountTo;
		this.PDI = PDI;
		this.sender = sender;
	}

	@Override
	public Menu response(String input) throws RemoteException {
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
