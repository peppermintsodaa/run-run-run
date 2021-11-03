package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

// button object to press on
public class Button {
    Sprite img;
    Coord pos;
    float scale = 1;
    float bounds;

    Button(Sprite img, float x, float y) {
        this.img = img;
        this.pos = new Coord(x,y);
    }

    void draw () {
        pos.position(img);
        img.setScale(scale);
        bounds = img.getWidth()/(2/scale);

        img.draw(MainGame.sprites.batch);
    }
}
