package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-11-04.
 */
public class FontButton extends Button {

    private static Typeface typeface;

    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (typeface == null)
            typeface = Typeface.createFromAsset(context.getAssets(), getResources().getString(R.string.font_name));

        setTypeface(typeface);
    }
}
