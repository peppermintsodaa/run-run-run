package com.cheese;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class ScreenModes {
	SpriteBatch batch = MainGame.sprites.batch;
	
    public class TitleScreen extends ScreenMode {     
		TitleScreen() {
			super();
		}

        public void draw () {
            if (this.isAtScreen) {
				Button new_game = MainGame.var_list.title_menu_options.get(0);
				Button options = MainGame.var_list.title_menu_options.get(1);
				Button exit = MainGame.var_list.title_menu_options.get(2);
				
                MainGame.var_list.titleText.tick();

				// NEW GAME
				new_game.setPosition(adjustW(640), adjustH(250), Align.center);
				new_game.draw(batch, 1);

				options.setPosition(adjustW(640), adjustH(200), Align.center);
				options.draw(batch, 1);

				exit.setPosition(adjustW(640), adjustH(150), Align.center);
				exit.draw(batch, 1);
            }
        }
    }

	public class OptionsScreen extends ScreenMode {
		boolean soundOff = false;

		float toggle_sound_w;
		float go_back_w;
        
		OptionsScreen() {
			super();
			this.isAtScreen = false;
		}

        public void draw () {
            if (this.isAtScreen) {
				Button go_back = MainGame.var_list.title_menu_options.get(3);
				
				MainGame.var_list.titleFont.draw(batch, "OPTIONS", adjustW(640), 
																   adjustH(600), 0, Align.center, false);
                MainGame.var_list.menuFont.draw(batch, "SOUND", adjustW(440), 
																adjustH(400), 0, Align.center, false);
				
				go_back.setPosition(adjustW(640), adjustH(100), Align.center);
				go_back.draw(batch, 1);

				if (soundOff) 
					toggle_sound_w = MainGame.var_list.offOption.draw(batch, "OFF", adjustW(840), 
																			 		adjustH(395), 0, Align.center, false).width;
				else toggle_sound_w = MainGame.var_list.onOption.draw(batch, "ON", adjustW(840), 
																  				   adjustH(395), 0, Align.center, false).width;
            }
        }

		public void soundSwitch() {
			if (!soundOff) soundOff = true;
			else soundOff = false;
		}
    }

	public class CharSelectScreen extends ScreenMode {
		CharSelectScreen() {
			super();
			this.isAtScreen = false;
		}

        public void draw () {
            if (this.isAtScreen) {
				Button go_back = MainGame.var_list.title_menu_options.get(3);

                MainGame.var_list.titleFont.draw(batch, "SELECT CHARACTER", adjustW(640), adjustH(600), 0, Align.center, false);

				go_back.setPosition(adjustW(640), adjustH(100), Align.center);
				go_back.draw(batch, 1);

				MainGame.drawInCharSelect(adjustW(640), adjustH(320), 
													MainGame.var_list.stickmanV, 
													MainGame.var_list.stickmanV.standing_sprites, 
													MainGame.var_list.stickmanV.standing_states);
        	}
		}
    }

	public class PauseScreen extends ScreenMode {
		float options_w;
		float resume_w;
		float give_up_w;

        PauseScreen () {
			super();
			this.isAtScreen = false;
		}
        
		@Override
        public void draw () {			
            if (this.isAtScreen) {
				Coord pos = new Coord (0,0);
				// grey background that appears when pausing
				MainGame.sprites.grey_bg.setSize(MainGame.var_list.screen_size.x, MainGame.var_list.screen_size.y);
				pos.position(MainGame.sprites.grey_bg);
				MainGame.sprites.grey_bg.draw(batch);

                if (!MainGame.screens.options.isAtScreen) {
					MainGame.var_list.titleFont.draw(batch, "PAUSED", adjustW(640), adjustH(400), 0, Align.center, false);
					options_w = MainGame.var_list.menuFont.draw(batch, "OPTIONS", adjustW(320), adjustH(150), 0, Align.center, false).width;
					resume_w = MainGame.var_list.menuFont.draw(batch, "RESUME", adjustW(640), adjustH(150), 0, Align.center, false).width;
					give_up_w = MainGame.var_list.menuFont.draw(batch, "GIVE UP", adjustW(960), adjustH(150), 0, Align.center, false).width;
				}
			}
		}
    }

	public class GameScreen extends ScreenMode {
		GameScreen() {
			super();
			this.isAtScreen = false;
			MainGame.var_list.game = new GameInstance();
			MainGame.var_list.game.setScene();
			MainGame.var_list.game.setCharPosition();
		}

        public void draw () {
            if (this.isAtScreen) {
				// MainGame.var_list.run90s.tick();
				MainGame.var_list.game.tick();
			}
			else {
				MainGame.var_list.game.reset();
				// MainGame.var_list.run90s.music.stop();
			}
		}
    }

	public static float adjustW(float orig_width) {
		return (orig_width/1280f)*VariableList.screen_w;
	}

	public static float adjustH(float orig_height) {
		return (orig_height/720f)*VariableList.screen_h;
	}

	void draw() {
		MainGame.screens.title.draw();
		MainGame.screens.char_select.draw();
		MainGame.screens.game.draw();
		MainGame.screens.pausing.draw();
		MainGame.screens.options.draw();
	}
}
