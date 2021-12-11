package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Obstacle {
    Sprite img;
    Coord orig_pos = new Coord(0,0);
    Coord pos = new Coord(0,0);
    float scale = 1;

    float count = 0;
    float wait_counter = 25;

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
        if (MainGame.var_list.gameCounter >= count) {
            vel.x = (float)speed;

            if (MainGame.screens.pausing.isAtScreen || MainGame.var_list.game.isStopped) {
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
                MainGame.var_list.game.setStopped(false);
            }
            else {
                chr.collide();
                shakeCamera(chr);
                MainGame.var_list.game.setStopped(true);
            }
        }
    }

    void shakeCamera(Character chr) {
        float orig_w = VariableList.screen_w;
        float orig_h = VariableList.screen_h;

        float offset = (4*wait_counter)*(float)Math.sin(2*wait_counter);

        if (!MainGame.screens.pausing.isAtScreen) {
            MainGame.var_list.camera.translate(offset, 0);
            
            if (wait_counter == 0) 
                MainGame.var_list.camera.setToOrtho(false, orig_w, orig_h);
            else --wait_counter;
        }
        else MainGame.var_list.camera.setToOrtho(false, orig_w, orig_h);
    }

    void reset() {
        orig_pos.setPosition(MainGame.var_list.screen_size.x + img.getWidth(),orig_pos.y);
        pos.setPosition(orig_pos.x, orig_pos.y);

        wait_counter = 30;
        recordCollide = false;
    }
}
