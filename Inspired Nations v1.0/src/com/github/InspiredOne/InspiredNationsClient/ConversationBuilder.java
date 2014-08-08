/*******************************************************************************
 * Copyright (c) 2013 InspiredOne.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     InspiredOne - initial API and implementation
 ******************************************************************************/
package com.github.InspiredOne.InspiredNationsClient;


import java.util.HashMap;

import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;

import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.ContextData;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuAlert;
import com.github.InspiredOne.InspiredNationsClient.ToolBox.MenuTools.MenuError;


public class ConversationBuilder {
	
	// Grabbing the instance of the plugin.
	InspiredNationsClient plugin;
	ClientPlayerData PDI;
	HashMap<Object, Object> initSessionData = new HashMap<Object, Object>();
	public ConversationBuilder(ClientPlayerData PDI) {
		plugin = InspiredNationsClient.plugin;
		this.PDI = PDI;
		this.initSessionData.put(ContextData.Error, MenuError.NO_ERROR());
		this.initSessionData.put(ContextData.Alert, MenuAlert.NO_ALERT());
	}
	
	public Conversation HudConvo() {
		Menu hud = new MainHud(PDI);
		hud.PDI = this.PDI;
		ConversationFactory HudConvo = new ConversationFactory(plugin)
		.withModality(true)
		.withFirstPrompt(hud)
		.withLocalEcho(false)
		.withInitialSessionData(this.initSessionData);
		//.withTimeout(180);
		return HudConvo.buildConversation((Conversable) plugin.getServer().getPlayer(PDI.getPlayerID().getID()));
	}
	
	public Conversation MapConvo() {
		Menu map = new Map(PDI);
		map.PDI = PDI;
		ConversationFactory MapConvo = new ConversationFactory(plugin)
		.withModality(true)
		.withFirstPrompt(map)
		.withLocalEcho(false)
		.withInitialSessionData(initSessionData);
		return MapConvo.buildConversation((Conversable) plugin.getServer().getPlayer(PDI.getPlayerID().getID()));
	}
	
	/*public Conversation CountryClaim(Player player) {
		ConversationFactory CountryClaim = new ConversationFactory(plugin)
		.withModality(true) 
		//.withEscapeSequence("exit")
		.withFirstPrompt(new ClaimLand(plugin, player, 0))
		.withLocalEcho(false);
		//.withTimeout(180);
		return CountryClaim.buildConversation((Conversable) player);
	}
	
	public Conversation ManageCountry(Player player) {
		ConversationFactory ManageCountry = new ConversationFactory(plugin)
		.withModality(true)
		.withEscapeSequence("exit")
		.withFirstPrompt(new ManageCountry(plugin, player, 0))
		.withLocalEcho(false);
		//.withTimeout(180);
		return ManageCountry.buildConversation((Conversable) player);
	}*/
}
