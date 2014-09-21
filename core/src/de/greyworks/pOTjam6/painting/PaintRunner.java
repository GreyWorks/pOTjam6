package de.greyworks.pOTjam6.painting;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;

import de.greyworks.pOTjam6.painting.painter.Painter;

public class PaintRunner implements Runnable {
	Pixmap canvas, target;
	ArrayList<Integer> colors;
	Painter painter;
	public boolean done = false;
	public double diff = Double.MAX_VALUE;

	public PaintRunner(Pixmap canvas, Pixmap target, ArrayList<Integer> colors,
			Painter painter) {
		this.canvas = new Pixmap(canvas.getWidth(), canvas.getHeight(), canvas.getFormat());
		this.canvas.drawPixmap(canvas, 0, 0);
		this.target = target;
		this.colors = colors;
		this.painter = painter;
	}


	@Override
	public void run() {
		painter.paint(canvas, target, colors);
		diff = ColorUtils.calculateDifference(canvas, target);
		done = true;
	}
	
	public void disposeCanvas() {
		canvas.dispose();
	}
	


}
