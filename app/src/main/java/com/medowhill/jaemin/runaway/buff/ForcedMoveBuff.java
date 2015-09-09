package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-08.
 */
public class ForcedMoveBuff extends Buff {

    private float dx, dy;

    public ForcedMoveBuff(GameObject gameObject, int remainingTime, float dx, float dy) {
        super(gameObject, remainingTime);

        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void duringBuff() {
        gameObject.move(dx, dy);
    }

    @Override
    public void endBuff() {
    }

    @Override
    public void startBuff() {
    }
}
