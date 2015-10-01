package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.Direction;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class ReverseDirectionBuff extends Buff {

    public ReverseDirectionBuff(GameObject gameObject, int remainingTime, boolean channeling) {
        super(gameObject, remainingTime, channeling);
    }

    @Override
    public void duringBuff() {
        int direction = gameObject.getDirection();
        if (direction != Direction.NONE)
            gameObject.setDirection((direction + 2) % 4);
    }

    @Override
    public void endBuff() {
    }

    @Override
    public void startBuff() {
    }
}
