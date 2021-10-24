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

	Sprite eye_img;
	Sprite mouth_img;

    Sprite platform_img;

    Array<Sprite> titleFrames;
	Array<Sprite> hamFrames;

    SpriteList() {
        this.batch = new SpriteBatch();

        // SPRITES        
        this.boo_img = new Sprite(new Texture(Gdx.files.internal("boo.png")));

		this.ball_img = new Sprite(new Texture(Gdx.files.internal("ball2.png")));
		ball_img.setScale(0.5f);

		this.eye_img = new Sprite(new Texture(Gdx.files.internal("eye.png")));
		this.mouth_img = new Sprite(new Texture(Gdx.files.internal("mouth.png")));

		this.pause_img = new Sprite(new Texture(Gdx.files.internal("pause.png")));

		this.grey_bg = new Sprite(new Texture(Gdx.files.internal("grey.png")), 1, 1);

        this.platform_img = new Sprite(new Texture(Gdx.files.internal("platform.png")));

        this.hamFrames = loadSprites("hams", "s");
		this.titleFrames = loadSprites("titleText/titleText", "s");
    }

    // loads sprites into an array to use for animating
	Array<Sprite> loadSprites(String atlas_name, String sprite_name) {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlas_name + ".atlas"));
		atlas.findRegions(sprite_name);
		Array<Sprite> sprites = atlas.createSprites();
		return sprites;
	}
}
