package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class DelayBuff extends Buff {

    private Buff buff;

    public DelayBuff(GameObject gameObject, int remainingTime, Buff buff, boolean channeling) {
        super(gameObject, remainingTime, channeling);
        this.buff = buff;
    }

    @Override
    public void endBuff() {
        gameObject.addBuff(buff);
    }

    @Override
    public void duringBuff() {

    }

    @Override
    public void startBuff() {

    }
}
