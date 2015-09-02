package com.medowhill.jaemin.runaway.object;

/**
 * Created by Jaemin on 2015-09-01.
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
