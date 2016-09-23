package com.desitum.zagg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.desitum.zagg.googleplay.GooglePlayInterface;

public class ZaggGame extends Game {

    public ZaggGame(GooglePlayInterface googlePlayInterface) {
    }

    @Override
    public void create() {
        Assets.load();
        Screen gameScreen = new GameScreen(this);
        this.setScreen(gameScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();

        Assets.dispose();

        getScreen().dispose();
    }
}
