package com.example.youkuDemo;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * 显示或隐藏指定控件
 */

public class Tools {

    public static void hideView(View view) {
        hideView(view, 0);
    }

    public static void showView(View view) {
        showView(view, 0);
    }

    public static void hideView(View view, int startOffset) {
        //旋转动画
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, view.getWidth()/2, view.getHeight());
//        rotateAnimation.setDuration(600); //设置动画播放持续的时间
//        rotateAnimation.setFillAfter(true); //设置动画停留在播放完成的状态
//        rotateAnimation.setStartOffset(startOffset); //延迟多久后播放动画
//        view.startAnimation(rotateAnimation);
//
//        view.setVisibility(View.GONE);


        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180);
        animator.setDuration(600); //设置动画播放持续的时间
        animator.setStartDelay(startOffset); //延迟多久后播放动画
        animator.start();
    }

    public static void showView(View view, int startOffset) {
        //旋转动画
//        RotateAnimation rotateAnimation = new RotateAnimation(180, 360, view.getWidth()/2, view.getHeight());
//        rotateAnimation.setDuration(600); //设置动画播放持续的时间
//        rotateAnimation.setFillAfter(true); //设置动画停留在播放完成的状态
//        rotateAnimation.setStartOffset(startOffset); //延迟多久后播放动画
//        view.startAnimation(rotateAnimation);

        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 180, 360);
        animator.setDuration(600); //设置动画播放持续的时间
        animator.setStartDelay(startOffset); //延迟多久后播放动画
        animator.start();
    }
}
