package com.github.InspiredOne.InspiredNations.Hud;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Hud.Implem.MainHud;
import com.github.InspiredOne.InspiredNations.ToolBox.Alert;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools;
import com.github.InspiredOne.InspiredNations.ToolBox.MenuTools.MenuError;
import com.github.InspiredOne.InspiredNations.ToolBox.Tools.TextColor;

public abstract  class Menu extends MessagePrompt implements Cloneable {

	private static final String footer = MenuTools.addDivider("") + TextColor.ENDINSTRU + "Type 'exit' to leave, 'say' to chat, or 'back'/'hud' to go back.";
	public PlayerData PDI;
	public InspiredNations plugin;
	protected boolean initialized = false;
	protected Menu self;
	
	public Menu(PlayerData PDI) {
		this.PDI = PDI;
		this.plugin = InspiredNations.plugin;
	}
	
	/**
	 * 
	 * @return	the <code>String</code> of the prompt text as it would appear exactly
	 */
	public final String getPromptText() {
		String space = MenuTools.space();
		String main = MenuTools.header(this.getHeader() + " " + InspiredNations.taxTimer.getTimeLeftReadout());
		String filler = this.getFiller();
		String end = footer;
		String errmsg = this.getError();
		
		return space + main + filler + end + errmsg;
	}
	
	public final PlayerData getPlayerData() {
		return this.PDI;
	}
	
	public final void Initialize() {
		try {
			self = (Menu) this.clone();
			Debug.print("self has been set in Menu");
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if(!initialized) {
			this.init();
			initialized = true;
		}
	}

	public final boolean passBy() {
		this.Initialize();
		return this.getPassBy() || !this.preRecsFilled();
	}
	
	@Override
	public final boolean blocksForInput(ConversationContext arg0) {
		return !this.passBy() && this.preRecsFilled();
	}
	@Override
	public final Prompt getNextPrompt(ConversationContext arg0) {
		if(!this.preRecsFilled()) {
			return this.preRecRetrivalMenu();
		}
		else {
			return this.getPassTo();
		}
	}
	@Override
	public final String getPromptText(ConversationContext arg0) {
		this.Initialize();
		this.register();
		return this.getPromptText();
	}
	@Override
	public final Prompt acceptInput(ConversationContext arg0, String arg) {
		this.setError(MenuError.NO_ERROR());
		this.unregister();
		if(arg == null) {
			return this.getNextPrompt(arg0);
		}
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		if (arg.equalsIgnoreCase("back")) {
			return this.checkBack();
		}
		if (arg.equalsIgnoreCase("hud")) {
			return new MainHud(PDI);
		}
		if (arg.equalsIgnoreCase("exit")) {
			return Menu.END_OF_CONVERSATION;
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PDI.getMsg().sendChatMessage(arg.substring(4));
			}
			return this.getSelf(this);
		}
		
		return this.checkNext(arg);
	}

	/**
	 * 
	 * @return	the <code>String</code> to be used for the error in the menu
	 */
	private final String getError() {
		String output = this.PDI.getMsg().getNotif();		
		return output;
	}
	/**
	 * 
	 * @param error	the <code>MenuError</code> to be used as the error
	 */
	public final Menu setError(String error) {
		this.PDI.getMsg().receiveError(error);
		return this;
	}
	/**
	 * Sets the message data to be displayed in the menu.
	 * @param msg
	 */
	public void setAlert(String msg) {
		this.PDI.getMsg().receiveAlert(new Alert(msg));
//		if(!msg.equals(MenuAlert.NO_ALERT())) {
//			this.PDI.getCon().outputNextPrompt();
//		}
		
	}
	/**
	 * Returns a new instance of itself. Used for user input errors.
	 * @return	the <code>Menu</code> of itself
	 */
	@SuppressWarnings("unchecked")
	public <T extends Menu> T getSelf(T self) {
		Debug.print("Is self null? " + this.self == null);
		Debug.print(self);
		return (T) this.self;
	}
	/**
	 * 
	 * @return the <code>ConversationContext</code> of the player using this menu
	 */
	public final ConversationContext getContext() {
		this.PDI.getCon();
		return this.PDI.getCon().getContext();
	}

	/**
	 * Looks at previous menu and determines if it should be skipped or not
	 * @return	the actual previous menu rather than just the one before this one
	 * in the menu graph
	 */
	private final Menu checkBack() {
		Menu previous = this.getPreviousMenu();
		if(!previous.passBy()) {
			return previous;
		}
		else {
			return previous.checkBack();
		}
	}
	/**
	 * Looks at next menu and determines if it should be skipped or not
	 * @param input	the <code>String</code> input by the player
	 * @return		the actual next Menu rather than just the one after this one
	 * in the menu graph
	 */
	private final Menu checkNext(String input) {
		Menu next = this.getNextMenu(input);
		while(next.passBy()) {
			next = (Menu) next.getNextPrompt(PDI.getCon().getContext());
		}
		return next;
	}
	/**
	 * Used to make sure all the fields that this menu needs to run are set.
	 * @return
	 */
	public boolean preRecsFilled() {
		return true;
	}
	/**
	 * Returns the menu that should be used to fill the PreRecs.
	 * @return
	 */
	public Menu preRecRetrivalMenu() {
		return null;
	}
	/**
	 * 
	 * @return	the <code>String</code> to be used for the header of the menu
	 */
	public abstract String getHeader();
	/**
	 * 
	 * @return	the <code>String</code> to be used for the filler of the menu
	 */
	public abstract String getFiller();
	/**
	 * A method that allows events to be registered by a superclass.
	 */
	public abstract void register();
	/**
	 * A method that allows events to be unregistered by a superclass;
	 */
	public abstract void unregister();
	/**
	 * Returns the prompt to go to when player uses "back"
	 * @return the <code>Prompt</code> that lead to this menu
	 * 
	 */
	public abstract Menu getPreviousMenu();
	/**
	 * 
	 * @param input	the <code>String</code> used to process the next <code>Prompt</code>
	 * @return		the next <code>Prompt</code> to be used in this conversation
	 */
	public abstract Menu getNextMenu(String input);
	/**
	 * Determines if this menu should be passed by or not
	 * @return	<code>true</code> if it will be passed by
	 */
	public abstract boolean getPassBy();
	/**
	 * Get's the menu that will be used if getPassBy() returns <code>true</code>
	 * @return	the menu passed to
	 */
	public abstract Menu getPassTo();
	/**
	 * Used to do things for the conversation, but only for when the user is directed to it. Use
	 * for adding options, managers, grabbing context data, and tab-completes.
	 */
	public abstract void init();


}
