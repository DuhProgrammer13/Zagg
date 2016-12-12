package com.desitum.zagg.game_objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.interpolation.Interpolation;
import com.desitum.library.math.CollisionDetection;
import com.desitum.zagg.Assets;
import com.desitum.zagg.ZaggGameScreen;

/**
 * Created by kody on 12/19/15.
 * can be used by kody and people in [kody}]
 */
public class Wall extends GameObject {

    public static final int RIGHT = 0;
    public static final int LEFT = 1;

    public static final float WIDTH = 100;
    public static final float HEIGHT = 10;

    private int direction;
    private Polygon polygon;

    private MovementAnimator animIn;
    private MovementAnimator animOut;

    public Wall(float x, float y, int direction) {
        super(((direction == RIGHT) ? Assets.leftFlag : Assets.rightFlag));
        setSize(WIDTH, HEIGHT);
        this.direction = direction;

        float[] vertices = getMyVertices();
        polygon = new Polygon(vertices);

        if (direction == RIGHT) {
            setX(-100);
            animIn = new MovementAnimator(this, -WIDTH, x, 0.5f, 0, Interpolation.OVERSHOOT_INTERPOLATOR, true, false);
            animIn.start(false);
            animOut = new MovementAnimator(this, x, -WIDTH, 0.5f, 0, Interpolation.ANTICIPATE_INTERPOLATOR, true, false);
        } else {
            setX(100);
            animIn = new MovementAnimator(this, ZaggGameScreen.SCREEN_WIDTH, x, 0.5f, 0, Interpolation.OVERSHOOT_INTERPOLATOR, true, false);
            animIn.start(false);
            animOut = new MovementAnimator(this, x, ZaggGameScreen.SCREEN_WIDTH, 0.5f, 0, Interpolation.ANTICIPATE_INTERPOLATOR, true, false);
        }
    }

    public void update(float delta) {
        animIn.update(delta);
        animOut.update(delta);

        if (direction == RIGHT) {
            polygon.setPosition(getX(), getY());
        } else {
            polygon.setPosition(getX() - HEIGHT / 2, getY());
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        System.out.println("WTF");
    }

    public boolean checkCollision(Diamond diamond) {
        if (CollisionDetection.overlapRectangles(getBoundingRectangle(), diamond.getBoundingRectangle())) {
            if (polygon.contains(diamond.getX() + diamond.getWidth() / 2, diamond.getY() + diamond.getHeight() / 2)) {
                return true;
            }
        }
        return false;
    }

    public float[] getMyVertices() {
        float[] returnVertices = null;
        float x = 0;
        float y = 0;
        if (direction == RIGHT) {
            returnVertices =
                    new float[]{x, y,
                            x + WIDTH, y,
                            x + WIDTH + HEIGHT / 2, y + HEIGHT / 2,
                            x + WIDTH, y + HEIGHT,
                            x, y + HEIGHT};
        } else {
            returnVertices =
                    new float[]{x, y + HEIGHT / 2,
                            x + HEIGHT / 2, y,
                            x + WIDTH, y,
                            x + WIDTH, y + HEIGHT,
                            x + HEIGHT / 2, y + HEIGHT};
        }
        return returnVertices;
    }

    public void moveIn() {
        if (!animIn.didFinish() && !animIn.isRunning()) animIn.start(true);
    }

    public void moveOut() {
        if (!animOut.didFinish() && !animOut.isRunning()) animOut.start(false);
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void dispose() {

    }
}
