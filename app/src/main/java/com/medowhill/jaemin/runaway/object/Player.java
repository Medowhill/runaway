package com.medowhill.jaemin.runaway.object;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class Player extends GameObject {

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    void setSize(float size) {
        this.width = size;
        this.height = size;
    }

    public void setDirection(int direction) {
        if (directionModifiable)
            this.direction = direction;
    }
}
