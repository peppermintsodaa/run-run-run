package com.cheese;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character {
    SpriteBatch batch;
    int wait_counter;
    float delay;
    CharacterV charV;
    boolean hasJumped = false;
    boolean hasDoubleJumped = false;
    boolean isOnGround = true;
    boolean hasCollided = false;

    double jump_vel = 15;
    double d_jump_vel = 15;
    double jump_accel = -0.5;
    final float WAIT = 25;

    Character (SpriteBatch batch, CharacterV charV) {
        this.batch = batch;
        this.charV = charV;
    }

    float p() {
        return MainGame.var_list.gameCounter/delay;
    }

    void run(float speed) {
        if (isOnGround && !hasCollided) {
            delay = speed;
            final int RUNNING_SPRITES_SIZE = 4;
    
            int which = (int)(p() % RUNNING_SPRITES_SIZE);
            charV.draw(charV.running_sprites, "running", which);
        }
    }

    void jump() {
        if (hasJumped) {
            // max_height = MainGame.screens.game.game.platform.cur_pos.y + 200;
            
            if (!hasCollided) charV.draw(charV.running_sprites, "running", 4); 
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
                if (charV.run_pos.y < charV.run_orig_pos.y) reset();
            }
        }
    }

    void collide() {
        if (isOnGround && !MainGame.screens.pausing.isAtScreen) wait_counter++;

        if (wait_counter > WAIT) {
            setCollided(false);
        }
        else {
            setCollided(true);
        }
    }

    void setCollided(boolean state) {
        hasCollided = state;
    }

    void draw(float speed) {
        run(speed);
        jump();

        if (hasCollided) {
            charV.draw(charV.running_sprites, "running", 0);
            if (isOnGround && !MainGame.screens.pausing.isAtScreen) wait_counter++;
        }
    }

    void reset() {
        charV.run_pos.y = charV.run_orig_pos.y;
        jump_vel = 15;
        d_jump_vel = 15;
        jump_accel = -0.5;
        wait_counter = 0;

        hasJumped = false;
        hasDoubleJumped = false;
        isOnGround = true;
    }
}
