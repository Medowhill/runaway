package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.SpeedChangeBuff;
import com.medowhill.jaemin.runaway.object.GameObject;

/**
 * Created by Jaemin on 2015-10-12.
 */
public class SpeedUp extends Ability {

    private final int frame;
    private final float dSpeed;

    public SpeedUp() {
        super(0, context.getResources().getString(R.string.abilitySpeedUpName));

        WAITING_FRAME = context.getResources().getInteger(R.integer.speedUpEnemyCool);
        frame = context.getResources().getInteger(R.integer.speedUPEnemyFrame);
        dSpeed = context.getResources().getInteger(R.integer.speedUpEnemyDSpeed) / 10.f;
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        gameObject.addBuff(new SpeedChangeBuff(gameObject, frame, dSpeed, false));
    }
}
