package com.buynow.fansafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.buynow.fansafe.R;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by buynow on 16-6-4.
 * App 主界面
 */
public class HomeActivity extends Activity {
    //显示菜单标题 及其 描述
    private static final String[] names = new String[]{"手机防盗","通讯卫士",
            "软件管家","进程管理","流量统计","病毒查杀","缓存清理","高级工具"};
    private static final String[] descs = new String[]{"手机丢失好找","防骚扰防监听",
            "方便管理软件","保持手机通畅","注意流量超标","手机安全保障","手机快步如飞","特性处理更好"};
    //菜单图标
    private static final int[] icons = new int[]{R.drawable.sjfd,R.drawable.srlj,R.drawable.rjgj,R.drawable.jcgl,
            R.drawable.lltj,R.drawable.sjsd,R.drawable.hcql,R.drawable.szzx};

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return names.length;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.item_home_gv, null);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);

            iv_icon.setImageResource(icons[position]);
            tv_title.setText(names[position]);
            tv_desc.setText(descs[position]);
            return view;
        }
    }
    private ImageView iv_loge;
    private ImageView iv_setting;
    private GridView gv_control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();

        initData();
    }

    private void initData() {
        MyAdapter adapter = new MyAdapter();
        gv_control.setAdapter(adapter);

    }

    private void initView() {
        iv_loge = (ImageView) findViewById(R.id.iv_loge);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        gv_control = (GridView)findViewById(R.id.gv_home_tools);

        startAnimation();
    }

    //为图片添加动画 loge
    private void startAnimation() {
        //属性动画 loge旋转
        ObjectAnimator oa = ObjectAnimator.ofFloat(iv_loge, "rotationY", 0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360);
        oa.setDuration(1000);
        oa.setRepeatCount(ObjectAnimator.INFINITE);
        oa.start();
    }
}
