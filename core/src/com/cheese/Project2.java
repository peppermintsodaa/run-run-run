package com.cheese;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;

/* future implementations:
- rewriting input processor to incorporate some sort of bound for text options 
*/

public class Project2 extends ApplicationAdapter {

	public class InputProcessor extends InputAdapter
	{
		public boolean touchDown (int screenX, int screenY, int pointer, int button)
		{ 
			var_list.mouse_pos = new Coord(screenX, var_list.screen_size.y - screenY);

			if (var_list.title.isAtScreen) {
				// clicking on NEW GAME
				if (screenX >= 560 && screenX <= 750 && screenY >= 460 && screenY <= 490)  {
					var_list.b.booAppeared = true;
					playSound(var_list.boo);
					return false;
				}
				// clicking on OPTIONS
				if (screenX >= 590 && screenX <= 720 && screenY >= 500 && screenY <= 550)  {
					playSound(var_list.click);
					var_list.title.close();
					var_list.options.open();
					return false;
				}
				// clicking on EXIT
				if (screenX >= 620 && screenX <= 690 && screenY >= 560 && screenY <= 595)  {
					playSound(var_list.click);
					Gdx.app.exit();
					return false;
				}
			}
			if (var_list.options.isAtScreen) {
				// configuring sound
				if (screenX >= 795 && screenX <= 840 && screenY >= 320 && screenY <= 355)  {
					var_list.options.soundSwitch();
					playSound(var_list.click);
					return false;
				}
				// clicking on GO BACK
				if (screenX >= 570 && screenX <= 710 && screenY >= 620 && screenY <= 655)  {
					playSound(var_list.click);
					var_list.options.close();
					if (!var_list.game.isAtScreen) {
						var_list.title.open();
					}
					return false;
				}
			}
			if (var_list.char_select.isAtScreen) {
				// clicking on GO BACK
				if (screenX >= 570 && screenX <= 710 && screenY >= 620 && screenY <= 655)  {
					playSound(var_list.click);
					var_list.char_select.close();
					var_list.title.open();
					return false;
				}
				// clicking on CHARACTER
				if ((Math.abs(var_list.mouse_pos.minus(var_list.stickmanV.pos).x) < (var_list.stickmanV.dims.x / 2) + 15) && 
					(Math.abs(var_list.mouse_pos.minus(var_list.stickmanV.pos).y) < (var_list.stickmanV.dims.y / 2) + 30)) {
					playSound(var_list.run_90s_b);
					return false;
				}
			}
			if (var_list.game.isAtScreen) {
				// clicking on pause button
				if (var_list.mouse_pos.minus(var_list.pause.pos).radius() < 25) {
					if (!var_list.pausing.isAtScreen) {
						playSound(var_list.click);
						var_list.pausing.open();
					}
					return false;
				}
			}
			if (var_list.pausing.isAtScreen) {
				// clicking on OPTIONS
				if (screenX >= 250 && screenX <= 360 && screenY >= 570 && screenY <= 605) {
					var_list.options.open();
					playSound(var_list.click);
				}
				// clicking on RESUME
				if (screenX >= 570 && screenX <= 700 && screenY >= 570 && screenY <= 605) {
					var_list.pausing.close();
					playSound(var_list.click);
				}
				// clicking on GIVE UP
				if (screenX >= 895 && screenX <= 1000 && screenY >= 570 && screenY <= 605) {
					var_list.pausing.close();
					var_list.game.close();
					playSound(var_list.click);
					var_list.title.open();
				}
			}
			return true;
		}

		public boolean touchUp (int screenX, int screenY, int pointer, int button)
		{ 
			var_list.mouse_pos = new Coord(screenX, var_list.screen_size.y - screenY);

			if (var_list.title.isAtScreen) {
				// clicking on NEW GAME
				if (screenX >= 560 && screenX <= 750 && screenY >= 460 && screenY <= 490)  {
					var_list.b.booAppeared = false;
					playSound(var_list.click);
					var_list.title.close();
					var_list.char_select.open();
					return false;
				}
			}
			if (var_list.char_select.isAtScreen) {
				// clicking on CHARACTER
				if ((Math.abs(var_list.mouse_pos.minus(var_list.stickmanV.pos).x) < (var_list.stickmanV.dims.x / 2) + 15) && 
					(Math.abs(var_list.mouse_pos.minus(var_list.stickmanV.pos).y) < (var_list.stickmanV.dims.y / 2) + 30)) {
					var_list.char_select.close();
					var_list.game.open();
					return false;
				}
			}
			return false;
		}

		public boolean mouseMoved (int screenX, int screenY) {
			var_list.mouse_pos = new Coord(screenX, var_list.screen_size.y - screenY);

			return true;
		}
	}

	// this function had to be implemented here because it involves input from mouse
	static void drawInCharSelect(float x, float y, CharacterV character, Array<Sprite> sprites, HashMap<String, Integer> states) {
		character.pos.setPosition(x,y);
		character.scale = 0.9f;

		character.eye_l.pos.setPosition(character.pos.x - 15, character.pos.y + 120);
		character.eye_r.pos.setPosition(character.pos.x + 25, character.pos.y + 120);
		character.mouth.pos.setPosition(character.pos.x + 5, character.pos.y + 90);

		if (var_list.mouse_pos.x >= character.pos.x && var_list.mouse_pos.x <= character.pos.x + 200 && 
			var_list.mouse_pos.y >= 150 && var_list.mouse_pos.y <= 500) {
			character.setState(sprites, states.get("stand/looking right"));

			character.eye_r.pos.setPosition(character.pos.x + 15, character.pos.y + 120);
			character.eye_r.draw();
			character.mouth.draw();
		}
		if (var_list.mouse_pos.x < character.pos.x && var_list.mouse_pos.x >= character.pos.x - 200 &&
			var_list.mouse_pos.y >= 150 && var_list.mouse_pos.y <= 500) {
			character.setState(sprites, states.get("stand/looking left"));
			
			character.eye_l.draw();
			character.mouth.pos.setPosition(character.pos.x, character.pos.y + 90);
			character.mouth.draw();
		}
		if (var_list.mouse_pos.x > character.pos.x + 200 || var_list.mouse_pos.x < character.pos.x - 200 || 
			var_list.mouse_pos.y < 150 || var_list.mouse_pos.y > 500) {
			character.setState(sprites, states.get("stand/looking front"));

			character.eye_l.draw();
			character.eye_r.draw();
			character.mouth.draw();
		}
	}

