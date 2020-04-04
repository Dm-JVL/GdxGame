package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Weapon {
    private TextureRegion texture;
    private float firePeriod;
    private int damage;
    private float radius;
    private float projectilleSpeed;
    private float projectilleLifetime;

    public float getProjectilleSpeed() {
        return projectilleSpeed;
    }


    public float getProjectilleLifetime() {
        return projectilleLifetime;
    }



    public float getRadius() {
        return radius;
    }

    public Weapon(TextureAtlas atlas) {
        this.texture = atlas.findRegion("simple_weapon40");
        this.firePeriod = 0.1f;
        this.damage = 1;
        this.radius = 300.0f;
        this.projectilleSpeed=320.0f;
        this.projectilleLifetime = this.radius / this.projectilleSpeed;

    }

    public float getFirePeriod() {
        return firePeriod;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public int getDamage() {
        return damage;
    }




}
