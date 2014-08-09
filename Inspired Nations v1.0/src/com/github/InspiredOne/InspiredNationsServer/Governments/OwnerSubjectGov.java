package com.github.InspiredOne.InspiredNationsServer.Governments;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeMilitaryLevelExecption;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNations.Exceptions.NegativeProtectionLevelException;
import com.github.InspiredOne.InspiredNations.ToolBox.IndexedSet;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.Relation;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuAlert;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;

public abstract class OwnerSubjectGov extends OwnerGov {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3448350609903588185L;
	private int militarylevel = 0;
	private IndexedSet<PlayerID> subjects = new IndexedSet<PlayerID>();
	private IndexedSet<PlayerID> subjectRequests = new IndexedSet<PlayerID>();
	private IndexedSet<PlayerID> subjectOffers = new IndexedSet<PlayerID>();

	public OwnerSubjectGov() {
		super();
	}

	@Override
	public BigDecimal getAdditionalCost(Currency curren) {

		return getAdditionalCost(this.getMilitaryLevel(), curren);
	}
	
	public BigDecimal getAdditionalCost(int militarylevel, Currency curren) {
		//TODO determine the function for the cost of war here.
		return (new BigDecimal(100)).multiply((new BigDecimal(militarylevel)).pow(2));
	}
	
	public void addSubject(PlayerID player) {
		if(!this.canAddWithoutConsequence(player.getPDI())) {
			for(InspiredGov gov:player.getPDI().getCitizenship(this.getClass())) {
				if(!gov.equals(this)) {
					((OwnerSubjectGov) gov).removeSubject(player);
				}
			}
		}
		this.subjectOffers.remove(player);
		this.subjectRequests.remove(player);
		this.subjects.add(player);
		for(OwnerGov gov:player.getPDI().getAllOwnerApplications()) {
			if(this.getGovsLost(gov, player).size() > 0) {
				gov.getOwnerRequests().remove(player);
			}
		}
		for(OwnerSubjectGov gov:player.getPDI().getAllResidenceApplications()) {
			if(this.getGovsLost(gov, player).size() > 0) {
				gov.getSubjectRequests().remove(player);
			}
		}
		player.getPDI().sendNotification(MenuAlert.ADDED_AS_SUBJECT_TO_GOV(this, this.getSubjectPositionName()));
	}
	
	public void removeSubject(PlayerID player) {
		this.removeOwner(player);
		this.subjects.remove(player);
		for(OwnerGov govlost:this.getGovsLost(player)) {
			govlost.removePlayer(player);
		}
		if(this.isSubjectLess()) {
			player.getPDI().sendNotification(MenuAlert.GOV_UNREGISTERED(this));
			this.unregister();
			this.joinAccount(player.getPDI());
		}
	}
	
	@Override
	public IndexedSet<PlayerID> getSubjects() {
		subjects.addAll(this.getOwners());
		return subjects;
	}

	public void setSubjects(IndexedSet<PlayerID> subjects) {
		this.subjects = subjects;
	}
	
