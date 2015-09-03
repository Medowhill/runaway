package com.medowhill.jaemin.runaway.object;

import android.content.Context;
import android.graphics.Path;

import com.medowhill.jaemin.runaway.R;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class Stage {

    public final ArrayList<Enemy> enemies;

    public final ArrayList<Wall> walls;

    public final Path area;

    private float xMax, yMax;

    private Player player;

    private float xFinish, yFinish;

    public Stage(Context context, int stage) {
        String stageData = context.getResources().getStringArray(R.array.stageInfo)[stage - 1];

        String[] stageDatas = stageData.split("/");

        enemies = new ArrayList<>();
        walls = new ArrayList<>();
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
                    player = new Player(x, y);
                    break;
                case 'w':
                    x = 0;
                    y = 0;
                    for (int j = 1; j < datas.length; j++) {
                        if (j % 2 == 1) {
                            float x_ = Float.parseFloat(datas[j]);
                            walls.add(new Wall(true, x, x_, y));
                            x = x_;
                            if (x > xMax)
                                xMax = x;
                        } else {
                            float y_ = Float.parseFloat(datas[j]);
                            walls.add(new Wall(false, y, y_, x));
                            y = y_;
                            if (y > yMax)
                                yMax = y;
                        }
                        area.lineTo(x, y);
                    }
                    break;
                case 'e':
                    Enemy enemy = null;
                    x = Float.parseFloat(datas[2]);
                    y = Float.parseFloat(datas[3]);
                    switch (datas[1].charAt(0)) {
                        case 'o':
                            enemy = new Observer(x, y);
                            break;
                    }
                    enemies.add(enemy);
                    break;
                case 'f':
                    xFinish = Float.parseFloat(datas[1]);
                    yFinish = Float.parseFloat(datas[2]);
                    break;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public float getxFinish() {
        return xFinish;
    }

    public float getyFinish() {
        return yFinish;
    }

    public float getxMax() {
        return xMax;
    }

    public float getyMax() {
        return yMax;
    }
}
