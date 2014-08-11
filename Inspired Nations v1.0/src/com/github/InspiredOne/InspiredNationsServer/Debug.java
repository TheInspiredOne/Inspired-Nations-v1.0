package com.github.InspiredOne.InspiredNationsServer;

public class Debug {
	private static String InformPluginDev = "If you see this, tell plugin developer";
	public static void info(Object msg) {
		System.out.println(msg.toString());
	}


	public static void print(Object msg) {
		System.out.print(msg.toString());
	}
	
	public static void print(int i) {
		System.out.print("Debuger Check: " + i);
	}
	
	public static void InformPluginDev() {
		System.out.print(InformPluginDev);
	}
	
	public static void node(String string) {
		// TODO Auto-generated method stub
		
	}
}

