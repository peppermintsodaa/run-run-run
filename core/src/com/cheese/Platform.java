package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Platform {
    Sprite img = Project2.sprites.platform_img;
    Coord screen_size = Project2.var_list.screen_size;
    Array<Sprite> platforms = new Array<Sprite>();

    double speed;
    ArrayList<Coord> orig_pos = new ArrayList<Coord>();
    ArrayList<Coord> pos = new ArrayList<Coord>();
    Coord first_pos;     // do not change
    Coord cur_pos;
    Coord vel;
    float scale = 3;

    int platform_size = (int)((screen_size.x / (img.getWidth()*scale)) + 1);  // intended size

    Platform(float speed, float x, float y) {
        setSpeed(3);
        orig_pos.add(new Coord (x,y));
        first_pos = orig_pos.get(0);
        this.vel = new Coord(speed, 0);

        for (int i = 0; i < platform_size; i++) {
            platforms.add(img);
        }
        create();
    }

    double getSpeed() {
        return speed;
    }

    void setSpeed(double speed) {
        this.speed = speed;
    }

    void create() {
        Coord cur_pos = first_pos;

        for (int i = 0; i < platform_size-1; i++) {
            cur_pos = cur_pos.plus(new Coord(img.getWidth()*scale, 0));
            orig_pos.add(cur_pos);
        }
        pos.addAll(orig_pos);
    }

    void draw() {
        if (Project2.screens.game.isAtScreen) {
            vel.x = (float)speed;

            if (Project2.screens.pausing.isAtScreen) {
                vel.x = 0;
                first_pos = first_pos.minus(vel);
            }

            img.setScale(scale);
            int i = 0;

            // there is currently an issue where the platforms seem to flicker
            while (i < pos.size()) {
                cur_pos = pos.get(i);
                cur_pos = cur_pos.minus(vel);
                cur_pos.position(platforms.get(i));
                platforms.get(i).draw(Project2.sprites.batch);

                pos.set(i, cur_pos);

                // append new platform unit to keep continuous flow
                if (i == pos.size() - 1 && pos.get(i).x < (screen_size.x -(img.getWidth()*scale / 2))
                                        && pos.size() < platform_size + 1) {
                    platforms.add(img);
                    pos.add(pos.get(pos.size()-1).plus(new Coord(img.getWidth()*scale, 0)));
                }

                // remove off-screen platform unit
                if (i == 0 && pos.get(i).x < -(img.getWidth()*scale / 2)) {
                    platforms.removeIndex(i);
                    pos.remove(i);
                }

                i++;
            }
        }
    }

    // resets placement of platforms after playing game
    void reset() {
        pos.removeAll(pos);
        pos.addAll(orig_pos);
    }
}
