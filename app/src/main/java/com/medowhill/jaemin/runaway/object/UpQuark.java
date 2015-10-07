package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.ShockWave;

/**
 * Created by Jaemin on 2015-10-07.
 */
public class UpQuark extends Enemy {

    private final float range;

    public UpQuark(Stage stage, float radius, int color, int colorDetecting, float x, float y, float speed, float sight) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.upQuarkSize),
                context.getResources().getColor(R.color.upQuarkNormal), context.getResources().getColor(R.color.upQuarkDetecting),
                x, y, context.getResources().getInteger(R.integer.upQuarkSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.upQuarkSight));

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
