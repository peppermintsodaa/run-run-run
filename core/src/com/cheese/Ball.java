package com.cheese;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Ball {
    Coord pos = new Coord((int)(Math.random()*(MainGame.var_list.camera.viewportWidth+1)), 
                                               MainGame.var_list.camera.viewportHeight+30);		// determines ball's location at top of screen
    Coord vel = new Coord(0,(int)(Math.random()*(5-2+1)+2));				            // determines ball's random velocity 
    Coord accel = new Coord(0, -0.5f);

    boolean tick () {
        float menu_dims_w = MainGame.var_list.actors.get(0).getWidth()/2;
        float menu_dims_h = MainGame.var_list.actors.get(0).getHeight() - 5;

        pos = pos.plus(vel);
        vel = vel.plus(accel);

        if (MainGame.screens.title.isAtScreen) {
            if (pos.x > (adjustW(640) - menu_dims_w) && pos.x < (adjustW(640) + menu_dims_w) 
                                                     && pos.y < (adjustH(250) + menu_dims_h)) {
                playSplash(pos.x, (adjustH(250) + menu_dims_h));
                return false;
            }
        }

        if (pos.y < 0) {
            playSplash(pos.x, 12.5f);
            return false;
        }
        return true;
    }

    void playSplash(float x, float y) {
        BallSplash splash = new BallSplash(MainGame.sprites.batch);
        splash.duration = 9;
        splash.scale_min = splash.scale_max = .5f;
        splash.pos = new Coord(x, y);
        MainGame.var_list.effects.add(splash);
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
        // updatePos();
        ball.pos.position(MainGame.sprites.ball_img);
        MainGame.sprites.ball_img.draw(MainGame.sprites.batch);

        if (!ball.tick()) {
            trash.add(ball);
        }
    }

    public static float adjustW(float orig_width) {
		return (orig_width/1280f)*VariableList.screen_w;
	}

	public static float adjustH(float orig_height) {
		return (orig_height/720f)*VariableList.screen_h;
	}
}
