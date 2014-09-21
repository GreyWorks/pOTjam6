package de.greyworks.pOTjam6.painting;

import com.badlogic.gdx.graphics.Texture;

public interface PainterManager {
	
	public void newCanvas();
	public void paint();
	public void stop();
	public Texture getTexture();

	
}
