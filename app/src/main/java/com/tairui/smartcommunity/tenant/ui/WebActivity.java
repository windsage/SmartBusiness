package com.tairui.smartcommunity.tenant.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.tairui.smartcommunity.tenant.R;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

/**
 * Created by Jeffery on 2017/5/31.
 */

public class WebActivity extends CordovaActivity {

    private CordovaWebView cordovaWebView = null;
    private SystemWebView systemWebView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        launchUrl = getIntent().getStringExtra("path");
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

}
