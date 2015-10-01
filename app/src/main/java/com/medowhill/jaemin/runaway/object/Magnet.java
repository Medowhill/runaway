package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Magnetism;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class Magnet extends Enemy {

    private int detectingFrame = 0;

    public Magnet(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.magnetSize),
                context.getResources().getColor(R.color.magnetNormal), context.getResources().getColor(R.color.magnetDetecting),
                x, y, context.getResources().getInteger(R.integer.magnetSpeed), context.getResources().getInteger(R.integer.magnetSight));

        Magnetism magnetism = new Magnetism(1);
        abilities.add(magnetism);
    }

    @Override
    public void useAbility() {
        if ((detect || detectIllusion) && abilityUsable) {
            detectingFrame++;
            abilities.get(0).use(this);
        } else
            detectingFrame = 0;
    }

    public int getDetectingFrame() {
        return detectingFrame;
    }
}
