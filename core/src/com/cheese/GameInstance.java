package com.cheese;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameInstance {
    Platform platform;
    Background background;
    Character character;
    Obstacle obstacle;

    BitmapFont scoreText = MainGame.var_list.score;
    int score = 0;

    boolean isStopped = false;

    float sprite_height;
    final int OFFSET = 7;
    
    void setScene() {
        platform = new Platform(6, 75, 35);
        background = new Background(MainGame.sprites.game_bg_img, 25, 640, 360);
        character = MainGame.var_list.stickman;
        obstacle = new Obstacle(MainGame.sprites.obstacle_img, 6, 1350, 200);

        sprite_height = character.charV.running_sprites.get(0).getHeight();
    }

    void setCharPosition() {
        float char_pos_y = platform.first_pos.y + sprite_height/2 + OFFSET*platform.scale;

        character.charV.run_orig_pos.setPosition(150, char_pos_y);
        character.charV.run_pos.setPosition(150, char_pos_y);
    }
    
    void tick() {
        background.draw();
        platform.draw();
        obstacle.draw();
        character.run(4);
        character.jump();
        updateScore();

        collide(obstacle);
    }

    void collide(Obstacle obs) {
        if (!obs.recordCollide && Math.abs(obs.pos.minus(character.charV.run_pos).x) < obs.img.getWidth()/2 &&
                                  Math.abs(obs.pos.minus(character.charV.run_pos).y) < obs.img.getHeight()/2) {
            character.collide();
            isStopped = true;

            if (character.wait_counter > character.WAIT) {
                character.wait_counter = 0;
                isStopped = false;
                obs.recordCollide = true;
            }
        }
    }

    void updateScore() {
        scoreText.draw(MainGame.sprites.batch, "SCORE: " + score, 10, MainGame.var_list.screen_size.y - 10);
    }

    void reset() {
        background.reset();
        platform.reset();
        obstacle.reset();
        character.reset();
        score = 0;
    }
}
