package com.github.InspiredOne.InspiredNationsServer.Governments;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Datable;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Economy.Account;
import com.github.InspiredOne.InspiredNationsServer.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNationsServer.Economy.Currency;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.BalanceOutOfBoundsException;
import com.github.InspiredOne.InspiredNationsServer.Exceptions.NegativeMoneyTransferException;
import com.github.InspiredOne.InspiredNationsServer.Regions.InspiredRegion;
import com.github.InspiredOne.InspiredNationsServer.Remotes.InspiredGovPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedMap;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Tools;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Alert;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Notifyable;

/**
 * Used as the base class to build governments. Each level
 * of government must be an <code>InspiredGov</code> object. The abstract
 * methods to be developed dictate the hierarchy of governments
 * and how they are treated by the plugin.
 * 
 * @author InspiredOne
 *
 */
public abstract class InspiredGov implements InspiredGovPortal, Serializable, Nameable, Datable<InspiredGov>, Notifyable, Payable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5014430464149332251L;
	
	private static int globalid = 0;
	private final int hashID;
	protected AccountCollection accounts;
	private InspiredRegion region;
	//private List<Facility> facilities = new ArrayList<Facility>();
	private IndexedMap<Class<? extends InspiredGov>, Double> taxrates = new IndexedMap<Class<? extends InspiredGov>, Double>();
	private InspiredGov supergov;
	protected String name;
	private double taxedrate = 1; // the last tax rate used on this gov. Used to calculate prices.
	protected int protectionlevel = 0;
	private Currency currency = Currency.DEFAULT;
	
	/**
	 * @param instance	the plugin instance
	 */
	public InspiredGov() {
		for(Class<? extends InspiredGov> gov:this.getSubGovs()) {
			this.registerTaxRates(gov);
		}
		this.hashID = globalid;
		globalid++;
		//accounts = new AccountCollection("");
	}
	/**
	 * registers all the tax rates for a given this government
	 */
	public void registerTaxRates(Class<? extends InspiredGov> govType) {
		InspiredGov temp = GovFactory.getGovInstance(govType);
		if(temp.getSelfGovs().size() == 1 && temp.getSelfGovs().contains(govType)) {
			taxrates.put(govType, 1.0);
		}
		else {
			for(Class<? extends InspiredGov> govnext:temp.getSelfGovs()) {
				this.registerTaxRates(govnext);
			}
		}
	}
	/**
	 * 
	 * @return	a <code>HashMap<code> with each subgov class mapping to it's associated tax rate
	 */
	public IndexedMap<Class<? extends InspiredGov>, Double> getTaxrates() {
		return taxrates;
	}
	/**
	 * 
	 * @param taxrates	the new <code>HashMap</code> of class mappings to tax rates
	 */
	public void setTaxrates(IndexedMap<Class<? extends InspiredGov>, Double> taxrates) {
		this.taxrates = taxrates;
	}
	@Override
	public void sendNotification(Alert msg) {
		for(PlayerID player:this.getSubjects()) {
			player.getPDI().getMsg().receiveAlert(msg, true);
		}
	}
	/**
	 * Returns the <code>InspiredRegion</code> associated with this government.
	 * The <code>InspiredRegion</code> dictates what selection types can be used
	 * and the nesting of the regions. 
	 * @return	the <code>InspiredRegion</code> associated with this government
	 */
	public InspiredRegion getRegion() {
		if(region == null) {
			region = Tools.getInstance(this.getInspiredRegion());
		}
		return region;
	}
	/**
	 * 
	 * @param region	the new <code>InspiredRegion</code> to be used
	 */
	public void setRegion(InspiredRegion region) {
		this.region = region;
	}

	/**
	 * If supergov has not been defined yet, then returns a new instance of type
	 * that getSuperGov() returns.
	 * @return the <code>InspiredGov</code> instance above this government
	 */
	public InspiredGov getSuperGovObj() {
		if(supergov == null) {
			return GovFactory.getGovInstance(this.getSuperGov());
		}
		else {
			return supergov;
		}
	}
	public InspiredGov getSuperGovObj(Class<? extends OwnerGov> govtype) {
		InspiredGov govtest = this;
		while(govtest != InspiredNationsServer.global) {
			if(govtest.getClass().equals(govtype)) {
				return govtest;
			}
			else {
				govtest = govtest.getSuperGovObj();
			}
		}
		return InspiredNationsServer.global;
	}
	/**
	 * 
	 * @param supergov	the new <code>InspiredGov</code> instance above this government
	 */
	public void setSuperGovObj(InspiredGov supergov) {
		this.supergov = supergov;
	}
	/**
	 * Finds if there are any govs within it that still have citizens.
	 * @return
	 */
	public boolean isSubjectLess() {
		if(this.getSubjects().isEmpty()) {
			for(Class<? extends InspiredGov> govtype:this.getSubGovs()) {
				for(InspiredGov govtest:this.getAllSubGovs(govtype)) {
					if(!govtest.isSubjectLess()) {
						return false;
					}
				}
			}
			return true;
		}
		else{
			return false;
		}
	}
	public void unregister() {
		// First send all the players a notification so that they know what is going on.
		Debug.print("*********************************** Inside Unregister");
		for (PlayerID PID: this.getSubjects()) {
			PID.getPDI().sendNotification(MenuAlert.GOV_UNREGISTERED(this));
		}
		try {
			// Refunds all the protection that wasn't used.
			this.setLand(new nullRegion());
		} catch (BalanceOutOfBoundsException e1) {
			e1.printStackTrace();
		} catch (InspiredGovTooStrongException e1) {
			e1.printStackTrace();
		} catch (RegionOutOfEncapsulationBoundsException e1) {
			e1.printStackTrace();
		} catch (InsufficientRefundAccountBalanceException e1) {
		}
		// Make sure that all subgovs also become unregistered. They cannot exist without a supergov.
		for(InspiredGov gov:this.getAllSubGovsAndFacilitiesJustBelow()) {
			Debug.print("Inside unregister: " + gov.getName());
			gov.unregister();
		}

		if (this instanceof Facility) {
			//join facility account with supergov account
			try {
				this.transferMoney(this.getAccounts().getTotalMoney(currency.DEFAULT, InspiredNations.Exchange.mcdown), currency.DEFAULT, this.getSuperGovObj());
			} catch (BalanceOutOfBoundsException e) {
				e.printStackTrace();
			} catch (NegativeMoneyTransferException e) {
				e.printStackTrace();
			}
		}
		else {
			// Or transfer all the money to the owners' accounts
			try {
				Account GovAccount = new Account(this.getName());
				
				this.getAccounts().transferMoney(this.getAccounts().getTotalMoney(currency.DEFAULT, InspiredNations.Exchange.mcdown), currency.DEFAULT, GovAccount);
				
				if (this instanceof OwnerGov) {
					for (PlayerID PID: ((OwnerGov) this).getOwnersList()) {
						PID.getPDI().getAccounts().add(GovAccount);
					}
				}
			} catch (BalanceOutOfBoundsException e) {
				e.printStackTrace();
			} catch (NegativeMoneyTransferException e) {
				e.printStackTrace();
			}
		}
		// remove the government from all of the gov relations
		for(InspiredGov gov:InspiredNations.regiondata) {
			if(gov instanceof OwnerGov) {
				((OwnerGov) gov).getRelations().remove(this);
			}
		}
		// Finally remove the government from regiondata
		Debug.print("Getting unregistered: " + this.getName());
		InspiredNations.regiondata.removeValue(this);
	}

	/**
	 * Returns the set containing the names of all players who
	 * are directly under this governments control.
	 * @return	the <code>HashSet</code> of all subject names
	 */
	protected IndexedSet<PlayerID> getSubjects() {
		return new IndexedSet<PlayerID>();
	}
	public final boolean isSubject(PlayerID player) {
		return this.getSubjects().contains(player);
	}
	/**
	 * Gets the supergov that this gov pays taxes to.
	 * @return
	 */
	public abstract InspiredGov getTaxSuper();
	/**
	 * A function to update the TaxRate that this government will use when claiming and unclaiming land.
	 */
	public final void updateTaxRate() {
		this.taxedrate = this.getSuperTaxRate();
	}
	/**
	 * Returns a <code>List</code> of all <code>InspiredGovs</code> that have been created under this government.
	 * It does this by searching 6666through the <code>HashMap</code> in the plugin class
	 * for any <code>InspiredGov</code> instance that returns this <code>InspiredGov</code> instance
	 * from <code>getSuperGovObj()</code>.
	 * @param key	the Class of InspiredGovs to find
	 * @return		a List of InspiredGovs of the type 
	 */
	@SuppressWarnings("unchecked")
	public <T extends InspiredGov> List<T> getAllSubGovs(Class<T> key) {
		List<T> output = new ArrayList<T>();
		for(Iterator<InspiredGov> iter = InspiredNations.regiondata.get(key).iterator(); iter.hasNext(); ) {
			InspiredGov gov = iter.next();
			if (gov.getSuperGovObj().equals(this)) {
				output.add((T) gov);
			}
		}
		
		return output;
		
	}
	/**
	 * Finds all the governments that are required to be within the region of this gov.
	 * @return
	 */
