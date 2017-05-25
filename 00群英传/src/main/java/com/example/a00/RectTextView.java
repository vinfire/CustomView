package com.example.a00;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by GTR on 2017/5/11.
 */

public class RectTextView extends android.support.v7.widget.AppCompatTextView {

    public RectTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //初始化画笔
        Paint paint1 = new Paint();
        paint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        paint1.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        paint2.setColor(Color.YELLOW);
        paint2.setStyle(Paint.Style.FILL);

        //绘制外层矩形
        canvas.drawRect(
                0,
                0,
                getMeasuredWidth(),
                getMeasuredHeight(),
                paint1);
        //绘制内层矩形
        canvas.drawRect(
                10,
                10,
                getMeasuredWidth()-10,
                getMeasuredHeight()-10,
                paint2);
        super.onDraw(canvas);

    }
}
