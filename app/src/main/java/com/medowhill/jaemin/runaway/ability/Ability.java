package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public abstract class Ability {

    public final int iconResourceID;
    public final int WAITING_FRAME;
    final int LEVEL;
    int remainWaitingFrame;

    Ability(int level, int waitingFrame, int iconResourceID) {
        this.LEVEL = level;
        this.WAITING_FRAME = waitingFrame;
        this.iconResourceID = iconResourceID;
    }

    public void use(GameObject gameObject) {
        if (remainWaitingFrame != 0)
            return;

        remainWaitingFrame = WAITING_FRAME;
    }

    public float getRemainRatio() {
        return 1.f * remainWaitingFrame / WAITING_FRAME;
    }

    public void decreaseRemaining(int frame) {
        remainWaitingFrame -= frame;
        if (remainWaitingFrame < 0)
            remainWaitingFrame = 0;
    }

    public boolean isWaiting() {
        return remainWaitingFrame != 0;
    }

    public int getRemainWaitingFrame() {
        return remainWaitingFrame;
    }
}
