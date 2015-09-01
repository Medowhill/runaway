package com.medowhill.jaemin.runaway.ability;

/**
 * Created by Jaemin on 2015-09-01.
 */
public abstract class Ability {

    final int WAITING_FRAME;

    int remainWaitingFrame;

    Ability(int waitingFrame) {
        this.WAITING_FRAME = waitingFrame;
    }

    public void use() {
        if (remainWaitingFrame == 0) {
            remainWaitingFrame = WAITING_FRAME;
        }
    }

    public float getRemainRatio() {
        return 1.f * remainWaitingFrame / WAITING_FRAME;
    }

    public void decreaseRemaining(int frame) {
        remainWaitingFrame -= frame;
        if (remainWaitingFrame < 0)
            remainWaitingFrame = 0;
    }

    public int getRemainWaitingFrame() {
        return remainWaitingFrame;
    }
}
