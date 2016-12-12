package com.desitum.zagg.game_objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.desitum.library.animation.ScaleAnimator;
import com.desitum.library.interpolation.Interpolation;
import com.desitum.library.math.CollisionDetection;
import com.desitum.zagg.Assets;
import com.desitum.zagg.ZaggGameScreen;

import java.util.Random;

/**
 * Created by kody on 12/23/15.
 * can be used by kody and people in [kody}]
 */
public class ColorChanger extends Sprite {

    public static final float SIZE = 7.5f;

    private ZaggGameScreen world;
    private float speed;
    private Color color;
    private boolean beenUsed;
    private ScaleAnimator scaleAnimation;

    private static final Color BLUE = Color.valueOf("#0023dcff");
    private static final Color RED = Color.valueOf("#dc0000ff");
    private static final Color GREEN = Color.valueOf("#00dc00ff");
    private static final Color ORANGE = Color.valueOf("#ff8707ff");
    private static final Color PURPLE = Color.valueOf("#dc00c6ff");
    private static final Color YELLOW = Color.valueOf("#fafd27ff");

    public ColorChanger (float middlex, float y, float speed, ZaggGameScreen world) {
        super(Assets.particle);
        setSize(SIZE, SIZE);
        setPosition(middlex - SIZE/2, y);
        this.speed = speed;
        this.world = world;
        this.beenUsed = false;

        setOrigin(getWidth()/2, getHeight()/2);

        color = chooseColor();
        setColor(color);
    }

    public void update(float delta) {
        setY(getY() - speed * delta);
        if (CollisionDetection.overlapRectangles(world.getDiamond().getBoundingRectangle(), getBoundingRectangle())) {
            world.changeColor(color);
            beenUsed = true;
        }
        if (scaleAnimation != null) {
            scaleAnimation.update(delta);
        }
    }

    public boolean hasBeenUsed() {
        return beenUsed;
    }

    public static Color chooseColor() {
        Random random = new Random();
        switch (random.nextInt(6)) {
            case 0:
                return BLUE;
            case 1:
                return RED;
            case 2:
                return GREEN;
            case 3:
                return ORANGE;
            case 4:
                return PURPLE;
            case 5:
                return YELLOW;
        }
        return BLUE;
    }

    public void shrink() {
        scaleAnimation = new ScaleAnimator(this, 0.5f, 0, 1, 0, Interpolation.ANTICIPATE_INTERPOLATOR, true, true);
        scaleAnimation.start(false);
        speed = 0;
    }
}
