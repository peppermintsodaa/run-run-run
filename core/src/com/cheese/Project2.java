package com.cheese;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
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
import java.util.HashMap;

public class Project2 extends ApplicationAdapter {
	public class InputProcessor extends InputAdapter
	{
		public boolean touchDown (int screenX, int screenY, int pointer, int button)
		{ 
			mouse_pos = new Coord(screenX, screen_size.y - screenY);

			if (title.isAtTitleScreen) {
				// clicking on NEW GAME
				if (screenX >= 560 && screenX <= 750 && screenY >= 460 && screenY <= 490)  {
					Boo b = new Boo(530, 237);
					boos.add(b);
					playSound(boo);
					return false;
				}
				// clicking on OPTIONS
				if (screenX >= 590 && screenX <= 720 && screenY >= 500 && screenY <= 550)  {
					playSound(click);
					title.close();
					options.open();
					return false;
				}
				// clicking on EXIT
				if (screenX >= 620 && screenX <= 690 && screenY >= 560 && screenY <= 595)  {
					playSound(click);
					Gdx.app.exit();
					return false;
				}
			}
			if (options.isAtOptionsScreen) {
				// configuring sound
				if (screenX >= 795 && screenX <= 840 && screenY >= 320 && screenY <= 355)  {
					options.soundSwitch();
					playSound(click);
					return false;
				}
				// clicking on GO BACK
				if (screenX >= 570 && screenX <= 710 && screenY >= 620 && screenY <= 655)  {
					playSound(click);
					options.close();
					title.open();
					return false;
				}
			}
			if (char_select.isAtCharScreen) {
				// clicking on GO BACK
				if (screenX >= 570 && screenX <= 710 && screenY >= 620 && screenY <= 655)  {
					playSound(click);
					char_select.close();
					title.open();
					return false;
				}
				// clicking on CHARACTER
				if (screenX >= (stickman.pos.x - (stickman.dims.x / 2) - 15) && 
					screenX <= (stickman.pos.x + (stickman.dims.x / 2) + 15) && 
					(screen_size.y - screenY) >= (stickman.pos.y - (stickman.dims.y / 2) - 30) && 
					(screen_size.y - screenY) <= (stickman.pos.y + (stickman.dims.y / 2) + 30)) {
					// System.out.println(mouse_pos.x/10 + ", "+ mouse_pos.y/10);
					playSound(run_90s_b);
					return false;
				}
			}
			if (game.isInGame) {
				// clicking on random location
				if (screenX >= 560 && screenX <= 750 && screenY >= 460 && screenY <= 490) {
					if (!pausing.isPaused) {
						pausing.open();
						if (!options.soundOff) run_90s_a.pause();
					}
					else {
						pausing.close();
						if (!options.soundOff)run_90s_a.resume();
					}
					return false;
				}
			}
			return true;
		}

		public boolean touchUp (int screenX, int screenY, int pointer, int button)
		{ 
			mouse_pos = new Coord(screenX, screen_size.y - screenY);

			if (title.isAtTitleScreen) {
				if (screenX >= 560 && screenX <= 750 && screenY >= 460 && screenY <= 490)  {
					boos.removeAll(boos);
					playSound(click);
					title.close();
					char_select.open();
					return false;
				}
			}
			if (char_select.isAtCharScreen) {
				// clicking on CHAR
				if (screenX >= (stickman.pos.x - (stickman.dims.x / 2) - 15) && 
					screenX <= (stickman.pos.x + (stickman.dims.x / 2) + 15) && 
					(screen_size.y - screenY) >= (stickman.pos.y - (stickman.dims.y / 2) - 30) && 
					(screen_size.y - screenY) <= (stickman.pos.y + (stickman.dims.y / 2) + 30)) {
					// System.out.println(mouse_pos.x/10 + ", "+ mouse_pos.y/10);
					char_select.close();
					game.open();
					if (!options.soundOff) run_90s_a.loop();
					return false;
				}
			}
			return false;
		}

