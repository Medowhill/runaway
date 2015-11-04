package com.medowhill.jaemin.runaway.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.medowhill.jaemin.runaway.R;

/**
 * Created by Jaemin on 2015-11-04.
 */
public class FontTextView extends TextView {

    private static Typeface typeface;

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (typeface == null)
            typeface = Typeface.createFromAsset(context.getAssets(), getResources().getString(R.string.font_name));

        setTypeface(typeface);
    }
}
