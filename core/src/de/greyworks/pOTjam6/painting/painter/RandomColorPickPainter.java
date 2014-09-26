package de.greyworks.pOTjam6.painting.painter;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;

import de.greyworks.pOTjam6.painting.ColorUtils;

public class RandomColorPickPainter implements Painter {

	@Override
	public Pixmap paint(Pixmap canvas, Pixmap target, ArrayList<Integer> colors) {
		int x = (int) (Math.random() * 370 + 15);
		int y = (int) (Math.random() * 570 + 15);
		int r = (int) (Math.random() * 10 + 5);
		int pixColor = target.getPixel(x, y);
		double colorDiff = Double.MAX_VALUE;
		double tmpDiff;
		int color = -1;
		for (int c : colors) {
			tmpDiff = ColorUtils.calculateHexDiff(c, pixColor);
			if(tmpDiff < colorDiff) {
				colorDiff = tmpDiff;
				color = c;
			}
		}
		canvas.setColor(color);
		canvas.fillCircle(x, y, r);

		return canvas;
	}

	@Override
	public String getName() {
		return "Zufällige Punkte mit erkannten Farben";
	}

}
