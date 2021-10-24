package com.cheese;

import com.badlogic.gdx.audio.Sound;

public class BGM {
    // background music for game
    Sound music;
    int soundCounter;
    float duration; 	// calculate by number of seconds including milliseconds*60

    BGM (Sound music) {
        this.music = music;
        this.soundCounter = 0;
    }

    void tick() {
        if (Project2.var_list.game.isAtScreen) {
            int length = (int)(soundCounter % duration);

            if (!Project2.var_list.pausing.isAtScreen && !Project2.var_list.options.soundOff) 
                soundCounter++;
                if (length == 0) music.play();
            if (Project2.var_list.pausing.isAtScreen || Project2.var_list.options.soundOff) {
                music.pause();
            }
            else music.resume();
        }
    }
}
