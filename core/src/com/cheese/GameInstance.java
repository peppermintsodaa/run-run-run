package com.cheese;

public class GameInstance {
    Platform platform;
    Background background;
    Character character;
    
    void setScene() {
        platform = new Platform(4, 75, 50);
        background = new Background(MainGame.sprites.game_bg_img, 25, 640, 360);
        character = MainGame.var_list.stickman;
        character.charV.run_orig_pos.setPosition(150,160); 
        character.charV.run_pos.setPosition(150,160);
    }
    
    void tick() {
        background.draw();
        platform.draw();
        character.run(4);
        character.jump();
    }

    void reset() {
        background.reset();
        platform.reset();
        character.reset();
    }
}
