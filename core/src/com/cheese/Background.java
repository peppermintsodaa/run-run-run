package com.cheese;

public class Background extends Scrollable {
    Background(float speed, float x, float y) {
        super(speed, x, y);

        this.img = MainGame.sprites.game_bg_img;
        this.scale = 0.7111f;

        create();
    }
}
