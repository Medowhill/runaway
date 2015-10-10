package com.medowhill.jaemin.runaway.ability;

import android.content.Context;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public abstract class Ability {

    static Context context;

    public final int iconResourceID;
    public final String name;

    public int WAITING_FRAME;
    int remainWaitingFrame;

    Ability(int iconResourceID, String name) {
        this.iconResourceID = iconResourceID;
        this.name = name;
    }

    public static void setContext(Context context) {
        Ability.context = context;
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
