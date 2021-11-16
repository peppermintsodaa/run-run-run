package com.cheese;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenModes {
	SpriteBatch batch = MainGame.sprites.batch;
	
    public class TitleScreen extends ScreenMode {      
		TitleScreen() {
			super();
		}

        public void draw () {
            if (this.isAtScreen) {
                MainGame.var_list.titleText.tick();
                MainGame.var_list.menuFont.draw(batch, "NEW GAME", VariableList.adjustW(570), VariableList.adjustH(250));
				MainGame.var_list.menuFont.draw(batch, "OPTIONS", VariableList.adjustW(585), VariableList.adjustH(200));
				MainGame.var_list.menuFont.draw(batch, "EXIT", VariableList.adjustW(620), VariableList.adjustH(150));
            }
        }
    }

	public class OptionsScreen extends ScreenMode {
		boolean soundOff = true;
        
		OptionsScreen() {
			super();
			this.isAtScreen = false;
		}

        public void draw () {
            if (this.isAtScreen) {
                MainGame.var_list.titleFont.draw(batch, "OPTIONS", VariableList.adjustW(475), VariableList.adjustH(600));
                MainGame.var_list.menuFont.draw(batch, "SOUND", VariableList.adjustW(400), VariableList.adjustH(400));
				MainGame.var_list.menuFont.draw(batch, "GO BACK", VariableList.adjustW(570), VariableList.adjustH(100));
				if (soundOff) MainGame.var_list.offOption.draw(batch, "OFF", VariableList.adjustW(800), VariableList.adjustH(390));
				else MainGame.var_list.onOption.draw(batch, "ON", VariableList.adjustW(800), VariableList.adjustH(390));
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
                MainGame.var_list.titleFont.draw(batch, "SELECT CHARACTER", VariableList.adjustW(250), VariableList.adjustH(600));
				MainGame.var_list.menuFont.draw(batch, "GO BACK", VariableList.adjustW(570), VariableList.adjustH(100));

				MainGame.drawInCharSelect(VariableList.adjustW(640), VariableList.adjustH(320), 
													MainGame.var_list.stickmanV, 
													MainGame.var_list.stickmanV.standing_sprites, 
													MainGame.var_list.stickmanV.standing_states);
        	}
		}
    }

	public class PauseScreen extends ScreenMode {
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
					MainGame.var_list.titleFont.draw(batch, "PAUSED", VariableList.adjustW(500), VariableList.adjustH(400));
					MainGame.var_list.menuFont.draw(batch, "OPTIONS", VariableList.adjustW(250), VariableList.adjustH(150));
					MainGame.var_list.menuFont.draw(batch, "RESUME", VariableList.adjustW(575), VariableList.adjustH(150));
					MainGame.var_list.menuFont.draw(batch, "GIVE UP", VariableList.adjustW(900), VariableList.adjustH(150));
				}
			}
		}
    }

	public class GameScreen extends ScreenMode {
		GameInstance game;

		GameScreen() {
			super();
			this.isAtScreen = false;
			game = new GameInstance();
			game.setScene();
			game.setCharPosition();
		}

        public void draw () {
            if (this.isAtScreen) {
				// MainGame.var_list.run90s.tick();
				game.tick();
			}
			else {
				game.reset();
				// MainGame.var_list.run90s.music.stop();
			}
		}
    }

	void draw() {
		MainGame.screens.title.draw();
		MainGame.screens.char_select.draw();
		MainGame.screens.game.draw();
		MainGame.screens.pausing.draw();
		MainGame.screens.options.draw();
	}
}
