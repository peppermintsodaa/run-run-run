package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.Array;

// creates an animated miscellaneous object
public class Animated {
    SpriteBatch batch;

    Coord pos = new Coord(0,0);
    float scale = 1;
    Array<Sprite> frameList;
    Sprite curFrame;
    int counter;
    int delay;

    Animated(SpriteBatch batch) {
        this.batch = batch;
    }

    void tick() {
        int which = (int)((counter/delay) % frameList.size);
        if (which >= frameList.size) {
            which = frameList.size - 1;
        }
        curFrame = frameList.get(which);
        curFrame.setScale(scale);
        
        pos.position(curFrame);
        curFrame.draw(batch);
    }
}
