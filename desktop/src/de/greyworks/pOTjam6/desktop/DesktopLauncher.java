package de.greyworks.pOTjam6.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.greyworks.pOTjam6.BruteForce;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Art Of Brute Force";
		config.width = 1280;
		config.height = 800;
		config.foregroundFPS = 30;
		new LwjglApplication(new BruteForce(), config);
	}
}
