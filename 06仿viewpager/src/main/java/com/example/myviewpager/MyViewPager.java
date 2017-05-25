package com.example.myviewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by GTR on 2017/5/2.
 */

public class MyViewPager extends ViewGroup {

    /**
     * 手势识别器
     * 1.定义出来
     * 2.实例化--把想要的方法给重写
     * 3.在onTouchEvent()把事件传递给手势识别器
     */
    private GestureDetector detector;

    private int currentIndex;
    private Scroller scroller;


    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        scroller = new Scroller(context);
        //2.实例化手势识别器
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Toast.makeText(context, "长按", Toast.LENGTH_SHORT).show();
            }

            /**
             *
             * @param e1
             * @param e2
             * @param distanceX 在X轴滑动了的距离
             * @param distanceY 在Y轴滑动了的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Toast.makeText(context, "滑动", Toast.LENGTH_SHORT).show();

                scrollBy((int)distanceX, 0);

                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(context, "双击", Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历孩子，给每个孩子指定在屏幕的坐标位置
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(i*getMeasuredWidth(), 0, (i+1)*getMeasuredWidth(), getMeasuredHeight());
        }
    }

    private float startX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //把事件传递给手势识别器
        detector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.记录坐标
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                //2.来到新的坐标
                float endX = event.getX();
                int tempIndex = currentIndex;
                if ((startX-endX) > getWidth()/3){
                    //显示下一个页面
                    tempIndex ++;
                }else if ((endX-startX) > getWidth()/3){
                    //显示上一个页面
                    tempIndex --;
                }
                scrollToPager(tempIndex);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 屏蔽非法值，根据位置移动到指定页面
     * @param tempIndex
     */
    public void scrollToPager(int tempIndex) {
        if (tempIndex < 0){
            tempIndex = 0;
        }
        if (tempIndex > getChildCount()-1){
            tempIndex = getChildCount()-1;
        }
        currentIndex = tempIndex;

        if (mOnPagerChangeListener != null){
            mOnPagerChangeListener.onScrollToPager(currentIndex);
        }

        int distanceX = currentIndex*getWidth() - getScrollX();
        //移动到指定的位置
//        scrollTo(currentIndex*getWidth(), getScrollY());  //瞬间移动，比较生硬
//        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0);
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0, Math.abs(distanceX/2));  //防止多页面滑动时过快

        invalidate();  //导致onDraw与computeScroll执行
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        scroller.computeScrollOffset();
        if (scroller.computeScrollOffset()){
            float currX = scroller.getCurrX();
            scrollTo((int) currX, 0);
            invalidate();
        }
    }

    /**
     * 监听页面的改变
     */
    public interface OnPagerChangeListener{
        //当页面改变的时候回调这个方法， 把当前页面的下标回传
        void onScrollToPager(int position);
    }

    private OnPagerChangeListener mOnPagerChangeListener;

    public void setOnPagerChangeListener(OnPagerChangeListener listener){
        mOnPagerChangeListener = listener;
    }
}
