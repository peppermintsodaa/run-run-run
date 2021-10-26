package com.cheese;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenModes {
	SpriteBatch batch = Project2.sprites.batch;
	
    public class TitleScreen extends ScreenMode {      
		TitleScreen() {
			super();
		}

        public void draw () {
            if (this.isAtScreen) {
                Project2.var_list.titleText.tick();
                Project2.var_list.menuFont.draw(batch, "NEW GAME", 570, 250);
				Project2.var_list.menuFont.draw(batch, "OPTIONS", 585, 200);
				Project2.var_list.menuFont.draw(batch, "EXIT", 620, 150);
            }
        }
    }

	public class OptionsScreen extends ScreenMode {
		boolean soundOff = false;
        
		OptionsScreen() {
			super();
			this.isAtScreen = false;
		}

        public void draw () {
            if (this.isAtScreen) {
                Project2.var_list.titleFont.draw(batch, "OPTIONS", 475, 600);
                Project2.var_list.menuFont.draw(batch, "SOUND", 400, 400);
				Project2.var_list.menuFont.draw(batch, "GO BACK", 570, 100);
				if (soundOff) {
					Project2.var_list.offOption.draw(batch, "OFF", 800, 390);
				}
				else Project2.var_list.onOption.draw(batch, "ON", 800, 390);
            }
        }

		public void soundSwitch() {
			if (!soundOff) {
				soundOff = true;
			}
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
                Project2.var_list.titleFont.draw(batch, "SELECT CHARACTER", 250, 600);
				Project2.var_list.menuFont.draw(batch, "GO BACK", 570, 100);

				Project2.drawInCharSelect(640, 320, Project2.var_list.stickmanV, 
													Project2.var_list.stickmanV.standing_sprites, 
													Project2.var_list.stickmanV.standing_states);
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
				Project2.var_list.pause.scale = 0.5f;
				Project2.var_list.run90s.duration = 726f;

                Project2.var_list.titleFont.draw(batch, "W.I.P", 340, 600);
				Project2.var_list.hamster.tick();
				Project2.var_list.run90s.tick();
				Project2.var_list.pause.draw();

				game.tick();
			}
			else {
				game.platform.reset();

				Project2.var_list.run90s.soundCounter = 0;
				Project2.var_list.run90s.music.stop();
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
				pos.position(Project2.sprites.grey_bg);
				Project2.sprites.grey_bg.draw(batch);

                if (!Project2.screens.options.isAtScreen) {
					Project2.var_list.titleFont.draw(batch, "PAUSED", 500, 400);
					Project2.var_list.menuFont.draw(batch, "OPTIONS", 250, 150);
					Project2.var_list.menuFont.draw(batch, "RESUME", 575, 150);
					Project2.var_list.menuFont.draw(batch, "GIVE UP", 900, 150);
				}
			}
		}
    }

	void draw() {
		Project2.screens.title.draw();
		Project2.screens.char_select.draw();
		Project2.screens.game.draw();
		Project2.screens.pausing.draw();
		Project2.screens.options.draw();
	}
}
