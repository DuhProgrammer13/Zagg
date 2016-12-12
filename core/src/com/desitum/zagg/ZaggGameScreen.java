package com.desitum.zagg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.animation.ColorEffects;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.drawing.Drawing;
import com.desitum.library.game.World;
import com.desitum.library.game.WorldRenderer;
import com.desitum.library.interpolation.Interpolation;
import com.desitum.library.listener.OnClickListener;
import com.desitum.library.widgets.Button;
import com.desitum.library.widgets.LinearLayout;
import com.desitum.library.widgets.Widget;
import com.desitum.zagg.game_objects.ColorChanger;
import com.desitum.zagg.game_objects.Diamond;
import com.desitum.zagg.game_objects.ParticleManager;
import com.desitum.zagg.game_objects.Wall;
import com.desitum.zagg.game_objects.WallManager;
import com.desitum.zagg.game_objects.WallSet;
import com.desitum.zagg.library.Label;

import java.util.ArrayList;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class ZaggGameScreen extends com.desitum.library.game.GameScreen implements OnClickListener {

    private ColorEffects colorEffects;

    public static final float SCREEN_WIDTH = 100.0f;
    public static final float SCREEN_HEIGHT = 150.0f;

    private static final String PLAY_BUTTON = "play";
    private static final String SETTINGS_BUTTON = "settings";
    private static final String SHARE_BUTTON = "share";
    private static final float DIAMOND_SIZE = 10;
    public static final float GAME_SPEED = 15;
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

    private ArrayList<Widget> widgets;


    public ZaggGameScreen(ZaggGame game) {
        super(SCREEN_WIDTH, SCREEN_HEIGHT);

        setupWorld();
        setupWorldRenderer();

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
//        world = new ZaggGameScreen(cam);
    }

    private void setupWorld() {
        setWorld(new World(getCam(), getViewport()) {
            @Override
            public void update(float delta) {
                super.update(delta);
                playButton.update(delta);
                shareButton.update(delta);
                if (colorEffects != null) {
                    colorEffects.update(delta);
                    if (colorEffects.isRunning()) themeColor.set(colorEffects.getCurrentColor());
                }
                if (mode == GAME) label.setText(points + "");
                if (mode == MENU) label.setText("Zagg");
                if (mode == GAME_OVER) label.setText(points + "");
                diamond.update(delta);
                wallManager.update(delta);
                particleManager.update(delta);
            }

            @Override
            public void updateTouchInput(Vector3 touchPos, boolean touchDown) {
                super.updateTouchInput(touchPos, touchDown);

                if (Gdx.input.justTouched()) {
                    diamond.switchDirections();
                }
            }
        });
    }

    private void setupWorldRenderer() {
        setWorldRenderer(new WorldRenderer(getWorld()) {
            @Override
            public void draw(Batch batch) {
                super.draw(batch);

                wallManager.draw(batch);
                particleManager.draw(batch);
                diamond.draw(batch);
                for (Widget widget: widgets) {
                    widget.draw(batch, getViewport());
                }
            }
        });
    }

//    @Override
//    public void draw() {
//        System.out.println("Start draw");
//        super.draw();
//        System.out.println("End draw");
//    }

    private void buildMenu() {
        widgets = new ArrayList<Widget>();
        playButton = new Button(new Texture("play.png"), PLAY_BUTTON, 70, 10, 90, 90, null);
        getWorld().addWidget(playButton);
        settingsButton = new Button(new Texture("settings.png"), SETTINGS_BUTTON, 70, 10, 90, 70, null);
        getWorld().addWidget(settingsButton);
        shareButton = new Button(new Texture("share.png"), SHARE_BUTTON, 70, 10, 90, 50, null);
        getWorld().addWidget(shareButton);

        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/tech.fnt"), new TextureRegion(new Texture("fonts/tech.png")));

        label = new Label(Drawing.getFilledRectangle(1, 1, new Color(0, 0, 0, 0)), "topLabel", 100, 20, 0, 110,
                font);

        label.setAlignment(LinearLayout.ALIGNMENT_CENTER);
        label.setText("Zagg");

        playButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);

        widgets.add(playButton);
        widgets.add(settingsButton);
        widgets.add(shareButton);
        widgets.add(label);

        diamond = new Diamond(DIAMOND_SIZE, ZaggGameScreen.SCREEN_WIDTH/2 - DIAMOND_SIZE/2, 10, GAME_SPEED, this);
    }

    public void moveMenuIn() {
        MovementAnimator playAnimator = new MovementAnimator(playButton, 100, 30, 0.5f, 0, Interpolation.DECELERATE_INTERPOLATOR, true, false);
        MovementAnimator settingsAnimator = new MovementAnimator(settingsButton, 100, 30, 0.5f, 0.2f, Interpolation.DECELERATE_INTERPOLATOR, true, false);
        MovementAnimator shareAnimator = new MovementAnimator(shareButton, 100, 30, 0.5f, 0.4f, Interpolation.DECELERATE_INTERPOLATOR, true, false);
        playButton.startAnimator(playAnimator);
        settingsButton.startAnimator(settingsAnimator);
        shareButton.startAnimator(shareAnimator);
    }

    private void moveMenuOut() {MovementAnimator playAnimatorOut = new MovementAnimator(playButton, 30, 110, 0.5f, 0, Interpolation.ACCELERATE_INTERPOLATOR, true, false);
        MovementAnimator settingsAnimatorOut = new MovementAnimator(playButton, 30, 110, 0.5f, 0, Interpolation.ACCELERATE_INTERPOLATOR, true, false);
        MovementAnimator shareAnimatorOut = new MovementAnimator(playButton, 30, 110, 0.5f, 0, Interpolation.ACCELERATE_INTERPOLATOR, true, false);
        playButton.startAnimator(playAnimatorOut);
        settingsButton.startAnimator(settingsAnimatorOut);
        shareButton.startAnimator(shareAnimatorOut);
    }

    private void startGame() {
        mode = GAME;
        points = 0;
        diamond.moveIn();
        wallManager.start();
    }

    public Color getThemeColor() {
        return themeColor;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (this.colorEffects != null) {
            this.colorEffects.update(delta);
            if (colorEffects.isRunning()) themeColor.set(colorEffects.getCurrentColor());
        }
        if (mode == GAME) label.setText(points + "");
        if (mode == MENU) label.setText("Zagg");
        if (mode == GAME_OVER) label.setText(points + "");
        diamond.update(delta);
        wallManager.update(delta);
//        for (Widget widget: widgets) {
//            widget.update(delta);
//        }
        particleManager.update(delta);
    }

