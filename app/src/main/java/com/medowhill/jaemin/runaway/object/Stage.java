package com.medowhill.jaemin.runaway.object;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.buff.Buff;

import java.util.ArrayList;

/**
 * Copyright 2015. Hong Jaemin
 * All rights reserved.
 */

public class Stage {

    private static final int[] WORLD_ID = new int[]{R.array.stageInfo1, R.array.stageInfo2, R.array.stageInfo3, R.array.stageInfo4};

    public final int MAP_RADIUS;

    public final ArrayList<Enemy> enemies;

    public final ArrayList<Wall> walls;

    public final ArrayList<Buff> buffs;

    public final ArrayList<Star> stars;

    public final ArrayList<Field> fields;

    public final ArrayList<Bullet> bullets;

    public final Path area;

    public Player player;

    public Finish finish;

    private float xMax, yMax;

    private float xStart, yStart, xFinish, yFinish;

    public Stage(Context context) {
        MAP_RADIUS = context.getResources().getInteger(R.integer.mapRadius);

        enemies = new ArrayList<>();
        walls = new ArrayList<>();
        buffs = new ArrayList<>();
        stars = new ArrayList<>();
        fields = new ArrayList<>();
        bullets = new ArrayList<>();
        area = new Path();
    }

    public Stage(Context context, int world, int stage) {
        this(context, world, stage, false);
    }

    public Stage(Context context, int world, int stage, boolean forPreview) {
        MAP_RADIUS = context.getResources().getInteger(R.integer.mapRadius);

        String stageData = context.getResources().getStringArray(WORLD_ID[world])[stage - 1];

        String[] stageDatas = stageData.split("/");

        enemies = new ArrayList<>();
        walls = new ArrayList<>();
        buffs = new ArrayList<>();
        stars = new ArrayList<>();
        fields = new ArrayList<>();
        bullets = new ArrayList<>();
        area = new Path();
        area.moveTo(0, 0);

        for (int i = 0; i < stageDatas.length; i++) {
            String data = stageDatas[i];
            String[] datas = data.split(",");
            float x, y;
            switch (data.charAt(0)) {
                case 'p':
                    x = Float.parseFloat(datas[1]);
                    y = Float.parseFloat(datas[2]);
                    xStart = x;
                    yStart = y;
                    if (!forPreview)
                        player = new Player(this, x, y, false);
                    break;
                case 'w':
                    x = Float.parseFloat(datas[datas.length - 2]);
                    y = Float.parseFloat(datas[datas.length - 1]);
                    boolean yInc = false, xInc = false;

                    for (int j = 1; j < datas.length; j++) {
                        if (j % 2 == 1) {
                            float x_ = Float.parseFloat(datas[j]);
                            walls.add(new Wall(true, x, x_, y));
                            xInc = x < x_;

                            if (!forPreview) {
                                if (j != 1) {
                                    if (xInc) {
                                        if (yInc)
                                            area.arcTo(new RectF(x, y - 2 * MAP_RADIUS, x + 2 * MAP_RADIUS, y), 180, -90);
                                        else
                                            area.arcTo(new RectF(x, y, x + 2 * MAP_RADIUS, y + 2 * MAP_RADIUS), 180, 90);
                                    } else {
                                        if (yInc)
                                            area.arcTo(new RectF(x - 2 * MAP_RADIUS, y - 2 * MAP_RADIUS, x, y), 0, 90);
                                        else
                                            area.arcTo(new RectF(x - 2 * MAP_RADIUS, y, x, y + 2 * MAP_RADIUS), 0, -90);
                                    }
                                } else {
                                    if (xInc)
                                        area.moveTo(MAP_RADIUS, 0);
                                    else
                                        area.moveTo(-MAP_RADIUS, 0);
                                }
                                if (xInc)
                                    area.lineTo(x_ - MAP_RADIUS, y);
                                else
                                    area.lineTo(x_ + MAP_RADIUS, y);
                            }

                            x = x_;
                            if (x > xMax)
                                xMax = x;
                        } else {
                            float y_ = Float.parseFloat(datas[j]);
                            walls.add(new Wall(false, y, y_, x));
                            yInc = y < y_;

                            if (!forPreview) {
                                if (yInc) {
                                    if (xInc)
                                        area.arcTo(new RectF(x - 2 * MAP_RADIUS, y, x, y + 2 * MAP_RADIUS), 270, 90);
                                    else
                                        area.arcTo(new RectF(x, y, x + 2 * MAP_RADIUS, y + 2 * MAP_RADIUS), 270, -90);
                                } else {
                                    if (xInc)
                                        area.arcTo(new RectF(x - 2 * MAP_RADIUS, y - 2 * MAP_RADIUS, x, y), 90, -90);
                                    else
                                        area.arcTo(new RectF(x, y - 2 * MAP_RADIUS, x + 2 * MAP_RADIUS, y), 90, 90);
                                }

                                if (yInc)
                                    area.lineTo(x, y_ - MAP_RADIUS);
                                else
                                    area.lineTo(x, y_ + MAP_RADIUS);
                            }

                            y = y_;
                            if (y > yMax)
                                yMax = y;
                        }
                    }

                    if (!forPreview) {
                        float x_ = Float.parseFloat(datas[1]);
                        xInc = x < x_;
                        if (xInc) {
                            if (yInc)
                                area.arcTo(new RectF(x, y - 2 * MAP_RADIUS, x + 2 * MAP_RADIUS, y), 180, -90);
                            else
                                area.arcTo(new RectF(x, y, x + 2 * MAP_RADIUS, y + 2 * MAP_RADIUS), 180, 90);
                        } else {
                            if (yInc)
                                area.arcTo(new RectF(x - 2 * MAP_RADIUS, y - 2 * MAP_RADIUS, x, y), 0, 90);
                            else
                                area.arcTo(new RectF(x - 2 * MAP_RADIUS, y, x, y + 2 * MAP_RADIUS), 0, -90);
                        }
                    }

                    break;
                case 'e':
                    if (!forPreview) {
                        x = Float.parseFloat(datas[2]);
                        y = Float.parseFloat(datas[3]);
                        Enemy enemy = Enemy.makeEnemy(this, x, y, datas[1].charAt(0));
                        enemies.add(enemy);
                    }
                    break;
                case 'f':
                    xFinish = Float.parseFloat(datas[1]);
                    yFinish = Float.parseFloat(datas[2]);
                    if (!forPreview)
                        finish = new Finish(this, xFinish, yFinish);
                    break;
                case 's':
                    if (!forPreview) {
                        x = Float.parseFloat(datas[1]);
                        y = Float.parseFloat(datas[2]);
                        Star star = new Star(this, x, y);
                        stars.add(star);
                    }
                    break;
            }
        }
    }

    public float getxFinish() {
        return xFinish;
    }

    public float getyFinish() {
        return yFinish;
    }

    public float getxStart() {
        return xStart;
    }

    public float getyStart() {
        return yStart;
    }

    public float getxMax() {
        return xMax;
    }

    public float getyMax() {
        return yMax;
    }

}
