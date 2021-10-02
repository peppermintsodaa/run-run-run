package com.cheese;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator; // custom font generator package
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class Project2 extends ApplicationAdapter {
	public class InputProcessor extends InputAdapter
	{
		public boolean touchDown (int screenX, int screenY, int pointer, int button)
		{ 
			mouse_pos = new Coord(screenX, screen_size.y - screenY);

			if (title.isAtTitleScreen) {
				if (screenX >= 560 && screenX <= 750 && screenY >= 460 && screenY <= 490)  {
					Boo b = new Boo(530, 237);
					boos.add(b);
					boo.play();
					return false;
				}
				if (screenX >= 620 && screenX <= 690 && screenY >= 510 && screenY <= 550)  {
					Gdx.app.exit();
					return false;
				}	
			}
			return false;
		}

		public boolean touchUp (int screenX, int screenY, int pointer, int button)
		{ 
			if (title.isAtTitleScreen) {
				if (screenX >= 560 && screenX <= 750 && screenY >= 460 && screenY <= 490)  {
					boos.removeAll(boos);
					return false;
				}
			}
			return false;
		}
	}

	static class Coord
	{
		float x, y;

		static Coord polar (double r, double theta)
		{
		return new Coord((float)Math.cos(theta)*r, (float)Math.sin(theta)*r);
		}

		Coord (double x, double y)
		{
		this.x = (float)x; this.y = (float)y;
		}

		Coord rotated (double angle)
		{
		// Return a rotated vector by making a new one with the same radius
		// and adding the angles.
		return Coord.polar(radius(), angle + theta());
		}

		float theta_deg ()
		{
		return (float)(theta() / (Math.PI*2) * 360);
		}

		Coord theta_deg (double t)
		{
		return theta( (t / 360) * Math.PI*2 );
		}

		float theta ()
		{
		return (float)Math.atan2(y, x);
		}

		Coord theta (double t)
		{
		return polar(radius(), t);
		}

		float radius ()
		{
		return (float)Math.sqrt(x*x+y*y);
		}

		Coord radius (double r)
		{
		return polar(r, theta());
		}

		Coord plus (Coord o)
		{
		return new Coord(x+o.x, y+o.y);
		}

		Coord minus (Coord o)
		{
		return new Coord(x-o.x, y-o.y);
		}

		Coord times (Coord o)
		{
		return new Coord(x*o.x, y*o.y);
		}

		Coord times (double d)
		{
		return times(new Coord(d,d));
		}

		Coord position (Sprite s)
		{
		s.setOriginBasedPosition(x, y);
		return this;
		}

		Coord rotation (Sprite s)
		{
		s.setRotation(theta_deg());
		return this;
		}

		public String toString ()
		{
		return "("+x+","+y+")";
		}
	}

	public class TitleScreen {
        boolean isAtTitleScreen = true;
		boolean booAppear = false;
        
        void draw () {
            if (isAtTitleScreen) {
                batch.draw(currFrameTitle, 240, 500);
                menuFont.draw(batch, "NEW GAME", 570, 250);
				menuFont.draw(batch, "EXIT", 620, 200);
            }
        }

        void close () {
            isAtTitleScreen = false;
        }

        void open () {
            isAtTitleScreen = true;
        }
    }

	class Boo {
		Boo (int x, int y) {
			Coord pos = new Coord(x, y);
			pos.position(boo_img);
		}
	}

	public class Ball {
		Coord pos = new Coord((int)(Math.random()*(screen_size.x+1)),screen_size.y+30);
    	Coord vel = new Coord(0,(int)(Math.random()*(2+1)));
    	Coord accel = new Coord(0, -0.1f);

		boolean tick () {
			pos = pos.plus(vel);
			vel = vel.plus(accel);

			if (pos.y < 0) {
				return false;
			}
			return true;
		}
	}

	void createBall() {
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				Ball b = new Ball();
				balls.add(b);
			}
		}, 0, 0.5f);
	}

	//-------------------- V A R I A B L E S --------------------//

	Animation<TextureRegion> textStyles;
	TextureRegion[] textFrames;
	TextureRegion currFrameTitle;
	Texture titleSheet;

	TitleScreen title = new TitleScreen();

	BitmapFont menuFont;

	Coord screen_size;
	Coord mouse_pos = new Coord(0,0);

	SpriteBatch batch;
	Sprite ball_img;
	Sprite boo_img;

	Sound boo;

	ArrayList<Boo> boos = new ArrayList<Boo>();
	ArrayList<Ball> balls = new ArrayList<Ball>();

	float currTime;
	int currTimeInt;
	int again = 1;

	//-----------------------------------------------------------//

	// split texture sheets for use in animation
	TextureRegion[] split(Texture img, TextureRegion[] regionArray, int rows, int columns) {
		TextureRegion[][] tmp = TextureRegion.split(img,
			img.getWidth() / columns,
			img.getHeight() / rows);

		regionArray = new TextureRegion[columns * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				regionArray[index++] = tmp[i][j]; 
			}
		}
	return regionArray;
	}
	
	@Override
	public void create () {
		screen_size = new Coord(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		// SPRITES
		boo_img = new Sprite(new Texture(Gdx.files.internal("boo.png")));

		ball_img = new Sprite(new Texture(Gdx.files.internal("ball2.png")));
		ball_img.setScale(0.5f);
		createBall();	// creates array of balls

		// AUDIO
		boo = Gdx.audio.newSound(Gdx.files.internal("boo.wav"));

		// TITLE SCREEN
		titleSheet = new Texture("titleText/run_run.png");
		textFrames = split(titleSheet, textFrames, 5, 1);
		textStyles = new Animation<TextureRegion>(0.15f, textFrames);

		// current elapsed time
		currTime = 0f;

		// custom text generator
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("couture-bld.otf"));

		FreeTypeFontGenerator.FreeTypeFontParameter parMenu = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parMenu.size = 30;

		menuFont = generator.generateFont(parMenu);

		Gdx.input.setInputProcessor(new InputProcessor());
	}

	@Override
	public void render () {
		ScreenUtils.clear(43/225f, 29/255f, 23/255f, 1);
		currTime += Gdx.graphics.getDeltaTime();

		currFrameTitle = textStyles.getKeyFrame(currTime, true);
		
		batch.begin();

		ArrayList<Object> trash_bin = new ArrayList<Object>();

		// TITLE SCREEN
		for (Ball ball: balls) {
			ball.pos.position(ball_img);
			ball_img.draw(batch);

			if (!ball.tick()) {
				trash_bin.add(ball);
			}
		}

		title.draw();
		for (Boo boo: boos) {
			boo_img.draw(batch);
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		titleSheet.dispose();
		boo_img.getTexture().dispose();
		ball_img.getTexture().dispose();

		boo.dispose();
	}
}
