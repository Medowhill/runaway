package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.medowhill.jaemin.runaway.R;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class Player extends GameObject {

    Paint paintInvisible, paintImmortal;

    public Player(float x, float y) {
        super(context.getResources().getInteger(R.integer.playerSize) * context.getResources().getInteger(R.integer.baseSize),
                context.getResources().getInteger(R.integer.playerSize) * context.getResources().getInteger(R.integer.baseSize),
                context.getResources().getColor(R.color.playerNormal), x, y, context.getResources().getInteger(R.integer.playerSpeed));

        paintInvisible = new Paint();
        paintInvisible.setColor(context.getResources().getColor(R.color.playerInvisible));

        paintImmortal = new Paint();
        paintImmortal.setColor(context.getResources().getColor(R.color.playerImmortal));
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setDirection(int direction) {
        if (directionModifiable)
            this.direction = direction;
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
        if (!mortal)
            canvas.drawRect(x - WIDTH / 2, y - HEIGHT / 2, x + WIDTH / 2, y + HEIGHT / 2, paintImmortal);
        else if (!visible)
            canvas.drawRect(x - WIDTH / 2, y - HEIGHT / 2, x + WIDTH / 2, y + HEIGHT / 2, paintInvisible);
        else
            canvas.drawRect(x - WIDTH / 2, y - HEIGHT / 2, x + WIDTH / 2, y + HEIGHT / 2, paintNormal);
    }
}
