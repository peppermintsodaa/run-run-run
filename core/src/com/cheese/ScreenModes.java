package com.cheese;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class ScreenModes {
	SpriteBatch batch = MainGame.sprites.batch;
	
    public class TitleScreen extends ScreenMode {     
		float new_game_w;
		float options_w;
		float exit_w;

		TitleScreen() {
			super();
		}

        public void draw () {
            if (this.isAtScreen) {
                MainGame.var_list.titleText.tick();
                new_game_w = MainGame.var_list.menuFont.draw(batch, "NEW GAME", adjustW(640), 
																			  	adjustH(250), 0, Align.center, false).width;
				options_w = MainGame.var_list.menuFont.draw(batch, "OPTIONS", adjustW(640), 
																			  adjustH(200), 0, Align.center, false).width;
				exit_w = MainGame.var_list.menuFont.draw(batch, "EXIT", adjustW(640), 
																		adjustH(150), 0, Align.center, false).width;
            }
        }
    }

	public class OptionsScreen extends ScreenMode {
		boolean soundOff = true;

		float toggle_sound_w;
		float go_back_w;
        
		OptionsScreen() {
			super();
			this.isAtScreen = false;
		}

        public void draw () {
            if (this.isAtScreen) {
				MainGame.var_list.titleFont.draw(batch, "OPTIONS", adjustW(640), 
																   adjustH(600), 0, Align.center, false);
                MainGame.var_list.menuFont.draw(batch, "SOUND", adjustW(440), 
																adjustH(400), 0, Align.center, false);
				go_back_w = MainGame.var_list.menuFont.draw(batch, "GO BACK", adjustW(640), 
																 			  adjustH(100), 0, Align.center, false).width;
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
		float go_back_w;

        CharSelectScreen() {
			super();
			this.isAtScreen = false;
		}

        public void draw () {
            if (this.isAtScreen) {
                MainGame.var_list.titleFont.draw(batch, "SELECT CHARACTER", adjustW(640), adjustH(600), 0, Align.center, false);
				go_back_w = MainGame.var_list.menuFont.draw(batch, "GO BACK", adjustW(640), adjustH(100), 0, Align.center, false).width;

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
