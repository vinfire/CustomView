package com.example.myviewpager;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {

    private RadioGroup rg_main;
    private MyViewPager myViewPager;

    private int[] images = {R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        myViewPager = (MyViewPager) findViewById(R.id.myviewpager);

        //添加页面
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(images[i]);
            //添加到MyViewPager中
            myViewPager.addView(imageView);
        }

        //为RadioGroup添加指示器的点
        for (int i = 0; i < myViewPager.getChildCount(); i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);  //为RadioButton设置ID
            if (i == 0){
                button.setChecked(true);
            }

            //添加到RadioGroup
            rg_main.addView(button);
        }

        //设置RadioGroup选中状态的变化
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                myViewPager.scrollToPager(checkedId);  //根据下标位置定位到具体的某个页面
            }
        });

        //设置监听页面的改变
        myViewPager.setOnPagerChangeListener(new MyViewPager.OnPagerChangeListener() {
            @Override
            public void onScrollToPager(int position) {
                rg_main.check(position);
            }
        });
    }


}
