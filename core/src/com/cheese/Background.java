package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Background extends Scrollable {
    Background(Sprite img, float speed, float x, float y) {
        super(speed, x, y);

        this.img = img;
        this.scale = 0.7111f;

        create();
    }
}
