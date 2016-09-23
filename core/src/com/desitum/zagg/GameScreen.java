package com.desitum.zagg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.zagg.world.World;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class GameScreen implements Screen {

    public static final float SCREEN_WIDTH = 100.0f;
    public static final float SCREEN_HEIGHT = 150.0f;

    private OrthographicCamera cam;
    private Viewport viewport;

    private SpriteBatch spriteBatch;

    ZaggGame game;

    Vector3 mousePos;

    World world;

    public GameScreen(ZaggGame game) {
        this.game = game;

        spriteBatch = new SpriteBatch();
        cam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        cam.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, cam);

        mousePos = new Vector3(0, 0, 0);

        world = new World(cam);
    }

    @Override
    public void show() {

    }

    private void update(float delta) {
        mousePos = viewport.unproject(mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        world.updateTouchInput(mousePos, Gdx.input.isTouched());

        world.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);

        draw();
    }

    public void draw() {
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        spriteBatch.begin();
        world.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
