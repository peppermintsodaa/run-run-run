package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

// button object to press on
public class Button {
    Sprite img;
    Coord pos;
    float scale = 1;

    Button(Sprite img, float x, float y) {
        this.img = img;
        this.pos = new Coord(x,y);
    }

    void draw () {
        pos.position(img);
        img.setScale(scale);
        img.draw(Project2.var_list.batch);
    }
}
