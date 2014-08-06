package com.github.InspiredOne.InspiredNationsClient;

import com.github.InspiredOne.InspiredNations.Economy.Nodes.Node;


public class Debug {

	private static boolean report = false;
	public static int startcount = 0;
	private static String InformPluginDev = "If you see this, tell plugin developer";
	public static void print(Object msg) {
		InspiredNationsClient.plugin.logger.info(msg.toString());
	}
	
	public static void print(int i) {
		InspiredNationsClient.plugin.logger.info("Debuger check: " + i);
	}
	
	public static void InformPluginDev() {
		InspiredNationsClient.plugin.logger.info(InformPluginDev);
	}
	public static void node(Object msg) {
		//InspiredNations.plugin.logger.info("Node Tier " + Node.tier + ": " + msg.toString());
		Node.tier--;
	}
	
	
}
