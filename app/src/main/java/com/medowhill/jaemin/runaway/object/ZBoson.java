package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Magnetism;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class ZBoson extends Enemy {

    private int detectingFrame = 0;

    public ZBoson(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.zBosonSize),
                context.getResources().getColor(R.color.zBosonNormal), context.getResources().getColor(R.color.zBosonDetecting),
                x, y, context.getResources().getInteger(R.integer.zBosonSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.zBosonSight),
                context.getResources().getString(R.string.enemyWBosonName), 'w');

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
