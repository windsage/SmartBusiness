package org.apache.cordova.intent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RadioButton;

import com.tairui.smartcommunity.tenant.R;
import com.tairui.smartcommunity.tenant.ui.LoginActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

/**
 * Intent cordova plugin
 * Created by Jeffery on 2017/5/12.
 */

public class PluginIntent extends CordovaPlugin {

    private static CallbackContext cbCtx = null;

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        cbCtx = callbackContext;
        if ("goBack".equals(action)) {
            goBack();
            return true;
        } else if ("startLogin".equals(action)) {
            startLogin();
            return true;
        } else if ("goHome".equals(action)) {
            goHome();
            return true;
        } else if ("goOrder".equals(action)) {
            goOrder();
            return true;
        } else if ("goCommodity".equals(action)) {
            goCommodity();
            return true;
        }else if ("quit".equals(action)){
            quit();
            return true;
        }
        return super.execute(action, args, callbackContext);
    }

    /**
     * 返回方法
     */
    private void goBack() {
        cordova.getActivity().finish();
    }


    /**
     * 调用登录界面
     */
    private void startLogin() {
        Intent intent = new Intent(cordova.getActivity(), LoginActivity.class);
        intent.putExtra("auto", false);
        cordova.getActivity().startActivity(intent);
        cordova.getActivity().finish();
    }

    /**
     * 调用首页
     */
    private void goHome() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RadioButton group = (RadioButton) (cordova.getActivity()).findViewById(R.id.radioButton_home);
                group.performClick();
            }
        });

    }

    /**
     * 调用订单
     */
    private void goOrder() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RadioButton group = (RadioButton) (cordova.getActivity()).findViewById(R.id.radioButton_order);
                group.performClick();
            }
        });
    }

    /**
     * 调用商品
     */

    private void goCommodity() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RadioButton group = (RadioButton) (cordova.getActivity()).findViewById(R.id.radioButton_commodity);
                        group.performClick();
                    }
                });

            }
        });
    }


    /**
     * 调用退出
     */
    private void quit() {
        SharedPreferences sp = cordova.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password", "");
        editor.apply();
        Intent intent = new Intent(cordova.getActivity(), LoginActivity.class);
        intent.putExtra("auto", false);
        cordova.getActivity().startActivity(intent);
        cordova.getActivity().finish();
    }
}