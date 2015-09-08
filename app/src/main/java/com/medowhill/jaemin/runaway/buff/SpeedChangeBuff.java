package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class SpeedChangeBuff extends Buff {

    float dSpeed;

    public SpeedChangeBuff(GameObject gameObject, int remainingTime, float dSpeed) {
        super(gameObject, remainingTime);
        this.dSpeed = dSpeed;
    }

    @Override
    public void endBuff() {
        gameObject.modifySpeedMultiplier(-dSpeed);
    }

    @Override
    public void startBuff() {
        gameObject.modifySpeedMultiplier(dSpeed);
    }

    @Override
    public void duringBuff() {

    }
}
