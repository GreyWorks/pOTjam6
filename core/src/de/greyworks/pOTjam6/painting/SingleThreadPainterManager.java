package de.greyworks.pOTjam6.painting;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import de.greyworks.pOTjam6.painting.painter.Painter;
import de.greyworks.pOTjam6.painting.painter.RandomPainter;

public class SingleThreadPainterManager implements PainterManager {
	Pixmap canvas = new Pixmap(400, 600, Pixmap.Format.RGBA8888);
	Pixmap input = new Pixmap(Gdx.files.internal("pika.jpg"));
	Pixmap target = new Pixmap(400, 600, Pixmap.Format.RGBA8888);
	Texture tex = new Texture(canvas);
	Painter p = new RandomPainter();
	
	ArrayList<Color> colors = new ArrayList<Color>();
	double lastDiff = Double.MAX_VALUE;

	boolean paint = false;

	public SingleThreadPainterManager() {
		// initialize target with right bit depth & release input
		target.drawPixmap(input, 0, 0);
		input.dispose();
		// calculate colors
		colors = ColorUtils.kMeans(target, 16, 150);
		// start painter
		paint = true;

	}

	public void newCanvas() {
		canvas = new Pixmap(400, 600, Pixmap.Format.RGBA8888);

		System.out.println(target.getFormat().toString());
		canvas.setColor(1f, 1f, 1f, 1f);
		canvas.fill();
	}

	public void paint() {
		paint = true;
	}
	
	public void stop() {
		paint = false;
	}

	public void paintIteration(Pixmap canvas) {

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

			p.paint(n, target, colors);
			double diff = ColorUtils.calculateDifference(n, target);
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

	

	
}
