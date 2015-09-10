package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class Ghost extends Enemy {

    public Ghost(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.ghostSize),
                context.getResources().getColor(R.color.ghostNormal), context.getResources().getColor(R.color.ghostDetecting),
                x, y, context.getResources().getInteger(R.integer.ghostSpeed), context.getResources().getInteger(R.integer.ghostSight));
    }

    @Override
    public void useAbility() {
    }

    @Override
    public void detect() {

        Player player = stage.getPlayer();

        float x1 = player.x, y1 = player.y;

        detectIllusion = false;
        if (player.isUsingIllusion())
            detectIllusion(player.getIllusion());

        detect = false;

        if (!player.isVisible())
            return;

        if ((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y) > sight * sight)
            return;

        detect = true;
        directingFrame = 0;
        active = true;
    }

    @Override
    void detectIllusion(Player illusion) {
        float x1 = illusion.x, y1 = illusion.y;

        if ((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y) > sight * sight)
            return;

        detectIllusion = true;
        directingFrame = 0;
        active = true;
    }

    @Override
    boolean willTouchAfterMove(Wall wall, float distance, boolean horizontal) {
        return false;
    }
}
