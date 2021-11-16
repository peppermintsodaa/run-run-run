package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Obstacle {
    Sprite img;
    Coord orig_pos = new Coord(0,0);
    Coord pos = new Coord(0,0);
    float scale = 1;

    float count = 0;

    double speed;
    Coord vel;

    boolean hasCollided = false;
    boolean recordCollide = false;

    Obstacle(Sprite img, float count, double speed, float y) {
        this.img = img;
        float x = MainGame.var_list.screen_size.x + img.getWidth();

        orig_pos.setPosition(x,y);
        pos.setPosition(x,y);

        this.count = count;
        this.speed = speed;
        vel = new Coord(speed, 0);
    }

    boolean draw() {
        if (MainGame.screens.game.isAtScreen && MainGame.var_list.gameCounter >= count) {
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

    void collide(Character chr) {
        if (!recordCollide && Math.abs(pos.minus(chr.charV.run_pos).x) < img.getWidth()/2 &&
                              Math.abs(pos.minus(chr.charV.run_pos).y) < img.getHeight()/2) {
            if (chr.wait_counter > chr.WAIT) {
                chr.wait_counter = 0;
                recordCollide = true;
                MainGame.screens.game.game.setStopped(false);
            }
            else {
                chr.collide();
                MainGame.screens.game.game.setStopped(true);
            }
        }
    }

    void reset() {
        pos.setPosition(orig_pos.x, orig_pos.y);
        recordCollide = false;
    }
}
