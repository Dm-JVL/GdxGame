package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Utils.Direction;
import com.mygdx.game.Utils.TankOwner;
import com.mygdx.game.Utils.Utils;
import com.mygdx.game.Weapon;

public abstract class Tank {
    MyGdxGame game;
    Weapon weapon;

    public TankOwner getOwnerType() {
        return ownerType;
    }

    TankOwner ownerType;
    TextureRegion texture;
    TextureRegion textureHp;
    Vector2 position;
    Vector2 tmp;

    public Circle getCircle() {
        return circle;
    }

    Circle circle;
    int hp;
    int hpMax;

    float speed;
    float angle;

    float turrentAngle;
    float fireTimer;

    int heght;
    int width;


    public Tank(MyGdxGame game) {
        this.game = game;
        this.tmp = new Vector2(0.0f, 0.0f);

    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - width / 2, position.y - heght / 2, width / 2, heght / 2, width, heght, 1, 1, angle);
        batch.draw(weapon.getTexture(), position.x - width / 2, position.y - heght / 2, width / 2, heght / 2, width, heght, 1, 1, turrentAngle);
        if (hp < hpMax) {
            batch.setColor(0, 0, 0, 0.8f);
            batch.draw(textureHp, position.x - width / 2 - 2, position.y + heght / 2 - 8 - 2, 44, 12);
            batch.setColor(0, 1, 0, 0.8f);
            batch.draw(textureHp, position.x - width / 2, position.y + heght / 2 - 8, ((float) hp / hpMax) * 40, 8);
            batch.setColor(1, 1, 1, 1);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) destroy();


    }

    public abstract void destroy();

    public void rotateTorrentToPoint(float pointX, float pointY, float dt) {
        float angleTo = Utils.getAngel(position.x, position.y, pointX, pointY);
        turrentAngle = Utils.makeRotation(turrentAngle, angleTo, 270.0f, dt);
        turrentAngle = Utils.angleToFromNegPitToPosPi(turrentAngle);
    }


    public void update(float dt) {
        fireTimer += dt;
        if (position.x < 0.0f) {
            position.x = 0.0f;
        }
        if (position.x > Gdx.graphics.getWidth()) {
            position.x = Gdx.graphics.getWidth();
        }
        if (position.y < 0.0f) {
            position.y = 0.0f;
        }
        if (position.y > Gdx.graphics.getHeight()) {
            position.y = Gdx.graphics.getHeight();
        }
        circle.setPosition(position);
    }

    public void move(Direction direction, float dt) {
        tmp.set(position);
        tmp.add(speed * direction.getVx() * dt, speed * direction.getVy() * dt);
        if (game.getMap().isAreaClear(tmp.x, tmp.y, width / 2)) {
            angle = direction.getAngle();
            position.add(speed * direction.getVx() * dt, speed * direction.getVy() * dt);
        }
    }

    public void fire() {
        if (this.fireTimer >= weapon.getFirePeriod()) {
            this.fireTimer = 0.0f;
            float angleRad = (float) Math.toRadians(turrentAngle);
            game.getBulletEmitter().activate(this, position.x, position.y, weapon.getProjectilleSpeed() * (float) Math.cos(angleRad), weapon.getProjectilleSpeed() * (float) Math.sin(angleRad), weapon.getDamage(), weapon.getProjectilleLifetime());
        }

    }
}
