package com.github.InspiredOne.InspiredNationsServer.ToolBox;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
 

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
 
/**
* A serializable ItemStack
*/
public class CardboardBox implements Serializable {
    private static final long serialVersionUID = 729890133797629668L;
 
    private final int type, amount;
    private final short damage;
    private final byte data;
 
    private final HashMap<CardboardEnchantment, Integer> enchants;
 
    public CardboardBox(ItemStack item) {
        this.type = item.getTypeId();
        this.amount = item.getAmount();
        this.damage = item.getDurability();
        this.data = item.getData().getData();
 
        HashMap<CardboardEnchantment, Integer> map = new HashMap<CardboardEnchantment, Integer>();
 
        Map<Enchantment, Integer> enchantments = item.getEnchantments();
 
        for(Enchantment enchantment : enchantments.keySet()) {
            map.put(new CardboardEnchantment(enchantment), enchantments.get(enchantment));
        }
 
        this.enchants = map;
    }
 
    public ItemStack unbox() {
        ItemStack item = new ItemStack(type, amount, damage, data);
 
        HashMap<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
 
        for(CardboardEnchantment cEnchantment : enchants.keySet()) {
            map.put(cEnchantment.unbox(), enchants.get(cEnchantment));
        }
 
        item.addUnsafeEnchantments(map);
 
        return item;
    }
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(type).
            append(amount).
            append(damage).
            append(data).
            append(enchants).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof CardboardBox))
            return false;

        CardboardBox rhs = (CardboardBox) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(type, rhs.type).
            append(amount, rhs.amount).
            append(damage, rhs.damage).
            append(data, rhs.data).
            append(enchants, rhs.enchants).
            isEquals();
    }
}
