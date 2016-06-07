package com.buynow.fansafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buynow.fansafe.R;
import com.buynow.fansafe.utils.MD5utils;
import com.buynow.fansafe.utils.MyConstants;
import com.buynow.fansafe.utils.SPUtils;
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
    private AlertDialog mAd;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        initView();

        initData();

        initEvent();
    }

    private void initEvent() {
        gv_control.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //"手机防盗","通讯卫士","软件管家","进程管理","流量统计","病毒查杀","缓存清理","高级工具"
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://手机防盗
                        //首次使用设置密码
                        //之后先弹出输入密码验证
                        String password = SPUtils.getString(getApplicationContext(), MyConstants.PASSWORD);

                        if (TextUtils.isEmpty(password)) {
                            //第一次使用 设置密码
                            showSetPasswordDialog(true);
                        }else {
                            showSetPasswordDialog(false);
                        }
                        break;

                }
            }
        });
    }


    //显示设置密码对话框
    private void showSetPasswordDialog(boolean isSetPassword) {
        AlertDialog.Builder ab = new AlertDialog.Builder(HomeActivity.this);
        View view = View.inflate(HomeActivity.this, R.layout.dialog_set_password, null);
        final EditText et_inputPassword = (EditText) view.findViewById(R.id.et_inputPassword);
        final EditText et_confirmPassword = (EditText) view.findViewById(R.id.et_confirmPassword);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);

        View.OnClickListener listener ;
        if (!isSetPassword){
            tv_title.setText("密码认证");
            final String password = SPUtils.getString(HomeActivity.this, MyConstants.PASSWORD);
            //不为初次设置密码的情况
            //设置确认密码框为隐藏
            et_confirmPassword.setVisibility(View.GONE);
            //确认密码监听
            listener = new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String userInputPassword = et_inputPassword.getText().toString().trim();
                    //密文对比
                    userInputPassword = MD5utils.encode(userInputPassword);
                    if (userInputPassword.equals(password)) {
                        //登陆成功
                        //TODO 进入防盗界面

                    }else {
                        //密码认证失败
                        Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            };
        }else{
            //设置密码 监听
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password = et_inputPassword.getText().toString().trim();
                    String confirmPassword = et_confirmPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!password.equals(confirmPassword)) {
                        Toast.makeText(HomeActivity.this, "两次密码不匹配", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //保存成功
                    //对密码加密
                    password = MD5utils.encode(password);
                    SPUtils.putString(HomeActivity.this,MyConstants.PASSWORD,password);
                    Toast.makeText(HomeActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
                    //退出对话框
                    mAd.dismiss();
                }
            };
        }



        //确定
        view.findViewById(R.id.bt_commit).setOnClickListener(listener);
        //取消按钮
        //点击退出对话框
        view.findViewById(R.id.bt_cancal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAd.dismiss();
            }
        });

        ab.setView(view);
        mAd = ab.create();
        mAd.show();
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
