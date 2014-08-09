package com.github.InspiredOne.InspiredNationsServer.Economy.Implem;

import org.bukkit.inventory.ItemStack;

public interface ItemBuyer extends Buyer {
	public void recieveItem(ItemStack item);
}
