package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Bullet extends GameObject {

    public Bullet(Stage stage, float radius, float height, int color, float x, float y, float speed) {
        super(stage, radius, color, x, y, speed);
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    void modifyMove(Wall wall) {

    }
}
