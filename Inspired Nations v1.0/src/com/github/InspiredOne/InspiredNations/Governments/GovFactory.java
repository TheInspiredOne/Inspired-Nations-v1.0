package com.github.InspiredOne.InspiredNations.Governments;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.AccountCollection;
import com.github.InspiredOne.InspiredNations.Economy.Currency;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools;

public class GovFactory<T extends InspiredGov> {

	T gov;
	BigDecimal diamondvalue = BigDecimal.ONE;
	PlayerID owner;
	public GovFactory(Class<T> gov) {
		this.gov = GovFactory.getGovInstance(gov);
		//this.gov.setRegion(Tools.getInstance(this.gov.getInspiredRegion()));
	}
	
	public GovFactory<T> withName(String name) {
		this.gov.setName(name);
		return this;
	}
	
	public GovFactory<T> withOwner(PlayerID player) {
		this.owner = player;
		return this;
	}
	
	public GovFactory<T> withSuperGov(InspiredGov gov) {
		Debug.print("Putting super gov on: "  + gov);
		this.gov.setSuperGovObj(gov);
		
		if(!this.gov.getCommonEcon().equals(this.gov.getClass())) {
			this.gov.setCurrency(gov.getCurrency());
		}
		this.gov.updateTaxRate();
		return this;
	}
	
	public GovFactory<T> withMoneyname(String name) {
		this.gov.setCurrency(new Currency(name));
		return this;
	}
	
	public GovFactory<T> withDiamondValue(BigDecimal multiplyer) {
		this.diamondvalue = multiplyer;
		return this;
	}
	
	public GovFactory<T> withCurrency(Currency currency) {
		this.gov.setCurrency(currency);
		return this;
	}
	
	public GovFactory<T> withAccountCollection(AccountCollection collection) {
		this.gov.setAccounts(collection);
		return this;
	}
	
	public T getGov() {
		return this.gov;
	}

	public void registerGov() {
		if(gov instanceof OwnerGov) {
			((OwnerGov) gov).addOwner(owner);
		}
		InspiredNations.regiondata.putValue(gov.getClass(), gov);
		InspiredNations.Exchange.registerCurrency(this.getGov().getCurrency(), diamondvalue);
		//TODO change BigDecimal.ONE to whatever you're going to use for default values for exchanger
	}
	
	public static <E extends InspiredGov> E getGovInstance(Class<E> gov) {
		return Tools.getInstance(gov);
	}
	
	
	
}
