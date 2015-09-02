package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-01.
 */
public abstract class Buff {

    GameObject gameObject;

    int remainingTime;

    boolean start = false, end = false;

    boolean movable = true, directionModifiable = true, abilityUsable = true, visible = true, mortal = true;

    Buff(GameObject gameObject, int remainingTime) {
        this.gameObject = gameObject;
        this.remainingTime = remainingTime;
    }

    void startBuff() {
        start = true;
    }

    void endBuff() {
        end = true;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isEnd() {
        return end;
    }

    public void framePass() {
        remainingTime--;

        if (remainingTime == 0)
            endBuff();
    }
}
