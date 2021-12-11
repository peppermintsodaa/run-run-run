package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Background extends Scrollable {
    Background(Sprite img, float speed) {
        super(speed, MainGame.adjustW(640), MainGame.adjustH(360));

        this.img = img;
        this.scale = 1;

        create();
    }
}
