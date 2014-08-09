package com.github.InspiredOne.InspiredNationsServer.Economy.Implem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.PlayerData;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Economy.Sellable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NoShopRegionException;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.CardboardBox;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Point3D;

public class ItemSellable implements Sellable, Nameable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2447803453590052464L;
	private CardboardBox saveData;
	ChestShop shop;
	private BigDecimal price = BigDecimal.ZERO;
	private Currency curren = Currency.DEFAULT;
	//private String saveData;
	
	public ItemSellable(ItemStack item, ChestShop shop) {
		saveData = new CardboardBox(item);
		this.shop = shop;
	}
	
	public ItemStack getItem() {
		return saveData.unbox();
	}
	
	public void setAmount(int amount) {
		ItemStack stack = this.getItem();
		stack.setAmount(amount);
		saveData=new CardboardBox(stack);
	}

	@Override
	public String getName() {
		String enchantments ="";
		if(this.getItem().getItemMeta().hasEnchants()) {
			enchantments = " [";
			for(Entry<Enchantment, Integer> ench:this.getItem().getEnchantments().entrySet()) {
				if(ench.getValue() > 0) {
					enchantments = enchantments.concat(ench.getKey().getName() + " " + ench.getValue()+",").toLowerCase().replace("_", " ");
				}
			}
			enchantments = enchantments.substring(0, enchantments.length()-2).concat("]");	
		}
		
		String name = this.getItem().getType().name().toLowerCase().replace('_', ' ') + enchantments;
		//name = name.concat(" (" + this.getItem().getData().getItemType().name() + ")");
/*		if(this.getItem().getItemMeta().hasEnchants()) {
			for(Enchantment ench:this.getItem().getItemMeta().getEnchants().keySet()) {
				name = name.concat("*" + ench.getName() + " " + this.getItem().getItemMeta().getEnchantLevel(ench) + "*");
			}
		}*/
		return name;
	}

	@Override
	public void setName(String name) {

	}

	@Override
	public String getDisplayName(PlayerID viewer) {
		if(this.shop.getItems().contains(this)) {
			String output = this.getName() + "\n" + TextColor.VALUE(viewer) + this.getItem().getAmount() + TextColor.INSTRUCTION(viewer) + " for " +
					TextColor.VALUE(viewer) + Tools.cut(this.getPrice(viewer.getCurrency(), viewer.getLocation())) + " " +
					TextColor.UNIT(viewer) + viewer.getCurrency() + ": " + TextColor.VALUE(viewer) + 
					Tools.cut(InspiredNations.Exchange.getTransferValue(price, this.curren, viewer.getCurrency(), InspiredNations.Exchange.mcup)) + 
					TextColor.INSTRUCTION(viewer) + " + " + TextColor.VALUE(viewer) + Tools.cut(this.getTransCost(viewer.getCurrency(), viewer.getLocation()));
			return output;
		}
		else {
			return this.getName();
		}
	}

	@Override
	public void transferOwnership(Buyer buyer, Payable account) {
		int sold = this.getItem().getAmount();
		ArrayList<ItemStack> selling = new ArrayList<ItemStack>();
		for(ItemStack item:this.shop.getInvetorySellables()) {
			if(this.getItem().isSimilar(item)) {
				selling.add(item);
				if(item.getAmount() >= sold) {
					sold = 0;
				}
				else {
					sold = sold - item.getAmount();
				}
			}
			if(sold == 0) break;
		}
		ItemStack transfer = getItem().clone();

		transfer.setAmount(transfer.getAmount() - sold);
		try {
			BigDecimal pricereal = this.price.multiply(new BigDecimal((double) transfer.getAmount()).
					divide(new BigDecimal((double) this.getItem().getAmount()),InspiredNations.Exchange.mcup));
			account.transferMoney(pricereal, this.curren, this.shop.getAccounts());
			for(PlayerID player:shop.getOwners()) {
				player.getPDI().payNPC(this.getTransCost(this.curren, buyer.getLocation())
						.divide(new BigDecimal(shop.getOwners().size()), InspiredNations.Exchange.mcdown),
						this.curren, account);
			}
			((ItemBuyer) buyer).recieveItem(transfer);
			try {
				shop.getInventory().removeItem(transfer);
			} catch (NoShopRegionException e) {
				//e.printStackTrace();
			}
		} catch (BalanceOutOfBoundsException | NegativeMoneyTransferException e) {
			if(buyer instanceof PlayerData) {
				((PlayerData) buyer).getMsg().receiveError(MenuError.NOT_ENOUGH_MONEY((PlayerData) buyer));
			}
			//e.printStackTrace();
		}
	}
	
	public void transferOwnership(PlayerData PDI) {
		this.transferOwnership(PDI, PDI);
	}
	
	@Override
	public boolean isForSale() {
		if(this.shop.getItems().contains(this)) {
			try {
				for(ItemStack stack:shop.getInventory()) {
					if(stack != null) {
						ItemSellable itemtemp = new ItemSellable(stack, shop);
						if(itemtemp.equals(this)) {
								return true;
						}
					}
				}
				return false;
			} 
			catch (NoShopRegionException e) {
				return false;
			}
		}
		else return false;
	}

	@Override
	public Point3D getLocation() {
		return new Point3D(shop.getRegion().getRegion().getCharacteristicPoint());
	}

	@Override
	public BigDecimal getPrice(Currency curren, Point3D point3d) {
		return InspiredNationsServer.Exchange.getTransferValue(price, this.curren, curren, InspiredNationsServer.Exchange.mcup).
				add(this.getTransCost(curren, point3d));
		
	}
	
	public BigDecimal getUnitPrice(Currency curren, Point3D point3d) {
		//Debug.print("Inside getUnitPrice" + this.getPrice(curren, locto).divide(new BigDecimal(this.getItem().getAmount())));
		return this.getPrice(curren, point3d).divide(new BigDecimal(this.getItem().getAmount()), InspiredNationsServer.Exchange.mcup);
	}
	
	public BigDecimal getTransCost(Currency curren, Point3D point3d) {
		//TODO think about the transportation cost function.
		double dist = point3d.distance(this.getLocation());
		
		return InspiredNationsServer.Exchange.getTransferValue((new BigDecimal(dist)).divide((new BigDecimal(10000))),
				Currency.DEFAULT,curren, InspiredNationsServer.Exchange.mcup);
	}
	
	public void setPrice(BigDecimal mon, Currency monType) {
		this.price = mon;
		this.curren = monType;
	}
	
	@Override
	public boolean equals(Object e) {
		try {
		return this.getItem().isSimilar(((ItemSellable) e).getItem());
		}
		catch (Exception ex) {
			return false;
		}
	}

}
