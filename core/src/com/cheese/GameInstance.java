package com.cheese;

// import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameInstance {
    Platform platform;
    Background background;
    
    void setScene() {
        platform = new Platform(3, 75, 50);
        background = new Background(25, 640, 360);
    }
    
    void tick() {
        background.draw();
        platform.draw();
    }
}
