package com.github.InspiredOne.InspiredNations.Economy;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemBuyer;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemMarketplace;
import com.github.InspiredOne.InspiredNations.Economy.Implem.ItemSellable;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNations.ToolBox.CardboardBox;
import com.github.InspiredOne.InspiredNations.ToolBox.Payable;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
import com.github.InspiredOne.InspiredNations.ToolBox.Messaging.Alert;

public class NPC implements Serializable, ItemBuyer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8606492088647654688L;
	public AccountCollection accounts = new AccountCollection("NPC");
	HashMap<CardboardBox,Account> buy = new HashMap<CardboardBox, Account>();
	MathContext mcdown = InspiredNations.Exchange.mcdown;
	MathContext mcup = InspiredNations.Exchange.mcup;
	public NPC() {
		try {
			this.accounts.addMoney(new BigDecimal(100), Currency.DEFAULT);
		} catch (NegativeMoneyTransferException e) {
			e.printStackTrace();
		}
	}
	
	public PlayerData getPlayer() {
		for(PlayerData player:InspiredNations.playerdata.values()) {
			if(player.npcs.contains(this)) {
				return player;
			}
		}
		return null;
	}
	
	@Override
	public String getName() {
		return "NPC";
	}
	
	@Override
	public void setName(String name) throws NameAlreadyTakenException {
		
	}

	@Override
	public String getDisplayName(PlayerData viewer) {
		return this.getName();
	}

	@Override
	public void sendNotification(Alert msg) {
		
	}

	@Override
	public void transferMoney(BigDecimal amount, Currency monType,
			Payable target) throws BalanceOutOfBoundsException,
			NegativeMoneyTransferException {
		if(amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new NegativeMoneyTransferException();
		}
		if(amount.compareTo(this.getTotalMoney(monType, mcup)) <= 0) {
			if(amount.compareTo(this.getTotalUnallocatedMoney(monType)) > 0) {
				amount = amount.subtract(accounts.getTotalMoney(monType, mcdown));
				accounts.transferMoney(accounts.getTotalMoney(monType, mcup), monType, target);
			}
			else {
				accounts.transferMoney(amount, monType, target);
				amount = BigDecimal.ZERO;
			}

			for(Account test:buy.values()) {
				if(amount.compareTo(test.getTotalMoney(monType,mcup)) > 0) {
					amount = amount.subtract(test.getTotalMoney(monType,mcdown));
					test.transferMoney(test.getTotalMoney(monType,mcup), monType, target);
				}
				else {
					test.transferMoney(amount, monType, target);
					amount = BigDecimal.ZERO;
					break;
				}
			}
		}

		else {
			throw new BalanceOutOfBoundsException();
		}
	}

	@Override
	public void addMoney(BigDecimal amount, Currency monType)
			throws NegativeMoneyTransferException {
		this.accounts.addMoney(amount, monType);
	}

	@Override
	public BigDecimal getTotalMoney(Currency valueType, MathContext round) {
		BigDecimal output = accounts.getTotalMoney(valueType, round);
		
		for(Account account:buy.values()) {
			output = output.add(account.getTotalMoney(valueType, round));
		}
		return output;
	}
	
	public BigDecimal getTotalUnallocatedMoney(Currency valueType) {
		return accounts.getTotalMoney(valueType, InspiredNations.Exchange.mcdown);
	}
	
	public void saveMoneyFor(ItemStack stack, BigDecimal amount, Currency curren) throws BalanceOutOfBoundsException, NegativeMoneyTransferException {
		ItemStack stackkey = stack.clone();
		stackkey.setAmount(1);
		if(this.buy.containsKey(new CardboardBox(stackkey))) {
			this.transferMoney(amount, curren, buy.get(new CardboardBox(stackkey)));
		}
		else {
			this.buy.put(new CardboardBox(stackkey), new Account());
			this.saveMoneyFor(stack, amount, curren);
		}
	}
	
/*	public void buyItem(ItemStack stack, Buyer buyer) {
		ItemSellable sellable = ((ItemMarketplace) InspiredNations.Markets.get(0)).getCheapestUnit(stack, buyer);
		sellable.transferOwnership(buyer);
	}*/

	@Override
	public Location getLocation() {
		if(this.getPlayer() == null) {
			return new Location(InspiredNations.plugin.getServer().getWorld("world"),0,0,0);
		}
		return this.getPlayer().getLocation();
	}

	@Override
	public Currency getCurrency() {
		if(this.getPlayer() == null) {
			return Currency.DEFAULT;
		}
		else {
			return this.getPlayer().getCurrency();
		}
	}

	@Override
	public void recieveItem(ItemStack item) {
		
	}
	/**
	 * A method that runs through the buy hashmap and purchases all the items
	 * the npc can afford.
	 */
	public void buyOut() {
		NodeRef noderef = new NodeRef();
		noderef.allocateMoney(this);
		Debug.print("Cardboardbox size: " + buy.size());
		for(CardboardBox boxitem : this.buy.keySet()) {
			ItemStack stack = boxitem.unbox();
			ItemSellable cheapest =((ItemMarketplace) InspiredNations.Markets.get(0)).getCheapestUnit(stack, this);
			Debug.print(stack.getData().getItemType().toString() + ": " + Tools.cut(this.buy.get(boxitem).getTotalMoney(getCurrency(), mcdown)) + " " +this.getCurrency());
			if(cheapest != null) {
				//Debug.print("Enough money? " + (this.buy.get(boxitem).getTotalMoney(this.getCurrency()).compareTo(cheapest
				//		.getPrice(this.getCurrency(), getLocation())) >= 0));
				while(this.buy.get(boxitem).getTotalMoney(this.getCurrency(), mcdown).compareTo(cheapest
						.getPrice(this.getCurrency(), getLocation())) >= 0) {
				//	Debug.print("We're doing it! We're making it hapen!!!" );
					cheapest.transferOwnership(this, this.buy.get(boxitem));
					Debug.print("Looping! " + this.buy.get(boxitem).getTotalMoney(this.getCurrency(), mcdown));
					if(((ItemMarketplace) InspiredNations.Markets.get(0)).getCheapestUnit(stack, this)==null) {
						break;
					}
					else {
						cheapest = ((ItemMarketplace) InspiredNations.Markets.get(0)).getCheapestUnit(stack, this);
					}
				}	
			}
		}
	}


}
