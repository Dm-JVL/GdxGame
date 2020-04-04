package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Utils.Direction;
import com.mygdx.game.Utils.TankOwner;
import com.mygdx.game.Weapon;

public class PlayerTank extends Tank {
    int lifes;

    public PlayerTank(MyGdxGame game, TextureAtlas atlas) {

        super(game);
        this.ownerType = TankOwner.PLAYER;
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("player_tank_base");
        this.textureHp = atlas.findRegion("bat");
        this.position = new Vector2(100.0f, 100.0f);
        //   this.x = 640.0f;
        //   this.y = 360.0f;
        this.speed = 100.0f;
        this.width = texture.getRegionWidth();
        this.heght = texture.getRegionHeight();
        this.fireTimer = 0.0f;
        this.hpMax = 10;
        this.hp = this.hpMax;
        this.lifes = 5;
        this.circle = new Circle(position.x, position.y, (this.width + this.heght) / 2);
    }

    public void update(float dt) {
        this.fireTimer += dt;
        checkMovement(dt);
        float mx = Gdx.input.getX();
        float my = Gdx.graphics.getHeight() - Gdx.input.getY();

        rotateTorrentToPoint(mx, my, dt);
        //if (Gdx.input.justTouched()) fire();
        if (Gdx.input.isTouched()) {
            fire();

        }
        super.update(dt);
    }

    public void checkMovement(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            move(Direction.LEFT, dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            move(Direction.RIGHT, dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            move(Direction.UP, dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            move(Direction.DOWN, dt);
        }
    }

    @Override
    public void destroy() {
        lifes--;
        hp = hpMax;
    }
}