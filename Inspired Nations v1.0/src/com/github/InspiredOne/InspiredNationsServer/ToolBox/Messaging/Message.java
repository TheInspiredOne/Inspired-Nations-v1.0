package com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging;

import java.rmi.RemoteException;
import java.util.Locale;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataInter;

public class Message extends Alert implements Nameable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6130859000755211465L;
	PlayerDataInter from;
	String msg;
	
	public Message(boolean menuVisible, PlayerDataInter from, String msg) {
		this.from = from;
		this.menuVisible = menuVisible;
		this.msg = msg;
	}
	
	public String getMessage(PlayerDataInter receiver) throws RemoteException {
		return from.getDisplayName(receiver.getPlayerID()) + ": " + conditionForMoney(msg, from, receiver);
	}
	
	@Override
	public String getName() {
		return "Received: " + calendar.getDisplayName(0, 0, Locale.US) + " From: " + from;
	}
	
	/**
	 * Takes the input string and replaces every instances of $(amount)
	 *  with the exchanged value relevant
	 * to the player looking at it.
	 * @param input	
	 * @param target	the player who sees the message;
	 * @return
	 */
	public String conditionForMoney(String input, PlayerDataInter from, PlayerDataInter to) {
		String output = "";
		if(input.contains("$")) {
			String[] args = input.split(" ");
			boolean checkNext = false;
			boolean scan = false;
			int index = 0;
			String append;
			for(String test:args) {
				String orig = test;
				append = "";
				if(test.startsWith("$")) {
					scan = true;
					test = test.substring(1);
				}
				// If we want to check next or encountered a $
				if(checkNext || scan) {
					//Check for punctuation
					if(test.endsWith(".") || test.endsWith("!") || test.endsWith(",")
							|| test.endsWith(";") || test.endsWith(":")) {
						append = test.substring(test.length() - 1);
						test = test.substring(0, test.length() - 1);
					}
					scan = false;
					// Try to do the conversion
					try {
//						amount = new BigDecimal(test);
//						test = Tools.cut(InspiredNations.Exchange.getTransferValue(amount, from.getCurrency(), to.getCurrency(),InspiredNations.Exchange.mcup)).toString() + " " + to.getCurrency();
						checkNext = false;
					}
					catch (Exception ex) {
						checkNext = !checkNext;
						test = orig;
					}
					// Modify the value of the string in the list
					args[index] = test + append;
				}
				index++;
			}
			// Assemble the list back into a readable string.
			for(String concat:args) {
				if(!concat.isEmpty()) {
				output = output.concat(concat + " ");
				}
			}
			if(output.length() > 0) {
				return output.substring(0, output.length() - 1);
			}
			else return "";
		}
		else {
			return input;
		}
	}

	@Override
	public boolean menuPersistent() {
		return true;
	}
	
	

}
