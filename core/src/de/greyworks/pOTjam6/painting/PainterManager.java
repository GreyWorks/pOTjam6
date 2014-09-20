package de.greyworks.pOTjam6.painting;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class PainterManager {
	Pixmap canvas = new Pixmap(400, 600, Pixmap.Format.RGBA8888);
	Pixmap input = new Pixmap(Gdx.files.internal("babe.jpg"));
	Pixmap target = new Pixmap(400, 600, Pixmap.Format.RGBA8888);
	Texture tex = new Texture(canvas);

	ArrayList<Color> colors = new ArrayList<Color>();
	double lastDiff = Double.MAX_VALUE;

	boolean paint = false;

	public PainterManager() {
		// initialize target with right bit depth & release input
		target.drawPixmap(input, 0, 0);
		input.dispose();
		// calculate colors
		colors = kMeans(target, 8, 150);
		// start painter
		paint = true;

	}

	public void newCanvas() {
		canvas = new Pixmap(400, 600, Pixmap.Format.RGBA8888);

		System.out.println(target.getFormat().toString());
		canvas.setColor(1f, 1f, 1f, 1f);
		canvas.fill();
	}

	public void paint(Pixmap canvas) {

		canvas.setColor(colors.get((int) Math.floor((Math.random() * colors
				.size()))));
		int x = (int) (Math.random() * 380 + 10);
		int y = (int) (Math.random() * 580 + 10);
		int r = (int) (Math.random() * 10 + 5);
		canvas.fillCircle(x, y, r);

	}

	public Texture getTexture() {
		if (paint) {
			Pixmap n = new Pixmap(canvas.getWidth(), canvas.getHeight(),
					canvas.getFormat());
			n.drawPixmap(canvas, 0, 0);

			paint(n);
			double diff = calculateDifference(n);
			if (diff < this.lastDiff) {
				this.lastDiff = diff;
				canvas.dispose();
				canvas = n;
				// Statics.log("d", "Difference: " + diff);

			} else {
				n.dispose();
			}
		}
		tex.draw(canvas, 0, 0);
		return tex;
	}

	public double calculateDifference(Pixmap map) {
		double diff = 0;
		for (int x = 0; x < target.getWidth(); x += 2) {
			for (int y = 0; y < target.getHeight(); y += 2) {
				diff += ColorUtils.calculateHexDiff(map.getPixel(x, y),
						target.getPixel(x, y));
			}
		}

		return diff;
	}

	public ArrayList<Color> kMeans(Pixmap img, int num, int iterations) {
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

		ArrayList<Color> colors = new ArrayList<Color>();
		for (int c : cols) {
			colors.add(new Color(c));
		}
		return colors;
	}
}
