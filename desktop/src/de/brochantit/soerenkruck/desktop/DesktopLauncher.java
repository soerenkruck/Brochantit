package de.brochantit.soerenkruck.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.brochantit.soerenkruck.Start;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		System.out.println("ARGS: " + arg);

		String name;

		if (arg != null) {
			config.width = Integer.valueOf(arg[0]);
			config.height = Integer.parseInt(arg[1]);
			config.fullscreen = Boolean.valueOf(arg[2]);

			name = arg[3];
		} else {
			config.width = 960;
			config.height = 720;
			config.fullscreen = false;

			name = "Otis";
		}

		config.samples = 8;
		config.vSyncEnabled = true;

		config.title = "Brochantit";

		new LwjglApplication(new Start(name), config);
	}
}
