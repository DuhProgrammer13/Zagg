package com.desitum.zagg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.desitum.zagg.ZaggGame;
import com.desitum.zagg.googleplay.GooglePlayInterface;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 400;
        config.height = 600;
        GooglePlayInterface playInterface = getPlayInterface();
		new LwjglApplication(new ZaggGame(playInterface), config);
	}

    public static GooglePlayInterface getPlayInterface() {
        return new GooglePlayInterface() {
        };
    }
}