		public boolean mouseMoved (int screenX, int screenY) {
			mouse_pos = new Coord(screenX, screen_size.y - screenY);

			return true;
		}
	}

	static class Coord
	{
		float x, y;

		static Coord polar (double r, double theta) {
			return new Coord((float)Math.cos(theta)*r, (float)Math.sin(theta)*r);
		}

		Coord (double x, double y) {
			this.x = (float)x; this.y = (float)y;
		}

		void setPosition(float x, float y) {
			this.x = (float)x; this.y = (float)y;
		}

		Coord rotated (double angle) {
			// Return a rotated vector by making a new one with the same radius
			// and adding the angles.
			return Coord.polar(radius(), angle + theta());
		}

		float theta_deg () {
			return (float)(theta() / (Math.PI*2) * 360);
		}

		Coord theta_deg (double t) {
			return theta( (t / 360) * Math.PI*2 );
		}

		float theta () {
			return (float)Math.atan2(y, x);
		}

		Coord theta (double t) {
			return polar(radius(), t);
		}

		float radius () {
			return (float)Math.sqrt(x*x+y*y);
		}

		Coord radius (double r) {
			return polar(r, theta());
		}

		Coord plus (Coord o) {
			return new Coord(x+o.x, y+o.y);
		}

		Coord minus (Coord o) {
			return new Coord(x-o.x, y-o.y);
		}

		Coord times (Coord o) {
			return new Coord(x*o.x, y*o.y);
		}

		Coord times (double d) {
			return times(new Coord(d,d));
		}

		Coord position (Sprite s) {
			s.setOriginBasedPosition(x, y);
			return this;
		}

		Coord dimensions (Sprite s, float scale, int rows, int cols) {
			float width = (s.getTexture().getWidth() * scale) / cols;
			float height = (s.getTexture().getHeight() * scale) / rows;
	
			this.x = width;
			this.y = height;

			return this;
		}

		Coord rotation (Sprite s) {
			s.setRotation(theta_deg());
			return this;
		}

