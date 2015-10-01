package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public abstract class Buff {

    public final boolean channeling;

    GameObject gameObject;

    int remainingTime;

    boolean start = false, end = false;

    boolean movable = true, directionModifiable = true, abilityUsable = true, visible = true, mortal = true;

    Buff(GameObject gameObject, int remainingTime, boolean channeling) {
        this.gameObject = gameObject;
        this.remainingTime = remainingTime;
        this.channeling = channeling;
    }

    abstract public void startBuff();

    abstract public void endBuff();

    abstract public void duringBuff();

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isEnd() {
        return remainingTime == 0;
    }

    public void framePass() {
        remainingTime--;
    }

    public boolean isAbilityUsable() {
        return abilityUsable;
    }

    public boolean isDirectionModifiable() {
        return directionModifiable;
    }

    public boolean isMortal() {
        return mortal;
    }

    public boolean isMovable() {
        return movable;
    }

    public boolean isVisible() {
        return visible;
    }
}
