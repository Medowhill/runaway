package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Hiding;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class MuonNeutrino extends Enemy {

    public MuonNeutrino(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.muonNeutrinoSize),
                context.getResources().getColor(R.color.muonNeutrinoNormal), context.getResources().getColor(R.color.muonNeutrinoDetecting),
                x, y, context.getResources().getInteger(R.integer.muonNeutrinoSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.muonNeutrinoSight));

        abilities.add(new Hiding(1, false));
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && (detect || detectIllusion))
            ability.use(this);
    }
}
