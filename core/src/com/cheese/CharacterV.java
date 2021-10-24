package com.cheese;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.utils.Array;

public class CharacterV {
    SpriteBatch batch;
    Array<Sprite> sprites;
    HashMap<String, Integer> states = new HashMap<>();

    Coord mouse_pos;

    BodyPart eye_l;
    BodyPart eye_r;
    BodyPart mouth;

    CharacterV (SpriteBatch batch, String eye_img, String mouth_img) {
        this.batch = batch;
        eye_l = new BodyPart(new Sprite(new Texture(Gdx.files.internal(eye_img))));
		eye_r = new BodyPart(new Sprite(new Texture(Gdx.files.internal(eye_img))));
		mouth = new BodyPart(new Sprite(new Texture(Gdx.files.internal(mouth_img))));
    }

    // list of character states
	void createCharStates() {
		states.put("stand/looking front", 0);
		states.put("stand/looking left", 1);
		states.put("stand/looking right", 2);
	}

    Coord pos = new Coord(0,0);
    Coord dims = new Coord(0,0);
    float scale = 1;

    Sprite curFrame;
    int duration;
    int ticks;

    float p () {
        return ticks/(float)duration;
    }

    // sets state of character based on index corresponding to hash map
    void setState(Array<Sprite> sprite_list, int state) {
        switch (state) {
            case 1: draw(sprite_list, 1);
                    break;
            case 2: draw(sprite_list, 2);
                    break;
            default:draw(sprite_list, 0);
                    break;
        }
    }

    // draws character in current state
    boolean draw(Array<Sprite> sprite_list, int state) {
        curFrame = sprite_list.get(state);
        curFrame.setScale(scale);
        pos.position(curFrame);
        dims.dimensions(curFrame, scale);
        curFrame.draw(batch);
        return true;
    }

    // body parts to add onto character
	class BodyPart {
		Coord pos = new Coord(0,0);
		float scale = 0.25f;
		Sprite img;

		BodyPart (Sprite img) {
			this.img = img;
		}

		boolean draw () {
			pos.position(img);
			img.setScale(scale);
			img.draw(batch);

			return true;
		}
	}
}
