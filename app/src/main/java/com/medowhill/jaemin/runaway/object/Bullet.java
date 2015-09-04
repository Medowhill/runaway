package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class Bullet extends GameObject {

    public Bullet(Stage stage, float width, float height, int color, float x, float y, float speed) {
        super(stage, width, height, color, x, y, speed);
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    void modifyMove(Wall wall) {

    }
}
