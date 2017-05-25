package com.example.a00;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_recttextview = (Button) findViewById(R.id.btn_recttextview);
        Button btn_shadertextview = (Button) findViewById(R.id.btn_shadertextview);
        Button btn_myscrollview = (Button) findViewById(R.id.btn_myscrollview);

        btn_recttextview.setOnClickListener(this);
        btn_shadertextview.setOnClickListener(this);
        btn_myscrollview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_recttextview:
                startActivity(new Intent(this, RectTextViewActivity.class));
                break;
            case R.id.btn_shadertextview:
                startActivity(new Intent(this, ShaderTextViewActivity.class));
                break;
            case R.id.btn_myscrollview:
                startActivity(new Intent(this, MyScrollViewActivity.class));
                break;
            default:
                break;
        }
    }
}
