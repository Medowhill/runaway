package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.SlowBulletFire;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class CharmQuark extends Enemy {

    public CharmQuark(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.charmQuarkSize),
                context.getResources().getColor(R.color.charmQuarkNormal), context.getResources().getColor(R.color.charmQuarkDetecting),
                x, y, context.getResources().getInteger(R.integer.charmQuarkSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.charmQuarkSight));

        SlowBulletFire slowBulletFire = new SlowBulletFire(1);
        abilities.add(slowBulletFire);
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && (detect || detectIllusion))
            ability.use(this);
    }
}
