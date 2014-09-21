package de.greyworks.pOTjam6.painting.painter;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class RandomPainter implements Painter {

	@Override
	public Pixmap paint(Pixmap canvas, Pixmap target, ArrayList<Color> colors) {
		canvas.setColor(colors.get((int) Math.floor((Math.random() * colors
				.size()))));
		int x = (int) (Math.random() * 380 + 10);
		int y = (int) (Math.random() * 580 + 10);
		int r = (int) (Math.random() * 10 + 5);
		canvas.fillCircle(x, y, r);
		return canvas;
	}

	


}
