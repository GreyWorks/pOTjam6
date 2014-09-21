package de.greyworks.pOTjam6.painting.painter;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import de.greyworks.pOTjam6.painting.ColorUtils;

public class RandomColorPickPainter implements Painter {

	@Override
	public Pixmap paint(Pixmap canvas, Pixmap target, ArrayList<Color> colors) {
		int x = (int) (Math.random() * 380 + 10);
		int y = (int) (Math.random() * 580 + 10);
		int r = (int) (Math.random() * 10 + 5);
		int pixColor = target.getPixel(x, y);
		int color;
		double colorDiff = Double.MAX_VALUE;
		double tmpDiff;
		int colIdx = -1;
		for (Color c : colors) {
			color = c.toIntBits();
			tmpDiff = ColorUtils.calculateHexDiff(color, pixColor);
			if(tmpDiff < colorDiff) {
				colorDiff = tmpDiff;
				colIdx = colors.indexOf(c);
			}
		}
		canvas.setColor(colors.get(colIdx));
		canvas.fillCircle(x, y, r);

		return canvas;
	}

}
