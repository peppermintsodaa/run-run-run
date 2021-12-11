package com.cheese;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.HashMap;

public class MainGame extends Game {

	public class InputProcessor extends InputAdapter
	{
		public boolean keyDown (int keycode) {
			if (screens.game.isAtScreen) {
				if (!screens.pausing.isAtScreen && !var_list.game.isStopped) {
					if (keycode == Keys.SPACE) {
						if (var_list.game.character.hasJumped) 
							var_list.game.character.hasDoubleJumped = true;
						var_list.game.character.hasJumped = true;
					}
					if (keycode == Keys.ESCAPE) {
						screens.pausing.open();
					}
				}
				else if (keycode == Keys.ESCAPE) screens.pausing.close();
			}
			return false;
		}

		public boolean touchDown (int screenX, int screenY, int pointer, int button)
		{ 
			var_list.mouse_pos = new Coord(screenX, var_list.screen_size.y - screenY);
			float menu_dims_w = var_list.actors.get(0).getWidth()/1.5f;
			float menu_dims_h = var_list.actors.get(0).getHeight()/1.5f;

			if (screens.title.isAtScreen) {
				if (Math.abs(var_list.mouse_pos.x - adjustW(640)) < (menu_dims_w/1.5f) &&
					Math.abs(var_list.mouse_pos.y - adjustH(250)) < (menu_dims_h/1.5f))  {
					var_list.b.booAppeared = true;
					playSound(var_list.boo);
					return false;
				}
				// clicking on OPTIONS
				if (Math.abs(var_list.mouse_pos.x - adjustW(640)) < (menu_dims_w/1.5f) &&
					Math.abs(var_list.mouse_pos.y - adjustH(200)) < (menu_dims_h/1.5f))  {
					playSound(var_list.click);
					screens.title.close();
					screens.options.open();
					return false;
				}
				// clicking on EXIT
				if (Math.abs(var_list.mouse_pos.x - adjustW(640)) < (menu_dims_w/1.5f) &&
					Math.abs(var_list.mouse_pos.y - adjustH(150)) < (menu_dims_h/1.5f))  {
					playSound(var_list.click);
					Gdx.app.exit();
					return false;
				}
		 	}
			if (screens.options.isAtScreen) {
				// configuring sound
				if (Math.abs(var_list.mouse_pos.x - adjustW(840)) < (screens.options.toggle_sound_w/1.5f) &&
					Math.abs(var_list.mouse_pos.y - (adjustH(395) - var_list.TOGGLE_FONT_H/2)) < (var_list.TOGGLE_FONT_H/1.5f))  {
					screens.options.soundSwitch();
					playSound(var_list.click);
					return false;
				}
				// clicking on GO BACK
				if (Math.abs(var_list.mouse_pos.x - adjustW(640)) < (menu_dims_w/1.5f) &&
					Math.abs(var_list.mouse_pos.y - adjustH(100)) < (menu_dims_h/1.5f))  {
					playSound(var_list.click);
					screens.options.close();
					if (!screens.game.isAtScreen) {
						screens.title.open();
					}
					return false;
				}
			}
			if (screens.char_select.isAtScreen) {
				// clicking on GO BACK
				if (Math.abs(var_list.mouse_pos.x - adjustW(640)) < (menu_dims_w/1.5f) &&
					Math.abs(var_list.mouse_pos.y - adjustH(100)) < (menu_dims_h/1.5f))  {
					playSound(var_list.click);
					screens.char_select.close();
					screens.title.open();
					return false;
				}
				// clicking on CHARACTER
				if ((Math.abs(var_list.mouse_pos.minus(var_list.stickmanV.stand_pos).x) 
													< (var_list.stickmanV.stand_dims.x / 2) + 15) && 
					(Math.abs(var_list.mouse_pos.minus(var_list.stickmanV.stand_pos).y) 
													< (var_list.stickmanV.stand_dims.y / 2) + 30)) {
					// playSound(var_list.run_90s_b);
					return false;
				}
			}
			if (screens.game.isAtScreen) {
				// clicking on pause button
				if (var_list.mouse_pos.minus(var_list.pause.pos).radius() < var_list.pause.bounds) {
					if (!screens.pausing.isAtScreen) {
						playSound(var_list.click);
						screens.pausing.open();
					}
					return false;
				}
			}
			if (screens.pausing.isAtScreen && !screens.options.isAtScreen) {
				// clicking on OPTIONS
				if (Math.abs(var_list.mouse_pos.x - adjustW(320)) < (screens.pausing.options_w/1.5f) &&
					Math.abs(var_list.mouse_pos.y - (adjustH(150) - var_list.MENU_FONT_H/2)) < (var_list.MENU_FONT_H/1.5f)) {
					screens.options.open();
					playSound(var_list.click);
				}
				// clicking on RESUME
				if (Math.abs(var_list.mouse_pos.x - adjustW(640)) < (screens.pausing.options_w/1.5f) &&
					Math.abs(var_list.mouse_pos.y - (adjustH(150) - var_list.MENU_FONT_H/2)) < (var_list.MENU_FONT_H/1.5f)) {
					screens.pausing.close();
					playSound(var_list.click);
				}
				// clicking on GIVE UP
				if (Math.abs(var_list.mouse_pos.x - adjustW(960)) < (screens.pausing.options_w/1.5f) &&
					Math.abs(var_list.mouse_pos.y - (adjustH(150) - var_list.MENU_FONT_H/2)) < (var_list.MENU_FONT_H/1.5f)) {
					screens.pausing.close();
					screens.game.close();
					playSound(var_list.click);
					screens.title.open();
				}
			}
		 	return true;
		}

