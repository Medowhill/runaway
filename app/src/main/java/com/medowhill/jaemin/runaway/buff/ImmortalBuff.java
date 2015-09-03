package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-03.
 */
public class ImmortalBuff extends Buff {

    public ImmortalBuff(GameObject gameObject, int remainingTime) {
        super(gameObject, remainingTime);
        mortal = false;
    }

    @Override
    public void duringBuff() {

    }

    @Override
    public void endBuff() {
        mortal = true;
        gameObject.setMortal();
    }

    @Override
    public void startBuff() {
        gameObject.setMortal();
    }
}
