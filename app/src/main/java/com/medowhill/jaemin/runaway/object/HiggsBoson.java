package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.DistortionField;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class HiggsBoson extends Enemy {

    private final float range;

    public HiggsBoson(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.higgsBosonSize),
                context.getResources().getColor(R.color.higgsBosonNormal), context.getResources().getColor(R.color.higgsBosonDetecting),
                x, y, context.getResources().getInteger(R.integer.higgsBosonSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.higgsBosonSight));

        DistortionField distortionField = new DistortionField(1, 1, false);
        abilities.add(distortionField);
        range = distortionField.getRange();
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && (detect || detectIllusion)) {
            Player player = stage.getPlayer();
            if (detectIllusion)
                player = player.getIllusion();
            float x1 = player.getX(), y1 = player.getY();
            float d = (float) Math.sqrt((x1 - this.x) * (x1 - this.x) + (y1 - this.y) * (y1 - this.y)) - player.RADIUS;
            if (d < range)
                ability.use(this);
        }
    }
}
