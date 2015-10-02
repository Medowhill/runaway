package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.ShockWave;

/**
 * Created by Jaemin on 2015-10-02.
 */
public class TopQuark extends Enemy {

    private final float range;

    public TopQuark(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.topQuarkSize),
                context.getResources().getColor(R.color.topQuarkNormal), context.getResources().getColor(R.color.topQuarkDetecting),
                x, y, context.getResources().getInteger(R.integer.topQuarkSpeed), context.getResources().getInteger(R.integer.topQuarkSight));

        ShockWave shockWave = new ShockWave(1, 1, false);
        abilities.add(shockWave);
        range = shockWave.getRange();
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
