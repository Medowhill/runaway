package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.Direction;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-08.
 */
public class ReverseDirectionBuff extends Buff {

    public ReverseDirectionBuff(GameObject gameObject, int remainingTime) {
        super(gameObject, remainingTime);
    }

    @Override
    public void duringBuff() {
        int direction = gameObject.getDirection();
        if (direction != Direction.NONE)
            gameObject.setDirection((direction + 2) % 4);
    }

    @Override
    public void endBuff() {
    }

    @Override
    public void startBuff() {
    }
}
