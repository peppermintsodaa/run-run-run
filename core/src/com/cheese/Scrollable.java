package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Scrollable {
    Sprite img;
    Coord screen_size = MainGame.var_list.screen_size;
    ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    double speed;
    ArrayList<Coord> orig_pos = new ArrayList<Coord>();
    ArrayList<Coord> pos = new ArrayList<Coord>();
    Coord first_pos;     // do not change
    Coord cur_pos;
    Coord vel;
    float scale;

    boolean hasCollided = false;

    Scrollable(float speed, float x, float y) {
        setSpeed(speed);

        orig_pos.add(new Coord (x,y));
        first_pos = orig_pos.get(0);
        this.vel = new Coord(speed, 0);
    }

    // intended size
    int getSpriteSize() {
        if (img.getWidth() > screen_size.x) {
            return 2;
        }
        else return (int)((screen_size.x / (img.getWidth()*scale)) + 1);
    }

    double getSpeed() {
        return speed;
    }

    void setSpeed(double speed) {
        this.speed = speed;
    }

    void create() {
        for (int i = 0; i < getSpriteSize(); i++) {
            sprites.add(img);
        }

        Coord cur_pos = first_pos;

        for (int i = 0; i < getSpriteSize()-1; i++) {
            cur_pos = cur_pos.plus(new Coord(img.getWidth()*scale, 0));
            orig_pos.add(cur_pos);
        }
        pos.addAll(orig_pos);
    }

    void draw() {
        if (MainGame.screens.game.isAtScreen) {
            vel.x = (float)speed;

            if (MainGame.screens.pausing.isAtScreen || MainGame.screens.game.game.isStopped) {
                vel.x = 0;
                first_pos = first_pos.minus(vel);
            }

            img.setScale(scale);
            int i = 0;

            // scroll items
            while (i < pos.size()) {
                cur_pos = pos.get(i);
                cur_pos = cur_pos.minus(vel);
                cur_pos.position(sprites.get(i));
                sprites.get(i).draw(MainGame.sprites.batch);

                pos.set(i, cur_pos);

                // append new platform unit to keep continuous flow
                if (i == pos.size() - 1 && pos.get(i).x <= (screen_size.x -(img.getWidth()*scale / 2))
                                        && pos.size() < 2*getSpriteSize() + 1) {
                    sprites.add(img);
                    pos.add(pos.get(i).plus(new Coord(img.getWidth()*scale, 0)));
                }

                // remove off-screen platform unit
                if (i == 0 && pos.get(i).x < -(img.getWidth()*scale / 2) && pos.size() >= 2*getSpriteSize()) {
                    sprites.remove(i);
                    pos.remove(i);
                }

                i++;
            }
        }
    }

    // resets placement of scrollable items after playing game
    void reset() {
        pos.removeAll(pos);
        pos.addAll(orig_pos);
    }
}
