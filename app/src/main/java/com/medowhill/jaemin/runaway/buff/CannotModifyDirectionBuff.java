package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-02.
 */
public class CannotModifyDirectionBuff extends Buff {

    public CannotModifyDirectionBuff(GameObject gameObject, int remainingTime) {
        super(gameObject, remainingTime);
        directionModifiable = false;
    }

    @Override
    public void endBuff() {
        directionModifiable = true;
        gameObject.setDirectionModifiable();
    }

    @Override
    public void startBuff() {
        gameObject.setDirectionModifiable();
    }

    @Override
    public void duringBuff() {
    }
}
