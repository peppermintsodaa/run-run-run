package com.cheese;

public class GameInstance {
    Platform platform;
    Background background;
    Character character;
    Obstacle obstacle;

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
        character.charV.run_orig_pos.setPosition(150, platform.first_pos.y + sprite_height/2 + OFFSET*platform.scale);
        character.charV.run_pos.setPosition(150, platform.first_pos.y + sprite_height/2 + OFFSET*platform.scale);
    }
    
    void tick() {
        background.draw();
        platform.draw();
        obstacle.draw();
        character.run(4);
        character.jump();

        collide(obstacle);
    }

    void collide(Obstacle obs) {
        // float obs_bounds_width = character.charV.getBounds("width") + obs.img.getWidth()/2;
        // float obs_bounds_height = character.charV.getBounds("height") + obs.img.getHeight()/2;

        if (!obs.recordCollide && Math.abs(obs.pos.minus(character.charV.run_pos).x) < obs.img.getWidth()/2 &&
                                  Math.abs(obs.pos.minus(character.charV.run_pos).y) < obs.img.getHeight()/2) {
            character.collide();
            setCollided(true);

            if (character.wait_counter > character.WAIT) {
                character.wait_counter = 0;
                setCollided(false);
                obs.recordCollide = true;
            }
        }
    }

    void setCollided(boolean state) {
        background.setCollided(state);
        platform.setCollided(state);
        obstacle.setCollided(state);
    }

    void reset() {
        background.reset();
        platform.reset();
        obstacle.reset();
        character.reset();
    }
}
