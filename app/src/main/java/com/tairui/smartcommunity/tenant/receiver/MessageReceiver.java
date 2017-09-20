package com.tairui.smartcommunity.tenant.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.tairui.smartcommunity.tenant.R;
import com.tairui.smartcommunity.tenant.ui.LoginActivity;
import com.tairui.smartcommunity.tenant.util.ALog;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 推送消息的广播
 * Created by Jeffery on 2017/5/4.
 */

public class MessageReceiver extends BroadcastReceiver {

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        //badge
        SharedPreferences sp = context.getSharedPreferences("smart_business", Context.MODE_PRIVATE);
        int badgeCount = sp.getInt("badgeCount", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            ALog.e("JPush用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            ALog.e("接受到推送下来的自定义消息");
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            badgeCount++;
            editor.putInt("badgeCount", badgeCount);
            editor.apply();
            ShortcutBadger.applyCount(context, badgeCount);
            MediaPlayer mp = MediaPlayer.create(context, R.raw.neworder);
            mp.setLooping(false);
            mp.start();
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            badgeCount--;
            editor.putInt("badgeCount", badgeCount);
            editor.apply();
            ShortcutBadger.applyCount(context, badgeCount);
            openNotification(context, bundle);
//            Intent i = new Intent(context, LoginActivity.class);  //自定义打开的界面
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
        } else {
            ALog.e("Unhandled intent - " + intent.getAction());
        }
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("layout_name");
            Intent i = new Intent(context, LoginActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("layout_name", myValue);
            context.startActivity(i);
        } catch (Exception e) {
            ALog.e("Unexpected: extras is not a valid json", e);
        }
    }
}
