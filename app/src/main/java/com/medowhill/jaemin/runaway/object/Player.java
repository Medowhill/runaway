package com.medowhill.jaemin.runaway.object;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class Player extends GameObject {

    public Player(float x, float y, float speed, int size, int color) {
        super(x, y, speed, color);

        this.width = size;
        this.height = size;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
