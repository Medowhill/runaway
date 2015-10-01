package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class CannotMoveBuff extends Buff {

    public CannotMoveBuff(GameObject gameObject, int remainingTime, boolean channeling) {
        super(gameObject, remainingTime, channeling);
        movable = false;
    }

    @Override
    public void duringBuff() {

    }

    @Override
    public void endBuff() {
        movable = true;
        gameObject.setMovable();
    }

    @Override
    public void startBuff() {
        gameObject.setMovable();
    }
}
