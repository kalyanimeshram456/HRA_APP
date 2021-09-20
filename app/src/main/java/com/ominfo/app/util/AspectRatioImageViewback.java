package com.ominfo.app.util;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class AspectRatioImageViewback extends AppCompatImageView {

    public AspectRatioImageViewback(Context context) {
        super(context);
    }

    public AspectRatioImageViewback(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioImageViewback(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            if (getDrawable() != null) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
                setMeasuredDimension(width, height);
            }else {
                setMeasuredDimension(0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}