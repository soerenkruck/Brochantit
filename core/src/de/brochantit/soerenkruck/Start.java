package de.brochantit.soerenkruck;

import com.badlogic.gdx.Gdx;

public class Start extends com.badlogic.gdx.Game {

	String name;

	public Start (String name) {
		this.name = name;
	}

	@Override
	public void create() {
		((Start) Gdx.app.getApplicationListener()).setScreen(new Splash(name));
	}
}


