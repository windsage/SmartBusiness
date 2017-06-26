package com.tairui.smartcommunity.tenant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.tairui.smartcommunity.tenant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {

    @BindView(R.id.num)
    TextView mNum;

    private int count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        mNum.setText("跳过 " + String.valueOf(count));
        mHandler.sendEmptyMessageDelayed(0, 1000);

        mNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(0);
                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            count--;
            if (count > 0) {
                mNum.setText("跳过 " + String.valueOf(count));
                mHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                finish();
            }
        }
    };

}