//    public void draw(SpriteBatch batch) {
//        wallManager.draw(batch);
//        particleManager.draw(batch);
//        diamond.draw(batch);
//        for (Widget widget: widgets) {
//            widget.draw(batch);
//        }
//    }

    public void explodeWallSet(WallSet wallSet) {
        particleManager.explodeWall(wallSet);
    }


    public void updateTouchInput(Vector3 mousePos, boolean touchDown) {

        if (Gdx.input.justTouched()) {
            diamond.switchDirections();
        }
    }

    public ArrayList<Widget> getWidgets() {
        return widgets;
    }

    @Override
    public void onClickDown(Widget widget) {

    }

    @Override
    public void onClick(Widget widget) {
        if (widget.getName().equals(PLAY_BUTTON)) {
            moveMenuOut();
            startGame();
        } else if (widget.getName().equals(SETTINGS_BUTTON)) {
            moveMenuOut();
            moveSettingsIn();
        } else if (widget.getName().equals(SHARE_BUTTON)) {
            share();
        }
    }

    private void moveSettingsIn() {

    }

    private void share() {

    }

    public void gameOver() {
        wallManager.stop();
        diamond.fallOut();
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public Diamond getDiamond() {
        return diamond;
    }

    public void changeColor(Color color) {
        colorEffects = new ColorEffects(themeColor, color, 0.5f);
        colorEffects.start(false);
    }

    public void dispose() {
        //particleManager.dispose();
    }

    public int getPoints() {
        return points;
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
