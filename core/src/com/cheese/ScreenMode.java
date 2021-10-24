package com.cheese;

public class ScreenMode {
    boolean isAtScreen = true;

    public void draw() {};

    public void close () {
        isAtScreen = false;
    }

	public void open () {
        isAtScreen = true;
    }
}
