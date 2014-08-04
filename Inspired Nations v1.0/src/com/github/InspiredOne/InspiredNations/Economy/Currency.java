package com.github.InspiredOne.InspiredNations.Economy;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.NameAlreadyTakenException;
import com.github.InspiredOne.InspiredNations.ToolBox.Config;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;
	
public final class Currency implements Serializable, Nameable, Comparable<Currency>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2855995345401677901L;
	private String name;
	public static final Currency DEFAULT = new Currency("Coin");;
	

	public Currency(String name) {
		//TODO Remove later, figure out when to add a currency to the exchange
		//InspiredNations.Exchange.registerCurrency(this, new BigDecimal(500));
		this.name = name;

		
	}
	
	public BigDecimal getExchangeRate(Currency output) {
		return InspiredNations.Exchange.getExchangeValue(BigDecimal.ONE, this, output);
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) throws NameAlreadyTakenException {
		Currency test = new Currency(name);
		if(InspiredNations.Exchange.getExchangeMap().containsKey(test)) {
			throw new NameAlreadyTakenException();
		}
		else {
			BigDecimal value = InspiredNations.Exchange.getExchangeMap().get(this);
			BigDecimal amount = new BigDecimal(Config.exchangemultiplier);
			InspiredNations.Exchange.unregisterCurrency(this);
			this.name = name;
			InspiredNations.Exchange.registerCurrency(this, value.divide(amount, InspiredNations.Exchange.mcdown));

			/*InspiredNations.Exchange.getExchangeMap().put(test, value);
			Debug.print("Does the exchange map contain: " + test.getName() + InspiredNations.Exchange.getExchangeMap().containsKey(test));
			for(InspiredGov gov:InspiredNations.regiondata) {
				if(gov.getCurrency().equals(this)) {
					gov.setCurrency(test);
				}
				for(Account account:gov.getAccounts()) {
					for(CurrencyAccount caccount:account.getMoney()) {
						if(caccount.getCurrency().equals(this)) {
							caccount.setCurrency(test);
						}
					}
				}
			}
			for(PlayerData PDI:InspiredNations.playerdata.values()) {
				if(PDI.getCurrency().equals(this)) {
					PDI.setCurrency(test);
				}
				for(Account account:PDI.getAccounts()) {
					for(CurrencyAccount caccount:account.getMoney()) {
						if(caccount.getCurrency().equals(this)) {
							caccount.setCurrency(test);
						}
					}
				}
			}
*/
			//this.name = name;
		}
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(name).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Currency))
            return false;

        Currency rhs = (Currency) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).3
            append(name, rhs.getName()).
            isEquals();
    }
    
    @Override
    public String toString() {
    	return this.getName();
    }
    
    @Override
    public String getDisplayName(PlayerData viewer) {
    	return this.getName() + " (1.00 " + this + " =~ " + Tools.cut(InspiredNations.Exchange.getExchangeValue(BigDecimal.ONE, this, viewer.getCurrency()))
				+ viewer.getCurrency() + ")";
    }

	@Override
	public int compareTo(Currency arg0) {
		int compareVal = arg0.getExchangeRate(DEFAULT).multiply(new BigDecimal(1000)).intValue();
		int thisval = this.getExchangeRate(DEFAULT).multiply(new BigDecimal(1000)).intValue();
		return thisval - compareVal;
	}
}
