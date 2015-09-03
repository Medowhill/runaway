package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class Bullet extends GameObject {

    public Bullet(float width, float height, int color, float x, float y, float speed) {
        super(width, height, color, x, y, speed);
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    void modifyMove(Wall wall, ArrayList<Wall> walls) {

    }
}