		public String toString () {
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
				menuFont.draw(batch, "OPTIONS", 585, 200);
				menuFont.draw(batch, "EXIT", 620, 150);
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

	public class OptionsScreen implements ScreenMode {
        boolean isAtOptionsScreen = false;
		boolean soundOff = false;
        
		@Override
        public void draw () {
            if (isAtOptionsScreen) {
                titleFont.draw(batch, "OPTIONS", 475, 600);
                menuFont.draw(batch, "SOUND", 400, 400);
				menuFont.draw(batch, "GO BACK", 570, 100);
				if (soundOff) {
					offOption.draw(batch, "OFF", 800, 390);
				}
				else onOption.draw(batch, "ON", 800, 390);
            }
        }

		public void soundSwitch() {
			if (!soundOff) {
				soundOff = true;
			}
			else soundOff = false;
		}

		@Override
        public void close () {
            isAtOptionsScreen = false;
        }

		@Override
        public void open () {
            isAtOptionsScreen = true;
        }
    }

	public class CharSelectScreen implements ScreenMode {
        boolean isAtCharScreen = false;
        
		@Override
        public void draw () {
            if (isAtCharScreen) {
                titleFont.draw(batch, "SELECT CHARACTER", 250, 600);
				menuFont.draw(batch, "GO BACK", 570, 100);

				drawCharacter(stickman, 640, 320);
        	}
		}

		@Override
        public void close () {
            isAtCharScreen = false;
        }

		@Override
        public void open () {
            isAtCharScreen = true;
        }
    }

	public class GameInterface implements ScreenMode {
        boolean isInGame = false;
        
		@Override
        public void draw () {
            if (isInGame) {
				pause.scale = 0.5f;

                titleFont.draw(batch, "WIP", 340, 600);
				hamster.tick();
				pause.draw();
			}
		}

		@Override
        public void close () {
            isInGame = false;
        }

		@Override
        public void open () {
            isInGame = true;
        }
    }

	public class PauseScreen implements ScreenMode {
        boolean isPaused = false;
        
		@Override
        public void draw () {
            if (isPaused) {
				Coord pos = new Coord (0,0);
				pos.position(grey_bg);
				grey_bg.draw(batch);

                titleFont.draw(batch, "PAUSED", 500, 400);
			}
		}

		@Override
        public void close () {
            isPaused = false;
        }

		@Override
        public void open () {
            isPaused = true;
        }
    }

	//----------------- C H A R A C T E R S ----------------//

	// for some reason hash maps have to be created in a static env?
	HashMap<String, Integer> states = new HashMap<>();

	void createCharStates() {
		states.put("stand/looking front", 0);
		states.put("stand/looking left", 1);
		states.put("stand/looking right", 2);
	}

	class Character {
		Coord pos = new Coord(0,0);
		Coord dims = new Coord(0,0);
		int rows = 1;	// if sprite sheet, specifies how many rows in sprite sheet
		int cols = 1;	// if sprite sheet, specifies how many columns in sprite sheet
		float scale = 1;

		Sprite curFrame;
		int duration;
		int ticks;

		float p () {
			return ticks/(float)duration;
		}

		boolean draw(ArrayList<Sprite> sprite_list, int state) {
			curFrame = sprite_list.get(state);
			// System.out.println(stickman.curFrame.getOriginX());
			curFrame.setScale(scale);
			pos.position(curFrame);
			dims.dimensions(curFrame, scale, rows, cols);
			curFrame.draw(batch);
			return true;
		}

		void setState(int state) {
			switch (state) {
				case 1: draw(stickman_sprites, 1);
						break;
				case 2: draw(stickman_sprites, 2);
						break;
				default:draw(stickman_sprites, 0);
						break;
        	}
		}		
	}

	class BodyPart {
		Coord pos = new Coord(0,0);
		Coord dims = new Coord(0,0);
		float scale = 0.25f;
		Sprite img;

		BodyPart (Sprite img) {
			this.img = img;
		}

		boolean draw () {
			pos.position(img);
			dims.dimensions(img, scale, 1, 1);
			img.setScale(scale);
			img.draw(batch);

			return true;
		}
	}

	void drawCharacter(Character character, float x, float y) {
		character.pos.setPosition(x,y);
		character.rows = 1;
		character.cols = 3;
		character.scale = 0.9f;

		BodyPart eye_l = new BodyPart(eye_img);
		BodyPart eye_r = new BodyPart(eye_img);
		BodyPart mouth = new BodyPart(mouth_img);
		eye_l.pos.setPosition(character.pos.x - 15, 440);
		eye_r.pos.setPosition(character.pos.x + 25, 440);
		mouth.pos.setPosition(character.pos.x + 5, 410);

		if (mouse_pos.x >= character.pos.x && mouse_pos.x <= character.pos.x + 200 && 
			mouse_pos.y >= 150 && mouse_pos.y <= 500) {
			character.setState(states.get("stand/looking right"));

			eye_r.pos.setPosition(character.pos.x + 15, 440);
			eye_r.draw();
			mouth.draw();
		}
		if (mouse_pos.x < character.pos.x && mouse_pos.x >= character.pos.x - 200 &&
			mouse_pos.y >= 150 && mouse_pos.y <= 500) {
			character.setState(states.get("stand/looking left"));
			
			eye_l.draw();
			mouth.pos.setPosition(character.pos.x, 410);
			mouth.draw();
		}
		if (mouse_pos.x > character.pos.x + 200 || mouse_pos.x < character.pos.x - 200 || 
			mouse_pos.y < 150 || mouse_pos.y > 500) {
			character.setState(states.get("stand/looking front"));

			eye_l.draw();
			eye_r.draw();
			mouth.draw();
		}
	}

	//-------------------- E F F E C T S --------------------//

	class Effect {
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

	class Ball {
		Coord pos = new Coord((int)(Math.random()*(screen_size.x+1)),screen_size.y+30);
    	Coord vel = new Coord(0,(int)(Math.random()*(5-2+1)+2));
    	Coord accel = new Coord(0, -0.5f);

		boolean tick () {
			pos = pos.plus(vel);
			vel = vel.plus(accel);

			if (title.isAtTitleScreen && pos.x > 560 && pos.x < 750 && pos.y < 267) {
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
			BallSplash splash = new BallSplash();
				splash.duration = 9;
				splash.scale_min = splash.scale_max = .5f;
				splash.pos = new Coord(x, y);
				effects.add(splash);
		}
	}

	void createBall() {
		if ((titleCounter % 20) == 0) {
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

	class Button {
		Sprite img;
		Coord pos;
		Coord dims = new Coord(0,0);
		float scale = 1;

		Button(Sprite img, float x, float y) {
			this.img = img;
			this.pos = new Coord(x,y);
		}

		void draw () {
			pos.position(img);
			dims.dimensions(img, scale, 1, 1);
			img.setScale(scale);
			img.draw(batch);
		}
	}


	//------------------------ M I S C ------------------------//

	class Animated { 
		Coord pos = new Coord(0,0);
		float scale = 1;
		ArrayList<Sprite> frameList;
		Sprite curFrame;
		int counter;
		int delay;				// plays every 2/15th second

		void tick() {
			int which = (int)((counter/delay) % frameList.size());
			if (which >= frameList.size()) {
				which = frameList.size() - 1;
			}
			curFrame = frameList.get(which);
			curFrame.setScale(scale);
			
			pos.position(curFrame);
			curFrame.draw(batch);
		}
	}
	
	class TitleText extends Animated {
		void tick() {
			counter = titleCounter;

			pos.setPosition(640, 500);
			frameList = titleFrames;
			delay = 8; 	// plays every 2/15th second

			super.tick();
		}
	}

	class Hamster extends Animated {
		void tick() {
			counter = gameCounter;

			pos.setPosition(640, 300);
			frameList = hamFrames;
			scale = 2.5f;
			delay = 3; 	// plays every 1/15th second

			super.tick();
		}
	}

	class Boo {
		Boo (int x, int y) {
			Coord pos = new Coord(x, y);
			pos.position(boo_img);
		}
	}

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

	void playSound(Sound s) {
		if (!options.soundOff) {
			s.play(); 
		}
	}

	//-------------------- V A R I A B L E S --------------------//

	Coord screen_size;
	Coord mouse_pos = new Coord(0,0);

	SpriteBatch batch;
	Sprite ball_img;
	Sprite boo_img;
	Sprite eye_img;
	Sprite mouth_img;
	Sprite pause_img;
	Sprite grey_bg;

	ArrayList<Sprite> stickman_sprites;
	Character stickman;

	Sound boo;
	Sound click;
	Sound run_90s_b;
	Sound run_90s_a;

	ArrayList<Boo> boos = new ArrayList<Boo>();
	ArrayList<Ball> balls = new ArrayList<Ball>();

	ArrayList<Effect> effects = new ArrayList<Effect>();

	ArrayList<Sprite> titleFrames;
	ArrayList<Sprite> hamFrames;

	TitleText titleText;
	Hamster hamster;

	Button pause;

	TitleScreen title = new TitleScreen();
	OptionsScreen options = new OptionsScreen();
	CharSelectScreen char_select = new CharSelectScreen();
	GameInterface game = new GameInterface();
	PauseScreen pausing = new PauseScreen();

	BitmapFont menuFont;
	BitmapFont titleFont;
	BitmapFont offOption;
	BitmapFont onOption;

	static final Color GREEN = new Color(0, 1, 0, 1);
	static final Color RED = new Color(1, 0, 0, 1);

	float currTime;
	int gameCounter = 0;
	int titleCounter = 0;
	int again = 1;

	//-----------------------------------------------------------//
	
	@Override
	public void create () {
		screen_size = new Coord(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		// SPRITES
		boo_img = new Sprite(new Texture(Gdx.files.internal("boo.png")));

		ball_img = new Sprite(new Texture(Gdx.files.internal("ball2.png")));
		ball_img.setScale(0.5f);

		eye_img = new Sprite(new Texture(Gdx.files.internal("eye.png")));
		mouth_img = new Sprite(new Texture(Gdx.files.internal("mouth.png")));

		pause_img = new Sprite(new Texture(Gdx.files.internal("pause.png")));

		grey_bg = new Sprite(new Texture(Gdx.files.internal("grey.png")), 1, 1);
		grey_bg.setSize(screen_size.x, screen_size.y);
		grey_bg.setColor(0,0,0,0.3f);

		createCharStates();

		// ANIMATED SPRITES
		stickman = new Character();
		stickman_sprites = split(new Texture("stickman.png"), stickman_sprites, 1, 3);

		hamFrames = split(new Texture("ham.png"), hamFrames, 14, 5);
		hamFrames.remove(69);
		hamFrames.remove(68);
		hamFrames.remove(67);
		hamster = new Hamster();

		// AUDIO
		boo = Gdx.audio.newSound(Gdx.files.internal("boo.wav"));
		click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
		run_90s_b = Gdx.audio.newSound(Gdx.files.internal("running90s_before.wav"));
		run_90s_a = Gdx.audio.newSound(Gdx.files.internal("running90s_after.wav"));

		// TITLE
		titleFrames = split(new Texture("titleText/run_run.png"), titleFrames, 5, 1);
		titleText = new TitleText();

		// BUTTON
		pause = new Button(pause_img, screen_size.x - 35, screen_size.y - 35);

		// custom text generator
		FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("couture-bld.otf"));
		FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("fontClayToy.ttf"));

		FreeTypeFontGenerator.FreeTypeFontParameter parMenu = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parTitle = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parOptionRed = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parOptionGreen = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parMenu.size = 30;

		parTitle.size = 90;

		parOptionRed.size = 20;
		parOptionRed.color = RED;

		parOptionGreen.size = 20;
		parOptionGreen.color = GREEN;

		menuFont = generator1.generateFont(parMenu);
		titleFont = generator2.generateFont(parTitle);
		offOption = generator1.generateFont(parOptionRed);
		onOption = generator1.generateFont(parOptionGreen);

		Gdx.input.setInputProcessor(new InputProcessor());
	}

	@Override
	public void render () {
		ScreenUtils.clear(43/225f, 29/255f, 23/255f, 1);
		if (game.isInGame && !pausing.isPaused) gameCounter++;
		
		batch.begin();
		
		// menuFont.draw(batch, "( " + mouse_pos.x/10 + ", " + mouse_pos.y/10 + ")" , 10, 30); 		// displays mouse position
		ArrayList<Object> trash_bin = new ArrayList<Object>();

		// TITLE SCREEN / OPTIONS SCREEN
		if (title.isAtTitleScreen || options.isAtOptionsScreen || char_select.isAtCharScreen) {	
			titleCounter++;
			createBall();	// creates array of balls


			for (Ball ball: balls) {
				ball.pos.position(ball_img);
				ball_img.draw(batch);
	
				if (!ball.tick()) {
					trash_bin.add(ball);
				}
			}
		}

		title.draw();
		options.draw();
		char_select.draw();
		game.draw();
		pausing.draw();

		for (Boo b: boos) {
			boo_img.draw(batch);
		}

		for (Effect e : effects) {
			if (e.draw() == false) trash_bin.add(e);
		}

		balls.removeAll(trash_bin);
		effects.removeAll(trash_bin);
		trash_bin.clear();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		boo_img.getTexture().dispose();
		ball_img.getTexture().dispose();
		eye_img.getTexture().dispose();
		grey_bg.getTexture().dispose();
		for (Sprite f: ball_splash_frames) f.getTexture().dispose();
		for (Sprite f: titleFrames) f.getTexture().dispose();
		for (Sprite s: stickman_sprites) s.getTexture().dispose();

		boo.dispose();
		click.dispose();
		run_90s_a.dispose();
		run_90s_b.dispose();
		
		menuFont.dispose();
		titleFont.dispose();
		offOption.dispose();
		onOption.dispose();
	}
}
