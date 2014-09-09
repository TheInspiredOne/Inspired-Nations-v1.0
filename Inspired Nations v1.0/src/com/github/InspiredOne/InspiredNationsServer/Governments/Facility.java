package com.github.InspiredOne.InspiredNationsServer.Governments;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.NPC;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
/**
 * Facilities cannot have subgovs
 * @author Jedidiah E. Phillips
 *
 */
public abstract class Facility extends InspiredGov implements Serializable, Nameable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6514705223412901675L;

	@Override
	public final List<Class<? extends OwnerGov>> getSubGovs() {
		List<Class<? extends OwnerGov>> output = new ArrayList<Class<? extends OwnerGov>>();
		return output;
	}
	
	/**
	 * Gets the super that taxes are paid to.
	 * @return
	 */
	@Override
	public InspiredGov getTaxSuper() {
		InspiredGov output = this;
		while(output instanceof Facility) {
			output = output.getSuperGovObj();
		}
		return output.getSuperGovObj();
	}
	
	@Override
	public BigDecimal currentTaxCycleValue(Currency curren) {
		return this.taxValue(this.getRegion().getRegion(), 1, this.protectionlevel, this.getAdditionalCost(curren),
				this.getCurrentTaxedRate(), curren);
	}
	
	@Override
	public void paySuper(BigDecimal amount, Currency curren) throws BalanceOutOfBoundsException, NegativeMoneyTransferException {
		if(this.getTaxSuper() instanceof GlobalGov) {
			int npccount = 0;
			for(PlayerID player:this.getSuperGovObj().getSubjects()) {
				npccount += player.getPDI().npcs.size();
			}
			BigDecimal payment = amount.divide(new BigDecimal(npccount), InspiredNations.Exchange.mcdown);
			for(PlayerID player:this.getSuperGovObj().getSubjects()) {
				for(NPC npc:player.getPDI().npcs) {
					this.transferMoney(payment, curren, npc);
				}
			}
		}
		else {
			this.transferMoney(amount, curren, this.getTaxSuper());
		}
	}

	@Override
	public void pullFromSuper(BigDecimal amount, Currency curren) throws BalanceOutOfBoundsException, NegativeMoneyTransferException {
		this.getTaxSuper().transferMoney(amount, curren, this);
		//this.transferMoney(amount, curren, this.getSuperGovObj().getSuperGovObj()); I'm not sure why this is like this rather than the line above.
	}
	@Override
	public double getSuperTaxRate() {
		Debug.print("Facility: GetSuperTaxRate()");
		 return this.getSuperGovObj().getSuperTaxRate();
	}
	
	@Override
	public String getOwnerPositionName() {
		return "Caretaker";
	}
	
	public List<PlayerID> getOwners() {
		List<PlayerID> output = new ArrayList<PlayerID>();
		if(this.getSuperGovObj() instanceof Facility) {
			output.addAll(((Facility) this.getSuperGovObj()).getOwners());
		}
		else {
			output.addAll(((OwnerGov) this.getSuperGovObj()).getOwners());
		}
		return output;
	}
	
	public int getMilitaryLevel() {
		return this.getSuperGovObj().getMilitaryLevel();
	}

	/**
	 * Gets all the options that this facility needs to be functional as a facility.
	 * @return
	 */
	public abstract void setFunctionOptions(PlayerData PDI, OptionMenu menu);
	/**
	 * Is there only allowed to be one of these? 
	 * @return
	 */
	public abstract boolean isUnique();
}
