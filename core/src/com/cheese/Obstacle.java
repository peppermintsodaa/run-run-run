package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Obstacle {
    Sprite img;
    Coord orig_pos = new Coord(0,0);
    Coord pos = new Coord(0,0);
    float scale = 1;

    double speed;
    Coord vel;

    boolean hasCollided = false;
    boolean recordCollide = false;

    Obstacle(Sprite img, double speed, float x, float y) {
        this.img = img;
        orig_pos.setPosition(x,y);
        pos.setPosition(x,y);

        this.speed = speed;
        vel = new Coord(speed, 0);
    }

    boolean draw() {
        if (MainGame.screens.game.isAtScreen) {
            vel.x = (float)speed;

            if (MainGame.screens.pausing.isAtScreen || MainGame.screens.game.game.isStopped) {
                vel.x = 0;
                pos = pos.minus(vel);
            }

            img.setScale(scale);
            pos = pos.minus(vel);
            pos.position(img);
            img.draw(MainGame.sprites.batch);
        }
        if (pos.x < -img.getWidth()/2) {
            return false;
        }
        return true;
    }

    void reset() {
        pos.setPosition(orig_pos.x, orig_pos.y);
        recordCollide = false;
    }
}
