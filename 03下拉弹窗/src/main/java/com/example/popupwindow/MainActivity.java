package com.example.popupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText et_input;
    private ImageView iv_down_arrow;
    private PopupWindow popupWindow;
    private ListView listView;
    private ArrayList<String> msgs;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_input = (EditText) findViewById(R.id.et_input);
        iv_down_arrow = (ImageView) findViewById(R.id.iv_down_arrow);

        iv_down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null){
                    popupWindow = new PopupWindow(MainActivity.this);
                    popupWindow.setWidth(et_input.getWidth());
                    popupWindow.setHeight(DisplayUtil.dip2px(MainActivity.this, 200));
                    popupWindow.setContentView(listView);
                    popupWindow.setFocusable(true);  //设置焦点
                    Log.i("down", "onClick: ");
                }
                popupWindow.showAsDropDown(et_input, 0, 0);
            }
        });

        listView = new ListView(this);
        listView.setBackgroundResource(R.drawable.listview_background);
        //准备数据
        msgs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            msgs.add(i+"--aaaaaaaaa--"+i);
        }
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.得到数据
                String msg = msgs.get(position);
                //2.设置到输入框
                et_input.setText(msg);
                if (popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                    Log.i("popup", "onItemClick: ");
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                convertView = View.inflate(MainActivity.this, R.layout.item_main, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
                viewHolder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //根据数据得到位置
            String msg = msgs.get(position);
            viewHolder.tv_msg.setText(msg);

            //设置删除
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1.从集合中删除
                    msgs.remove(position);
                    // 2.刷新UI--适配器刷新
                    adapter.notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }
    static class ViewHolder{
        TextView tv_msg;
        ImageView iv_delete;
    }
}
