package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.CannotMoveBuff;
import com.medowhill.jaemin.runaway.buff.CannotUseAbilityBuff;
import com.medowhill.jaemin.runaway.buff.ForcedMoveBuff;
import com.medowhill.jaemin.runaway.buff.RemoveChannelingBuff;
import com.medowhill.jaemin.runaway.object.Enemy;
import com.medowhill.jaemin.runaway.object.Field;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.ShockWavePlayerField;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class ShockWave extends Ability {

    private final float range;

    private final int frame;
    private final float distance;

    public ShockWave(int levelRange, int levelDistance) {
        super(R.drawable.ability_icon_shockwave);

        WAITING_FRAME = context.getResources().getInteger(R.integer.shockWavePlayerCool);
        range = context.getResources().getIntArray(R.array.shockWavePlayerRange)[levelRange - 1];
        distance = context.getResources().getIntArray(R.array.shockWavePlayerDistance)[levelDistance - 1];
        frame = context.getResources().getInteger(R.integer.shockWavePlayerFrame);
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        boolean used = false;

        for (Enemy enemy : gameObject.getStage().enemies) {
            float x1 = gameObject.getX(), y1 = gameObject.getY(), x2 = enemy.getX(), y2 = enemy.getY();
            float d = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) - enemy.RADIUS;
            if (d < range) {
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

                Buff buff = new RemoveChannelingBuff(enemy, 1, false);
                enemy.addBuff(buff);
                buff = new ForcedMoveBuff(enemy, frame, dx, dy, false);
                enemy.addBuff(buff);
                buff = new CannotUseAbilityBuff(enemy, frame, false);
                enemy.addBuff(buff);
                buff = new CannotMoveBuff(enemy, frame, false);
                enemy.addBuff(buff);

                Field field = new ShockWavePlayerField(gameObject.getStage(), distance, frame, gameObject);
                gameObject.getStage().fields.add(field);

                used = true;
            }
        }

        if (!used)
            remainWaitingFrame = 0;
    }
}
