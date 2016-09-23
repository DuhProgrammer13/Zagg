package com.desitum.zagg.game_objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.desitum.zagg.GameScreen;
import com.desitum.zagg.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by kody on 12/19/15.
 * can be used by kody and people in [kody}]
 */
public class WallManager {

    private ArrayList<WallSet> wallSets;
    private ArrayList<ColorChanger> colorChangers;
    private boolean produceWalls;
    private World world;
    private Random randomizer;

    public WallManager(World world) {
        this.world = world;
        wallSets = new ArrayList<WallSet>();
        colorChangers = new ArrayList<ColorChanger>();
        randomizer = new Random();
        produceWalls = false;
    }

    public void update(float delta) {
        Iterator<WallSet> iter = wallSets.iterator();
        while (iter.hasNext()) {
            WallSet wallSet = iter.next();
            wallSet.update(delta);
            if (wallSet.getY() + Wall.HEIGHT/2 <= world.getDiamond().getY() + world.getDiamond().getHeight()/2) {
                world.addPoints(wallSet.takePoints());
            }
            if (wallSet.getY() < 0) {
                iter.remove();
            }
        }

        if (wallSets.size() < 8 && produceWalls) {
            WallSet lastWallSet = wallSets.get(wallSets.size() - 1);
            if (lastWallSet.getMiddleX() < GameScreen.SCREEN_WIDTH - WallSet.SPREAD/2 && lastWallSet.getMiddleX() > WallSet.SPREAD/2) {
                wallSets.add(new WallSet(lastWallSet.getMiddleX() + (randomizer.nextBoolean() ? 20 : -20), lastWallSet.getY() + Wall.HEIGHT*2, World.GAME_SPEED, world));
            } else if (lastWallSet.getMiddleX() < GameScreen.SCREEN_WIDTH - WallSet.SPREAD/2) {
                wallSets.add(new WallSet(lastWallSet.getMiddleX() + (randomizer.nextBoolean() ? 20 : 0), lastWallSet.getY() + Wall.HEIGHT*2, World.GAME_SPEED, world));
            } else {
                wallSets.add(new WallSet(lastWallSet.getMiddleX() + (randomizer.nextBoolean() ? 0 : -20), lastWallSet.getY() + Wall.HEIGHT*2, World.GAME_SPEED, world));
            }
            if (world.getPoints() >= 10 && world.getPoints() % 10 == 0) {
                colorChangers.add(new ColorChanger(wallSets.get(wallSets.size() - 1).getMiddleX(), wallSets.get(wallSets.size() - 1).getY(), World.GAME_SPEED, world));
            }
        }

        Iterator<ColorChanger> iterator = colorChangers.iterator();
        while (iterator.hasNext()) {
            ColorChanger changer = iterator.next();
            changer.update(delta);
            if (changer.hasBeenUsed() || changer.getY() <= -changer.getHeight()) iterator.remove();
        }
    }

    public void start() {
        produceWalls = true;
        wallSets = new ArrayList<WallSet>();
        WallSet wallSet1 = new WallSet(GameScreen.SCREEN_WIDTH/2, GameScreen.SCREEN_HEIGHT, World.GAME_SPEED, world);
        wallSets.add(wallSet1);
        colorChangers = new ArrayList<ColorChanger>();
    }

    public void stop() {
        produceWalls = false;
        for (WallSet wallSet: wallSets) {
            wallSet.moveOut();
        }
        for (ColorChanger colorChanger: colorChangers) {
            colorChanger.shrink();
        }
    }

    public void draw(SpriteBatch batch) {
        for (WallSet wallSet: wallSets) {
            wallSet.draw(batch);
        }
        for (ColorChanger colorChanger: colorChangers) {
            colorChanger.draw(batch);
        }
    }
}
