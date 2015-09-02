package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-01.
 */
public abstract class Ability {

    final public int iconResourceID;

    final int WAITING_FRAME;

    final int LEVEL;

    int remainWaitingFrame;

    Ability(int level, int waitingFrame, int iconResourceID) {
        this.LEVEL = level;
        this.WAITING_FRAME = waitingFrame;
        this.iconResourceID = iconResourceID;
    }

    public void use(GameObject gameObject) {
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

    public int getIconResourceID() {
        return iconResourceID;
    }
}
