package com.example.myviewpager;

import android.os.SystemClock;

/**
 * Created by GTR on 2017/5/3.
 */

public class MyScroller {

    /**
     * 起始坐标
     */
    private float startX;
    private float startY;

    /**
     * 移动的距离
     */
    private float distanceX;
    private float distanceY;

    private long startTime;   //开始的时间

    private long totalTime = 500;  //移动总时长

    private boolean isFinish; //是否移动完成

    private float currX;  //当前X的坐标

    public float getCurrX() {
        return currX;
    }

    public void startScroll(float startX, float startY, float distanceX, float distanceY) {
        this.startX = startX;
        this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.elapsedRealtime(); //系统开机时间
        this.isFinish = false;
    }

    /**
     * 移动的距离
     * @return true：正在移动     false：移动结束
     */
    public boolean computeScrollOffset(){
        if (isFinish){
            return false;
        }
        //移动一小段对应的结束时间
        long endTime = SystemClock.elapsedRealtime();
        //所花的时间
        long passTime = endTime - startTime;
        if (passTime < totalTime){
            //还没有移动结束
            //计算平均速度
//            float voleCity = distanceX/totalTime;
            //移动一小段对应的距离
            float distanceSmallX = passTime * distanceX/totalTime;
            currX = startX + distanceSmallX;
        }else {
            //移动结束
            isFinish = true;
            currX = startX + distanceX;
        }

        return true;
    }
}