	public void payTaxes() {
		for(Class<? extends InspiredGov> govtype:this.getAllSubGovs()) {
			for(InspiredGov subgov:this.getAllSubGovs(govtype)) {
				subgov.payTaxes();
			}
		}
		while(this.getTotalMoney(this.getCurrency(), InspiredNations.Exchange.mcdown).compareTo(this.currentTaxCycleValue(this.getCurrency())) < 0 &&
				this.getMilitaryLevel() > 0) {
				try {
					this.setMilitaryLevel(this.getMilitaryLevel() - 1);
				} catch (NegativeMilitaryLevelExecption e) {
					e.printStackTrace();
				} catch (BalanceOutOfBoundsException e) {
					e.printStackTrace();
				}
		}
		while(this.getTotalMoney(this.getCurrency(), InspiredNations.Exchange.mcdown).compareTo(this.currentTaxCycleValue(this.getCurrency())) < 0 &&
				this.getProtectionlevel() > 0) {
			try {
				this.setProtectionlevel(this.getProtectionlevel() - 1);
			} catch (NegativeProtectionLevelException e) {
				e.printStackTrace();
			} catch (BalanceOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		try {
			this.paySuper(this.currentTaxCycleValue(getCurrency()), this.getCurrency());
		} catch (BalanceOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NegativeMoneyTransferException e) {
			e.printStackTrace();
		}
		for(Facility fac:this.getFacilities()) {
			fac.payTaxes();
		}
	}

	@Override
	public int getMilitaryLevel() {
		return militarylevel;
	}

	public void setMilitaryLevel(int militarylevel) throws NegativeMilitaryLevelExecption, BalanceOutOfBoundsException {
		if(militarylevel < 0) {
			throw new NegativeMilitaryLevelExecption();
		}
/*		else {
			BigDecimal refund = this.taxValue(this.getRegion().getRegion(),InspiredNations.taxTimer.getFractionLeft(), this.protectionlevel, Currency.DEFAULT);
			BigDecimal newcost = this.taxValue(this.getRegion().getRegion(),InspiredNations.taxTimer.getFractionLeft(), this.protectionlevel, this.getAdditionalCost(militarylevel), this.taxedrate, Currency.DEFAULT);
			BigDecimal cost = refund.subtract(newcost);
			if(cost.compareTo(BigDecimal.ZERO) < 0) {
				try {
					this.paySuper(cost.negate(), Currency.DEFAULT);
				} catch (NegativeMoneyTransferException e) {
				}
			}
			else {
				try {
					this.pullFromSuper(cost, Currency.DEFAULT);
				} catch (NegativeMoneyTransferException e) {
				} catch (BalanceOutOfBoundsException e) {
					try {
						this.pullFromSuper(this.getSuperGovObj().getAccounts().getTotalMoney(Currency.DEFAULT), Currency.DEFAULT);
					} catch (NegativeMoneyTransferException e1) {
					}
				}
			}
			this.militarylevel = militarylevel;
		}*/
		else {
			
		}
		this.militarylevel = militarylevel;
	}
	/**
	 * Gets a list of all the requests made by players to join the subjects of this government
	 * @return
	 */
	public IndexedSet<PlayerID> getSubjectRequests() {
		return subjectRequests;
	}
	/**
	 * sets a list of all the requests made by players to join the subjects of this government
	 * @return
	 */
	public void setSubjectRequests(IndexedSet<PlayerID> subjectRequests) {
		this.subjectRequests = subjectRequests;
	}
	/**
	 * gets a list of all the offers made by this government for players to join the subjects.
	 * @return
	 */
	public IndexedSet<PlayerID> getSubjectOffers() {
		return subjectOffers;
	}
	/**
	 * sets a list of all the offers made by this government for players to join the subjects.
	 * @param subjectOffers
	 */
	public void setSubjectOffers(IndexedSet<PlayerID> subjectOffers) {
		this.subjectOffers = subjectOffers;
	}
	/**
	 * Gets the name of the subjects to be used in the menus
	 * @return
	 */
	public abstract String getSubjectPositionName();
	
	@Override
	public String getDisplayName(PlayerData PDI) {
		String color = TextColor.NEUTRAL(PDI);
		for(OwnerGov gov:PDI.getCitizenship()) {
			if(this.getRelations().get(gov) == Relation.ENEMY
					|| gov.getRelations().get(this) == Relation.ENEMY) {
				color = TextColor.ENEMY(PDI);
				break;
			}
			else if(this.getRelations().get(gov) == Relation.ALLY
					&& gov.getRelations().get(this) == Relation.ALLY) {
				color = TextColor.ALLY(PDI);
			}
		}
		return color + this.getName().concat(" [" + this.getProtectionlevel()
						+ ", " + this.getMilitaryLevel() + "]");
	}

}
