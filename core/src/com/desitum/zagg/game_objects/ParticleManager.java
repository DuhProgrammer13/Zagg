package com.desitum.zagg.game_objects;

import com.badlogic.gdx.Gdx;
import com.desitum.library.particles.Particle;
import com.desitum.library.particles.ParticleBuilder;
import com.desitum.library.particles.ParticleEmitter;
import com.desitum.zagg.world.World;

/**
 * Created by kody on 12/20/15.
 * can be used by kody and people in [kody}]
 */
public class ParticleManager extends ParticleEmitter {

    //TODO this file needs a lot of love :)

    private World world;

    private static final int PARTICLE_AMOUNT = 5;

    public ParticleManager(World world) {
        ParticleEmitter emitter = ParticleBuilder.buildParticleEmitter(Gdx.files.internal("wallParticles.prt"));
        setParticleSettingsArrayList(emitter.getParticleSettingsArrayList());
        setParticleTexture(emitter.getParticleTexture());
        setWidth(emitter.getWidth());
        setHeight(emitter.getHeight());

        this.world = world;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (Particle particle: getParticles()) {
            particle.setColor(world.getThemeColor());
        }
    }

    public void explodeWall(WallSet wallSet){
        setX(wallSet.getMiddleX() - WallSet.SPREAD/2 - getWidth());
        for (int x = 0; x < PARTICLE_AMOUNT; x++) {
            addParticle(createNewParticle());
        }
        setX(wallSet.getMiddleX() + WallSet.SPREAD/2);
        for (int x = 0; x < PARTICLE_AMOUNT; x++) {
            addParticle(createNewParticle());
        }
    }
}
