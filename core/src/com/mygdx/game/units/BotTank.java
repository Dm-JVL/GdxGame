package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Utils.Direction;
import com.mygdx.game.Utils.TankOwner;
import com.mygdx.game.Weapon;

public class BotTank extends Tank {
    Direction prefferedDirection;
    float aiTimer;
    float aiTimerTo;
    float pursuitRadius;
    boolean active;
    Vector3 lastPosition;

    public boolean isActive() {
        return active;
    }

    public BotTank(MyGdxGame game, TextureAtlas atlas) {
        super(game);
        this.ownerType = TankOwner.AI;
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("bot_tank_base");
        this.textureHp = atlas.findRegion("bat");
        this.position = new Vector2(500.0f, 500.0f);
        //   this.x = 640.0f;
        //   this.y = 360.0f;
        this.speed = 100.0f;
        this.width = texture.getRegionWidth();
        this.heght = texture.getRegionHeight();
        this.fireTimer = 0.0f;
        this.hpMax = 10;
        this.hp = this.hpMax;
        this.aiTimerTo = 3.0f;
        this.prefferedDirection = Direction.UP;
        this.circle = new Circle(position.x, position.y, (this.width + this.heght) / 2);
        this.pursuitRadius = 300.0f;
        this.lastPosition = new Vector3(0.0f, 0.0f, 0.0f);
    }

    public void activate(float x, float y) {
        hpMax = 3;
        hp = hpMax;
        prefferedDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
        position.set(x, y);
        aiTimer = 0.0f;
        active = true;
        angle = prefferedDirection.getAngle();

    }

    public void update(float dt) {
        aiTimer += dt;

        if (aiTimer > aiTimerTo) {
            aiTimer = 0.0f;
            aiTimerTo = MathUtils.random(3.50f, 6.0f);
            prefferedDirection = Direction.values()[MathUtils.random(Direction.values().length - 1)];
            angle = prefferedDirection.getAngle();
        }
        move(prefferedDirection, dt);
        float dst = this.position.dst(game.getPlayer().getPosition());
        if (dst < pursuitRadius) {
            rotateTorrentToPoint(game.getPlayer().getPosition().x, game.getPlayer().getPosition().y, dt);

            fire();
        }
        lastPosition.x = position.x;
        if (Math.abs(position.x - lastPosition.x) < 0.5f && Math.abs(position.y - lastPosition.y) < 0.5f) {
            lastPosition.z += dt;
            if (lastPosition.z > 0.3f) {
                aiTimer += 10.0f;
            }

        } else {
            lastPosition.x = position.x;
            lastPosition.y = position.y;
            lastPosition.z = 0.0f;
        }

        super.update(dt);

    }

    @Override
    public void destroy() {
        active = false;
    }
}
