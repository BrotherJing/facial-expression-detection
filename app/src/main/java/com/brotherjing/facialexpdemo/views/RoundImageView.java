package com.brotherjing.facialexpdemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.brotherjing.facialexpdemo.R;

/**
 * Created by Administrator on 2015/1/27 0027.
 */
public class RoundImageView extends ImageView {

    final int BORDER_RADIUS_DEFAULT = 10;
    final int TYPE_CIRCLE = 0;
    final int TYPE_ROUND = 1;

    int mBorderRadius;
    int type;
    int mWidth,mHeight;

    Matrix matrix;
    BitmapShader bitmapShader;
    Paint mpaint;
    RectF rect;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
        mpaint = new Paint();
        mpaint.setAntiAlias(true);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mBorderRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView_radius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,BORDER_RADIUS_DEFAULT,
                        getResources().getDisplayMetrics()));
        type = ta.getInt(R.styleable.RoundImageView_type,TYPE_CIRCLE);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setUpBitmapShader();
        if(getDrawable()==null)return;
        if(type==TYPE_CIRCLE){
            canvas.drawCircle(mBorderRadius,mBorderRadius,mBorderRadius,mpaint);
        }else if(type==TYPE_ROUND){
            canvas.drawRoundRect(rect,mBorderRadius,mBorderRadius,mpaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(type==TYPE_ROUND)
            rect = new RectF(0,0,getWidth(),getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(type==TYPE_CIRCLE){
            mWidth = Math.min(getMeasuredWidth(),getMeasuredHeight());
            mBorderRadius = mWidth/2;
            setMeasuredDimension(mWidth,mWidth);
        }
    }

    private void setUpBitmapShader(){
        Drawable drawable = getDrawable();
        if(drawable==null)return;
        Bitmap bitmap = drawableToBitmap(drawable);

        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if(type==TYPE_CIRCLE){
            int bsize = Math.min(bitmap.getHeight(),bitmap.getWidth());
            scale = mWidth*1.0f/bsize;
        }
        else{
            scale = Math.max(getWidth()/bitmap.getWidth(),getHeight()/bitmap.getHeight());
        }
        matrix.setScale(scale,scale);
        bitmapShader.setLocalMatrix(matrix);
        mpaint.setShader(bitmapShader);
    }

    private Bitmap drawableToBitmap(Drawable drawable){
        if(drawable instanceof BitmapDrawable)return ((BitmapDrawable) drawable).getBitmap();
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,w,h);
        drawable.draw(canvas);
        return bitmap;
    }
}
