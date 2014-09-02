package com.github.InspiredOne.InspiredNationsClient.ToolBox;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.bukkit.ChatColor;

import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsServer.Debug;
import com.github.InspiredOne.InspiredNationsServer.InspiredNationsServer;
import com.github.InspiredOne.InspiredNationsServer.Economy.Payable;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNationsServer.Governments.OwnerGov;
import com.github.InspiredOne.InspiredNationsServer.Remotes.CurrencyPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.ProtectionLevels;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Relation;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Tools;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging.Alert;

/**
 * Formating for all the text in the menus. Methods in here control everything from
 * the color of text to the spacing and the style.
 * @author Jedidiah Phillips
 *
 */
public class MenuTools {

	public static InspiredNationsClient plugin = InspiredNationsClient.plugin;
	
	public MenuTools() {
	}

	/**
	 * Builds the refresh space out of new-line characters.
	 * @return	the space required to clear the chat area for a menu
	 */
	public static String space() {
		return repeat("\n ", 20); //TODO add config stuff here.
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Nameable> ArrayList<T> filter(String key, ArrayList<T> fulllist) throws RemoteException {
		ArrayList<T> output = new ArrayList<T>();
		if(key.isEmpty()) {
			Debug.info("Filter is sending out initial list " + fulllist.size());
			return (ArrayList<T>) fulllist.clone();
		}
		for(T test:fulllist) {
			if(test.getName().toLowerCase().contains(key.toLowerCase())) {
				output.add(test);
			}
		}
		Debug.info("Filter left the list with # entries: " + output.size());
		return output;
	}
	
	// A method to simply repeat a string
	public static String repeat(String entry, int multiple) {
		String temp = "";
		for (int i = 0; i < multiple; i++) {
			temp = temp.concat(entry);
		}
		return temp;
	}
	
	/**
	 * Builds the divider to be used in the menus
	 * @param text
	 * @return
	 * @throws RemoteException 
	 */
	public static String addDivider(String text, PlayerDataPortal receiver) throws RemoteException {
		return text.concat(receiver.DIVIDER() + repeat("-", 53) + "\n" + ChatColor.RESET);
	}
	/**
	 * Adds a line that shows the value of the player's wallet.
	 * @param text
	 * @return
	 * @throws RemoteException 
	 */
	public static String oneLineWallet(String text, PlayerDataPortal PDI, Payable account) throws RemoteException {
		String output = text.concat(PDI.LABEL() + "Holdings: " + PDI.VALUE() +
				Tools.cut(account.getTotalMoney(PDI.getCurrency(), InspiredNationsServer.Exchange.mcdown)) + PDI.UNIT() +" " + PDI.getCurrency().getName() + "\n");
		return output;
	}
	/**
	 * Builds the header for menus. Adds the <code>ChatColor</code> character to the beginning
	 * and clears it afterward.
	 * @param msg	the <code>String</code> to be used in the header
	 * @return		the <code>String</code> processed to be in the menu
	 * @throws RemoteException 
	 */
	public static String header(String msg, PlayerDataPortal receiver) throws RemoteException {
		return addDivider(receiver.HEADER() + msg + "\n" + ChatColor.RESET, (receiver));
	}

	public class ContextData {
		public static final String Error = "Error";
		public static final String Alert = "Alert";
	//	public static final String PromptData = "PromptData";
	}
	
	public enum OptionUnavail {
		NOT_UNAVAILABLE(""),
		NOBODY_TO_SHARE_WITH("No governments or people."),
		NEED_HIGHER_PROTECTION("You need at least Protection Level " + ProtectionLevels.IMMIGRATION_CONTROL + "."),
		NO_PEOPLE_TO_ADD("There are no other players to invite."),
		NO_GOVERNMENTS_TO_ADD("There are no other governments to add."),
		NO_CURRENCIES_TO_ADD("There are no other currencies to add.");
		
		private String reason;
		
		private OptionUnavail(String reason) {
			this.reason = reason;
		}
        @Override
        public String toString() {
        	return reason;
        }
	}
	
	public static class MenuAlert {
		public static Alert NO_ALERT() {
			
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 3618500001516080580L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return "";
				}

				@Override	
				public boolean menuPersistent() {
					// TODO Auto-generated method stub
					return false;
				}
				
			};
			
		}
		public static Alert GOV_UNREGISTERED(final InspiredGov gov) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -4486493616302101435L;


