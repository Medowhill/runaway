package com.medowhill.jaemin.runaway.buff;

import com.medowhill.jaemin.runaway.object.Enemy;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class DistortionFieldBuff extends Buff {

    private final float range;
    private final float dspeed;

    public DistortionFieldBuff(GameObject gameObject, int remainingTime, float range, float dspeed, boolean channeling) {
        super(gameObject, remainingTime, channeling);

        this.range = range;
        this.dspeed = dspeed;
    }

    @Override
    public void duringBuff() {
        for (Enemy enemy : gameObject.getStage().enemies) {
            float x1 = gameObject.getX(), y1 = gameObject.getY(), x2 = enemy.getX(), y2 = enemy.getY();
            float d = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) - enemy.RADIUS;
            if (d < range)
                enemy.addBuff(new SpeedChangeBuff(enemy, 1, dspeed, channeling));
        }
    }

    @Override
    public void endBuff() {
    }

    @Override
    public void startBuff() {
    }
}
