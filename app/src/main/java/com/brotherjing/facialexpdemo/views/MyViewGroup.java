package com.brotherjing.facialexpdemo.views;

/**
 * Created by Brotherjing on 2016-01-01.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 靖哥 on 2014/8/28 0028.
 */
public class MyViewGroup extends ViewGroup {

    private int VIEW_MARGIN = 4;

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4) {

        int row = 0;
        int left = 0;
        int right = i3 - i;
        int width, height;
        int childleft = left;
        //Log.i("yj", "initial: left = " + left + " right = " + right);

        for (int j = 0; j < getChildCount(); ++j) {
            View child = getChildAt(j);
            width = child.getMeasuredWidth();
            height = child.getMeasuredHeight();
            //Log.i("yj", "child left = " + childleft + " row = " + row + " width = " + width + " height = " + height);
            childleft += width + VIEW_MARGIN;
            if (childleft > right) {
                row++;
                childleft = left + width + VIEW_MARGIN;
            } else {
            }
            child.layout(childleft - width - VIEW_MARGIN, row * (height + VIEW_MARGIN), childleft, row * (height + VIEW_MARGIN) + height);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int row = 1;
        int wholewidth = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0, height = 0;

        for (int j = 0; j < getChildCount(); ++j) {
            View child = getChildAt(j);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            height = child.getMeasuredHeight();
            width += child.getMeasuredWidth() + VIEW_MARGIN;
            if (width > wholewidth) {
                row++;
                width = child.getMeasuredWidth() + VIEW_MARGIN;

            }
        }
        setMeasuredDimension(resolveSize(wholewidth, widthMeasureSpec), resolveSize(row * (height + VIEW_MARGIN), heightMeasureSpec));
    }

}