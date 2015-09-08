package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.medowhill.jaemin.runaway.R;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Player extends GameObject {

    // State
    boolean usingIllusion = false, isIllusion = false;

    // Illusion & Shadow
    Player illusion;

    // Paint
    Paint paintInvisible, paintImmortal, paintIllusion;

    // Constructor
    public Player(Stage stage, float x, float y, boolean isIllusion) {
        super(stage, context.getResources().getInteger(R.integer.playerSize) * context.getResources().getInteger(R.integer.baseSize),
                context.getResources().getColor(R.color.playerNormal), x, y, context.getResources().getInteger(R.integer.playerSpeed));

        paintInvisible = new Paint();
        paintInvisible.setColor(context.getResources().getColor(R.color.playerInvisible));

        paintImmortal = new Paint();
        paintImmortal.setColor(context.getResources().getColor(R.color.playerImmortal));

        paintIllusion = new Paint();
        paintIllusion.setColor(context.getResources().getColor(R.color.playerIllusion));

        this.isIllusion = isIllusion;

        if (!isIllusion)
            illusion = new Player(stage, 0, 0, true);
    }

    // Getter

    public boolean isUsingIllusion() {
        return usingIllusion;
    }

    public void setUsingIllusion(boolean usingIllusion) {
        this.usingIllusion = usingIllusion;
    }

    // Setter

    public Player getIllusion() {
        return illusion;
    }

    public void setIllusionLocation() {
        illusion.x = x;
        illusion.y = y;
    }

    public void setDirection(int direction) {
        if (directionModifiable)
            this.direction = direction;

        if (illusion != null)
            illusion.setDirection(direction);
    }

    // Moving Method

    @Override
    void modifyMove(Wall wall) {
        float start = wall.START, end = wall.END, location = wall.LOCATION;

        if (wall.HORIZONTAL) {
            float dy = 0;
            if (start - radius < x && x < start)
                dy = (float) Math.sqrt(radius * radius - (start - x) * (start - x));
            else if (x < end)
                dy = radius;
            else if (x < end + radius)
                dy = (float) Math.sqrt(radius * radius - (end - x) * (end - x));

            if (location < y)
                y = location + dy;
            else
                y = location - dy;
        } else {
            float dx = 0;
            if (start - radius < y && y < start)
                dx = (float) Math.sqrt(radius * radius - (start - y) * (start - y));
            else if (y < end)
                dx = radius;
            else if (y < end + radius)
                dx = (float) Math.sqrt(radius * radius - (end - y) * (end - y));

            if (location < x)
                x = location + dx;
            else
                x = location - dx;
        }
    }

    // Drawing Method

    @Override
    public void draw(Canvas canvas) {
        if (isIllusion)
            canvas.drawCircle(x, y, radius, paintIllusion);
        else if (!mortal)
            canvas.drawCircle(x, y, radius, paintImmortal);
        else if (!visible)
            canvas.drawCircle(x, y, radius, paintInvisible);
        else
            canvas.drawCircle(x, y, radius, paintNormal);
    }
}
