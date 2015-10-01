package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class RemoveChannelingBuff extends Buff {

    public RemoveChannelingBuff(GameObject gameObject, int remainingTime, boolean channeling) {
        super(gameObject, remainingTime, channeling);
    }

    @Override
    public void duringBuff() {
    }

    @Override
    public void endBuff() {
    }

    @Override
    public void startBuff() {
        gameObject.removeChannelingBuff();
    }
}
