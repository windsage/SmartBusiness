package com.tairui.smartcommunity.tenant.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.RadioButton;

import com.jaeger.library.StatusBarUtil;
import com.tairui.smartcommunity.tenant.BuildConfig;
import com.tairui.smartcommunity.tenant.Config;
import com.tairui.smartcommunity.tenant.R;
import com.tairui.smartcommunity.tenant.model.HttpResult;
import com.tairui.smartcommunity.tenant.net.ApiService;
import com.tairui.smartcommunity.tenant.util.ALog;
import com.tairui.smartcommunity.tenant.widget.UpdateDialog;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends CordovaActivity {

    private CordovaWebView cordovaWebView = null;
    private SystemWebView systemWebView = null;

    private RadioButton homeBtn;
    private RadioButton orderBtn;
    private RadioButton commodityBtn;
    private RadioButton quitBtn;

    private String layoutName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, android.R.color.black), 0);
        String username = getIntent().getStringExtra("username");
        layoutName = getIntent().getStringExtra("layout_name");
        JPushInterface.setAlias(this, username, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                ALog.e(s);
            }
        });
        launchUrl = getIntent().getStringExtra("path");
        homeBtn = (RadioButton) findViewById(R.id.radioButton_home);
        orderBtn = (RadioButton) findViewById(R.id.radioButton_order);
        commodityBtn = (RadioButton) findViewById(R.id.radioButton_commodity);
        quitBtn = (RadioButton) findViewById(R.id.radioButton_quit);
        homeBtn.setChecked(true);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBtn.setChecked(true);
                orderBtn.setChecked(false);
                commodityBtn.setChecked(false);
                quitBtn.setChecked(false);
                launchUrl = Config.BASE_URL + "index.do?phoneos=0";
                loadUrl(launchUrl);
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBtn.setChecked(false);
                orderBtn.setChecked(true);
                commodityBtn.setChecked(false);
                quitBtn.setChecked(false);
                launchUrl = Config.BASE_URL + "orderinfo/index.do?phoneos=0";
                loadUrl(launchUrl);
            }
        });

        commodityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBtn.setChecked(false);
                orderBtn.setChecked(false);
                commodityBtn.setChecked(true);
                quitBtn.setChecked(false);
                launchUrl = Config.BASE_URL + "goods/index.do?phoneos=0";
                loadUrl(launchUrl);
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBtn.setChecked(false);
                orderBtn.setChecked(false);
                commodityBtn.setChecked(false);
                quitBtn.setChecked(true);
                SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("password", "");
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("auto", false);
                startActivity(intent);
                finish();
            }
        });
        loadUrl(launchUrl);
        checkUpdate();

        if (layoutName != null) {
            if (layoutName.equals("home")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        homeBtn.performClick();
                    }
                });
            } else if (layoutName.equals("order")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        orderBtn.performClick();
                    }
                });
            }
        }
    }

    public void reload(String url) {
        launchUrl = url;
        loadUrl(launchUrl);
    }

    @Override
    protected void createViews() {
        if (preferences.contains("BackgroundColor")) {
            int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
            // Background of activity:
            appView.getView().setBackgroundColor(backgroundColor);
        }

        appView.getView().requestFocusFromTouch();
    }

    @Override
    protected CordovaWebView makeWebView() {
        systemWebView = (SystemWebView) findViewById(R.id.cordovaWebView);
        cordovaWebView = new CordovaWebViewImpl(new SystemWebViewEngine(systemWebView));
        try {
            systemWebView.setWebViewClient(new SystemWebViewClient((SystemWebViewEngine) cordovaWebView.getEngine()) {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }

                @Override
                public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                    super.doUpdateVisitedHistory(view, url, isReload);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cordovaWebView;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && systemWebView.canGoBack()) {
            systemWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void checkUpdate() {
        ApiService.getService().checkUpdate(BuildConfig.VERSION_NAME, 0).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult httpResult) {
                        if (httpResult.getCode() == Config.SUCCESS) {
                            //有版本更新
                            String url = httpResult.getMsg();
                            showDownloadDialog(url);
                        }
                    }
                });
    }

    /**
     * 版本更新提示
     */
    private void showDownloadDialog(String fileUrl) {
        UpdateDialog mUpdateDialog = new UpdateDialog(this, R.style.DialogStyle, fileUrl);
        mUpdateDialog.show();
    }
}
