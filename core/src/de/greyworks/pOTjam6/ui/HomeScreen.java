package de.greyworks.pOTjam6.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.greyworks.pOTjam6.painting.MultiThreadPainterManager;
import de.greyworks.pOTjam6.painting.painter.Painter;

public class HomeScreen implements Screen {
	Texture bg = new Texture("house1.png");
	Texture bgL = new Texture("button1.png");
	MultiThreadPainterManager pMan = new MultiThreadPainterManager("pika.jpg",
			4, 8);
	BitmapFont font = new BitmapFont();
	Label lDiff = new Label(bgL, font, new Vector2(820, 35), new Vector2(300,
			28));

	// SingleThreadPainterManager pMan = new SingleThreadPainterManager();

	public HomeScreen() {
		font.setColor(Color.BLACK);
		pMan.newCanvas();
		pMan.paint();
	}

	@Override
	public void render(SpriteBatch batch) {
		// batch.draw(bg, 0, 0, 1280, 800);
		batch.draw(bg, 0, 0);
		batch.draw(pMan.getTexture(), 800, 100);

		ArrayList<Painter> pnts = pMan.getPainters();
		for (Painter p : pnts) {
			final int idx = pnts.indexOf(p);
			Label l = new Label(bgL, font, new Vector2(10,
					30 + idx * 35), new Vector2(300, 28));
			if(pMan.getPainterIdx() == idx) {
				l.setText("> " + p.getName());	
			} else {
				l.setText(p.getName());
			}
			
			l.render(batch);
		}

		lDiff.setText("% Difference: " + pMan.getDiff());
		lDiff.render(batch);
	}

	@Override
	public void click(Vector2 pos) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		font.dispose();
	}

}
