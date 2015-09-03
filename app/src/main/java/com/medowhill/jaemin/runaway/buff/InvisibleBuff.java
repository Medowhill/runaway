package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-03.
 */
public class InvisibleBuff extends Buff {

    public InvisibleBuff(GameObject gameObject, int remainingTime) {
        super(gameObject, remainingTime);
        visible = false;
    }

    @Override
    public void duringBuff() {

    }

    @Override
    public void endBuff() {
        visible = true;
        gameObject.setVisible();
    }

    @Override
    public void startBuff() {
        gameObject.setVisible();
    }
}
