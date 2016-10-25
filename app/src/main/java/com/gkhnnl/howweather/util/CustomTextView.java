package com.gkhnnl.howweather.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gkhnnl.howweather.R;

/**
 * Created by gkhnnl on 04/09/16.
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (isInEditMode())
            return;

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TWTextView);
            String fontName = a.getString(R.styleable.TWTextView_tvFontName);
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }

    public void setFont(String fontName) {
        Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
        setTypeface(myTypeface);
    }

}
