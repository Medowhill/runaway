package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;

/**
 * Created by Jaemin on 2015-10-01.
 */
public abstract class Field extends GameObject {

    final GameObject owner;

    final float DR;

    float radius, remainingFrame;

    public Field(Stage stage, float initialRadius, float radius, int remainingFrame, int color, GameObject owner) {
        super(stage, radius, color, 0, 0, 0);

        this.owner = owner;
        this.radius = initialRadius;
        this.DR = (radius - initialRadius) / remainingFrame;
        this.remainingFrame = remainingFrame;
    }

    @Override
    void modifyMove(Wall wall) {
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(owner.x, owner.y, radius, paintNormal);
    }

    public void resize() {
        radius += DR;
        remainingFrame--;
    }

    public boolean isFinish() {
        return remainingFrame < 0;
    }
}
