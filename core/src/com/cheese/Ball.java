package com.cheese;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Ball {
    Coord pos = new Coord((int)(Math.random()*(Project2.var_list.screen_size.x+1)), 
                                               Project2.var_list.screen_size.y+30);		// determines ball's location at top of screen
    Coord vel = new Coord(0,(int)(Math.random()*(5-2+1)+2));				            // determines ball's random velocity 
    Coord accel = new Coord(0, -0.5f);

    boolean tick () {
        pos = pos.plus(vel);
        vel = vel.plus(accel);

        if (Project2.var_list.title.isAtScreen && pos.x > 560 && pos.x < 750 && pos.y < 267) {
            playSplash(pos.x, 267);
            return false;
        }

        if (pos.y < 0) {
            playSplash(pos.x, 12.5f);
            return false;
        }
        return true;
    }

    void playSplash(float x, float y) {
        BallSplash splash = new BallSplash(Project2.sprites.batch);
        splash.duration = 9;
        splash.scale_min = splash.scale_max = .5f;
        splash.pos = new Coord(x, y);
        Project2.var_list.effects.add(splash);
    }

    ArrayList<Sprite> ball_splash_frames;

    // loads the ball splashing sprites
    ArrayList<Sprite> createBallEffect() {
        if (ball_splash_frames != null) {
            return ball_splash_frames;
        }
        ball_splash_frames = new ArrayList<Sprite>();

        for (int i = 1; i <= 8; ++i) {
            String name = "ball_splash/" + i + ".png";

            Sprite s = new Sprite(new Texture(Gdx.files.internal(name)));
            ball_splash_frames.add(s);
        }
        return ball_splash_frames;
    }

    // ball splash effect for ball object
    class BallSplash extends Effect {

        BallSplash(SpriteBatch batch) {
            super(batch);
        }

        ArrayList<Sprite> splash_frames = createBallEffect();

        boolean draw() {
            int which = (int)(p() * splash_frames.size());
            if (which >= splash_frames.size()) {
                which = splash_frames.size() - 1;
            }
            curFrame = splash_frames.get(which);
            return super.draw();
        }
    }

    void draw(Ball ball, ArrayList<Object> trash) {
        ball.pos.position(Project2.sprites.ball_img);
        Project2.sprites.ball_img.draw(Project2.sprites.batch);

        if (!ball.tick()) {
            trash.add(ball);
        }
    }
}
