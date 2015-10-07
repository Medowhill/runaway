package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-07.
 */
public class TauNeutrino extends Enemy {

    public TauNeutrino(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.tauNeutrinoSize),
                context.getResources().getColor(R.color.tauNeutrinoNormal), context.getResources().getColor(R.color.tauNeutrinoDetecting),
                x, y, context.getResources().getInteger(R.integer.tauNeutrinoSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.tauNeutrinoSight));
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

        float d = (float) Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y)) - player.RADIUS - this.RADIUS;
        if (d > sight)
            return;

        detect = true;
        directingFrame = 0;
        active = true;
    }

    @Override
    void detectIllusion(Player illusion) {
        float x1 = illusion.x, y1 = illusion.y;

        float d = (float) Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y)) - illusion.RADIUS - this.RADIUS;
        if (d > sight)
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