		public boolean touchUp (int screenX, int screenY, int pointer, int button)
		{ 
			var_list.mouse_pos = new Coord(screenX, var_list.screen_size.y - screenY);
			float menu_dims_w = var_list.actors.get(0).getWidth()/1.5f;
			float menu_dims_h = var_list.actors.get(0).getHeight()/1.5f;

			if (screens.title.isAtScreen) {
				// clicking on NEW GAME
				if (Math.abs(var_list.mouse_pos.x - adjustW(640)) < (menu_dims_w/1.5f) &&
					Math.abs(var_list.mouse_pos.y - adjustH(250)) < (menu_dims_h/1.5f))  {
					var_list.b.booAppeared = false;
					screens.title.close();
					screens.char_select.open();
					return false;
				}
			}
			if (screens.char_select.isAtScreen) {
				// clicking on CHARACTER
				if ((Math.abs(var_list.mouse_pos.minus(var_list.stickmanV.stand_pos).x) 
													< (var_list.stickmanV.stand_dims.x / 2) + 15) && 
					(Math.abs(var_list.mouse_pos.minus(var_list.stickmanV.stand_pos).y) 
													< (var_list.stickmanV.stand_dims.y / 2) + 30)) {
					screens.char_select.close();
					screens.game.open();
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

	void setUpMenuButtons (ArrayList<Actor> actors) {
		Coord dims = new Coord(MainGame.sprites.menu_img.get(0).getWidth(), MainGame.sprites.menu_img.get(0).getHeight()); 

		for (Actor actor : actors) {
			if (actor.getName().equals("new_game")) {
				actor.setBounds(640, 250, dims.x, dims.y);
			}
			if (actor.getName().equals("options")) {
				actor.setBounds(640, 200, dims.x, dims.y);
			}
			if (actor.getName().equals("new_game")) {
				actor.setBounds(640, 150, dims.x, dims.y);
			}
		}
	}

	// this function had to be implemented here because it involves input from mouse
	static void drawInCharSelect(float x, float y, CharacterV character, Array<Sprite> sprites, HashMap<String, Integer> states) {
		character.stand_pos.setPosition(x,y);
		character.scale = 0.9f;

		character.eye_l.pos.setPosition(character.stand_pos.x - 15, character.stand_pos.y + 120);
		character.eye_r.pos.setPosition(character.stand_pos.x + 25, character.stand_pos.y + 120);
		character.mouth.pos.setPosition(character.stand_pos.x + 5, character.stand_pos.y + 90);

		if (var_list.mouse_pos.x >= character.stand_pos.x && var_list.mouse_pos.x <= character.stand_pos.x + 200 && 
			var_list.mouse_pos.y >= 150 && var_list.mouse_pos.y <= 500) {
			character.setState("standing", sprites, states.get("stand/looking right"));

			character.eye_r.pos.setPosition(character.stand_pos.x + 15, character.stand_pos.y + 120);
			character.eye_r.draw(250, 50);
			character.mouth.draw();
		}
		if (var_list.mouse_pos.x < character.stand_pos.x && var_list.mouse_pos.x >= character.stand_pos.x - 200 &&
			var_list.mouse_pos.y >= 150 && var_list.mouse_pos.y <= 500) {
			character.setState("standing", sprites, states.get("stand/looking left"));
			
			character.eye_l.draw(250, 50);
			character.mouth.pos.setPosition(character.stand_pos.x, character.stand_pos.y + 90);
			character.mouth.draw();
		}
		if (var_list.mouse_pos.x > character.stand_pos.x + 200 || var_list.mouse_pos.x < character.stand_pos.x - 200 || 
			var_list.mouse_pos.y < 150 || var_list.mouse_pos.y > 500) {
			character.setState("standing", sprites, states.get("stand/looking front"));

			character.eye_l.draw(250, 50);
			character.eye_r.draw(250, 50);
			character.mouth.draw();
		}
	}

	// initiates ball object
	void createBall() {
		if ((var_list.titleCounter % 20) == 0) {
			Ball b = new Ball();
			var_list.balls.add(b);
		}
	}

	//------------------------ M I S C ------------------------//
	
	// animating title text
	class TitleText extends Animated {
		TitleText() {
			super(sprites.batch);
		}

		void tick() {
			counter = var_list.titleCounter;

			pos.setPosition(adjustW(640), adjustH(500));
			frameList = sprites.titleFrames;
			delay = 20; 	// plays every 1/3rd second

			super.tick();
		}
	}

	// little boo icon appearing in title screen
	class Boo {
		boolean booAppeared = false;
		Coord pos = new Coord(0,0);

		void draw() {
			pos.position(sprites.boo_img);
			sprites.boo_img.draw(sprites.batch);
		}
	}

	// plays any sound if sound is on
	void playSound(Sound s) {
		if (!screens.options.soundOff) {
			s.play(); 
		}
	}

	
	public static float adjustW(float orig_width) {
		return (orig_width/1280f)*VariableList.screen_w;
	}

	public static float adjustH(float orig_height) {
		return (orig_height/720f)*VariableList.screen_h;
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
	public static SpriteList sprites;
	public static ScreensList screens;

	//-----------------------------------------------------------//
	
	@Override
	public void create () {
		sprites = new SpriteList();
		var_list = new VariableList();
		screens = new ScreensList();

		// ALT METHOD
		// titleFrames = split(new Texture("titleText/run_run.png"), titleFrames, 5, 1);

		// TITLE
		var_list.titleText = new TitleText();

		// boo c:
		var_list.b = new Boo();

		// INPUT PROCESSOR
		Gdx.input.setInputProcessor(new InputProcessor());

		setUpMenuButtons(var_list.actors);

		var_list.camera.setToOrtho(false);
		var_list.camera.update();
	}

	@Override
	public void resize(int width, int height) {
		var_list.camera.setToOrtho(false, width, height);
		var_list.camera.update();

		var_list.screen_size.setPosition(width, height);

		VariableList.screen_w = width;
		VariableList.screen_h = height;
		// var_list.screen_size.setPosition(width, height);
		// System.out.println(var_list.screen_size.x + ", " + var_list.screen_size.y);

		// if (var_list.screen_size.x != 1280 || var_list.screen_size.y != 720) var_list.isResized = true;
	}

	@Override
	public void render () {
		ScreenUtils.clear(43/225f, 29/255f, 23/255f, 1);

		if (screens.game.isAtScreen && !screens.pausing.isAtScreen) {
			var_list.playtime++;
			if (!var_list.game.isStopped) var_list.gameCounter++;
		}
		if (!screens.game.isAtScreen) var_list.gameCounter = 0;

		sprites.batch.setProjectionMatrix(var_list.camera.combined);
		var_list.camera.update();
		
		sprites.batch.begin();
		
		// var_list.menuFont.draw(sprites.batch, "( " + var_list.mouse_pos.x/10 + ", " + var_list.mouse_pos.y/10 + ")" , 10, 30); 		// displays mouse position
		ArrayList<Object> trash_bin = new ArrayList<Object>();

		// TITLE SCREEN / OPTIONS SCREEN
		if ((screens.title.isAtScreen || screens.options.isAtScreen 
									  || screens.char_select.isAtScreen) && !screens.game.isAtScreen) {	
			var_list.titleCounter++;
			createBall();	// creates array of balls

			// draws balls
			for (Ball ball: var_list.balls) {
				ball.draw(ball, trash_bin);
			}
		}
		else var_list.balls.removeAll(var_list.balls);	// respawns balls when moved to title screen
		
		// menu screen drawing
		screens.screens.draw();

		// boo appears
		var_list.b.pos.setPosition(adjustW(640) - var_list.actors.get(0).getWidth()/1.5f, 
								   adjustH(250));
		if (var_list.b.booAppeared) var_list.b.draw();

		for (Effect e : var_list.effects) {
			if (e.draw() == false) trash_bin.add(e);
		}

		var_list.balls.removeAll(trash_bin);
		var_list.effects.removeAll(trash_bin);
		trash_bin.clear();

		sprites.batch.end();
	}
	
	@Override
	public void dispose () {
		sprites.batch.dispose();
		sprites.boo_img.getTexture().dispose();
		sprites.ball_img.getTexture().dispose();
		sprites.eye_img.getTexture().dispose();
		sprites.grey_bg.getTexture().dispose();
		sprites.pause_img.getTexture().dispose();
		sprites.sky_bg_img.getTexture().dispose();
		sprites.grass1_bg_img.getTexture().dispose();
		sprites.grass2_bg_img.getTexture().dispose();
		for (Sprite f: sprites.titleFrames) f.getTexture().dispose();
		for (Sprite f: sprites.hamFrames) f.getTexture().dispose();
		for (Sprite s: var_list.stickmanV.standing_sprites) s.getTexture().dispose();
		for (Sprite s: var_list.stickmanV.running_sprites) s.getTexture().dispose();

		var_list.boo.dispose();
		var_list.click.dispose();
		// var_list.run_90s_a.dispose();
		// var_list.run_90s_b.dispose();
		
		var_list.menuFont.dispose();
		var_list.titleFont.dispose();
		var_list.offOption.dispose();
		var_list.onOption.dispose();

		var_list.stage.dispose();
	}
}
