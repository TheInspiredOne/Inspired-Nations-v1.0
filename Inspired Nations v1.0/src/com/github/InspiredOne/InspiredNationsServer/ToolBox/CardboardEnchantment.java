package com.github.InspiredOne.InspiredNationsServer.ToolBox;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.enchantments.Enchantment;

/**
* A serializable Enchantment
*/
public class CardboardEnchantment implements Serializable {
    private static final long serialVersionUID = 8973856768102665381L;
 
    private final int id;
 
    public CardboardEnchantment(Enchantment enchantment) {
        this.id = enchantment.getId();
    }
 
    public Enchantment unbox() {
        return Enchantment.getById(this.id);
    }
    
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(id).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof CardboardEnchantment))
            return false;

        CardboardEnchantment rhs = (CardboardEnchantment) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(id, rhs.id).
            isEquals();
    }
    
}
