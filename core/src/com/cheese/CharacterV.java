package com.cheese;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.utils.Array;

public class CharacterV {
    SpriteBatch batch;

    Array<Sprite> standing_sprites;
    Array<Sprite> running_sprites;

    HashMap<String, Integer> standing_states = new HashMap<>();
    HashMap<String, Integer> running_states = new HashMap<>();

    Coord mouse_pos;

    BodyPart eye_l;
    BodyPart eye_r;
    BodyPart mouth;

    CharacterV (SpriteBatch batch, String eye_img, String mouth_img) {
        this.batch = batch;
        eye_l = new BodyPart(new Sprite(new Texture(Gdx.files.internal(eye_img))), true);
		eye_r = new BodyPart(new Sprite(new Texture(Gdx.files.internal(eye_img))), true);
		// mouth = new BodyPart(new Sprite(new Texture(Gdx.files.internal(mouth_img))), false);
    }

    // list of character states
	void createCharStates(String type) {
        if (type.equals("standing")) {
            standing_states.put("stand/looking front", 0);
            standing_states.put("stand/looking left", 1);
            standing_states.put("stand/looking right", 2);
        }
        if (type.equals("running")) {
            running_states.put("run1", 0);
        }
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
    void setState(String type, Array<Sprite> sprite_list, int state) {
        if (type.equals("standing")) {
            switch (state) {
                case 1: draw(sprite_list, 1);
                        break;
                case 2: draw(sprite_list, 2);
                        break;
                default:draw(sprite_list, 0);
                        break;
            }
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

        boolean isEye;

		BodyPart (Sprite img, boolean isEye) {
			this.img = img;
            this.isEye = isEye;
		}

		boolean draw (float open, float blink) {
            float open_t = open; 
            float blink_t = blink;

            if (isEye) {
                if ((MainGame.var_list.titleCounter % open_t >= 0) && (MainGame.var_list.titleCounter % open_t < blink_t)) {
                    return false;
                }
            }

			pos.position(img);
			img.setScale(scale);
			img.draw(batch);

			return true;
		}
	}
}
