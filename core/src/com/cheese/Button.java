package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

// button object to press on
public class Button {
    Sprite img;
    Coord pos = new Coord(0,0);
    float scale = 1;
    float bounds;

    Button(Sprite img) {
        this.img = img;
    }

    void draw () {
        pos.position(img);
        img.setScale(scale);
        bounds = img.getWidth()/(2/scale);

        img.draw(MainGame.sprites.batch);
    }
}
