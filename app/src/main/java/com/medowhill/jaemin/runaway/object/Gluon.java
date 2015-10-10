package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.StunBulletFire;

/**
 * Created by Jaemin on 2015-10-01.
 */
public class Gluon extends Enemy {

    public Gluon(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.gluonSize),
                context.getResources().getColor(R.color.gluonNormal), context.getResources().getColor(R.color.gluonDetecting),
                x, y, context.getResources().getInteger(R.integer.gluonSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.gluonSight),
                context.getResources().getString(R.string.enemyGluonName), 'g');

        StunBulletFire stunBulletFire = new StunBulletFire(1);
        abilities.add(stunBulletFire);
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && (detect || detectIllusion))
            ability.use(this);
    }
}
