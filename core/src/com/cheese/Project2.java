package com.cheese;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator; // custom font generator package
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.utils.ScreenUtils;
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

	public class TitleScreen implements ScreenMode {
        boolean isAtTitleScreen = true;
        
		@Override
        public void draw () {
            if (isAtTitleScreen) {
                titleText.tick();
                menuFont.draw(batch, "NEW GAME", 570, 250);
				menuFont.draw(batch, "EXIT", 620, 200);
            }
        }

		@Override
        public void close () {
            isAtTitleScreen = false;
        }

		@Override
        public void open () {
            isAtTitleScreen = true;
        }
    }

	class TitleText {
		Coord pos = new Coord(640, 500);
		Sprite curFrame;
		final int DELAY = 8; 	// plays every 2/15th second

		void tick() {
			int which = (int)((counter/DELAY) % textFrames.size());
			if (which >= textFrames.size()) {
				which = textFrames.size() - 1;
			}
			curFrame = textFrames.get(which);
			
			pos.position(curFrame);
			curFrame.draw(batch);
		}
	}

	class Boo {
		Boo (int x, int y) {
			Coord pos = new Coord(x, y);
			pos.position(boo_img);
		}
	}

	class Ball {
		Coord pos = new Coord((int)(Math.random()*(screen_size.x+1)),screen_size.y+30);
    	Coord vel = new Coord(0,(int)(Math.random()*(2+1)));
    	Coord accel = new Coord(0, -0.1f);

		boolean tick () {
			pos = pos.plus(vel);
			vel = vel.plus(accel);

			if (pos.y < 0) {
				pos.y = 25;

				BallSplash splash = new BallSplash();
				splash.duration = 9;
				splash.pos = new Coord(pos.x, pos.y);
				effects.add(splash);

				return false;
			}
			return true;
		}
	}

	class Effect {
		Sprite curFrame;
		Coord pos;
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

	void createBall() {
		if ((counter % 30) == 0) {
			Ball b = new Ball();
			balls.add(b);
		}
	}

	ArrayList<Sprite> ball_splash_frames;

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

	class BallSplash extends Effect {
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

	//-------------------- V A R I A B L E S --------------------//

	Coord screen_size;
	Coord mouse_pos = new Coord(0,0);

	SpriteBatch batch;
	Sprite ball_img;
	Sprite boo_img;

	Sound boo;

	ArrayList<Boo> boos = new ArrayList<Boo>();
	ArrayList<Ball> balls = new ArrayList<Ball>();

	ArrayList<Effect> effects = new ArrayList<Effect>();

	ArrayList<Sprite> textFrames;
	Sprite currFrameTitle;
	Texture titleSheet;

	TitleScreen title = new TitleScreen();
	TitleText titleText;

	BitmapFont menuFont;

	float currTime;
	int counter = 0;
	int again = 1;

	//-----------------------------------------------------------//

	// split texture sheets to use when animating sprite
	ArrayList<Sprite> split(Texture img, ArrayList<Sprite> regionArray, int rows, int columns) {
		TextureRegion[][] tmp = TextureRegion.split(img,
			img.getWidth() / columns,
			img.getHeight() / rows);

		regionArray = new ArrayList<Sprite>(columns * rows);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Sprite s = new Sprite(tmp[i][j]);
				regionArray.add(s);
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

		// AUDIO
		boo = Gdx.audio.newSound(Gdx.files.internal("boo.wav"));

		// TITLE SCREEN
		titleSheet = new Texture("titleText/run_run.png");
		textFrames = split(titleSheet, textFrames, 5, 1);
		titleText = new TitleText();

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
		counter++;
		
		batch.begin();

		ArrayList<Object> trash_bin = new ArrayList<Object>();

		createBall();	// creates array of balls
		// TITLE SCREEN
		for (Ball ball: balls) {
			ball.pos.position(ball_img);
			ball_img.draw(batch);

			if (!ball.tick()) {
				trash_bin.add(ball);
			}
		}

		for (Effect e : effects) {
			if (e.draw() == false) trash_bin.add(e);
		}

		balls.removeAll(trash_bin);
		effects.removeAll(trash_bin);
		trash_bin.clear();

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
		for (Sprite s: ball_splash_frames) s.getTexture().dispose();

		boo.dispose();
	}
}
