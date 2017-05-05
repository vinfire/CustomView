package com.example.banner;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;

    // 图片资源ID
    private final int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e };

    // 图片标题集合
    private final String[] imageDescriptions = {
            "尚硅谷波河争霸赛！",
            "凝聚你我，放飞梦想！",
            "抱歉没座位了！",
            "7月就业名单全部曝光！",
            "平均起薪11345元"
    };

    //上一次高亮显示的位置
    private int prePosition = 0;

    private boolean isDragging = false;  //ViewPager是否正在滑动


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int item = viewPager.getCurrentItem()+1;
            viewPager.setCurrentItem(item);

            //发送延迟消息
            handler.sendEmptyMessageDelayed(0,3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        
        imageViews = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            imageViews.add(imageView);

            //添加指示点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dip2px(this, 8), DisplayUtil.dip2px(this, 8));
            if (i==0){
                point.setEnabled(true);  //显示红色
            }else {
                point.setEnabled(false);  //显示灰色
                params.leftMargin = DisplayUtil.dip2px(this, 8);
            }

            point.setLayoutParams(params);
            ll_point_group.addView(point);
        }

        MyPagerAdapter adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        tv_title.setText(imageDescriptions[prePosition]);
        //设置为中间位置，防止开始时不能右滑
        int item = imageViews.size()*2;  //要保证是 imageViews 的整数倍
        viewPager.setCurrentItem(item);

        //设置监听 ViewPager 页面的改变
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 当页面滚动了的时候回调这个方法
             * @param position  当前页面的位置
             * @param positionOffset  滑动页面的百分比
             * @param positionOffsetPixels  滑动了多像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = position%imageViews.size();
                tv_title.setText(imageDescriptions[realPosition]);
                ll_point_group.getChildAt(prePosition).setEnabled(false);
                ll_point_group.getChildAt(realPosition).setEnabled(true);  //改变指示器点的颜色
                prePosition = realPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /**
                 * SCROLL_STATE_DRAGGING----拖动
                 * SCROLL_STATE_SETTLING----通过拖动/滑动，安放到了目标页 （ViewPager在自动轮播过程中会走这个状态）
                 * SCROLL_STATE_IDLE----空闲，只要拖动/滑动结束，无论是否安放到了目标页
                 */
                if (state == ViewPager.SCROLL_STATE_DRAGGING){
                    isDragging = true;
                    handler.removeCallbacksAndMessages(null);
                }else if (state == ViewPager.SCROLL_STATE_SETTLING){

                }else if (state == ViewPager.SCROLL_STATE_IDLE && isDragging){
                    isDragging = false;
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(0, 3000);
                }
            }
        });

        //发送消息
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * 比较 view 和 object 是否是同一个实例
         * @param view 页面
         * @param object  方法 instantiateItem 返回的结果
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
//            if (view == object){
//                return true;
//            }else {
//                return false;
//            }
            return view == object;
        }

        /**
         * 相当于 getView 方法
         * @param container  ViewPager自身
         * @param position  当前实例化页面的位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realPosition = position%imageViews.size();
            ImageView imageView = imageViews.get(realPosition);
            container.addView(imageView);  //添加到ViewPager中

            //为图片添加触摸事件，让图片在按下时停止轮播，手指离开时继续轮播
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
//                        case MotionEvent.ACTION_CANCEL:  //ACTION_CANCEL事件是收到前驱事件后，后续事件被父控件拦截的情况下产生
//                            handler.removeCallbacksAndMessages(null);
//                            handler.sendEmptyMessageDelayed(0, 3000);
//                            break;
                        case MotionEvent.ACTION_UP:
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(0, 3000);
                            break;
                    }
                    return false;
                }
            });

            imageView.setTag(realPosition);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    String text = imageDescriptions[position];
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                }
            });

            return imageView;
        }

        /**
         * 释放资源
         * @param container  ViewPager
         * @param position  要释放的位置
         * @param object  要释放的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
