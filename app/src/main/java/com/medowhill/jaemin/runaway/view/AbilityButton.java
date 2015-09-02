package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.medowhill.jaemin.runaway.R;
import com.medowhill.jaemin.runaway.ability.Ability;

/**
 * Created by Jaemin on 2015-09-01.
 */
public class AbilityButton extends View {

    private final float OUTER_SIZE = 0.375f, INNER_SIZE = 0.325f;

    private boolean touched = false, clicked = false, cool = false;

    private Ability ability;

    private Bitmap icon;

    private Paint paintBorder, paintTouched, paintCool;

    public AbilityButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBorder = new Paint();
        paintBorder.setColor(getResources().getColor(R.color.abilityButtonBorder));

        paintTouched = new Paint();
        paintTouched.setColor(getResources().getColor(R.color.abilityButtonTouched));

        paintCool = new Paint();
        paintCool.setColor(getResources().getColor(R.color.abilityButtonCool));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (clicked)
            return true;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touched = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (touched) {
                    float x = event.getX(), y = event.getY();
                    if (x < 0 || x > getWidth() || y < 0 || y > getHeight()) {
                        touched = false;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (touched) {
                    touched = false;
                    if (!cool)
                        clicked = true;
                    invalidate();
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth(), height = getHeight();
        canvas.drawRect(width * (0.5f - OUTER_SIZE), height * (0.5f - OUTER_SIZE),
                width * (0.5f + OUTER_SIZE), height * (0.5f + OUTER_SIZE), paintBorder);

        if (icon == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ability.getIconResourceID());
            icon = Bitmap.createScaledBitmap(bitmap, (int) (getWidth() * 2 * INNER_SIZE), (int) (getHeight() * 2 * INNER_SIZE), false);
            bitmap.recycle();
            bitmap = null;
        }
        canvas.drawBitmap(icon, width * (0.5f - INNER_SIZE), height * (0.5f - INNER_SIZE), null);

        if (touched) {
            canvas.drawRect(width * (0.5f - INNER_SIZE), height * (0.5f - INNER_SIZE),
                    width * (0.5f + INNER_SIZE), height * (0.5f + INNER_SIZE), paintBorder);
        }

        if (cool) {
            float ratio = ability.getRemainRatio();
            canvas.drawRect(width * (0.5f - INNER_SIZE) * ratio, height * (0.5f - INNER_SIZE) * ratio,
                    width * (0.5f + INNER_SIZE) * ratio, height * (0.5f + INNER_SIZE) * ratio, paintCool);
        }
    }

    public boolean isClicked() {
        return clicked;
    }

    public void clearClick() {
        clicked = false;
        cool = true;
        invalidate();
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }
}
