package com.medowhill.jaemin.runaway.object;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.medowhill.jaemin.runaway.Direction;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-01.
 */
public abstract class Enemy extends GameObject {

    static final int NON_MOVING_DISTANCE = 1500, RE_DIRECTION_FRAME = 30;

    float sight;

    int direction2 = Direction.NONE;

    int directingFrame = 0;

    boolean detect = false, detectIllusion = false, active = false;

    Paint paintDetecting;

    public Enemy(float size, int color, int colorDetecting, float x, float y, float speed, float sight) {
        super(size, size, color, x, y, speed);

        paintDetecting = new Paint();
        paintDetecting.setColor(colorDetecting);

        this.sight = sight;
    }

    @Override
    public void draw(Canvas canvas) {
        if (detect)
            canvas.drawRect(x - WIDTH / 2, y - HEIGHT / 2, x + WIDTH / 2, y + HEIGHT / 2, paintDetecting);
        else
            canvas.drawRect(x - WIDTH / 2, y - HEIGHT / 2, x + WIDTH / 2, y + HEIGHT / 2, paintNormal);
    }

    public void detect(GameObject gameObject, ArrayList<Wall> walls) {
        detectIllusion = false;
        if (gameObject instanceof Player) {
            Player player = (Player) gameObject;
            if (player.isUsingIllusion())
                detectIllusion(player.getIllusion(), walls);
        }

        float x1 = gameObject.x, y1 = gameObject.y;

        detect = false;

        if (!gameObject.isVisible())
            return;

        if ((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y) > sight * sight)
            return;

        for (Wall wall : walls) {
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

    void detectIllusion(GameObject gameObject, ArrayList<Wall> walls) {
        float x1 = gameObject.x, y1 = gameObject.y;

        if (!gameObject.isVisible())
            return;

        if ((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y) > sight * sight)
            return;

        for (Wall wall : walls) {
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

    public void setDirection(GameObject gameObject, ArrayList<Wall> walls) {
        float x1 = gameObject.x, y1 = gameObject.y;

        if (!active && (x1 - x) * (x1 - x) + (y1 - y) * (y1 - y) > NON_MOVING_DISTANCE * NON_MOVING_DISTANCE) {
            direction = Direction.NONE;
            return;
        }

        detect(gameObject, walls);

        if (detect || detectIllusion) {
            if (detect && detectIllusion) {
                if (gameObject instanceof Player) {
                    Player player = (Player) gameObject;
                    float x2 = player.getIllusion().x, y2 = player.getIllusion().y;
                    if ((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y) > (x2 - x) * (x2 - x) + (y2 - y) * (y2 - y)) {
                        x1 = x2;
                        y1 = y2;
                    }
                }
            } else if (!detect && detectIllusion) {
                if (gameObject instanceof Player) {
                    Player player = (Player) gameObject;
                    x1 = player.getIllusion().x;
                    y1 = player.getIllusion().y;
                }
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
                direction = (int) (Math.random() * 4);
                direction2 = ((int) (Math.random() * 3) + 1 + direction) % 4;
            }
            directingFrame++;
            if (directingFrame == RE_DIRECTION_FRAME)
                directingFrame = 0;
        }
    }

    @Override
    void modifyMove(Wall wall, ArrayList<Wall> walls) {
        direction = direction2;
        direction2 = Direction.NONE;
        move(walls);
    }
}
