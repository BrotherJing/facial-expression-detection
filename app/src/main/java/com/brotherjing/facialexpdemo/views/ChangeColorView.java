package com.brotherjing.facialexpdemo.views;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Brotherjing on 2016-01-01.
 */
public class ChangeColorView extends View {

    public ChangeColorView(Context context) {
        super(context);
    }

    public ChangeColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChangeColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void toColor(int color){
        ValueAnimator anim = ObjectAnimator.ofInt(this,"backgroundColor",getDrawingCacheBackgroundColor(),color);
        anim.setDuration(300);
        anim.setEvaluator(new ArgbEvaluator());
        anim.start();
    }

}
