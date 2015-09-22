package com.medowhill.jaemin.runaway.object;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Wall {

    public final boolean HORIZONTAL;
    public final float START, END, FIRST, LAST;
    public final float LOCATION;

    public Wall(boolean horizontal, float start, float end, float location) {
        this.HORIZONTAL = horizontal;
        this.START = Math.min(start, end);
        this.END = Math.max(start, end);
        this.FIRST = start;
        this.LAST = end;
        this.LOCATION = location;
    }
}
