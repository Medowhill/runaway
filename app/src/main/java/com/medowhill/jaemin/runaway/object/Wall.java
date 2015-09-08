package com.medowhill.jaemin.runaway.object;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Wall {

    final boolean HORIZONTAL;
    final float START, END;
    final float LOCATION;

    public Wall(boolean horizontal, float start, float end, float location) {
        this.HORIZONTAL = horizontal;
        this.START = Math.min(start, end);
        this.END = Math.max(start, end);
        this.LOCATION = location;
    }
}
