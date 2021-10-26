package com.cheese;

// import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameInstance {
    Platform platform;
    
    void setScene() {
        platform = new Platform(3, 75, 50);
    }
    
    void tick() {
        platform.draw();
    }
}
