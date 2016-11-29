package com.desitum.zagg.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.animation.ColorEffects;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.drawing.Drawing;
import com.desitum.library.interpolation.Interpolation;
import com.desitum.library.listener.OnClickListener;
import com.desitum.library.widgets.Button;
import com.desitum.library.widgets.LinearLayout;
import com.desitum.library.widgets.Widget;
import com.desitum.zagg.GameScreen;
import com.desitum.zagg.game_objects.ColorChanger;
import com.desitum.zagg.game_objects.Diamond;
import com.desitum.zagg.game_objects.ParticleManager;
import com.desitum.zagg.game_objects.WallManager;
import com.desitum.zagg.game_objects.WallSet;
import com.desitum.zagg.library.Label;

import java.util.ArrayList;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class World implements OnClickListener {

    private ColorEffects colorEffects;

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

    public World(OrthographicCamera cam) {
        widgets = new ArrayList<Widget>();
        this.cam = cam;

//        Widget widget = MenuBuilder.build(Gdx.files.internal("layout.json"), cam);
//        LinearLayout ll = (LinearLayout) widget.findByName("myLayout");

        buildMenu();
        wallManager = new WallManager(this);

        moveMenuIn();

        themeColor = ColorChanger.chooseColor();
        this.particleManager = new ParticleManager(this);

        mode = MENU;
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

        diamond = new Diamond(DIAMOND_SIZE, GameScreen.SCREEN_WIDTH/2 - DIAMOND_SIZE/2, 10, GAME_SPEED, this);
    }

    public void update(float delta) {
        if (this.colorEffects != null) {
            this.colorEffects.update(delta);
            if (colorEffects.isRunning()) themeColor.set(colorEffects.getCurrentColor());
        }
        if (mode == GAME) label.setText(points + "");
        if (mode == MENU) label.setText("Zagg");
        if (mode == GAME_OVER) label.setText(points + "");
        diamond.update(delta);
        wallManager.update(delta);
        for (Widget widget: widgets) {
            widget.update(delta);
        }
        particleManager.update(delta);
    }

    public void draw(SpriteBatch batch) {
        wallManager.draw(batch);
        particleManager.draw(batch);
        diamond.draw(batch);
        for (Widget widget: widgets) {
            widget.draw(batch);
        }
    }

    public void explodeWallSet(WallSet wallSet) {
        particleManager.explodeWall(wallSet);
    }

    public void setCam(OrthographicCamera cam){
        this.cam = cam;
    }

    public OrthographicCamera getCam() {
        return this.cam;
    }

    public void updateTouchInput(Vector3 mousePos, boolean touchDown) {
        for (Widget widget: widgets) {
            widget.updateTouchInput(mousePos, touchDown);
        }

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

    public Color getThemeColor() {
        return themeColor;
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
}
