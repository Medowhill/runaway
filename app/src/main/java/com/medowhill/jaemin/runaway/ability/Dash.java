package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.Direction;
import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.CannotMoveBuff;
import com.medowhill.jaemin.runaway.buff.ForcedMoveBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Dash extends Ability {

    private final int frame;
    private final float distance;

    public Dash(int level) {
        super(level, 120, R.drawable.ability_icon_dash);
        frame = 10;
        distance = 625;
    }

    @Override
    public void use(final GameObject gameObject) {
        super.use(gameObject);

        if (gameObject.getDirection() == Direction.NONE) {
            remainWaitingFrame = 0;
            return;
        }

        float dx = 0, dy = 0;
        switch (gameObject.getDirection()) {
            case Direction.UP:
                dy = -distance / frame;
                break;
            case Direction.DOWN:
                dy = distance / frame;
                break;
            case Direction.RIGHT:
                dx = distance / frame;
                break;
            case Direction.LEFT:
                dx = -distance / frame;
                break;
        }

        Buff buff = new ForcedMoveBuff(gameObject, frame, dx, dy);
        gameObject.addBuff(buff);
        buff = new CannotMoveBuff(gameObject, frame);
        gameObject.addBuff(buff);
    }
}
