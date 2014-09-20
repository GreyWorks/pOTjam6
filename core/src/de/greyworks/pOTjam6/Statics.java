package de.greyworks.pOTjam6;

public class Statics {
	public static final boolean DEBUG = true;
	public static final boolean ERROR = true;
	
	public static void log(String type, String msg) {
		if(type.equals("d") && DEBUG) {
			System.out.println("DEBUG: " + msg);
		} else if (type.equals("e")) {
			System.out.println("ERROR: " + msg);
		}
	}
}
