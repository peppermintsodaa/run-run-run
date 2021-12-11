package com.cheese;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;

public class GameInstance {
    Platform platform;
    Background bg_front1, bg_front2, bg_back;

    Character character;
    ArrayList<Obstacle> obstacles;
    // ArrayList<Collectable> collectables;

    Button2 pause;
    Button pause2;

    Coord screen_size = MainGame.var_list.screen_size;

    Map map;

    Lives lives;

    BitmapFont scoreText = MainGame.var_list.score;
    int score = 0;

    boolean isStopped = false;
    boolean gameOver = false;
    int wait_counter = 20;

    float sprite_height;
    final int OFFSET = 7;
    
    void setScene() {
        platform = new Platform(6, MainGame.adjustW(75), MainGame.adjustH(35));

        bg_front1 = new Background(MainGame.sprites.grass2_bg_img, 10);
        bg_front2 = new Background(MainGame.sprites.grass1_bg_img, 7);
        bg_back = new Background(MainGame.sprites.sky_bg_img, 3);

        character = MainGame.var_list.stickman;
        obstacles = new ArrayList<Obstacle>();

        pause = MainGame.var_list.pause;

        map = new Map();
        map.loadMap();
        for (int i = 0; i < map.obs_list.length; ++i) map.loadObstacles(i, 6);
        // map.loadCollectables();

        lives = new Lives();
        lives.create();

        sprite_height = character.charV.running_sprites.get(0).getHeight();
    }
    
    void setCharPosition() {
        float char_pos_y = platform.first_pos.y + sprite_height/2 + OFFSET*platform.scale;

        character.charV.run_orig_pos.setPosition(150, char_pos_y);
        character.charV.run_pos.setPosition(150, char_pos_y);
    }
    
    class Map {
        String[] obs_list;
        String[] collectables_list;

        Sprite img;

        int when;
        float height;

        void loadMap() {
            String raw = new String((Gdx.files.internal("obstacles.txt")).readBytes());
            obs_list = raw.trim().split("\n\n");

            // raw = new String((Gdx.files.internal("collectables.txt")).readBytes());
            // collectables_list = raw.trim().split("\n\n");
        }
    
        void loadObstacles(int num, float speed) {
            String[] lines = obs_list[num].split("\n");
            
            for (int i = 0; i < lines.length; ++i) {    
                String line = lines[i];
                String value = line.substring(2);

                if (line.trim().startsWith("@")) 
                    when = Integer.valueOf(value).intValue();
                if (line.trim().startsWith("#")) 
                    img = new Sprite(new Texture(Gdx.files.internal(value + ".png")));
                if (line.trim().startsWith("h"))
                    height = Integer.valueOf(value).floatValue();

            }
            obstacles.add(new Obstacle(img, when, speed, height));
        }
    }

    class Lives {
        ArrayList<Sprite> lives;
        ArrayList<Coord> pos;

        Sprite img = MainGame.sprites.heart_img;
        final int NUMBER_OF_LIVES = 3;
        int count = 0;

        Lives() {
            lives = new ArrayList<Sprite>();
            pos = new ArrayList<Coord>();
        }
        
        void create() {
            while (lives.size() < NUMBER_OF_LIVES) lives.add(img);

            for (int i = 0; i < NUMBER_OF_LIVES; i++) {
                pos.add(new Coord(screen_size.x - (60*i) - 120, screen_size.y - 50));
            }
        }

        void update() {
            for (int i = 0; i < lives.size(); i++) {
                pos.get(i).position(lives.get(i));
                lives.get(i).draw(MainGame.sprites.batch);
            }

            for (int i = 0; i < pos.size(); i++) 
                pos.get(i).setPosition(screen_size.x - (60*i) - 120, screen_size.y - 50);

            if (character.hasCollided && count < 1) {
                lives.remove(0);
                count++;
            }
            else if (!character.hasCollided) count = 0;
            
            if (lives.size() == 0) gameOver = true;
        }

        void reset() {
            while (lives.size() < NUMBER_OF_LIVES) lives.add(img);
        }
    }
    
    void tick() {
        screen_size = MainGame.var_list.screen_size;
        
        float orig_w = VariableList.screen_w;
        float orig_h = VariableList.screen_h;

        if (!isStopped) MainGame.var_list.camera.setToOrtho(false, orig_w, orig_h);

        bg_back.draw();
        bg_front2.draw();
        bg_front1.draw();

        platform.draw();

        for (Obstacle obs : obstacles) {
            if (obs.draw()) obs.collide(character);
        }
        
        character.draw(4);
        updateScore();

        lives.update();

        pause.scale = 0.75f;
        pause.pos.setPosition(screen_size.x - 50, screen_size.y - 50);
        pause.draw();

        if (gameOver) setStopped(true);
    }

    void updateScore() {
        scoreText.draw(MainGame.sprites.batch, "SCORE: " + score, 10, MainGame.var_list.screen_size.y - 10);
    }

    void setStopped(boolean state) {
        this.isStopped = state;
    }

    void reset() {
        bg_back.reset();
        bg_front2.reset();
        bg_front1.reset();

        platform.reset();
        for (Obstacle obs : obstacles) obs.reset();
        character.reset();

        lives.reset();

        score = 0;
        isStopped = false;
        gameOver = false;
    }
}
