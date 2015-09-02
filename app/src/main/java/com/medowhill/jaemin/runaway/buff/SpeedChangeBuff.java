package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-02.
 */
public class SpeedChangeBuff extends Buff {

    float dSpeed;

    public SpeedChangeBuff(GameObject gameObject, int remainingTime, float dSpeed) {
        super(gameObject, remainingTime);
        this.dSpeed = dSpeed;
    }

    @Override
    void endBuff() {
        super.endBuff();
        gameObject.modifySpeedMultiplier(-dSpeed);
    }

    @Override
    void startBuff() {
        super.startBuff();
        gameObject.modifySpeedMultiplier(dSpeed);
    }
}
