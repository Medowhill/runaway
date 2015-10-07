package com.medowhill.jaemin.runaway.object;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-09-10.
 */
public class ElectronNeutrino extends Enemy {

    public ElectronNeutrino(Stage stage, float x, float y) {
        super(stage, context.getResources().getInteger(R.integer.baseSize) * context.getResources().getInteger(R.integer.electronNeutrinoSize),
                context.getResources().getColor(R.color.electronNeutrinoNormal), context.getResources().getColor(R.color.electronNeutrinoDetecting),
                x, y, context.getResources().getInteger(R.integer.electronNeutrinoSpeed),
                context.getResources().getInteger(R.integer.baseSight) * context.getResources().getInteger(R.integer.electronNeutrinoSight));
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
