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

    public Dash(int level, boolean player) {
        super(R.drawable.ability_icon_dash);

        if (player) {
            WAITING_FRAME = context.getResources().getInteger(R.integer.dashPlayerCool);
            frame = context.getResources().getIntArray(R.array.dashPlayerFrame)[level - 1];
            distance = context.getResources().getInteger(R.integer.dashPlayerDistance);
        } else {
            WAITING_FRAME = context.getResources().getInteger(R.integer.dashEnemyCool);
            frame = context.getResources().getIntArray(R.array.dashEnemyFrame)[level - 1];
            distance = context.getResources().getInteger(R.integer.dashEnemyDistance);
        }
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

    public float getDistance() {
        return distance;
    }
}
