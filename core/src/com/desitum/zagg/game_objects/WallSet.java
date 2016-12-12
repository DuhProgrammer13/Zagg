package com.desitum.zagg.game_objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.desitum.zagg.Assets;
import com.desitum.zagg.GameScreen;
import com.desitum.zagg.world.World;

import java.util.ArrayList;

/**
 * Created by kody on 12/19/15.
 * can be used by kody and people in [kody}]
 */
public class WallSet {

    private ShapeRenderer shapeRenderer;

    public static final float SPREAD = 20;

    private ArrayList<Wall> walls;
    private float y, middleX;
    private float speed;
    private World world;
    private int points;

    public WallSet (float middlex, float y, float speed, World world) {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(world.getCam().combined);

        this.y = y;
        this.points = 1;
        this.speed = speed;
        this.world = world;
        walls = new ArrayList<Wall>();
        this.middleX = middlex;

//        Wall leftWall = new Wall(middlex - Wall.WIDTH - SPREAD/2, y, Wall.RIGHT);
//        Wall rightWall = new Wall(middlex + SPREAD/2, y, Wall.LEFT);
//        Wall rightWall = new Wall(Assets.leftFlag);

//        walls.add(leftWall);
//        walls.add(rightWall);
    }

    public void update(float delta) {
        if (getY() < GameScreen.SCREEN_HEIGHT) {
            for (Wall wall: walls) wall.moveIn();
        }

        if (world.getDiamond().getY() <= getY() && world.getDiamond().getY() + world.getDiamond().getHeight() >= getY()) {
            for (Wall wall: walls) {
                if (wall.checkCollision(world.getDiamond())) {
                    world.gameOver();
                }
            }
        }
        y -= speed * delta;

        for (Wall wall: walls) {
            wall.setY(y);
            wall.update(delta);
        }

        if (y <= 0) {
            explodeIntoParticles();
        }
    }

    private void explodeIntoParticles() {
        world.explodeWallSet(this);
    }

    public void draw(SpriteBatch batch) {
        for (Wall wall: walls) {
//            shapeRenderer.begin();
//            shapeRenderer.polygon(wall.getPolygon().getTransformedVertices());
//            shapeRenderer.end();
            wall.setColor(world.getThemeColor());
            wall.draw(batch);
        }
    }

    public float getY() {
        return y;
    }

    public float getMiddleX() {
        return middleX;
    }

    public int takePoints() {
        if (points > 0) {
            points = 0;
            return 1;
        }
        return 0;
    }

    public void moveOut() {
        for (Wall wall: walls) {
            wall.moveOut();
        }
        speed = 0;
    }
}
