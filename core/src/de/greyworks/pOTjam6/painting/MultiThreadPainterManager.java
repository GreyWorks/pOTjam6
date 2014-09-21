package de.greyworks.pOTjam6.painting;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import de.greyworks.pOTjam6.painting.painter.Painter;
import de.greyworks.pOTjam6.painting.painter.RandomColorPickPainter;

public class MultiThreadPainterManager implements PainterManager {
	Pixmap canvas = new Pixmap(400, 600, Pixmap.Format.RGBA8888);
	Pixmap target = new Pixmap(400, 600, Pixmap.Format.RGBA8888);
	Texture tex = new Texture(canvas);
	ArrayList<Integer> colors = new ArrayList<Integer>();
	double lastDiff = Double.MAX_VALUE;
	Painter p = new RandomColorPickPainter();
	boolean paint = false;

	public MultiThreadPainterManager(String fileName, int threads, int numCol) {
		Pixmap input = new Pixmap(Gdx.files.internal(fileName));
		target.drawPixmap(input, 0, 0);
		input.dispose();
		
		canvas.setColor(Color.WHITE);
		canvas.fill();

		colors = ColorUtils.kMeans(target, numCol, 32);
	}

	@Override
	public void newCanvas() {

	}

	@Override
	public void paint() {
		paint = true;
		new Thread(runner).start();
	}

	@Override
	public void stop() {
		paint = false;
	}

	@Override
	public Texture getTexture() {
		tex.draw(canvas, 0, 0);
		return tex;
	}

	private Runnable runner = new Runnable() {

		@Override
		public void run() {
			int concThreads = 9;
			ExecutorService exec;
			ArrayList<PaintRunner> runners = new ArrayList<PaintRunner>();
			while (paint) {
				exec = Executors.newCachedThreadPool();
				for (int i = 0; i < concThreads; i++) {
					PaintRunner r = new PaintRunner(canvas, target, colors,
							p);
					runners.add(r);
					exec.execute(r);
				}
				exec.shutdown();
				try {
					exec.awaitTermination(2, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				double smallestDiff = Double.MAX_VALUE;
				int smallest = -1;
				for (PaintRunner r : runners) {
					if (r.diff < smallestDiff) {
						smallestDiff = r.diff;
						smallest = runners.indexOf(r);
					}
				}

				PaintRunner smRun = runners.get(smallest);
				if (smRun.diff < lastDiff) {
					canvas.drawPixmap(smRun.canvas, 0, 0);
					lastDiff = smRun.diff;
				}

				for (PaintRunner r : runners) {
					r.disposeCanvas();
				}
				runners.clear();

			}

		}
	};

}
