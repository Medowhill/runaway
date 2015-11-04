package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-10-16.
 */
public class UpgradeView extends View {

    private static Typeface typeface;

    private final TypedArray BITMAP_RESOURCE;
    private final String[] UPGRADE_INFO;
    private final int[] MAX_LEVEL;

    private final int BITMAP_MARGIN, BACKGROUND_RADIUS;

    private int width, height;

    private int upgrade = -1;

    private Bitmap icon;
    private Bitmap bitmapLocked;

    private RectF[] rects;

    private RectF rectBackground;

    private String stringLevel, stringMaxLevel;
    private int stringInfoHeight;
    private int level;

    private Paint paintBackground, paintUnlock, paintInfo, paintFill, paintStroke;

    public UpgradeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (typeface == null)
            typeface = Typeface.createFromAsset(context.getAssets(), getResources().getString(R.string.font_name));

        BITMAP_RESOURCE = getResources().obtainTypedArray(R.array.upgradeIcon);
        UPGRADE_INFO = getResources().getStringArray(R.array.upgradeInfo);
        MAX_LEVEL = getResources().getIntArray(R.array.upgradeMaxLevel);

        BITMAP_MARGIN = getResources().getDimensionPixelSize(R.dimen.upgradeBitmapMargin);
        BACKGROUND_RADIUS = getResources().getDimensionPixelSize(R.dimen.upgradeBackgroundRadius);

        stringLevel = getResources().getString(R.string.upgradeLevel);
        stringMaxLevel = getResources().getString(R.string.upgradeMaxLevel);

        rectBackground = new RectF(0, 0, getWidth(), getHeight());

        paintBackground = new Paint();
        paintBackground.setColor(getResources().getColor(R.color.upgradeBackground));

        paintUnlock = new Paint();
        paintUnlock.setColor(getResources().getColor(R.color.upgradeUnlock));

        paintInfo = new Paint();
        paintInfo.setTextSize(getResources().getDimension(R.dimen.upgradeInfoTextSize));
        paintInfo.setTypeface(typeface);

        paintFill = new Paint();
        paintFill.setColor(getResources().getColor(R.color.upgradeFill));

        paintStroke = new Paint();
        paintStroke.setColor(getResources().getColor(R.color.upgradeStroke));
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(getResources().getDimension(R.dimen.upgradeStrokeWidth));
        paintStroke.setStrokeCap(Paint.Cap.SQUARE);
    }

    public void setUpgrade(int upgrade, int level) {
        this.upgrade = upgrade;
        this.level = level;

        makeObjects();
    }

    private void makeObjects() {
        if (icon != null) {
            icon.recycle();
            icon = null;
        }
        if (bitmapLocked != null) {
            bitmapLocked.recycle();
            bitmapLocked = null;
        }
        rects = null;

        if (0 <= upgrade && upgrade < BITMAP_RESOURCE.length() && height > 0) {
            Bitmap temp = BitmapFactory.decodeResource(getResources(), BITMAP_RESOURCE.getResourceId(upgrade, -1));
            int size = height - 2 * BITMAP_MARGIN;
            icon = Bitmap.createScaledBitmap(temp, size, size, false);
            temp.recycle();

            temp = BitmapFactory.decodeResource(getResources(), R.drawable.locked);
            size = height / 3;
            bitmapLocked = Bitmap.createScaledBitmap(temp, size, size, false);
            temp.recycle();

            rects = new RectF[MAX_LEVEL[upgrade]];
            for (int i = 0; i < rects.length; i++)
                rects[i] = new RectF(height, height / 2 + BITMAP_MARGIN,
                        height + (width - BITMAP_MARGIN - height) * (i + 1) / rects.length, height - BITMAP_MARGIN);

            Rect bounds = new Rect();
            String text = UPGRADE_INFO[upgrade];
            paintInfo.getTextBounds(text, 0, text.length(), bounds);
            stringInfoHeight = bounds.height();
        }

        rectBackground = new RectF(0, 0, width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;

        makeObjects();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(rectBackground, BACKGROUND_RADIUS, BACKGROUND_RADIUS, paintBackground);

        if (icon != null)
            canvas.drawBitmap(icon, BITMAP_MARGIN, BITMAP_MARGIN, null);

        if (upgrade != -1)
            canvas.drawText(UPGRADE_INFO[upgrade], getHeight(), BITMAP_MARGIN / 2 + getHeight() / 4 + stringInfoHeight / 2, paintInfo);

        if (rects != null) {
            for (int i = 0; i < Math.min(rects.length, level); i++)
                canvas.drawRect(rects[i], paintFill);
            for (int i = 0; i < rects.length; i++)
                canvas.drawRect(rects[i], paintStroke);
        }

        if (level == 0) {
            canvas.drawRoundRect(rectBackground, BACKGROUND_RADIUS, BACKGROUND_RADIUS, paintUnlock);
            canvas.drawBitmap(bitmapLocked, getWidth() / 2 - getHeight() / 6, getHeight() / 3, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
