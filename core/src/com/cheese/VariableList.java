package com.cheese;

import com.cheese.Project2.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class VariableList {
    Coord screen_size;
	Coord mouse_pos = new Coord(0,0);

	SpriteBatch batch;
	Sprite ball_img;
	Sprite boo_img;
	Sprite pause_img;
	Sprite grey_bg;
	Sprite eye_img;
	Sprite mouth_img;

	Character stickman;
	CharacterV stickmanV;

	Sound boo;
	Sound click;
	Sound run_90s_b;
	Sound run_90s_a;

	BGM run90s;

	Boo b;
	ArrayList<Ball> balls = new ArrayList<Ball>();

	ArrayList<Effect> effects = new ArrayList<Effect>();

	Array<Sprite> titleFrames;
	Array<Sprite> hamFrames;

	TitleText titleText;
	Hamster hamster;

	Button pause;

	BitmapFont menuFont;
	BitmapFont titleFont;
	BitmapFont offOption;
	BitmapFont onOption;

    ScreenModes screens;
    ScreenModes.TitleScreen title;
	ScreenModes.OptionsScreen options;
	ScreenModes.CharSelectScreen char_select;
	ScreenModes.GameInterface game;
	ScreenModes.PauseScreen pausing;

	static final Color GREEN = new Color(0, 1, 0, 1);
	static final Color RED = new Color(1, 0, 0, 1);

    VariableList() {
        this.screen_size = new Coord(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.batch = new SpriteBatch();

		// SPRITES        
        this.boo_img = new Sprite(new Texture(Gdx.files.internal("boo.png")));

		this.ball_img = new Sprite(new Texture(Gdx.files.internal("ball2.png")));
		ball_img.setScale(0.5f);

		this.eye_img = new Sprite(new Texture(Gdx.files.internal("eye.png")));
		this.mouth_img = new Sprite(new Texture(Gdx.files.internal("mouth.png")));

		this.pause_img = new Sprite(new Texture(Gdx.files.internal("pause.png")));

		this.grey_bg = new Sprite(new Texture(Gdx.files.internal("grey.png")), 1, 1);
		grey_bg.setSize(this.screen_size.x, this.screen_size.y);
		grey_bg.setColor(0,0,0,0.3f);

        // ANIMATED SPRITES
		this.stickman = new Character(batch);
		this.stickmanV = new CharacterV(batch, "eye.png", "mouth.png");
        stickmanV.sprites = loadSprites("stickman", "s");
		stickmanV.createCharStates();

		// ALT METHODS
		// stickman_sprites = split(new Texture("stickman.png"), stickman_sprites, 1, 3);
		// hamFrames = split(new Texture("ham.png"), hamFrames, 14, 5);

		// AUDIO
		this.boo = Gdx.audio.newSound(Gdx.files.internal("boo.wav"));
		this.click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
		this.run_90s_b = Gdx.audio.newSound(Gdx.files.internal("running90s_before.wav"));
		this.run_90s_a = Gdx.audio.newSound(Gdx.files.internal("running90s_after.wav"));

		this.run90s = new BGM(run_90s_a);

        // TEXT
		FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("couture-bld.otf"));
		FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("fontClayToy.ttf"));

		FreeTypeFontGenerator.FreeTypeFontParameter parMenu = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parTitle = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parOptionRed = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parOptionGreen = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parMenu.size = 30;

		parTitle.size = 90;

		parOptionRed.size = 20;
		parOptionRed.color = VariableList.RED;

		parOptionGreen.size = 20;
		parOptionGreen.color = VariableList.GREEN;

		this.menuFont = generator1.generateFont(parMenu);
		this.titleFont = generator2.generateFont(parTitle);
		this.offOption = generator1.generateFont(parOptionRed);
		this.onOption = generator1.generateFont(parOptionGreen);

        this.screens = new ScreenModes();
        this.title = screens.new TitleScreen();
		this.options = screens.new OptionsScreen();
		this.char_select = screens.new CharSelectScreen();
		this.game = screens.new GameInterface();
		this.pausing = screens.new PauseScreen();

        this.hamFrames = loadSprites("hams", "s");
		this.titleFrames = loadSprites("titleText/titleText", "s");

        // BUTTON
		this.pause = new Button(pause_img, screen_size.x - 35, screen_size.y - 35);
    }

    // loads sprites into an array to use for animating
	Array<Sprite> loadSprites(String atlas_name, String sprite_name) {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlas_name + ".atlas"));
		atlas.findRegions(sprite_name);
		Array<Sprite> sprites = atlas.createSprites();
		return sprites;
	}
}