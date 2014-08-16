package com.github.InspiredOne.InspiredNationsServer.Economy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import com.github.InspiredOne.InspiredNationsServer.Config;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.MoneyExchangePortal;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.IndexedMap;


public class MoneyExchange implements Serializable, MoneyExchangePortal{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3674609233229292913L;
	public static MathContext mcup = new MathContext(50, RoundingMode.UP);
	public static MathContext mcdown = new MathContext(50, RoundingMode.DOWN);
	public MoneyExchange() throws RemoteException {
		UnicastRemoteObject.exportObject(this, InspiredNationsServer.port);
	}

	private IndexedMap<Currency, BigDecimal> Exchange = new IndexedMap<Currency, BigDecimal>();
	
	public void registerCurrency(Currency currency, BigDecimal diamondValue) {
		
		BigDecimal amount = new BigDecimal(Config.exchangemultiplier);
		if(!this.Exchange.containsKey(currency)) {
			Exchange.put(currency, amount.multiply(diamondValue));
		}
	}
	
	public void unregisterCurrency(Currency currency) {
		this.Exchange.remove(currency);
	}
	
	/**
	 * Finds the value of money required in valueType that would yeild mon in montype if exchanged.
	 * The reason getExchangeValue does not work in a transfer context is that getExchangeValue
	 * assumes that monType depreciates when exchanged. Because in a transfer there is no exchange
	 * happening, the monType does not depreciate and therefore more valueType is required than
	 * what is found using getExchangeValue.
	 * @param mon		the amount of mon you're trying to get
	 * @param monType	the type of mon
	 * @param valueType	the type of currency you're using to get mon
	 * @return
	 */
	public final BigDecimal getTransferValue(BigDecimal mon, CurrencyPortal monType, Currency valueType, MathContext round) {
		BigDecimal output = BigDecimal.ZERO;
		BigDecimal B = Exchange.get(valueType);
		BigDecimal A = Exchange.get(monType);
		if(monType.equals(valueType)) {
			return mon;
		}
		if(mon.compareTo(BigDecimal.ZERO) <= 0) {
			return BigDecimal.ZERO;
		}
		//Figure out the value of things after the exchange
/*		BigDecimal c = mon.multiply(B.pow(2));
		BigDecimal b = ((new BigDecimal(2)).multiply(B)).add(
				mon.multiply(B).subtract(A.multiply(B)));
		BigDecimal a = (new BigDecimal(2)).multiply(mon);
		
		Debug.print("Transfer Value a: " + Tools.cut(a));
		Debug.print("Transfer Value b: " + Tools.cut(b));
		Debug.print("Transfer Value c: " + Tools.cut(c));
		
		return BigDecimalUtils.quad(a, b, c, round);*/
		
		

		BigDecimal difference = A.subtract(mon);
		output = mon.multiply(B).divide(difference, round);
		return output;
	}
	
/*	public void tempfunction() {
		Currency test = InspiredNations.getTestingPlayer().getCurrency();
		for(int i = 0;i<400; i +=2) {
			BigDecimal Transvalue = this.getTransferValue(new BigDecimal(i), Currency.DEFAULT, test, mcup);
			//BigDecimal ExchangeValue = this.getExchangeValue(Transvalue, test, );
		}
	}*/

	
	/**
	 * Gets the total amount of valueType you would recieve if you exchanged mon amount of monType
	 * @param mon
	 * @param getType
	 * @param valueType
	 * @return
	 */
	public final BigDecimal getExchangeValue(BigDecimal mon, CurrencyPortal getType, CurrencyPortal valueType) {
		//TODO I'm still not sure about having mcup here. Maybe it should be mcdown.
		BigDecimal output = this.getExchangeValue(mon, getType, valueType, mcup);
		return output;
	}
	
	public final BigDecimal getExchangeValue(BigDecimal mon, CurrencyPortal monType, CurrencyPortal getType, MathContext round) {
		
		BigDecimal output;
		//TODO put these lines back into the else statement.
		BigDecimal valueAmount = Exchange.get(getType);
		BigDecimal monAmount = Exchange.get(monType);
		//TODO end of the lines I need to put back in the else statement.
		
		//Remove if(monType.equals(valueType) when you figure it out
		
		if(monType.equals(getType)) {
			output = mon;
		}
		else {
			BigDecimal monSum = monAmount.add(mon);
			BigDecimal division = valueAmount.divide(monSum, round);
			output = mon.multiply(division);
		}
		return output;
	}
	
	public final BigDecimal exchange(BigDecimal mon, CurrencyPortal monType, CurrencyPortal valueType) throws RemoteException {
		BigDecimal outputup = this.getExchangeValue(mon, monType, valueType, mcup);
		BigDecimal outputdown = this.getExchangeValue(mon, monType, valueType, mcdown); //added outputdown
		Exchange.put(monType.getSelf(), Exchange.get(monType).add(mon));
		Exchange.put(valueType.getSelf(), Exchange.get(valueType).subtract(outputdown));
		
		return outputup;
	}
	
	public final HashMap<Currency, BigDecimal> getExchangeMap() {
		HashMap<Currency, BigDecimal> output = new HashMap<Currency, BigDecimal>();
		for(Currency curren:this.Exchange) {
			output.put(curren, this.Exchange.get(curren));
		}
		return output;
	}

	@Override
	public int getSize() throws RemoteException {
		return this.Exchange.size();
	}

	@Override
	public CurrencyPortal getCurrency(int index) throws RemoteException {
		return this.Exchange.getIndex(index);
	}
	
}
