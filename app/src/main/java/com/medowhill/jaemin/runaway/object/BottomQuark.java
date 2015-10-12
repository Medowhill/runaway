package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.SpeedUp;

/**
 * Created by Jaemin on 2015-10-02.
 */
public class BottomQuark extends Enemy {

    public BottomQuark(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.bottomQuarkSize),
                context.getResources().getColor(R.color.bottomQuarkNormal), context.getResources().getColor(R.color.bottomQuarkDetecting),
                x, y, context.getResources().getInteger(R.integer.higgsBosonSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.bottomQuarkSight),
                context.getResources().getString(R.string.enemyBottomQuarkName), 'b');

        SpeedUp speedUp = new SpeedUp();
        abilities.add(speedUp);
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && (detect || detectIllusion))
            ability.use(this);
    }
}
