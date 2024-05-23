package com.mygdx.game;


import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class  DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Scramble-Wicth");
		config.setWindowedMode(1000, 530);
		config.setWindowIcon("images/logo128.png", "images/logo32.png", "images/logo.png");
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
