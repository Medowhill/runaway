package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.object.CharmQuark;
import com.medowhill.jaemin.runaway.object.BottomQuark;
import com.medowhill.jaemin.runaway.object.HiggsBoson;
import com.medowhill.jaemin.runaway.object.Enemy;
import com.medowhill.jaemin.runaway.object.ElectronNeutrino;
import com.medowhill.jaemin.runaway.object.ZBoson;
import com.medowhill.jaemin.runaway.object.Electron;
import com.medowhill.jaemin.runaway.object.MuonNeutrino;
import com.medowhill.jaemin.runaway.object.Gluon;
import com.medowhill.jaemin.runaway.object.StrangeQuark;
import com.medowhill.jaemin.runaway.object.TopQuark;

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

    private ArrayList<Integer> colors, sizes;

    private Paint paint, paint_bakcground;

    public EnemyPreView(Context context, AttributeSet attrs) {
        super(context, attrs);

        RATIO = getResources().getInteger(R.integer.enemyPreviewDecreasingRatio);
        BASE_SIZE = getResources().getInteger(R.integer.baseSize);

        int dp = getResources().getInteger(R.integer.enemyPreviewRadius);
        RADIUS = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());

        colors = new ArrayList<>();
        sizes = new ArrayList<>();

        paint = new Paint();
        paint_bakcground = new Paint();
        paint_bakcground.setColor(getResources().getColor(R.color.enemyPreViewBackground));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;

        rectF = new RectF(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(rectF, RADIUS, RADIUS, paint_bakcground);

        int size = colors.size();
        if (size > 0) {
            int w = width / (size + 1), h = height / (size + 1), s = Math.min(width, height) / RATIO;
            for (int i = 0; i < size; i++) {
                paint.setColor(colors.get(i));
                canvas.drawCircle(w * (i + 1), h * (i + 1), s * sizes.get(i), paint);
            }
        }
    }

    public void setStage(int stage) {
        colors.clear();
        sizes.clear();

        String stageData = getResources().getStringArray(R.array.stageInfo)[stage - 1];
        String[] stageDatas = stageData.split("/");

        for (int i = 0; i < stageDatas.length; i++) {
            String data = stageDatas[i];
            String[] datas = data.split(",");
            float x, y;
            if (data.charAt(0) == 'e') {
                Enemy enemy = null;
                x = Float.parseFloat(datas[2]);
                y = Float.parseFloat(datas[3]);
                switch (datas[1].charAt(0)) {
                    case 'o':
                        enemy = new Electron(null, x, y);
                        break;
                    case 'c':
                        enemy = new HiggsBoson(null, x, y);
                        break;
                    case 't':
                        enemy = new StrangeQuark(null, x, y);
                        break;
                    case 'g':
                        enemy = new ElectronNeutrino(null, x, y);
                        break;
                    case 'p':
                        enemy = new MuonNeutrino(null, x, y);
                        break;
                    case 's':
                        enemy = new Gluon(null, x, y);
                        break;
                    case 'a':
                        enemy = new CharmQuark(null, x, y);
                        break;
                    case 'm':
                        enemy = new ZBoson(null, x, y);
                        break;
                    case 'T':
                        enemy = new TopQuark(null, x, y);
                        break;
                    case 'b':
                        enemy = new BottomQuark(null, x, y);
                        break;
                }

                int color = enemy.COLOR;
                if (!colors.contains(color)) {
                    colors.add(color);
                    int size = (int) (enemy.RADIUS / BASE_SIZE);
                    sizes.add(size);
                }
            }
        }

        invalidate();
    }
}
