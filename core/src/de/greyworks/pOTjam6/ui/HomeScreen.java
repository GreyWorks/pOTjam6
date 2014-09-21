package de.greyworks.pOTjam6.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.greyworks.pOTjam6.painting.MultiThreadPainterManager;
import de.greyworks.pOTjam6.painting.SingleThreadPainterManager;

public class HomeScreen implements Screen {
	Texture bg = new Texture("pika.jpg");
	MultiThreadPainterManager pMan = new MultiThreadPainterManager("girl0.jpg",
			4, 8);
//	SingleThreadPainterManager pMan = new SingleThreadPainterManager();

	public HomeScreen() {
		pMan.newCanvas();
		pMan.paint();
	}

	@Override
	public void render(SpriteBatch batch) {
		// batch.draw(bg, 0, 0, 1280, 800);
		batch.draw(pMan.getTexture(), 800, 100);
	}

	@Override
	public void click(Vector2 pos) {
		// TODO Auto-generated method stub

	}

}