	// initiates ball object
	void createBall() {
		if ((Project2.var_list.titleCounter % 20) == 0) {
			Ball b = new Ball();
			Project2.var_list.balls.add(b);
		}
	}

	//------------------------ M I S C ------------------------//
	
	// animating title text
	class TitleText extends Animated {
		TitleText() {
			super(Project2.var_list.batch);
		}

		void tick() {
			counter = var_list.titleCounter;

			pos.setPosition(640, 500);
			frameList = var_list.titleFrames;
			delay = 8; 	// plays every 2/15th second

			super.tick();
		}
	}

	// dancing hamster in WIP game screen
	class Hamster extends Animated {
		Hamster() {
			super(Project2.var_list.batch);
		}
		void tick() {
			counter = var_list.gameCounter;

			pos.setPosition(640, 300);
			frameList = var_list.hamFrames;
			scale = 2.5f;
			delay = 3; 	// plays every 1/15th second

			super.tick();
		}
	}

	// little boo icon appearing in title screen
	class Boo {
		boolean booAppeared = false;

		Boo (int x, int y) {
			Coord pos = new Coord(x, y);
			pos.position(var_list.boo_img);
		}
	}

	// plays any sound if sound is on
	void playSound(Sound s) {
		if (!var_list.options.soundOff) {
			s.play(); 
		}
	}

	// alt method for separating sprites
	/* / split texture sheets to use when animating sprite
	ArrayList<Sprite> split(Texture img, ArrayList<Sprite> regionArray, int rows, int columns) {
		TextureRegion[][] textReg = TextureRegion.split(img,
			img.getWidth() / columns,
			img.getHeight() / rows);

		regionArray = new ArrayList<Sprite>(columns * rows);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Sprite s = new Sprite(textReg[i][j]);
				regionArray.add(s);
			}
		}
	return regionArray;
	} */

	//-------------------- V A R I A B L E S --------------------//

	public static VariableList var_list;

	//-----------------------------------------------------------//
	
	@Override
	public void create () {
		var_list = new VariableList();

		// ALT METHOD
		// titleFrames = split(new Texture("titleText/run_run.png"), titleFrames, 5, 1);

		var_list.hamster = new Hamster();

		// TITLE
		var_list.titleText = new TitleText();

		// boo c:
		var_list.b = new Boo(530, 237);

		// INPUT PROCESSOR
		Gdx.input.setInputProcessor(new InputProcessor());
	}

	@Override
	public void render () {
		ScreenUtils.clear(43/225f, 29/255f, 23/255f, 1);
		if (var_list.game.isAtScreen && !var_list.pausing.isAtScreen) {
			var_list.gameCounter++;
		}
		if (!var_list.game.isAtScreen) var_list.gameCounter = 0;
		
		var_list.batch.begin();
		
		// menuFont.draw(batch, "( " + mouse_pos.x/10 + ", " + mouse_pos.y/10 + ")" , 10, 30); 		// displays mouse position
		ArrayList<Object> trash_bin = new ArrayList<Object>();

		// TITLE SCREEN / OPTIONS SCREEN
		if ((var_list.title.isAtScreen || var_list.options.isAtScreen || var_list.char_select.isAtScreen) && !var_list.game.isAtScreen) {	
			var_list.titleCounter++;
			createBall();	// creates array of balls

			// draws balls
			for (Ball ball: var_list.balls) {
				ball.drawBalls(ball, trash_bin);
			}
		}
		else var_list.balls.removeAll(var_list.balls);	// respawns balls when moved to title screen
		
		// menu screen drawing
		var_list.screens.drawScreens();

		// boo appears
		if (var_list.b.booAppeared) var_list.boo_img.draw(var_list.batch);

		for (Effect e : var_list.effects) {
			if (e.draw() == false) trash_bin.add(e);
		}

		var_list.balls.removeAll(trash_bin);
		var_list.effects.removeAll(trash_bin);
		trash_bin.clear();

		var_list.batch.end();
	}
	
	@Override
	public void dispose () {
		var_list.batch.dispose();
		var_list.boo_img.getTexture().dispose();
		var_list.ball_img.getTexture().dispose();
		var_list.eye_img.getTexture().dispose();
		var_list.grey_bg.getTexture().dispose();
		var_list.pause_img.getTexture().dispose();
		for (Ball b: var_list.balls) {
			for (Sprite f: b.ball_splash_frames) f.getTexture().dispose();
		}
		for (Sprite f: var_list.titleFrames) f.getTexture().dispose();
		for (Sprite f: var_list.hamFrames) f.getTexture().dispose();
		for (Sprite s: var_list.stickmanV.sprites) s.getTexture().dispose();

		var_list.boo.dispose();
		var_list.click.dispose();
		var_list.run_90s_a.dispose();
		var_list.run_90s_b.dispose();
		
		var_list.menuFont.dispose();
		var_list.titleFont.dispose();
		var_list.offOption.dispose();
		var_list.onOption.dispose();
	}
}
