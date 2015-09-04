package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.medowhill.jaemin.runaway.Direction;
import com.medowhill.jaemin.runaway.R;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class Player extends GameObject {

    boolean usingIllusion = false, isIllusion = false, usingSubstitute = false;

    Player illusion;

    Enemy substitute;

    Paint paintInvisible, paintImmortal, paintIllusion;

    public Player(float x, float y, boolean isIllusion) {
        super(context.getResources().getInteger(R.integer.playerSize) * context.getResources().getInteger(R.integer.baseSize),
                context.getResources().getInteger(R.integer.playerSize) * context.getResources().getInteger(R.integer.baseSize),
                context.getResources().getColor(R.color.playerNormal), x, y, context.getResources().getInteger(R.integer.playerSpeed));

        paintInvisible = new Paint();
        paintInvisible.setColor(context.getResources().getColor(R.color.playerInvisible));

        paintImmortal = new Paint();
        paintImmortal.setColor(context.getResources().getColor(R.color.playerImmortal));

        paintIllusion = new Paint();
        paintIllusion.setColor(context.getResources().getColor(R.color.playerIllusion));

        this.isIllusion = isIllusion;

        if (!isIllusion)
            illusion = new Player(0, 0, true);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public boolean isUsingSubstitute() {
        return usingSubstitute;
    }

    public boolean isUsingIllusion() {
        return usingIllusion;
    }

    public void setUsingIllusion(boolean usingIllusion) {
        this.usingIllusion = usingIllusion;
    }

    public Player getIllusion() {
        return illusion;
    }

    public Enemy getSubstitute() {
        return substitute;
    }

    public void setIllusionLocation() {
        illusion.x = x;
        illusion.y = y;
    }

    public void setDirection(int direction) {
        if (directionModifiable) {
            this.direction = direction;
            if (direction == Direction.NONE)
                illusion.direction = Direction.NONE;
            else
                illusion.direction = (direction + 2) % 4;
        }
    }

    @Override
    void modifyMove(Wall wall, ArrayList<Wall> walls) {
        float location = wall.LOCATION;

        if (wall.HORIZONTAL) {
            if (location < y)
                y = location + HEIGHT / 2;
            else
                y = location - HEIGHT / 2;
        } else {
            if (location < x)
                x = location + WIDTH / 2;
            else
                x = location - WIDTH / 2;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (isIllusion)
            canvas.drawRect(x - WIDTH / 2, y - HEIGHT / 2, x + WIDTH / 2, y + HEIGHT / 2, paintIllusion);
        else if (!mortal)
            canvas.drawRect(x - WIDTH / 2, y - HEIGHT / 2, x + WIDTH / 2, y + HEIGHT / 2, paintImmortal);
        else if (!visible)
            canvas.drawRect(x - WIDTH / 2, y - HEIGHT / 2, x + WIDTH / 2, y + HEIGHT / 2, paintInvisible);
        else
            canvas.drawRect(x - WIDTH / 2, y - HEIGHT / 2, x + WIDTH / 2, y + HEIGHT / 2, paintNormal);
    }
}
