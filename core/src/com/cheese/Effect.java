package com.cheese;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Effect {
// generic effect class [stolen from example code pls don't hurt me]
    SpriteBatch batch;

    Effect(SpriteBatch batch) {
        this.batch = batch;
    }

    Sprite curFrame;
    Coord pos;
    Coord dims;
    Coord vel = new Coord(0,0);
    float scale_min = 1;
    float scale_max = 1;

    int duration;
    int ticks;

    float p () {
        return ticks/(float)duration;
    }

    boolean draw () {
        ticks++;
        if (p() > 1) {
            return false;
        }

        pos.position(curFrame);
        curFrame.setScale(scale_min*(1-p()) + scale_max*p() );
        curFrame.draw(batch);

        return true;
    }
}
