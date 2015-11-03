package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.object.Enemy;

import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-09-24.
 */
public class EnemyPreView extends View {

    private final int RATIO;
    private final int BASE_SIZE;
    private final int RADIUS;

    private int width, height;
    private RectF rectF;

    private ArrayList<Integer> types, colors, sizes;

    private Paint paint, paint_background;

    private Handler enemyInfoHandler;

    public EnemyPreView(Context context, AttributeSet attrs) {
        super(context, attrs);

        RATIO = getResources().getInteger(R.integer.enemyPreviewDecreasingRatio);
        BASE_SIZE = getResources().getInteger(R.integer.baseSize);

        int dp = getResources().getInteger(R.integer.enemyPreviewRadius);
        RADIUS = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());

        types = new ArrayList<>();
        colors = new ArrayList<>();
        sizes = new ArrayList<>();

        paint = new Paint();
        paint_background = new Paint();
        paint_background.setColor(getResources().getColor(R.color.enemyPreViewBackground));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;

        rectF = new RectF(0, 0, w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            float x = event.getX(), y = event.getY();
            if (colors.size() != 0 && 0 < x && x < width && 0 < y && y < height) {
                int xSize = width / colors.size(), ySize = height / colors.size();
                int xIndex = (int) (x / xSize), yIndex = (int) (y / ySize);
                if (xIndex == yIndex)
                    if (enemyInfoHandler != null)
                        enemyInfoHandler.sendEmptyMessage(types.get(xIndex));
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(rectF, RADIUS, RADIUS, paint_background);

        int size = colors.size();
        if (size > 0) {
            int w = width / (size + 1), h = height / (size + 1), s = Math.min(width, height) / RATIO;
            for (int i = 0; i < size; i++) {
                paint.setColor(colors.get(i));
                canvas.drawCircle(w * (i + 1), h * (i + 1), s * sizes.get(i), paint);
            }
        }
    }

    public void setEnemyInfoHandler(Handler enemyInfoHandler) {
        this.enemyInfoHandler = enemyInfoHandler;
    }

    public void setStage(int stage) {
        colors.clear();
        sizes.clear();

        String stageData = getResources().getStringArray(R.array.stageInfo1)[stage - 1];
        String[] stageDatas = stageData.split("/");

        for (int i = 0; i < stageDatas.length; i++) {
            String data = stageDatas[i];
            String[] datas = data.split(",");
            float x, y;
            if (data.charAt(0) == 'e') {

                x = Float.parseFloat(datas[2]);
                y = Float.parseFloat(datas[3]);
                Enemy enemy = Enemy.makeEnemy(null, x, y, datas[1].charAt(0));

                int type = enemy.typeCharacter;
                if (!types.contains(type)) {
                    types.add(type);
                    colors.add(enemy.COLOR);
                    int size = (int) (enemy.RADIUS / BASE_SIZE);
                    sizes.add(size);
                }
            }
        }

        invalidate();
    }
}
