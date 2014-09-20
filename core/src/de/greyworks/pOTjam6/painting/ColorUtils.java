package de.greyworks.pOTjam6.painting;


public class ColorUtils {

	static int maskR = 0xff000000;
	static int maskG = 0x00ff0000;
	static int maskB = 0x0000ff00;
	
	public static int getRandomColor() {
		return ((int) (Math.random() * Integer.MAX_VALUE)) | 0x000000ff;
	}

	public static int[] channelsFromInt(int in) {
		int[] channels = new int[4];
		channels[0] = ((in & maskR) >>> 24);
		channels[1] = ((in & maskG) >>> 16);
		channels[2] = ((in & maskB) >>> 8);
		channels[3] = 255;
		return channels;
	}

	public static int intFromChannels(int[] in) {
		int ri = in[0] << 24;
		int gi = in[1] << 16;
		int bi = in[2] << 8;
		return (ri | gi | bi | in[3]);
	}

	public static double calculateHexDiff(int a, int b) {
		int dr = ((a & maskR) >>> 24) - ((b & maskR) >>> 24);
		int dg = ((a & maskG) >>> 16) - ((b & maskG) >>> 16);
		int db = ((a & maskB) >>> 8) - ((b & maskB) >>> 8);
		int diff = dr * dr + dg * dg + db * db;
		if (diff != 0)
			return Math.sqrt(diff);
		return 0;
	}

}
