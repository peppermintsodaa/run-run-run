package com.cheese;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.utils.Array;

public class SpriteList {
    SpriteBatch batch;
	Sprite ball_img;
	Sprite boo_img;
	Sprite pause_img;
	Sprite grey_bg;

	Array<Sprite> menu_img;

	Sprite eye_img;
	Sprite mouth_img;

    Sprite platform_img;

	Sprite sky_bg_img;
	Sprite grass1_bg_img;
	Sprite grass2_bg_img;

	Sprite heart_img;

    Array<Sprite> titleFrames;
	Array<Sprite> hamFrames;

    SpriteList() {
        this.batch = new SpriteBatch();

        // SPRITES        
        this.boo_img = new Sprite(new Texture(Gdx.files.internal("boo.png")));

		this.ball_img = new Sprite(new Texture(Gdx.files.internal("ball2.png")));
		ball_img.setScale(0.5f);

		this.menu_img = loadAnimatedSprites("menu");

		this.eye_img = new Sprite(new Texture(Gdx.files.internal("eye.png")));
		this.mouth_img = new Sprite(new Texture(Gdx.files.internal("mouth.png")));

		this.pause_img = new Sprite(new Texture(Gdx.files.internal("pause.png")));

		this.grey_bg = new Sprite(new Texture(Gdx.files.internal("grey.png")));

        this.platform_img = new Sprite(new Texture(Gdx.files.internal("platform.png")));
		
		// BACKGROUND
		this.sky_bg_img = new Sprite(new Texture(Gdx.files.internal("sky.png")));
		this.grass1_bg_img = new Sprite(new Texture(Gdx.files.internal("grass1.png")));
		this.grass2_bg_img = new Sprite(new Texture(Gdx.files.internal("grass2.png")));

		// LIFE
		this.heart_img = new Sprite(new Texture(Gdx.files.internal("life.png")));

        this.hamFrames = loadAnimatedSprites("hams");
		this.titleFrames = loadAnimatedSprites("titleText/titleText");

		// this.pause2 = new Button();
    }

    // loads sprites into an array to use for animating
	Array<Sprite> loadAnimatedSprites(String atlas_name) {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlas_name + ".atlas"));
		Array<Sprite> sprites = atlas.createSprites();
		return sprites;
	}
}
