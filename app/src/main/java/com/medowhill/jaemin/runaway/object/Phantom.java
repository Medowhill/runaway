package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Hiding;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class Phantom extends Enemy {

    public Phantom(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.phantomSize),
                context.getResources().getColor(R.color.phantomNormal), context.getResources().getColor(R.color.phantomDetecting),
                x, y, context.getResources().getInteger(R.integer.phantomSpeed), context.getResources().getInteger(R.integer.phantomSight));

        abilities.add(new Hiding(1));
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && detect) {
            if (detect)
                ability.use(this);
        }
    }
}
