package de.brochantit.soerenkruck.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.brochantit.soerenkruck.Start;
import org.lwjgl.Sys;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		System.out.println("ARGS: " + arg);

		//BufferedReader br;
		//try {
		//	FileReader fr = new FileReader("config/window.afd");
		//	br = new BufferedReader(fr);
		//
		//	String tmpWinStat = br.readLine().replace(";", "").replace(" ", "");
		//	String WinStat[] = tmpWinStat.split(",");
		//	config.width = Integer.valueOf(WinStat[0]);
		//	config.height = Integer.valueOf(WinStat[1]);
		//	config.fullscreen = Boolean.valueOf(br.readLine().replace(";", ""));
		//} catch (FileNotFoundException e) {
		//	e.printStackTrace();
		//
		//	config.width = 1280;
		//	config.height = 1024;
		//	config.fullscreen = false;
		//} catch (IOException e) {
		//	e.printStackTrace();
		//
		//	config.width = 1280;
		//	config.height = 1024;
		//	config.fullscreen = false;
		//}

		config.width = Integer.valueOf(arg[0]);
		config.height = Integer.parseInt(arg[1]);
		config.fullscreen = Boolean.valueOf(arg[2]);

		String name = arg[3];

		config.samples = 0;
		config.vSyncEnabled = true;

		config.title = "Brochantit";

		new LwjglApplication(new Start(name), config);
	}
}
