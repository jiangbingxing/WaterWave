package com.study.jack.waterwave;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import android.view.animation.LinearInterpolator;

import static android.content.ContentValues.TAG;

/**
 * Created by jack on 2017/3/1.
 */

public class WaterWave extends View {

    private Paint paint;


    private Path path;

    private ValueAnimator animation;

    private int waveTime;


    private int waterHeight,waveLength,dx;

    private Bitmap bitmap;

    private  int width,height;

    private  Region region;

    private int bitmap_id;


    public WaterWave(Context context) {
        super(context);
    }

    public WaterWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WaterWave(Context context, AttributeSet attrs) {


        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WaterWave);

        int indexCount = ta.getIndexCount();

        for (int i = 0; i <=indexCount; i++) {

            int attr = ta.getIndex(i);
            switch (attr)
            {
                case R.styleable.WaterWave_car:
                    bitmap_id= ta.getResourceId(attr,0);
                    bitmap=BitmapFactory.decodeResource(getResources(),bitmap_id);
                    break;
                case R.styleable.WaterWave_time:
                    waveTime=ta.getInteger(attr,R.styleable.WaterWave_time);
                    break;
                case  R.styleable.WaterWave_wavelength:
                    waveLength=ta.getInteger(attr,R.styleable.WaterWave_wavelength);
                    break;
                case  R.styleable.WaterWave_waveHeight:
                    waterHeight=ta.getInteger(attr,R.styleable.WaterWave_waveHeight);
                    break;
            }

        }
        ta.recycle();



    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        drawWaterWavePath();
        canvas.drawPath(path,paint);

        Rect bounds = region.getBounds();

        if (bounds.top<500)
        canvas.drawBitmap(bitmap,bounds.right-bitmap.getWidth()/2+45,bounds.top-bitmap.getHeight()/2-50,paint);
        else
            canvas.drawBitmap(bitmap,bounds.right-bitmap.getWidth()/2+45,bounds.bottom-bitmap.getHeight()/2-50,paint);



    }

    private void drawWaterWavePath() {

        path=new Path();
        path.reset();
        path.moveTo(-waveLength+dx,500);

        for(int i=-waveLength;i<width+waveLength;i+=waveLength)
        {

            path.rQuadTo(waveLength/4,-waterHeight,waveLength/2,0);
            path.rQuadTo(waveLength/4,waterHeight,waveLength/2,0);

        }
        region=new Region();
        Region clip=new Region((int)(width/2-0.1),0,width/2,height);
        region.setPath(path,clip);
        path.lineTo(width,height);
        path.lineTo(0,height);
        path.close();

    }

    private void initPaint() {

        paint=new Paint();
        paint.setColor(getResources().getColor(R.color.waterWave));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10);
    }

    public void  startAnimation()
    {

        animation= ValueAnimator.ofFloat(0,1);
        animation.setDuration(waveTime);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = (float) valueAnimator.getAnimatedValue();
                dx= (int) (waveLength*animatedFraction);
                postInvalidate();

            }
        });

        animation.start();

    }
}
