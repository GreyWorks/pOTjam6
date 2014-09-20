package de.greyworks.pOTjam6.ui;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.greyworks.pOTjam6.Statics;

public class ScreenManager {

	HashMap<String, Screen> screens = new HashMap<String, Screen>();
	String currentScreen;

	public void addScreen(String name, Screen screen) {
		screens.put(name, screen);
		if(!screens.containsKey(currentScreen)) {
			Statics.log("d", "Screen automatically set to: " + name);
			currentScreen = name;
		}
	}

	public Screen getScreen(String name) {
		if (screens.containsKey(name)) {
			return screens.get(name);
		} else {
			Statics.log("e", "No such screen: " + name);
			return null;
		}
	}

	public void setActive(String name) {
		if (screens.containsKey(name)) {
			currentScreen = name;
		} else {
			Statics.log("e", "No such screen in manager: " + name);
		}
	}

	public Screen getCurrentScreen() {
		if (screens.containsKey(currentScreen)) {
			return screens.get(currentScreen);
		} else {
			Statics.log("e", "No current Screen set");
			return null;
		}
	}
	
	public void render(SpriteBatch batch) {
		getCurrentScreen().render(batch);
	}
	
	public void click(Vector2 pos) {
		getCurrentScreen().click(pos);
	}

}
