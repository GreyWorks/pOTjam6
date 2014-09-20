package de.greyworks.pOTjam6.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface Screen {
	public void render(SpriteBatch batch);
	public void click(Vector2 pos);
}