/*	public List<InspiredGov> getEncapsulatedGovs() {
		List<InspiredGov> output = new ArrayList<InpsiredGov>();
		for(Class<? extends InspiredGov> govtype:InspiredNations.regiondata.keySet()) {
			for(InspiredGov govtest:InspiredNations.regiondata.get(govtype)) {
				if(govtest.getRegion().getEncapsulatingRegions().contains(this.getRegion().getClass())) {
					if(govtest.isSubOf(this));
				}
			}
		}
	}*/
	/**
	 * Gets the class of the InspiredRegion for this government
	 * @return
	 */
	public abstract Class<? extends InspiredRegion> getInspiredRegion();
	/**
	 * Dictates which super government this government synchronizes it's economy with.
	 * The values of the economy fluctuate relative to other governments. The return must be either a government
	 * that is above this government or itself. 
	 * @return	the <code>InspiredGov</code> class that this class synchronizes it's economy variables
	 */
	public abstract Class<? extends InspiredGov> getCommonEcon();
/*	*//**
	 * 
	 * @return	the <code>InspiredRegion</code> class that this government uses
	 *//*
	public abstract Class<? extends InspiredRegion> getSelfRegionType();*/
	/**
	 * Gets the <code>Facility</code> classes that this government uses as facilities.
	 * facilities are not taxed by this gov, but are taxed by the supergov. They share owners with
	 * this government. They can only be claimed by owners of this government.
	 * @return	a <code>List</code> of <code>InspiredGov</code> classes that serve as facilities for this gov
	 */
	public abstract List<Class<? extends Facility>> getGovFacilities();
	/**
	 * Gets the <code>InspiredGov</code>s that are under this government's control.
	 * SubGovs are taxed by this government and can be claimed by any of the subjects.
	 * They have their own set of owners who are responsible for paying up to this government.
	 * @return	a <code>List</code> of the <code>InspiredGov</code> that can be claimed by subjects
	 */
	public abstract List<Class<? extends OwnerGov>> getSubGovs();
	/**
	 * Returns the class of the supergov. If this is the highest form of government, then it should
	 * return a <code>Class<? extends GlobalGov></code>. The Supergov must be the gov that 
	 * taxes are paid to.
	 * @return	the <code>InspiredGov</code> class that is the supergov to this government
	 */
	public abstract Class<? extends InspiredGov> getSuperGov();
	/**
	 * Returns all the governments that could be claimed with 
	 * @return	a <code>List</code> of <code>InspiredGov</code> classes that can
	 * be used in parallel to this government.
	 */
	public abstract List<Class<? extends InspiredGov>> getSelfGovs();
	/**
	 * Returns the general gov that returns this gov in it's getSelfGovs() method.
	 * @return	the class of the general gov of this type.
	 */
	public Class<? extends InspiredGov> getGeneralGovType() {
		return this.getClass();
	}
	/**
	 * 
	 * @return	the <code>String</code> to be used as the name for this government in the menus
	 */
	public abstract String getTypeName();
	/**
	 * Gets the name to be used for the Government Regions option.
	 * @return
	 */
	public abstract String getFacilityGroupName();
	/**
	 * 
	 * @param subgov	the <code>InspiredGov</code> type to be searched for
	 * @return			the <code>double</code> representation of the tax rate
	 */	
	public double getSubTaxRate(Class<? extends OwnerGov> subgov) {
		return taxrates.get(subgov);
	}
	/**
	 * 
	 * @return	the <code>double</code> representation of the tax rate effective on this government
	 */
	@SuppressWarnings("unchecked")
	public double getSuperTaxRate() {
		 return this.getTaxSuper().getSubTaxRate((Class<? extends OwnerGov>) this.getClass());
	}
	/**
	 * 
	 * @return a <code>List</code> of the <code>InspiredGov</code>s that are used as facilities
	 */
	public List<Facility> getFacilities() {
		List<Facility> output = new ArrayList<Facility>();
		for(Class<? extends Facility> facType:this.getGovFacilities()) {
			output.addAll(this.getAllSubGovs(facType));
		}
		return output;
	}
	/**
	 * Returns a list of all the governments below this government.
	 * @return
	 */
	public ArrayList<InspiredGov> getAllSubGovsBelow() {
		ArrayList<InspiredGov> output = new ArrayList<InspiredGov>();
		for(InspiredGov gov:this.getAllSubGovsAndFacilitiesJustBelow()) {
			output.add(gov);
			output.addAll(gov.getAllSubGovsBelow());
		}
		return output;
	}
	/**
	 * Sifts through all the InspiredGovs and coalesces all the governments that are one level below this
	 * government. Includes Facilities.
	 * @return
	 */
	public List<InspiredGov> getAllSubGovsAndFacilitiesJustBelow() {
		List<InspiredGov> output = new ArrayList<InspiredGov>();
		// Iterate over all the sub-gov types.
		for(Class<? extends OwnerGov> subType:this.getData().getSubGovs()) {
			// Iterate over every government of that type
			for(InspiredGov govToTest:InspiredNationsServer.regiondata.get(subType)) {
				// Check if the government is under the particular superGov
				if(govToTest.isSubOf(this.getData())) {
					output.add(govToTest);
				}
			}
		}
		output.addAll(this.getFacilities());
		return output;
	}
	
	/**
	 * 
	 * @return	the <code>String</code> to be used for this government's name
	 */
	@Override
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name	the <code>String</code> to be used for the name of this government
	 */
	@Override
	public void setName(String name) {
		this.accounts.setName(name);
		this.name = name;
	}
	/**
	 * Determines if the given location is within the geographical region of this government.
	 * Returns true if the <code>Location</code> is within the region and false otherwise.
	 * @param location	the <code>Location</code> to be checked
	 * @return			the <code>boolean</code> indicating if the location is within the region
	 */
	public boolean contains(Location location) {
		if(this.region == null) {
			return false;
		}
		else {
			return this.region.contains(location);
		}
	}
	/**
	 * 
	 * @param amount	the <code>BigDecimal</code> amount to be paid up to the supergov
	 * @param curren	the amount of money to transfer
	 * @throws BalanceOutOfBoundsException 
	 * @throws NegativeMoneyTransferException 
	 */
	public void paySuper(BigDecimal amount, Currency curren) throws BalanceOutOfBoundsException, NegativeMoneyTransferException {
		this.accounts.transferMoney(amount, curren, this.getTaxSuper().accounts);
	}
	/**
	 * 
	 * @param amount
	 * @param curren
	 * @throws BalanceOutOfBoundsException
	 * @throws NegativeMoneyTransferException 
	 */
	public void pullFromSuper(BigDecimal amount, Currency curren) throws BalanceOutOfBoundsException, NegativeMoneyTransferException {
		this.getTaxSuper().getAccounts().transferMoney(amount, curren, this.accounts);
	}
	/**
	 * Gets a list of all the governments that are below this government (including itself)
	 * @return	A list of all the subgovs
	 */
	public final List<Class<? extends OwnerGov>> getAllSubGovs() {
		List<Class<? extends OwnerGov>> output = new ArrayList<Class<? extends OwnerGov>>();
		for(Class<? extends OwnerGov> gov:this.getSubGovs()) {
			output.add((Class<? extends OwnerGov>) gov);
			output.addAll(GovFactory.getGovInstance(gov).getAllSubGovs());
/*			for(Class<? extends InspiredGov> gov2:GovFactory.getGovInstance(gov).getSelfGovs()) {
				output.add((Class<? extends OwnerGov>) gov2);
				output.addAll(GovFactory.getGovInstance(gov2).getAllSubGovs());
			}*/
		}
		return output;
	}
	
	public void payTaxes() {
		for(Class<? extends InspiredGov> govtype:this.getAllSubGovs()) {
			for(InspiredGov subgov:this.getAllSubGovs(govtype)) {
				subgov.payTaxes();
			}
		}
		while(this.getTotalMoney(currency, InspiredNations.Exchange.mcdown).compareTo(this.currentTaxCycleValue(currency)) < 0 &&
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
			this.updateTaxRate();
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
	
	/**
	 * Returns a list of all govs that are subgovs of the parameter and
	 * are super govs of this gov or are this gov.
	 * @param govtop
	 * @return
	 */
	public final OwnerGov getSuperGovBelow(InspiredGov govtop) throws NotASuperGovException{
		InspiredGov govbottom = this;
		for(Class<? extends OwnerGov> govtype:govtop.getSubGovs()) {
			for(InspiredGov govtest:InspiredNations.regiondata.get(govtype)) {
				if(govbottom.equals(govtest)) {
					return (OwnerGov) govtest;
				}
				else if(govbottom.isSubOf(govtest)) {
					return (OwnerGov) govtest;
				}
			}
		}
		throw new NotASuperGovException();
	}
	
	public final boolean isSubOfClass(Class<? extends OwnerGov> gov) {
		if(gov.equals(InspiredNations.global.getClass())) {
			return true;
		}
		else if (this instanceof GlobalGov) {
			return false;
		}
		else if(gov.equals(this.getClass())) {
			return false;
		}
		else {
			return this.getSuperGovObj().isSubOfClass(gov);
		}

	}
	
	/**
	 * Registers all the region types into the plugin.regiondata hashmap.
	 * @param plugin	the <code>InspiredNations</code> plugin where
	 * the regiondata hashmap is stored
	 */
	public void register() {
		List<InspiredGov> value = new ArrayList<InspiredGov>();
		if(!InspiredNations.regiondata.containsKey(this.getClass())) {
			InspiredNations.regiondata.put(this.getClass(), value);
		}
		
		for(Class<? extends InspiredGov> cla:this.getSubGovs()) {
			InspiredGov obj = GovFactory.getGovInstance(cla);
			obj.register();
		}
		
		for(Class<? extends InspiredGov> cla:this.getGovFacilities()) {
			InspiredGov obj = GovFactory.getGovInstance(cla);
			obj.register();
		}
		
		for(Class<? extends InspiredGov> cla:this.getSelfGovs()) {
			if(!cla.equals(this.getClass())) {
				InspiredGov obj = GovFactory.getGovInstance(cla);
				obj.register();
			}
		}
	}
	
	public static boolean fromSameBranch(InspiredGov gov1, InspiredGov gov2) {
		return gov1.getSuperGovObj().equals(gov2.getSuperGovObj());
	}
	
	/**
	 * Determines if this government is below gov.
	 * @param gov	the government to check if is above this government
	 * @return		true if this is below gov
	 */
	public final boolean isSubOf(InspiredGov gov) {
		if(this.equals(InspiredNations.global)) {
			return false;
		}
		else if(gov.equals(InspiredNations.global)) {
			return true;
		}
		else if(gov.equals(this.getSuperGovObj())) {
			return true;
		}
		else {
			return this.getSuperGovObj().isSubOf(gov);
		}
	}
	
	public int getProtectionlevel() {
		return protectionlevel;
	}
	public void setProtectionlevel(int protectionlevel) throws NegativeProtectionLevelException, BalanceOutOfBoundsException {
		if(protectionlevel < 0) {
			throw new NegativeProtectionLevelException();
		}
		else {
			BigDecimal refund = this.taxValue(this.getRegion().getRegion(),InspiredNations.taxTimer.getFractionLeft(), this.protectionlevel, Currency.DEFAULT);
			BigDecimal newcost = this.taxValue(this.getRegion().getRegion(),InspiredNations.taxTimer.getFractionLeft(), protectionlevel, Currency.DEFAULT);
			BigDecimal cost = refund.subtract(newcost);
			if(cost.compareTo(BigDecimal.ZERO) < 0) {
				try {
					this.paySuper(cost.negate(), Currency.DEFAULT);
				} catch (NegativeMoneyTransferException e) {
				}
			}
			else {
				try {
					this.pullFromSuper(newcost, Currency.DEFAULT);
				} catch (NegativeMoneyTransferException e) {
				} catch (BalanceOutOfBoundsException e) {
					try {
						this.pullFromSuper(this.getSuperGovObj().getAccounts().getTotalMoney(Currency.DEFAULT, InspiredNations.Exchange.mcdown), Currency.DEFAULT);
					} catch (NegativeMoneyTransferException e1) {
					}
				}
			}
			this.protectionlevel = protectionlevel;
		}
	}
	
	public int getMilitaryLevel() {
		return 0;
	}
	public final AccountCollection getAccounts() {
		return accounts;
	}
	public final void setAccounts(AccountCollection accounts) {
		this.accounts = accounts;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public void setData(InspiredGov datatemp) {
	}
	public InspiredGov getData() {
		return this;
	}
	public BigDecimal getExpectedRevenue(Currency curren) {
		BigDecimal output = BigDecimal.ZERO;
		for(Class<? extends OwnerGov> subgovtype:this.getSubGovs()) {
			output = output.add(this.getExpectedRevenueFrom(subgovtype, curren));
		}
		return output;
	}
	public BigDecimal getExpectedRevenueFrom(Class<? extends InspiredGov> gov, Currency curren) {
		BigDecimal output = BigDecimal.ZERO;
		for(InspiredGov subgov:InspiredNations.regiondata.get(gov)) {
			if(subgov.getSuperGovObj() == this) {
				output = output.add(subgov.currentTaxCycleValue(curren));
			}
		}
		return output;
	}
	/**
	 * Returns the amount of money that the the parameters would
	 * cost.
	 * @param region
	 * @param curren
	 * @return
	 */
	public final BigDecimal taxValue(Region region, double taxfrac,int protect, Currency curren) {
		return this.taxValue(region, taxfrac, protect, this.getAdditionalCost(curren),this.taxedrate, curren);
	}
	/**
	 * Gets the amount of money that will be charged for this gov as it stands at the next
	 * tax cycle.
	 * @param curren
	 * @return
	 */
	public BigDecimal currentTaxCycleValue(Currency curren) {
		BigDecimal output = this.taxValue(this.getRegion().getRegion(), 1, this.protectionlevel, this.getAdditionalCost(curren),
				this.getSuperTaxRate(), curren);
		return output;
	}
     /**
	 * Gets the tax rate that is currently set in the TaxSuperGov.
	 * @return
	 */
	public final double getCurrentTaxedRate() {
		if(this instanceof Facility) {
			return this.getSuperGovObj().getCurrentTaxedRate();
		}
		else if(this instanceof OwnerGov){
			return this.getSuperGovObj().getSubTaxRate((Class<? extends OwnerGov>) this.getClass());
		}
		else {
			Debug.InformPluginDev();
			return 0;
		}
	}
	/**
	 * Returns the amount of money that the parameters would costs
	 * @param region
	 * @param taxfrac
	 * @param protect
	 * @param additionalcost
	 * @param curren
	 * @return
	 */
	public final BigDecimal taxValue(Region region, double taxfrac, int protect, BigDecimal additionalcost, double taxrate, Currency curren) {
		BigDecimal output = BigDecimal.ZERO;
		// Basically... multiply them all together and it gets you the value in Defualt currency
		//TODO come up with some kind of war money function
		output = (new BigDecimal(region.volume()/10000.).multiply(new BigDecimal(taxfrac))).multiply(new BigDecimal(taxrate));
		output = output.multiply(new BigDecimal(protect)).add(additionalcost);
		output = InspiredNations.Exchange.getExchangeValue(output, Currency.DEFAULT, curren);
		return output;
	}
	 
	/**
	 * Used to get additional tax cost that is to be added to the total tax amount.
	 * Use it to insert the cost of war into the tax amount.
	 * @return
	 */
	public BigDecimal getAdditionalCost(Currency valueType) {
		return BigDecimal.ZERO;
	}
	/**
	 * The function that should be used to add land.
	 * @param region
	 * @throws BalanceOutOfBoundsException 
	 * @throws InspiredGovTooStrongException
	 * @throws RegionOutOfEncapsulationBoundsException 
	 * @throws InsufficientRefundAccountBalanceException 
	 */
	public void setLand(Region region) throws BalanceOutOfBoundsException, InspiredGovTooStrongException, RegionOutOfEncapsulationBoundsException, InsufficientRefundAccountBalanceException {
		Currency curren = Currency.DEFAULT;
		BigDecimal holdings = this.accounts.getTotalMoney(curren, InspiredNations.Exchange.mcdown);
		BigDecimal reimburse = this.taxValue(this.getRegion().getRegion(), InspiredNations.taxTimer.getFractionLeft(), this.protectionlevel, curren);
		BigDecimal cost = this.taxValue(region, InspiredNations.taxTimer.getFractionLeft(), this.protectionlevel, curren);
		BigDecimal difference = cost.subtract(reimburse);// positive if owe country money, negative if country owe money
		// Can they afford it?
		if(holdings.compareTo(difference) < 0) {
			throw new BalanceOutOfBoundsException();
		}
		
		// Is it inside all the regions it's supposed to be in?
		for(Class<? extends InspiredRegion > regionType:this.getRegion().getEncapsulatingRegions()) {
			InspiredRegion check = Tools.getInstance(regionType);
			for(InspiredGov gov:InspiredNations.regiondata.get(check.getRelatedGov())) {
				if(this.isSubOf(gov) && !region.IsIn(gov.getRegion().getRegion()) && !(region instanceof nullRegion)) {
					throw new RegionOutOfEncapsulationBoundsException(gov);
				}
			}
		}
		for(InspiredGov gov:this.getAllSubGovsAndFacilitiesJustBelow()) {
			if(gov.getRegion().getEncapsulatingRegions().contains(this.getRegion().getClass())) {
				gov.removeLandNotIn(region);
			}
		}

		// Does it cross over any regions that it can't be over?
		for(InspiredGov gov:this.getSuperGovObj().getAllSubGovsAndFacilitiesJustBelow()) {
			if(gov != this) {
				// now check if the gov is allowed to change the land of the region
				if(!gov.getRegion().getOverlap().contains(this.getRegion().getClass())) {
					if(gov.getRegion().getRegion().Intersects(region)) {
						if(gov.getProtectionlevel() >= this.getMilitaryLevel()-gov.getMilitaryLevel() + ProtectionLevels.CLAIM_PROTECTION) {
							throw new InspiredGovTooStrongException(gov);
						}
						else {
							// remove land, no need to check because it's already been done.
							gov.removeLand(region, false);
						}
					}
				}
			}
		}
		
		
		if(difference.compareTo(BigDecimal.ZERO) < 0) {
			try{
				this.pullFromSuper(difference.negate(), curren);
			}
			catch (BalanceOutOfBoundsException ex) {
				try {
					this.pullFromSuper(this.getSuperGovObj().getAccounts().getTotalMoney(curren, InspiredNations.Exchange.mcdown), curren);
				} catch (NegativeMoneyTransferException e) {
					e.printStackTrace();
				}
				//throw new InsufficientRefundAccountBalanceException(); This was preventing land from being claimed
			} 
			catch (NegativeMoneyTransferException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				this.paySuper(difference, curren);
			} catch (NegativeMoneyTransferException e) {
				e.printStackTrace();
			}
		}
		this.getRegion().addLand(region);
		this.sendNotification(MenuAlert.REGION_UPDATED_SUCCESSFULY(region, this));
	}
	
	public void removeLandNotIn(Region select) {
		Region regionfrom = this.getRegion().getRegion();
		Currency curren = Currency.DEFAULT;
		BigDecimal reimburse = this.taxValue(this.getRegion().getRegion(), InspiredNations.taxTimer.getFractionLeft(), this.protectionlevel, curren);
		if(regionfrom instanceof CummulativeRegion<?>) {
			ArrayList<NonCummulativeRegion> removed = new ArrayList<NonCummulativeRegion>();
			for(NonCummulativeRegion region:((CummulativeRegion<?>) regionfrom).getRegions()) {
				if(!region.IsIn(select)) {
					removed.add(region);
				}
			}
			((CummulativeRegion<?>) regionfrom).getRegions().removeAll(removed);
		}
		else {
			if(!regionfrom.IsIn(select)) {
				this.getRegion().setRegion(new nullRegion());
			}
		}
		for(InspiredGov gov:this.getAllSubGovsAndFacilitiesJustBelow()) {
			gov.removeLandNotIn(this.getRegion().getRegion());
		}
		BigDecimal newcost = this.taxValue(this.getRegion().getRegion(), InspiredNations.taxTimer.getFractionLeft(), this.protectionlevel, curren);
		BigDecimal difference = reimburse.subtract(newcost);
		try {
			this.pullFromSuper(difference, curren);	// Pull the reimbursment from them
		} catch (BalanceOutOfBoundsException e) {	// If they don't have enough
			try {
				this.pullFromSuper(this.getSuperGovObj().accounts.getTotalMoney(curren, InspiredNations.Exchange.mcdown), curren); // Pull all they have
			} catch (BalanceOutOfBoundsException e1) {	// Should not encounter
				e1.printStackTrace();	
			} catch (NegativeMoneyTransferException e1) { // Should not encounter
				e1.printStackTrace();
			}
		} catch (NegativeMoneyTransferException e) {	// Should not encounter
			e.printStackTrace();
		}
	}
	
	/**
	 * Assumes that this region intersects the region <code>select</code>. 
	 * @param select
	 */
	public void removeLand(Region select, boolean check) {
		Region regionfrom = this.getRegion().getRegion();
		Currency curren = Currency.DEFAULT;
		BigDecimal reimburse = this.taxValue(this.getRegion().getRegion(), InspiredNations.taxTimer.getFractionLeft(), this.protectionlevel, curren);
		
		ArrayList<NonCummulativeRegion> removed = new ArrayList<NonCummulativeRegion>();
		if(regionfrom instanceof CummulativeRegion<?>) {
			for(NonCummulativeRegion region:((CummulativeRegion<?>) regionfrom).getRegions()) {
				if(region.Intersects(select)) {
					removed.add(region);
				}
			}
			((CummulativeRegion<?>) regionfrom).getRegions().removeAll(removed);
		}
		else {
			if(check) {
				if(regionfrom.Intersects(select)) {
					removed.add((NonCummulativeRegion) regionfrom);
					this.getRegion().setRegion(new nullRegion());
				}
			}
			else {
				removed.add((NonCummulativeRegion) regionfrom);
				this.getRegion().setRegion(new nullRegion());
			}
		}
		//iterate through all the sub regions of this land
		for(InspiredGov govtest:this.getAllSubGovsAndFacilitiesJustBelow()) {
			if(govtest.getRegion().getRegion().Intersects(select)) {
				govtest.removeLand(select, false);
			}
			else if(govtest.getRegion().getEncapsulatingRegions().contains(this.getRegion().getClass())) {
				for(NonCummulativeRegion region:removed) {
					if(govtest.getRegion().getRegion().Intersects(region)) {
						govtest.removeLand(region, false);
					}
				}
			}
		}
		BigDecimal cost = this.taxValue(this.region.getRegion(), InspiredNations.taxTimer.getFractionLeft(), this.protectionlevel, curren);
		BigDecimal difference = cost.subtract(reimburse);// positive if own country money, negative if country ows money
		if(difference.compareTo(BigDecimal.ZERO) > 0) { 	// If for some reason you ow the country money for this
			try {
				this.paySuper(difference, curren);			// Pay what you owe
			} catch (BalanceOutOfBoundsException e) {		//	If you don't have enough
				try {
					this.paySuper(this.accounts.getTotalMoney(curren, InspiredNations.Exchange.mcdown), curren);	// Pay what you have
				} catch (BalanceOutOfBoundsException
						| NegativeMoneyTransferException e1) {
				}
			} catch (NegativeMoneyTransferException e) {	// Should not encounter this error
				e.printStackTrace();
			}
		}
		else {	// Else if the country ows you money for unfinished protection
			try {
				this.pullFromSuper(difference.negate(), curren);	// Pull the reimbursment from them
			} catch (BalanceOutOfBoundsException e) {	// If they don't have enough
				try {
					this.pullFromSuper(this.getSuperGovObj().accounts.getTotalMoney(curren, InspiredNations.Exchange.mcdown), curren); // Pull all they have
				} catch (BalanceOutOfBoundsException e1) {	// Should not encounter
					e1.printStackTrace();	
				} catch (NegativeMoneyTransferException e1) { // Should not encounter
					e1.printStackTrace();
				}
			} catch (NegativeMoneyTransferException e) {	// Should not encounter
				e.printStackTrace();
			}
		}
	}
	/**
	 * Used to split this govs account from the player's account. This
	 * makes it so that the wallet used for the gov is a separate wallet
	 * from the one used by the player. It preserves all other players' links
	 * to the account as well as all other gov's links to the account. 
	 * @param player
	 */
	public void splitAccount(PlayerData player, List<PlayerID> tested, AccountCollection newAccount) {
		for(Facility fac:this.getFacilities()) {
			fac.splitAccount(player, tested, newAccount);
		}
		for(PlayerData test:this.getPlayerHolders()) {
			if(!test.equals(player) && !tested.contains(test.getPlayerID())) {
				tested.add(test.getPlayerID());
				for(InspiredGov gov:test.getGovHolders()) {
					gov.splitAccount(player, tested, newAccount);
				}
				test.setAccounts(newAccount);
			}
		}
		try {
			this.getAccounts().transferMoney(this.getAccounts().getTotalMoney(this.getCurrency(), InspiredNations.Exchange.mcdown), this.getCurrency(), newAccount);
			
		} catch (BalanceOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NegativeMoneyTransferException e) {
			e.printStackTrace();
		}
		this.setAccounts(newAccount);
	}
	
	public List<PlayerData> getPlayerHolders() {
		List<PlayerData> holders = new ArrayList<PlayerData>();
		for(PlayerData player:InspiredNations.playerdata.values()) {
			if(player.getAccounts() == this.getAccounts()) {
				holders.add(player);
			}
		}
		return holders;
	}
	/**
	 * 
	 * @param player
	 */
	public void joinAccount(PlayerData player) {
		List<PlayerID> tested = new ArrayList<PlayerID>();
		AccountCollection newAccount = new AccountCollection(this.getName());
		newAccount.addAll(player.getAccounts());
		this.splitAccount(player, tested, newAccount);
		player.setAccounts(newAccount);
	}

	@Override
	public String getDisplayName(PlayerData PDI) {
		return TextColor.NEUTRAL(PDI) + this.getName().concat(" [" + this.getProtectionlevel() + "]");
	}
	/**
	 * Gets the name for the position of owner for this government to be
	 * used in menus.
	 * @return	Owner Position Name
	 */
	public abstract String getOwnerPositionName();
	
	@Override
	public void transferMoney(BigDecimal amount, Currency curren, Payable accountTo) throws BalanceOutOfBoundsException, NegativeMoneyTransferException {
		this.accounts.transferMoney(amount, curren, accountTo);
	}
	
	@Override
	public void addMoney(BigDecimal amount, Currency monType)
			throws NegativeMoneyTransferException {
		this.accounts.addMoney(amount, monType);
	}

	@Override
	public BigDecimal getTotalMoney(Currency valueType, MathContext round) {
		return this.accounts.getTotalMoney(valueType, round);
	}
	/**
	 * Returns all the governments of a given tier. Tier 0 is global gov. Tier 1 is countries
	 * @param tier
	 * @return
	 */
	public static List<InspiredGov> getTierGovs(int tier) {
		List<InspiredGov> output = new ArrayList<InspiredGov>();
		for(InspiredGov gov:InspiredNations.regiondata) {
			if(gov.getTier() == tier) {
				output.add(gov);
			}
		}
		return output;
	}
	public int getTier() {
		int tier = 0;
		InspiredGov gov = this;
		while(gov != InspiredNations.global) {
			tier++;
			gov = gov.getSuperGovObj();
		}
		return tier;
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(this.hashID).
            toHashCode();
    }

    @Override*
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof InspiredGov))
            return false;
        InspiredGov rhs = (InspiredGov) obj;

        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(hashID, rhs.hashID).
            isEquals();
    }
	
	@Override
	public String toString() {
		return this.getName();
	}
	
}
