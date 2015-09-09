package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.Direction;
import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.CannotMoveBuff;
import com.medowhill.jaemin.runaway.buff.DelayBuff;
import com.medowhill.jaemin.runaway.buff.ForcedMoveBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Teleportation extends Ability {

    private final int frame;
    private final float distance;

    public Teleportation(int level) {
        super(level, 120, R.drawable.ability_icon_teleportation);

        frame = 10;
        distance = 625;
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        if (gameObject.getDirection() == Direction.NONE) {
            remainWaitingFrame = 0;
            return;
        }

        float dx = 0, dy = 0;
        switch (gameObject.getDirection()) {
            case Direction.UP:
                dy = -distance;
                break;
            case Direction.DOWN:
                dy = distance;
                break;
            case Direction.RIGHT:
                dx = distance;
                break;
            case Direction.LEFT:
                dx = -distance;
                break;
        }

        Buff buff = new ForcedMoveBuff(gameObject, 1, dx, dy);
        gameObject.addBuff(new DelayBuff(gameObject, frame, buff));
        buff = new CannotMoveBuff(gameObject, frame + 1);
        gameObject.addBuff(buff);
    }
}
