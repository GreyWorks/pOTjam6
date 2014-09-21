package de.greyworks.pOTjam6.painting;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;


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
	
	public static ArrayList<Integer> kMeans(Pixmap img, int num, int iterations) {
		int[] cols = new int[num];
		int[][] zuordnung = new int[img.getWidth()][img.getHeight()];
		double currDiff;

		// vergabe
		for (int i = 0; i < cols.length; i++) {
			cols[i] = ColorUtils.getRandomColor();
		}

		// iterations
		for (int i = 0; i < iterations; i++) {

			// Zuordnung
			// init max values
			double[][] minDiffs = new double[img.getWidth()][img.getHeight()];
			for (int j = 0; j < img.getWidth() * img.getHeight(); j++) {
				int x = j % img.getWidth();
				int y = (int) Math.floor(j / img.getWidth());
				minDiffs[x][y] = Double.MAX_VALUE;
			}

			for (int c = 0; c < cols.length; c++) {
				for (int x = 0; x < img.getWidth(); x++) {
					for (int y = 0; y < img.getHeight(); y++) {
						currDiff = ColorUtils.calculateHexDiff(img.getPixel(x, y), cols[c]);
						if (currDiff < minDiffs[x][y]) {
							minDiffs[x][y] = currDiff;
							zuordnung[x][y] = c;
						}
					}
				}
			}

			// Neuzuordnung
			int[][] recalc = new int[cols.length][5];
			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {
					int c = zuordnung[x][y];
					int[] cSplit = ColorUtils.channelsFromInt(img
							.getPixel(x, y));
					recalc[c][0] += cSplit[0];
					recalc[c][1] += cSplit[1];
					recalc[c][2] += cSplit[2];
					recalc[c][3] += cSplit[3];
					recalc[c][4]++;
				}
			}
			for (int c = 0; c < cols.length; c++) {
				if (recalc[c][4] != 0) {
					recalc[c][0] = recalc[c][0] / recalc[c][4];
					recalc[c][1] = recalc[c][1] / recalc[c][4];
					recalc[c][2] = recalc[c][2] / recalc[c][4];
					recalc[c][3] = recalc[c][3] / recalc[c][4];
					cols[c] = ColorUtils.intFromChannels(new int[] {
							recalc[c][0], recalc[c][1], recalc[c][2],
							recalc[c][3] });
				} else {
					cols[c] = ColorUtils.getRandomColor();
				}
			}
		}

		ArrayList<Integer> colors = new ArrayList<Integer>();
		for (int c : cols) {
			colors.add(c);
		}
		return colors;
	}
	
	public static double calculateDifference(Pixmap map, Pixmap target) {
		double diff = 0;
		for (int x = 0; x < target.getWidth(); x += 2) {
			for (int y = 0; y < target.getHeight(); y += 2) {
				diff += ColorUtils.calculateHexDiff(map.getPixel(x, y),
						target.getPixel(x, y));
			}
		}

		return diff;
	}

}
