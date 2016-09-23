package com.desitum.zagg.game_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.desitum.library.animation.Animator;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.animation.OnAnimationFinishedListener;
import com.desitum.library.interpolation.Interpolation;
import com.desitum.zagg.GameScreen;
import com.desitum.zagg.world.World;


/**
 * Created by kody on 12/19/15.
 * can be used by kody and people in [kody}]
 */
public class Diamond extends Sprite implements OnAnimationFinishedListener {

    public static final int RIGHT = 0;
    public static final int LEFT = 1;

    private MovementAnimator animateIn;
    private MovementAnimator animateOut;
    private int direction;
    private float speed;
    private World world;
    private boolean moving = false;

    public Diamond(float size, float x, float y, float speed, World world) {
        super(new Texture("diamond.png"));
        setY(0 - size);
        setSize(size, size);
        setX(x);
        animateIn = new MovementAnimator(this, getY(), y, 0.5f, 0.5f, Interpolation.DECELERATE_INTERPOLATOR, false, true);
        animateOut = new MovementAnimator(this, y, 0 - size, 0.5f, 0f, Interpolation.ACCELERATE_INTERPOLATOR, false, true);
        animateOut.setOnFinishedListener(this);

        this.speed = speed;
        this.world = world;

        direction = RIGHT;
    }

    public void update(float delta) {
        animateIn.update(delta);
        animateOut.update(delta);
        if (moving) {
            switch (direction) {
                case RIGHT:
                    this.setX(getX() + speed * delta);
                    if (getX() + getWidth() >= GameScreen.SCREEN_WIDTH) {
                        direction = LEFT;
                        this.setX(getX() - speed * delta);
                    }
                    break;
                case LEFT:
                    this.setX(getX() - speed * delta);
                    if (getX() <= 0) {
                        direction = RIGHT;
                        this.setX(getX() + speed * delta);
                    }
                    break;
            }
        }
    }

    public void moveIn() {
        moving = true;
        this.animateIn.start(false);
    }

    public void fallOut() {
        moving = false;
        this.animateOut.start(false);
    }

    public void switchDirections() {
        direction = direction == RIGHT ? LEFT : RIGHT;
    }

    @Override
    public void onAnimationFinished(Animator animator) {
        if (animator.equals(animateOut)) {
            world.moveMenuIn();
        }
    }
}
