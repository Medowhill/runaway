package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;
import com.medowhill.jaemin.runaway.ability.Dash;

/**
 * Created by Jaemin on 2015-10-07.
 */
public class DownQuark extends Enemy {

    private final float distance;

    public DownQuark(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.downQuarkSize),
                context.getResources().getColor(R.color.downQuarkNormal), context.getResources().getColor(R.color.downQuarkDetecting),
                x, y, context.getResources().getInteger(R.integer.downQuarkSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.downQuarkSight),
                context.getResources().getString(R.string.enemyDownQuarkName), 'd');

        Dash dash = new Dash(1, false);
        abilities.add(dash);
        distance = dash.getDistance();
    }

    @Override
    public void useAbility() {
        Ability ability = abilities.get(0);

        if (!ability.isWaiting() && (detect || detectIllusion)) {
            Player player = stage.player;
            if (detectIllusion)
                player = player.getIllusion();
            float x1 = player.getX(), y1 = player.getY();
            float dx = Math.abs(x1 - x), dy = Math.abs(y1 - y);
            float min = Math.min(dx, dy), max = Math.max(dx, dy);
            if (min < RADIUS * 2 && distance * 0.75f < max && max < distance * 1.25f)
                ability.use(this);
        }
    }
}
