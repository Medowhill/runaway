package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.DistortionField;

/**
 * Created by Jaemin on 2015-10-02.
 */
public class BottomQuark extends Enemy {

    private final float range;

    public BottomQuark(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.bottomQuarkSize),
                context.getResources().getColor(R.color.bottomQuarkNormal), context.getResources().getColor(R.color.bottomQuarkDetecting),
                x, y, context.getResources().getInteger(R.integer.bottomQuarkSpeed), context.getResources().getInteger(R.integer.bottomQuarkSight));

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
