package com.tairui.smartcommunity.tenant;

import android.app.Application;

import com.tairui.smartcommunity.tenant.util.ALog;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Jeffery on 2017/5/4.
 */

public class App extends Application {

    public static ALog.Builder builder;

    @Override
    public void onCreate() {
        super.onCreate();
        builder = new ALog.Builder(this)
                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，默认开
                .setGlobalTag("TaiRui")// 设置log全局标签，默认为空
                .setLogHeadSwitch(true)// 设置log头部是否显示，默认显示
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(ALog.E);// log过滤器，和logcat过滤器同理，默认Verbose
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

}
