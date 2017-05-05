package com.example.youkuDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_icon_home;
    private ImageView iv_icon_menu;
    private RelativeLayout rl_level1;
    private RelativeLayout rl_level2;
    private RelativeLayout rl_level3;

    private boolean isShowLevel1 = true;  //是否显示第一圆环
    private boolean isShowLevel2 = true;  //是否显示第二圆环
    private boolean isShowLevel3 = true;  //是否显示第三圆环

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initListener();
    }

    private void initListener() {
        iv_icon_home.setOnClickListener(this);
        iv_icon_menu.setOnClickListener(this);
        rl_level1.setOnClickListener(this);
        rl_level2.setOnClickListener(this);
        rl_level3.setOnClickListener(this);
    }

    private void initView() {
        iv_icon_home = (ImageView) findViewById(R.id.iv_icon_home);
        iv_icon_menu = (ImageView) findViewById(R.id.iv_icon_menu);
        rl_level1 = (RelativeLayout) findViewById(R.id.rl_level1);
        rl_level2 = (RelativeLayout) findViewById(R.id.rl_level2);
        rl_level3 = (RelativeLayout) findViewById(R.id.rl_level3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_level1:
                
                break;

            case R.id.rl_level2:
                break;

            case R.id.rl_level3:
                break;

            case R.id.iv_icon_home:
                //如果三级菜单和二级菜单是显示的，都设置隐藏
                //如果都是隐藏的，让二级菜单显示
                if (isShowLevel2){
                    Tools.hideView(rl_level2);
                    isShowLevel2 = false;
                    if (isShowLevel3){
                        Tools.hideView(rl_level3, 200);
                        isShowLevel3 = false;
                    }
                }else {
                    Tools.showView(rl_level2);
                    isShowLevel2 = true;
                }
                break;

            case R.id.iv_icon_menu:
                if (isShowLevel3){
                    Tools.hideView(rl_level3);
                    isShowLevel3 = false;
                }else {
                    Tools.showView(rl_level3);
                    isShowLevel3 = true;
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU){
            //如果一级，二级，三级菜单是显示的就全部隐藏
            if (isShowLevel1){
                Tools.hideView(rl_level1);
                isShowLevel1 = false;
                if (isShowLevel2){
                    Tools.hideView(rl_level2, 200);
                    isShowLevel2 = false;
                    if (isShowLevel3){
                        Tools.hideView(rl_level3, 400);
                        isShowLevel3 = false;
                    }
                }
            }else {
                Tools.showView(rl_level1);
                isShowLevel1 = true;
                Tools.showView(rl_level2, 200);
                isShowLevel2 = true;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
