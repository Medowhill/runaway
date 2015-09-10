package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.CannotMoveBuff;
import com.medowhill.jaemin.runaway.buff.ForcedMoveBuff;
import com.medowhill.jaemin.runaway.object.Enemy;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class ShockWave extends Ability {

    private final float range;

    private final int frame;
    private final float distance;

    public ShockWave(int level) {
        super(level, 480, R.drawable.ability_icon_shockwave);

        range = 750;
        frame = 10;
        distance = 750;
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        boolean used = false;

        for (Enemy enemy : gameObject.getStage().enemies) {
            float x1 = gameObject.getX(), y1 = gameObject.getY(), x2 = enemy.getX(), y2 = enemy.getY();
            if ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) < range * range) {
                float dx = 0, dy = 0;
                if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
                    if (x1 > x2)
                        dx = -distance / frame;
                    else
                        dx = distance / frame;
                } else {
                    if (y1 > y2)
                        dy = -distance / frame;
                    else
                        dy = distance / frame;
                }

                Buff buff = new ForcedMoveBuff(enemy, frame, dx, dy);
                enemy.addBuff(buff);
                buff = new CannotMoveBuff(enemy, frame);
                enemy.addBuff(buff);
                used = true;
            }
        }

        if (!used)
            remainWaitingFrame = 0;
    }
}
