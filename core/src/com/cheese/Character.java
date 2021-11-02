package com.cheese;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character {
    SpriteBatch batch;
    int counter;
    float delay;
    CharacterV charV;
    boolean hasJumped = false;
    boolean hasDoubleJumped = false;
    boolean isOnGround = true;

    double jump_vel = 14;
    double d_jump_vel = 14;
    double jump_accel = -0.5;

    Character (SpriteBatch batch, CharacterV charV) {
        this.batch = batch;
        this.charV = charV;
    }

    float p() {
        return MainGame.var_list.gameCounter/delay;
    }

    void run(float speed) {
        if (isOnGround) {
            delay = speed;
            final int RUNNING_SPRITES_SIZE = 4;
    
            int which = (int)(p() % RUNNING_SPRITES_SIZE);
            charV.draw(charV.running_sprites, "running", which);
        }
    }

    // void jumpOneTime(double vel) {
    //     charV.run_pos.y = (float)(charV.run_pos.y + vel);
    //     vel += jump_accel;
    //     // jump_count++;
    // }

    void jump() {        
        // double increment;

        if (hasJumped) {
            // max_height = MainGame.screens.game.game.platform.cur_pos.y + 200;
            
            charV.draw(charV.running_sprites, "running", 4); 
            if (!MainGame.screens.pausing.isAtScreen) {
                isOnGround = false;

                if (hasDoubleJumped) {
                    charV.run_pos.y = (float)(charV.run_pos.y + d_jump_vel);
                    d_jump_vel += jump_accel;
                }
                else {
                    charV.run_pos.y = (float)(charV.run_pos.y + jump_vel);
                    jump_vel += jump_accel;
                }
            }

            if (charV.run_pos.y < charV.run_orig_pos.y) {
                reset();
            }
        }
    }

    void reset() {
        charV.run_pos.y = charV.run_orig_pos.y;
        jump_vel = 14;
        d_jump_vel = 14;
        jump_accel = -0.5;

        hasJumped = false;
        hasDoubleJumped = false;
        isOnGround = true;
    }
}
