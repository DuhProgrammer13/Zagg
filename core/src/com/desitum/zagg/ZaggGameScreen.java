package com.desitum.zagg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.desitum.library.drawing.Drawing;
import com.desitum.library.widgets.Button;
import com.desitum.library.widgets.LinearLayout;
import com.desitum.library.widgets.Widget;
import com.desitum.zagg.game_objects.ColorChanger;
import com.desitum.zagg.game_objects.Diamond;
import com.desitum.zagg.game_objects.ParticleManager;
import com.desitum.zagg.game_objects.Wall;
import com.desitum.zagg.game_objects.WallManager;
import com.desitum.zagg.library.Label;

import java.util.ArrayList;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class ZaggGameScreen extends com.desitum.library.game.GameScreen {

    public static final float SCREEN_WIDTH = 100.0f;
    public static final float SCREEN_HEIGHT = 150.0f;

    private static final String PLAY_BUTTON = "play";
    private static final String SETTINGS_BUTTON = "settings";
    private static final String SHARE_BUTTON = "share";
    private static final float DIAMOND_SIZE = 10;
    public static final float GAME_SPEED = 30;
    private Button playButton, settingsButton, shareButton;
    private Label label;
    private Diamond diamond;
    private WallManager wallManager;
    private Color themeColor;
    private ParticleManager particleManager;

    private int mode;

    private static final int GAME = 0;
    private static final int MENU = 1;
    private static final int GAME_OVER = 2;

    private int points;

    private OrthographicCamera cam;

    private ArrayList<Widget> widgets;


    public ZaggGameScreen(ZaggGame game) {
        super(SCREEN_WIDTH, SCREEN_HEIGHT);
        getWorld().addGameObject(new Wall(10, 10, Wall.RIGHT));

        buildMenu();
        wallManager = new WallManager(this);

        moveMenuIn();

        themeColor = ColorChanger.chooseColor();
        this.particleManager = new ParticleManager(this);

        mode = MENU;

//        spriteBatch = new SpriteBatch();
//        cam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
//        cam.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
//        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, cam);
//
//        mousePos = new Vector3(0, 0, 0);
//
//        world = new World(cam);
    }

    private void buildMenu() {
        playButton = new Button(new Texture("play.png"), PLAY_BUTTON, 70, 10, 100, 90, null);
        settingsButton = new Button(new Texture("settings.png"), SETTINGS_BUTTON, 70, 10, 100, 70, null);
        shareButton = new Button(new Texture("share.png"), SHARE_BUTTON, 70, 10, 100, 50, null);

        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/tech.fnt"), new TextureRegion(new Texture("fonts/tech.png")));

        label = new Label(Drawing.getFilledRectangle(1, 1, new Color(0, 0, 0, 0)), "topLabel", 100, 20, 0, 110,
                font, cam);

        label.setAlignment(LinearLayout.ALIGNMENT_CENTER);
        label.setText("Zagg");

        playButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);

        widgets.add(playButton);
        widgets.add(settingsButton);
        widgets.add(shareButton);
        widgets.add(label);

        diamond = new Diamond(DIAMOND_SIZE, ZaggGameScreen.SCREEN_WIDTH/2 - DIAMOND_SIZE/2, 10, GAME_SPEED, getWorld());
    }

//    @Override
//    public void show() {
//
//    }
//
//    private void update(float delta) {
//        mousePos = viewport.unproject(mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
//        world.updateTouchInput(mousePos, Gdx.input.isTouched());
//
//        world.update(delta);
//    }
//
//    @Override
//    public void render(float delta) {
//        update(delta);
//
//        draw();
//    }
//
//    public void draw() {
//        cam.update();
//        spriteBatch.setProjectionMatrix(cam.combined);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
//
//        spriteBatch.begin();
//        world.draw(spriteBatch, viewport);
//        spriteBatch.end();
//    }

//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//
//    }
}
