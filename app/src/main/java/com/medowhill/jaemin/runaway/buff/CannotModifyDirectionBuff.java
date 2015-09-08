package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class CannotModifyDirectionBuff extends Buff {

    public CannotModifyDirectionBuff(GameObject gameObject, int remainingTime) {
        super(gameObject, remainingTime);
        directionModifiable = false;
    }

    @Override
    public void endBuff() {
        directionModifiable = true;
        gameObject.setDirectionModifiable();
    }

    @Override
    public void startBuff() {
        gameObject.setDirectionModifiable();
    }

    @Override
    public void duringBuff() {
    }
}