				@Override
				public String getMessage(PlayerDataPortal receiver) {
					// TODO Auto-generated method stub
					return makeMessage("The government " + gov.getName() + " has been deleted.", receiver);
				}
				

				@Override
				public boolean menuPersistent() {
					// TODO Auto-generated method stub
					return true;
				}
				
			};
			
		}
		public static Alert ADDED_AS_OWNER_TO_GOV(final OwnerGov govadd, final String position) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -7757366529180653770L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
				
					return makeMessage("You are now " + position + " of " + govadd.getDisplayName(receiver) + TextColor.ALERT(receiver) +".", receiver);
				}

				@Override
				public boolean menuPersistent() {
					return false;
				}
				
			};
		}
		public static Alert ADDED_AS_SUBJECT_TO_GOV(final OwnerGov govadd, final String position) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -6179796899976083043L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					
					return makeMessage("You are now a " + position + " of " + govadd.getDisplayName(receiver) + TextColor.ALERT(receiver) +".", receiver);
				}

				@Override
				public boolean menuPersistent() {
					return false;
				}
				
			};
		}
		
		public static Alert ALLY_TRIED_TO_HURT_YOU(final PlayerDataPortal attacker) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 6265029800715995613L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return makeMessage(attacker.getDisplayName(receiver) + " is trying to hurt you.", receiver);
				}

				@Override
				public boolean menuPersistent() {
					return true;
				}
			};
		}
		
		public static Alert CANT_HURT_ALLY(final PlayerDataPortal target) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -3534863904446112401L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return makeMessage("You cannot hurt your ally, " + target.getDisplayName(receiver) + TextColor.ALERT(receiver) +".", receiver);
				}

				@Override
				public boolean menuPersistent() {
					return false;
				}
			};
		}
		
		public static Alert GOV_INVITED_YOU(final OwnerGov govinvite, final String position) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -8887323195358324916L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return makeMessage("You have been invited to be a "
				+ position + " of " + govinvite.getDisplayName(receiver)+ TextColor.ALERT(receiver)+ ".", receiver);
				}

				@Override
				public boolean menuPersistent() {
					return false;
				}
				
			};
		}
		public static Alert GOV_HAS_BEEN_RELATED(final Relation re, final OwnerGov govre, final OwnerGov gov) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -4462019551522672864L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					if(re.equals(Relation.ALLY)) {
						return makeMessage(gov.getDisplayName(receiver) + receiver.ALERT() + " has allied you.", receiver);
					}
					else if(re.equals(Relation.NEUTRAL)) {
						return makeMessage(gov.getDisplayName(receiver) + receiver.ALERT() + " has neutraled you.", receiver);
					}
					else if(re.equals(Relation.ENEMY)) {
						return makeMessage(gov.getDisplayName(receiver) + receiver.ALERT() + " has enemied you.", receiver);
					}
					else {
						return null;
					}
				}

				@Override
				public boolean menuPersistent() {
					return true;
				}
				
			};
		}
		public static Alert GOV_HAS_SUCCEFULLY_RELATED(final Relation re, final OwnerGov govre, final OwnerGov gov) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -1606958062365777950L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					if(re.equals(Relation.ALLY)) {
						return makeMessage("You have successfully allied " + govre.getDisplayName(receiver) + TextColor.ALERT(receiver) + ".", receiver);
					}
					else if(re.equals(Relation.NEUTRAL)) {
						return makeMessage("You have successfully neutraled " + govre.getDisplayName(receiver) + TextColor.ALERT(receiver) + ".", receiver);
					}
					else if(re.equals(Relation.ENEMY)) {
						return makeMessage("You have successfully enemied " + govre.getDisplayName(receiver) + TextColor.ALERT(receiver) + ".", receiver);
					}
					else {
						return null;
					}
				}

				@Override
				public boolean menuPersistent() {
					// TODO Auto-generated method stub
					return false;
				}
				
			};
		}

		
		public static Alert CANNOT_HURT(final InspiredGov gov, final PlayerDataPortal target) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -4462019551522672864L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return makeMessage("You cannot hurt "+target.getDisplayName(receiver.getPlayerID())+ receiver.ALERT() +
							" here in " + gov + receiver.ALERT()+". "
							 + "Their protection is too high.", (receiver));
				}

				@Override
				public boolean menuPersistent() {
					return false;
				}
				
			};
		}
		public static Alert TRIED_TO_HURT_YOU(final PlayerDataPortal attacker) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -1606958062365777950L;

				@Override
				public String getMessage(PlayerDataPortal receiver) throws RemoteException {
					return makeMessage(attacker.getDisplayName(receiver.getPlayerID()) + receiver.ALERT() + " tried to hurt you.", receiver);
				}

				@Override
				public boolean menuPersistent() {
					return false;
				}
				
			};
		}
		public static Alert CANNOT_INTERACT(final InspiredGov attacked) {
			return new Alert() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = -2499830621442259952L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return makeMessage("You cannot interact in " + attacked.getDisplayName(receiver) 
							+ receiver.ALERT()+". Their protection is too high.", (receiver));
				}

				@Override
				public boolean menuPersistent() {
					// TODO Auto-generated method stub
					return false;
				}
			};
		}
		public static Alert LOST_OWNERSHIP(final OwnerGov govlost) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 6630552666164107410L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return makeMessage("You are no longer " + govlost.getOwnerPositionName() + " of " + govlost.getName(), (receiver));
				}

				@Override
				public boolean menuPersistent() {
					// TODO Auto-generated method stub
					return true;
				}
				
			};
		}
		public static Alert LOST_CITIZENSHIP(final OwnerSubjectGov govlost) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 5356796387575937695L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return makeMessage("You are no longer " + govlost.getSubjectPositionName()
							+ " of " + govlost.getName() + ".", (receiver));
				}

				@Override
				public boolean menuPersistent() {
					// TODO Auto-generated method stub
					return true;
				}
				
			};
		}
		public static Alert MESSAGE_ALERT(final String msg) {
			return new Alert() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 2410593640115800320L;

				@Override
				public String getMessage(PlayerDataPortal reciever) {
					return makeMessage(msg, (reciever));
				}

				@Override
				public boolean menuPersistent() {
					// TODO Auto-generated method stub
					return false;
				}
			};
		}
		public static Alert REGION_UPDATED_SUCCESSFULY(final Region region, final InspiredGov gov) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -8470406903932530715L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return makeMessage(region.getTypeName() + " of " + gov.getDisplayName(receiver) + TextColor.ALERT(receiver) +
							" successfully modified.", (receiver));
				}

				@Override
				public boolean menuPersistent() {
					// TODO Auto-generated method stub
					return false;
				}
				
			};
		}
		public static Alert RECEIVED_MONEY(final BigDecimal amount, final CurrencyPortal curren, final Nameable sender) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -8611525947131654856L;

				@Override
				public String getMessage(PlayerDataPortal receiver) throws RemoteException {
					BigDecimal converted = Tools.cut(InspiredNationsClient.server.getExchange().getExchangeValue(amount, curren, receiver.getCurrency()));
					return makeMessage(sender.getDisplayName(receiver.getPlayerID()) + makeMessage(" paid you " +receiver.VALUE()+ converted + " "
							+ receiver.UNIT() + receiver.getCurrency() + receiver.ALERT()+ ".",(receiver)), (receiver));
				}

				@Override
				public boolean menuPersistent() {
					// TODO Auto-generated method stub
					return false;
				}
				
			};
		}
		public static Alert TRANSFER_SUCCESSFUL(final BigDecimal amount, final CurrencyPortal curren, final Nameable sender, final Nameable paid) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 8894522114770201993L;

				@Override
				public String getMessage(PlayerDataPortal receiver) throws RemoteException {
					BigDecimal converted = Tools.cut(InspiredNationsClient.server.getExchange().getExchangeValue(amount, curren, receiver.getCurrency()));
					return makeMessage(sender.getDisplayName(receiver.getPlayerID()), receiver) + makeMessage(receiver.ALERT() + " paid " + receiver.VALUE() + converted +
							" " + receiver.UNIT() + receiver.getCurrency() + receiver.ALERT() + " to " + paid.getDisplayName(receiver.getPlayerID())
							+receiver.ALERT() + ".", (receiver));
				}

				@Override
				public boolean menuPersistent() {
					// TODO Auto-generated method stub
					return false;
				}
			};
		}
		
		public static Alert WELCOME_TO_GOV(final InspiredGov gov) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -4291314964430337719L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					
					return makeMessage("You have entered " + gov.getDisplayName(receiver), receiver) +
							makeMessage(".", receiver);
				}

				@Override
				public boolean menuPersistent() {
					return true;
				}
				
			};
		}
		
		public static Alert GOODBYE_TO_GOV(final InspiredGov gov) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -2487985662361164037L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return makeMessage("You have exited " + gov.getDisplayName(receiver),receiver) +
							makeMessage(".", receiver);
				}

				@Override
				public boolean menuPersistent() {
					return true;
				}
			};
		}
		
		public static Alert GOV_MADE_SUCCESSFULLY(final InspiredGov gov) {
			return new Alert() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -7797465337304908395L;

				@Override
				public String getMessage(PlayerDataPortal receiver) {
					return makeMessage(gov.getTypeName() + " created successfully.", receiver);
				}

				@Override
				public boolean menuPersistent() {
					return true;
				}
			};
		}
		
		public static String makeMessage(Object input, PlayerDataPortal reciever) {
			return TextColor.ALERT(reciever) + input.toString();
		}

	}
	
	public static class MenuError {
		
		public static String NO_ERROR() {
			return "";
		}
		public static String HELP_PAGE_NOT_AVAILABLE(int maxPages, PlayerDataPortal PDI) {
			return makeMessage("There are only " + maxPages + " pages to this help document. Inputs must be positive.", PDI);
		}
		public static String ACCOUNT_ALREADY_LINKED(PlayerDataPortal PDI) {
			return makeMessage("The account is already linked.", PDI);
		}
		public static String INVALID_NUMBER_INPUT(PlayerDataPortal PDI) {
			return makeMessage("Your entry must be a number.", PDI);
		}
		public static String OUT_OF_RANGE_NUMBER_INPUT(PlayerDataPortal PDI) {
			return makeMessage("That is not an option.", PDI);
		}
		public static String NOT_AN_OPTION(PlayerDataPortal PDI) {
			return makeMessage("That is not an option.", PDI);
		}
		public static String NAME_ALREADY_TAKEN(Class<? extends InspiredGov> gov, PlayerDataPortal PDI) {
			
			String GovName = getTypeName(gov);
			return makeMessage("That " + GovName + " name is already taken.", PDI);
		}
		public static String MONEY_NAME_ALREADY_TAKEN(PlayerDataPortal PDI) {
			return makeMessage("That currency name is already in use.", PDI);
		}
		public static String MONEY_MULTIPLYER_TOO_LARGE(PlayerDataPortal PDI) {
			return makeMessage("Your currency is too inflated.", PDI);
		}
		public static String MONEY_MULTIPLYER_TOO_SMALL(PlayerDataPortal PDI) {
			return makeMessage("Your currency is too valuable.", PDI);
		}
		public static String ACCOUNT_NAME_ALREADY_TAKEN(PlayerDataPortal PDI) {
			return makeMessage("That account name is already in use.", PDI);
		}
		public static String NO_MATCHES_FOUND(PlayerDataPortal PDI) {
			return makeMessage("There are no matches found.", PDI);
		}
		public static String NO_SUB_GOVS_UNDER_THIS_GOV(PlayerDataPortal PDI) {
			return makeMessage("There are no governments under the control of this government.", PDI);
		}
		public static String NOT_ENOUGH_MONEY(PlayerDataPortal PDI) {
			return makeMessage("There is not enough money.", PDI);
		}
		public static String GOV_TOO_STRONG(InspiredGov gov, PlayerDataPortal PDI) {
			return makeMessage("The "+gov.getTypeName()+", " +gov.getName()+", is in the way.", PDI);
		}
		public static String CLAIM_OUT_OF_BOUNDS(InspiredGov gov, PlayerDataPortal PDI) {
			return makeMessage("Your claim goes outside of the " + gov.getTypeName() +", " + gov.getName() + ".", PDI);
		}
		public static String NEGATIVE_AMOUNTS_NOT_ALLOWED(BigDecimal useInstead, PlayerDataPortal PDI) {
			return makeMessage("You can't use negative values here. Use " + useInstead.abs() + " instead.", PDI);
		}
		public static String CUBOID_NOT_FULLY_SELECTED(PlayerDataPortal PDI) {
			return makeMessage("You have not selected both points of the cuboid.", PDI);
		}
		public static String POINTS_IN_DIFFERENT_WORLDS(PlayerDataPortal PDI) {
			return makeMessage("Your selected points were in different worlds.", PDI);
		}
		public static String POLYGON_NOT_SIMPLE_SHAPE(PlayerDataPortal PDI) {
			return makeMessage("The polygon you selected is not simple. This means that some of the sides"
					+ " cross. Make sure you select each corner in order.", PDI);
		}
		public static String SELECTION_MUST_BE_CHEST(PlayerDataPortal PDI) {
			return makeMessage("You may only select chests for your shop.", PDI);
		}
		public static String NEGATIVE_PROTECTION_LEVEL_NOT_ALLOWED(PlayerDataPortal PDI) {
			return makeMessage("Negative numbers are not allowed for protection levels.", PDI);
		}
		public static String NEGATIVE_MILITARY_LEVEL_NOT_ALLOWED(PlayerDataPortal PDI) {
			return makeMessage("Negative numbers are not allowed for military levels.", PDI);
		}
		public static String EMPTY_INPUT(PlayerDataPortal PDI) {
			return makeMessage("Your input was blank.", PDI);
		}
		public static String ACCOUNT_ALREADY_HAS_THAT_CURRENCY(PlayerDataPortal PDI) {
			return makeMessage("The account already has that currency.", PDI);
		}
		public static String ACCOUNT_COLLECTION_NOT_LINKED(PlayerDataPortal PDI) {
			return makeMessage("This account is not linked to any other accounts.", PDI);
		}
		private static final String getTypeName(Class<? extends InspiredGov> gov) {
			String GovName = "";
			GovName = GovFactory.getGovInstance(gov).getTypeName();
			return GovName;
		}
		public static final String makeMessage(Object msg, PlayerDataPortal PDI) {
			return "\n" + PDI.ERROR() + msg.toString();
		}
	}
	
	/**
	 * Draws a ascii map for the player to view. The map shows the gov
	 * and the gov below that. The gov is encoded with a color while the
	 * subgov is encoded with a symbol.
	 * @param plugin	the <code>InspiredNations</code> plugin
	 * @param PDI		the <code>PlayerData</code> of the player using the map
	 * @param res		the length of one pixel side in blocks
	 * @param gov		the highest government that will be shown on the map
	 * @return			the <code>String</code> that includes the map
	 */
	public static String drawMap(PlayerData PDI, int res, int tier, int size) {
		
		String output = "";
		Location location = null;
		try {
			location = PDI.getPlayer().getLocation();
		} catch (PlayerOfflineException e) {
			e.printStackTrace();
		}

		
		HashMap<InspiredGov, ChatColor> superGov = new HashMap<InspiredGov, ChatColor>();
		HashMap<InspiredGov, String> subGov = new HashMap<InspiredGov, String>();
		
		int superIter = 0;// for iterating through superGov color choices
		ChatColor[] superColors = {ChatColor.GREEN, ChatColor.RED, ChatColor.DARK_BLUE, ChatColor.DARK_GREEN, ChatColor.DARK_RED, ChatColor.GOLD, ChatColor.WHITE,
				ChatColor.AQUA, ChatColor.YELLOW, ChatColor.DARK_AQUA, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE};
		int subIter = 0;// for iterating through subGov Char choices
		String[] subChars = {"#", "$", "%", "&", "S", "~", "=", "+", "B", "T", "G"};

		ChatColor colorInit = ChatColor.DARK_GRAY;
		String characterInit = "/";
		ChatColor selfcolor = ChatColor.GRAY;
		String selfchar = ">";
		
		int above = size;
		int below = size + 1;
		
		//int[] remove = {160*above+240,160*above+77,160*above-80,160*above+83};
		
		for(int z = -above;z < below; z++) {
			Location loctest = location.clone();
			loctest.setZ(location.getBlockZ() + z*res);
			for (int x = -26; x < 27; x++) {
				String character = characterInit;
				ChatColor color = colorInit;
				loctest.setX(location.getBlockX() + x*res);
				
				//Check if self
				if(x == 0 && z == 0) {
					color = selfcolor;
				}
				
				//Loop through the superGovs to see if any of them contain loctest
				for(InspiredGov govtest:InspiredNations.regiondata) {
					if(govtest.contains(loctest) && govtest.getTier()==tier) {
						if(!superGov.containsKey(govtest)) {
							superGov.put(govtest, superColors[superIter]);
							superIter++;
							if(superIter == superColors.length) {
								superIter = 0;
							}
						}
						//Color is known, so append that to map string
						color = superGov.get(govtest);
						
						//Loop through the subGovs to see if any of them contain loctest
						for(InspiredGov subtest: govtest.getAllSubGovsAndFacilitiesJustBelow()) {
							if(subtest.contains(loctest)) {
								if(!subGov.containsKey(subtest)) {
									subGov.put(subtest, subChars[subIter]);
									subIter++;
									if(subIter == subChars.length) {
										subIter = 0;
									}
								}
								character = subGov.get(subtest);
								break;
							}
						}
						break;
					}
				}
				//Check if self
				if(x == 0 && z == 0) {
					//character = selfchar;
					if ((-45 < location.getYaw() && 45 >= location.getYaw()) || (315 < location.getYaw() && 360 >= location.getYaw())
							|| (-360 < location.getYaw() && -315 >= location.getYaw())) {
						character = "V";
					}
					if ((45 < location.getYaw() && 135 >= location.getYaw()) || (-315 < location.getYaw() && -225 >= location.getYaw())) {
						character = "<";
					} 
					if ((135 < location.getYaw() && 225 >= location.getYaw()) || (-225 < location.getYaw() && -135 >= location.getYaw())) {
						character = "^";

					}
					if ((225 < location.getYaw() && 315 >= location.getYaw()) || (-135 < location.getYaw() && -45 >= location.getYaw())) {
						character = ">";
					}
					selfchar = character;
				}
				output = output.concat(color + character);
			}
			output = output.concat("\n");
		}
/*		
		// Direction Icon
		if ((-45 < location.getYaw() && 45 >= location.getYaw()) || (315 < location.getYaw() && 360 >= location.getYaw())
				|| (-360 < location.getYaw() && -315 >= location.getYaw())) {
			output = output.substring(0, remove[0]).concat("`|`").concat(output.substring(remove[0] + 1));
		}
		if ((45 < location.getYaw() && 135 >= location.getYaw()) || (-315 < location.getYaw() && -225 >= location.getYaw())) {
			output = output.substring(0, remove[1]).concat("-").concat(output.substring(remove[1] + 1));
		} 
		if ((135 < location.getYaw() && 225 >= location.getYaw()) || (-225 < location.getYaw() && -135 >= location.getYaw())) {
			output = output.substring(0, remove[2]).concat(",|,").concat(output.substring(remove[2] + 1));

		}
		if ((225 < location.getYaw() && 315 >= location.getYaw()) || (-135 < location.getYaw() && -45 >= location.getYaw())) {
			output = output.substring(0, remove[3]).concat("-").concat(output.substring(remove[3] + 1));
		}
*/
		//Key
		for(InspiredGov key:superGov.keySet()) {
			output = output.concat(superGov.get(key) + key.getName() + ", ");
		}
		for(InspiredGov key:subGov.keySet()) {
			output = output.concat(superGov.get(key.getSuperGovObj()) + subGov.get(key) + "=" + key.getName() + ", ");
		}
		output = output.concat(ChatColor.DARK_GRAY + "/" + ChatColor.GRAY + "= Unclaimed Land, "+ selfchar +" = You.\n");
		
		return output;
		
	}
}
