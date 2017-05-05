package com.example.togglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 1.构造方法实例化类
 * 2.测量：onMeasure()  , 如果当前View是一个ViewGroup，还有义务测量其孩子
 * 3.指定其子控件的位置：onLayout()  , 一般View不用重写这个方法，ViewGroup的时候才需要
 * 4.绘制视图：onDraw()  ，根据上面两个方法参数在 Canvas 对象上进行绘制；ViewGroup若不指定背影则通常不需要绘制，它会调用dispatchDraw()方法来绘制其子View
 */

public class MyToggleButton extends View implements View.OnClickListener {

    private Bitmap backgroundBitmap;
    private Bitmap slidingBitmap;
    private int slideLeftMax;
    private int slideLeft;
    private Paint paint;
    private boolean isOpen;

    /**
     * 如果我们在布局文件使用该类，将会用这个构造方法实例化该类，若没有则会导致崩溃
     * @param context
     * @param attrs
     */
    public MyToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);  //设置抗锯齿
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        slideLeftMax = backgroundBitmap.getWidth() - slidingBitmap.getWidth();

        setOnClickListener(this);
    }

    /**
     * 视图的测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //通过setMeasuredDimension(int measuredWidth, int measuredHeight)保存计算结果
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        canvas.drawBitmap(slidingBitmap, slideLeft, 0, paint);
    }

    /**
     * true：点击事件生效，滑动事件不生效
     * false：点击事件不生效，滑动事件生效
     */
    private boolean isEnableClick = true;

    @Override
    public void onClick(View v) {
        if (isEnableClick){
            isOpen = !isOpen;
            flushView();
        }
    }

    private void flushView() {
        if (isOpen){
            slideLeft = slideLeftMax;
        }else {
            slideLeft = 0;
        }
        invalidate();  //invalidate()是用来刷新View的，调用invalidate()方法,请求重新onDraw()
    }

    private float startX;
    private float lastX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.记录按下的坐标
                lastX = startX = event.getX();
                isEnableClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                //2.计算结束值
                float endX = event.getX();
                //3.计算偏移量
                float distanceX = endX - startX;
                slideLeft = (int) (slideLeft+distanceX);
                //4.屏蔽非法值
                if (slideLeft < 0 ){
                    slideLeft = 0;
                }else if(slideLeft > slideLeftMax){
                    slideLeft = slideLeftMax;
                }
                //5.刷新
                invalidate();
                //6.数据还原
                startX = event.getX();
                //只要移动距离大于5px，就认为是滑动状态
                if (Math.abs(endX - lastX) >5){
                    isEnableClick = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isEnableClick){
                    if (slideLeft > slideLeftMax/2){
                        isOpen = true;
                    }else {
                        isOpen = false;
                    }
                }
                flushView();
                break;
            default:
                break;
        }
        return true;
    }
}
