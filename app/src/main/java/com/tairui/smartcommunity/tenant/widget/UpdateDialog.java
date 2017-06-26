package com.tairui.smartcommunity.tenant.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tairui.smartcommunity.tenant.R;
import com.tairui.smartcommunity.tenant.service.DownLoadService;

/**
 * 自定义软件升级弹框
 * Created by Jeffery on 2017/5/8.
 */

public class UpdateDialog extends Dialog {

    private String mUrl;
    private Context mContext;

    public UpdateDialog(@NonNull Context context, @StyleRes int themeResId, String fileUrl) {
        super(context, themeResId);
        setContentView(R.layout.dialog_update);
        this.mContext = context;
        this.mUrl = fileUrl;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        setCanceledOnTouchOutside(false);
        initViews();
    }


    private void initViews() {
        TextView mConfirm = (TextView) findViewById(R.id.update_confirm);
        ImageView mCancel = (ImageView) findViewById(R.id.update_cancel);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mUrl.endsWith("apk")) {
                    downApk();
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void downApk() {
        Intent intent = new Intent(mContext, DownLoadService.class);
        intent.putExtra("url", mUrl);
        mContext.startService(intent);
    }

}
