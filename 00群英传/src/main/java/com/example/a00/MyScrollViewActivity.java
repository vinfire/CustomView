package com.example.a00;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MyScrollViewActivity extends AppCompatActivity {

    private int[] images = {R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6};
    private MyScrollView myscrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scroll_view);

        myscrollview = (MyScrollView) findViewById(R.id.myscrollview);

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(images[i]);
            //添加到MyScrollView中
            myscrollview.addView(imageView);
        }
    }
}
