package com.cheese;

import java.util.HashMap;

// import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// import com.badlogic.gdx.Gdx;

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

    CharacterV (SpriteBatch batch, String sprite_name_s, String sprite_name_r) {
        this.batch = batch;

        this.standing_sprites = MainGame.sprites.loadAnimatedSprites(sprite_name_s);
		this.createCharStates("standing");
        this.running_sprites = MainGame.sprites.loadAnimatedSprites(sprite_name_r);
		this.createCharStates("running");
        // eye_l = new BodyPart(new Sprite(new Texture(Gdx.files.internal(eye_img))), true);
		// eye_r = new BodyPart(new Sprite(new Texture(Gdx.files.internal(eye_img))), true);
		// mouth = new BodyPart(new Sprite(new Texture(Gdx.files.internal(mouth_img))), false);
    }

    float getBounds(String type) {
        if (type.equals("width")) return running_sprites.get(0).getWidth()/2;
        if (type.equals("height")) return running_sprites.get(0).getHeight()/2;
        return 0f;
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
            running_states.put("run2", 1);
            running_states.put("run3", 2);
            running_states.put("run4", 3);
            running_states.put("jump1", 4);
        }
	}

    Coord stand_pos = new Coord(0,0);
    Coord stand_dims = new Coord(0,0);

    Coord run_orig_pos = new Coord(0,0);
    Coord run_pos = new Coord(0,0);
    Coord run_dims = new Coord(0,0);
    float scale = 1;

    Sprite curFrame;

    // sets state of character based on index corresponding to hash map
    void setState(String type, Array<Sprite> sprite_list, int state) {
        if (type.equals("standing")) {
            switch (state) {
                case 1: draw(sprite_list, type, 1);
                        break;
                case 2: draw(sprite_list, type, 2);
                        break;
                default:draw(sprite_list, type, 0);
                        break;
            }
        }
        if (type.equals("running")) {
            switch (state) {
                case 0: draw(sprite_list, type, 0);
                        break;
                case 1: draw(sprite_list, type, 1);
                        break;
                case 2: draw(sprite_list, type, 2);
                        break;
                case 3: draw(sprite_list, type, 3);
                        break;        
                case 4: draw(sprite_list, type, 4);
                        break;
            }
        }
    }

    // draws character in current state
    boolean draw(Array<Sprite> sprite_list, String type, int state) {
        curFrame = sprite_list.get(state);
        curFrame.setScale(scale);
        if (type.equals("standing")) {
            stand_pos.position(curFrame);
            stand_dims.dimensions(curFrame, scale);
        }
        if (type.equals("running")) {
            run_pos.position(curFrame);
            run_dims.dimensions(curFrame, scale);
        }
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
            float open_t = open;        // how long character's eye stay open
            float blink_t = blink;      // how long character's eye blinks.

            // open_t > blink_t always

            if (isEye) {
                if ((MainGame.var_list.titleCounter % open_t >= 0) && 
                    (MainGame.var_list.titleCounter % open_t < blink_t)) {
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
