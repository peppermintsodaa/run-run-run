package com.cheese;

public class ScreensList {
    ScreenModes screens;
    ScreenModes.TitleScreen title;
	ScreenModes.OptionsScreen options;
	ScreenModes.CharSelectScreen char_select;
	ScreenModes.GameScreen game;
	ScreenModes.PauseScreen pausing;

    ScreensList() {
        this.screens = new ScreenModes();
        this.title = screens.new TitleScreen();
		this.options = screens.new OptionsScreen();
		this.char_select = screens.new CharSelectScreen();
		this.game = screens.new GameScreen();
		this.pausing = screens.new PauseScreen();
    }
}
