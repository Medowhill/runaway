package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.medowhill.jaemin.runaway.Direction;

/**
 * Created by Jaemin on 2015-09-01.
 */
public abstract class Enemy extends GameObject {

    // Static Constant
    static final int RE_DIRECTION_FRAME = 20;

    // Sight
    float sight;

    // Direction
    int direction2 = Direction.NONE;
    int directingFrame = 0;

    // State
    boolean detect = false, detectIllusion = false, active = false;

    // Paint
    Paint paintDetecting;

    // Constructor
    public Enemy(Stage stage, float radius, int color, int colorDetecting, float x, float y, float speed, float sight) {
        super(stage, radius, color, x, y, speed);

        paintDetecting = new Paint();
        paintDetecting.setColor(colorDetecting);

        this.sight = sight;
    }

    // Moving Method

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

        for (Wall wall : stage.walls) {
            if (wall.HORIZONTAL) {
                if ((y1 - wall.LOCATION) * (y - wall.LOCATION) < 0) {
                    float x_ = (wall.LOCATION - y1) / (y - y1) * (x - x1) + x1;
                    if (wall.START < x_ && x_ < wall.END)
                        return;
                }
            } else {
                if ((x1 - wall.LOCATION) * (x - wall.LOCATION) < 0) {
                    float y_ = (wall.LOCATION - x1) / (x - x1) * (y - y1) + y1;
                    if (wall.START < y_ && y_ < wall.END)
                        return;
                }
            }
        }

        detect = true;
        directingFrame = 0;
        active = true;
    }

    void detectIllusion(Player illusion) {
        float x1 = illusion.x, y1 = illusion.y;

        if ((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y) > sight * sight)
            return;

        for (Wall wall : stage.walls) {
            if (wall.HORIZONTAL) {
                if ((y1 - wall.LOCATION) * (y - wall.LOCATION) < 0) {
                    float x_ = (wall.LOCATION - y1) / (y - y1) * (x - x1) + x1;
                    if (wall.START < x_ && x_ < wall.END)
                        return;
                }
            } else {
                if ((x1 - wall.LOCATION) * (x - wall.LOCATION) < 0) {
                    float y_ = (wall.LOCATION - x1) / (x - x1) * (y - y1) + y1;
                    if (wall.START < y_ && y_ < wall.END)
                        return;
                }
            }
        }

        detectIllusion = true;
        directingFrame = 0;
        active = true;
    }

    public void setDirection() {

        detect();

        Player player = stage.getPlayer();

        float x1 = player.x, y1 = player.y;

        if (detect || detectIllusion) {
            if (detect && detectIllusion) {
                float x2 = player.getIllusion().x, y2 = player.getIllusion().y;
                if ((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y) > (x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)) {
                    x1 = x2;
                    y1 = y2;
                }
            } else if (!detect && detectIllusion) {
                x1 = player.getIllusion().x;
                y1 = player.getIllusion().y;
            }

            float dx = x - x1, dy = y - y1;
            if (Math.abs(dx) < Math.abs(dy)) {
                if (dy < 0)
                    direction = Direction.DOWN;
                else
                    direction = Direction.UP;

                if (dx > 0)
                    direction2 = Direction.LEFT;
                else
                    direction2 = Direction.RIGHT;
            } else {
                if (dx > 0)
                    direction = Direction.LEFT;
                else
                    direction = Direction.RIGHT;

                if (dy < 0)
                    direction2 = Direction.DOWN;
                else
                    direction2 = Direction.UP;
            }
        } else {
            if (directingFrame == 0) {
                if (!active && directingFrame != Direction.NONE) {
                    direction = (direction + 2) % 4;
                    direction2 = (direction2 + 2) % 4;
                } else {
                    direction = (int) (Math.random() * 4);
                    direction2 = ((int) (Math.random() * 3) + 1 + direction) % 4;
                }
            }
            directingFrame++;
            if (directingFrame == RE_DIRECTION_FRAME)
                directingFrame = 0;
        }
    }

    @Override
    void modifyMove(Wall wall) {
        direction = direction2;
        direction2 = Direction.NONE;
        move();
    }

    // Drawing Method

    @Override
    public void draw(Canvas canvas) {
        if (detect)
            canvas.drawCircle(x, y, radius, paintDetecting);
        else
            canvas.drawCircle(x, y, radius, paintNormal);
    }

}
