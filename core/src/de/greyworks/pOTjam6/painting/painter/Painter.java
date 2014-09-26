package de.greyworks.pOTjam6.painting.painter;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;

public interface Painter {
	public String getName();
	public Pixmap paint(Pixmap canvas, Pixmap target, ArrayList<Integer> colors);

}
