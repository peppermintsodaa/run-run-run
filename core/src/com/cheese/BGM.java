package com.cheese;

import com.badlogic.gdx.audio.Music;

public class BGM {
    // background music for game
    Music music;

    BGM (Music music) {
        this.music = music;
        this.music.setLooping(true);
    }

    void tick() {
        if (MainGame.screens.game.isAtScreen) {
            if (MainGame.screens.pausing.isAtScreen || MainGame.screens.options.soundOff) {
                music.pause();
            }
            else music.play();
        }
    }
}
