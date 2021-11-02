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
                MainGame.var_list.menuFont.draw(batch, "NEW GAME", 570, 250);
				MainGame.var_list.menuFont.draw(batch, "OPTIONS", 585, 200);
				MainGame.var_list.menuFont.draw(batch, "EXIT", 620, 150);
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
                MainGame.var_list.titleFont.draw(batch, "OPTIONS", 475, 600);
                MainGame.var_list.menuFont.draw(batch, "SOUND", 400, 400);
				MainGame.var_list.menuFont.draw(batch, "GO BACK", 570, 100);
				if (soundOff) MainGame.var_list.offOption.draw(batch, "OFF", 800, 390);
				else MainGame.var_list.onOption.draw(batch, "ON", 800, 390);
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
                MainGame.var_list.titleFont.draw(batch, "SELECT CHARACTER", 250, 600);
				MainGame.var_list.menuFont.draw(batch, "GO BACK", 570, 100);

				MainGame.drawInCharSelect(640, 320, MainGame.var_list.stickmanV, 
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
				pos.position(MainGame.sprites.grey_bg);
				MainGame.sprites.grey_bg.draw(batch);

                if (!MainGame.screens.options.isAtScreen) {
					MainGame.var_list.titleFont.draw(batch, "PAUSED", 500, 400);
					MainGame.var_list.menuFont.draw(batch, "OPTIONS", 250, 150);
					MainGame.var_list.menuFont.draw(batch, "RESUME", 575, 150);
					MainGame.var_list.menuFont.draw(batch, "GIVE UP", 900, 150);
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
		}

        public void draw () {
            if (this.isAtScreen) {
				MainGame.var_list.pause.scale = 0.5f;
				MainGame.var_list.run90s.duration = 726f;

                MainGame.var_list.titleFont.draw(batch, "W.I.P", 340, 600);
				MainGame.var_list.run90s.tick();

				game.tick();

				// MainGame.var_list.hamster.tick();
				MainGame.var_list.pause.draw();
			}
			else {
				game.reset();

				MainGame.var_list.run90s.soundCounter = 0;
				MainGame.var_list.run90s.music.stop();
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
