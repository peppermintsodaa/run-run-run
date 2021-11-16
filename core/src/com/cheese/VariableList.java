package com.cheese;

import com.cheese.MainGame.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class VariableList {
    Coord screen_size;
	static float screen_w;
	static float screen_h;
	Coord mouse_pos = new Coord(0,0);

	OrthographicCamera camera;

	Character stickman;
	CharacterV stickmanV;

	Sound boo;
	Sound click;
	Sound run_90s_b;
	Music run_90s_a;

	BGM run90s;

	Boo b;
	ArrayList<Ball> balls = new ArrayList<Ball>();

	ArrayList<Effect> effects = new ArrayList<Effect>();

	TitleText titleText;
	Hamster hamster;

	Button pause;

	BitmapFont menuFont;
	BitmapFont titleFont;
	BitmapFont offOption;
	BitmapFont onOption;
	BitmapFont score;

	boolean isResized = false;

	float currTime;
	int gameCounter;
	int titleCounter = 0;

	static final Color GREEN = new Color(0, 1, 0, 1);
	static final Color RED = new Color(1, 0, 0, 1);

    VariableList() {
        this.screen_size = new Coord(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		this.camera = new OrthographicCamera();

		// MODIFYING SPRITES
		MainGame.sprites.grey_bg.setSize(screen_size.x, screen_size.y);
		MainGame.sprites.grey_bg.setColor(0,0,0,0.3f);

        // ANIMATED SPRITES
		this.stickmanV = new CharacterV(MainGame.sprites.batch, "stickman", "running");
		this.stickman = new Character(MainGame.sprites.batch, stickmanV);

		// AUDIO
		this.boo = Gdx.audio.newSound(Gdx.files.internal("audio/boo.wav"));
		this.click = Gdx.audio.newSound(Gdx.files.internal("audio/click.mp3"));
		// this.run_90s_b = Gdx.audio.newSound(Gdx.files.internal("audio/running90s_before.wav"));
		// this.run_90s_a = Gdx.audio.newMusic(Gdx.files.internal("audio/running90s_after.wav"));

		// this.run90s = new BGM(run_90s_a);

        // TEXT
		FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("font/couture-bld.otf"));
		FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("font/fontClayToy.ttf"));

		FreeTypeFontGenerator.FreeTypeFontParameter parMenu = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parTitle = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parOptionRed = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parOptionGreen = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FreeTypeFontGenerator.FreeTypeFontParameter parScore = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parMenu.size = 30;

		parTitle.size = 90;

		parOptionRed.size = 20;
		parOptionRed.color = RED;

		parOptionGreen.size = 20;
		parOptionGreen.color = GREEN;

		parScore.size = 36;

		this.menuFont = generator1.generateFont(parMenu);
		this.titleFont = generator2.generateFont(parTitle);
		this.offOption = generator1.generateFont(parOptionRed);
		this.onOption = generator1.generateFont(parOptionGreen);
		this.score = generator2.generateFont(parScore);

        // BUTTON
		this.pause = new Button(MainGame.sprites.pause_img);
    }

	public static float adjustW(float orig_width) {
		return (orig_width/1280f)*screen_w;
	}

	public static float adjustH(float orig_height) {
		return (orig_height/720f)*screen_h;
	}
}