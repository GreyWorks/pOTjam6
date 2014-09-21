package de.greyworks.pOTjam6;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.greyworks.pOTjam6.ui.HomeScreen;
import de.greyworks.pOTjam6.ui.ScreenManager;

public class BruteForce extends ApplicationAdapter {
	SpriteBatch batch;
	ScreenManager screenMan = new ScreenManager();
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		screenMan.addScreen("home", new HomeScreen());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		screenMan.render(batch);
		batch.end();
	}
}
