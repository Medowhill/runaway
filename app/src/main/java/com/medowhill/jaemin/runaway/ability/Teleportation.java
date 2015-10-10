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

    public Teleportation(int level, boolean player) {
        super(R.drawable.ability_icon_teleportation, context.getResources().getString(R.string.abilityTeleportationName));

        if (player) {
            WAITING_FRAME = context.getResources().getInteger(R.integer.teleportationPlayerCool);
            frame = context.getResources().getIntArray(R.array.teleportationPlayerFrame)[level - 1];
            distance = context.getResources().getInteger(R.integer.teleportationPlayerDistance);
        } else {
            WAITING_FRAME = context.getResources().getInteger(R.integer.teleportationEnemyCool);
            frame = context.getResources().getIntArray(R.array.teleportationEnemyFrame)[level - 1];
            distance = context.getResources().getInteger(R.integer.teleportationEnemyDistance);
        }
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

        Buff buff = new ForcedMoveBuff(gameObject, 1, dx, dy, false);
        gameObject.addBuff(new DelayBuff(gameObject, frame, buff, true));
        buff = new CannotMoveBuff(gameObject, frame + 1, true);
        gameObject.addBuff(buff);
    }

    public float getDistance() {
        return distance;
    }
}
