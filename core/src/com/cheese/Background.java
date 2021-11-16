package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Background extends Scrollable {
    Background(Sprite img, float speed) {
        super(speed, 640, 360);

        this.img = img;
        this.scale = 1;

        create();
    }
}
