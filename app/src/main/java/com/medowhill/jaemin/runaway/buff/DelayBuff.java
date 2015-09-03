package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-03.
 */
public class DelayBuff extends Buff {

    private Buff buff;

    public DelayBuff(GameObject gameObject, int remainingTime, Buff buff) {
        super(gameObject, remainingTime);
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
