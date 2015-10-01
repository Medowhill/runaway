package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Bullet extends GameObject {

    private boolean touchWall;
    private Buff[] buffs;

    public Bullet(Stage stage, int color, float x, float y, float speed, int direction, Buff[] buffs) {
        super(stage, context.getResources().getInteger(R.integer.baseSize), color, x, y, speed);

        this.direction = direction;
        this.buffs = buffs;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, RADIUS, paintNormal);
    }

    @Override
    void modifyMove(Wall wall) {
        touchWall = true;
    }

    public boolean isTouchWall() {
        return touchWall;
    }

    public Buff[] getBuff() {
        return buffs;
    }
}
