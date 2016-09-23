package com.desitum.zagg;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kody on 12/20/15.
 * can be used by kody and people in [kody}]
 */
public class Assets {

    public static Texture leftFlag;
    public static Texture rightFlag;

    public static Texture playButton;
    public static Texture shareButton;
    public static Texture settingsButton;

    public static Texture particle;

    public static void load() {
        leftFlag = new Texture("wall_left.png");
        rightFlag = new Texture("wall_right.png");

        playButton = new Texture("play.png");
        settingsButton = new Texture("settings.png");
        shareButton = new Texture("share.png");

        particle = new Texture("particle.png");
    }

    public static void dispose() {
        leftFlag.dispose();
        leftFlag.dispose();
        rightFlag.dispose();
        playButton.dispose();
        shareButton.dispose();
        settingsButton.dispose();
        particle.dispose();
    }
}
