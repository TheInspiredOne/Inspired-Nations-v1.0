package com.github.InspiredOne.InspiredNations;

import com.github.InspiredOne.InspiredNations.Economy.Nodes.Node;


public class Debug {

	private static boolean report = false;
	public static int startcount = 0;
	private static String InformPluginDev = "If you see this, tell plugin developer";
	public static void print(Object msg) {
		System.out.print(msg.toString());
	}
	
	public static void print(int i) {
		System.out.print("Debuger Check: " + i);
	}
	
	public static void InformPluginDev() {
		System.out.print(InformPluginDev);
	}
	public static void node(Object msg) {
		//InspiredNations.plugin.logger.info("Node Tier " + Node.tier + ": " + msg.toString());
		Node.tier--;
	}
	
	
}
