package com.cheese;

public class Platform extends Scrollable {
    Platform(float speed, float x, float y) {
        super(speed, x, y);

        this.img = MainGame.sprites.platform_img;
        this.scale = 3;

        create();
    }
}
