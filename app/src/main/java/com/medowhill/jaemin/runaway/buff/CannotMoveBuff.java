package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-03.
 */
public class CannotMoveBuff extends Buff {

    public CannotMoveBuff(GameObject gameObject, int remainingTime) {
        super(gameObject, remainingTime);
        movable = false;
    }

    @Override
    public void duringBuff() {

    }

    @Override
    public void endBuff() {
        movable = true;
        gameObject.setMovable();
    }

    @Override
    public void startBuff() {
        gameObject.setMovable();
    }
}
