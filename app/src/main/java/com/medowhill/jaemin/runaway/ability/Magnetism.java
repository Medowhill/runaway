package com.medowhill.jaemin.runaway.ability;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;
import com.medowhill.jaemin.runaway.buff.SpeedChangeBuff;
import com.medowhill.jaemin.runaway.object.GameObject;
import com.medowhill.jaemin.runaway.object.ZBoson;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class Magnetism extends Ability {

    private final float dSpeed;

    public Magnetism(int level) {
        super(0);

        dSpeed = context.getResources().getIntArray(R.array.magnetismEnemyDSpeed)[level - 1] / 800.f;
    }

    @Override
    public void use(GameObject gameObject) {
        super.use(gameObject);

        if (gameObject instanceof ZBoson) {
            ZBoson ZBoson = (ZBoson) gameObject;

            Buff buff = new SpeedChangeBuff(gameObject, 1, dSpeed * ZBoson.getDetectingFrame(), true);
            gameObject.addBuff(buff);
        }
    }
}
