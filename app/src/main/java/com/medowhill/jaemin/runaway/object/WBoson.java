package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Hiding;

/**
 * Created by Jaemin on 2015-10-07.
 */
public class WBoson extends Enemy {

    public WBoson(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.wBosonSize),
                context.getResources().getColor(R.color.wBosonNormal), context.getResources().getColor(R.color.wBosonDetecting),
                x, y, context.getResources().getInteger(R.integer.wBosonSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.wBosonSight));

        abilities.add(new Hiding(1, false));
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && (detect || detectIllusion))
            ability.use(this);
    }

}
