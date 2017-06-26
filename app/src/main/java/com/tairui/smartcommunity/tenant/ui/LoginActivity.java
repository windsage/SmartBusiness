package com.tairui.smartcommunity.tenant.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.tairui.smartcommunity.tenant.Config;
import com.tairui.smartcommunity.tenant.R;
import com.tairui.smartcommunity.tenant.model.HttpResult;
import com.tairui.smartcommunity.tenant.net.ApiService;
import com.tairui.smartcommunity.tenant.util.ALog;
import com.tairui.smartcommunity.tenant.util.Utils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements SoftKeyBoardStatusView.SoftKeyboardListener {

    @BindView(R.id.login_username_et)
    EditText mUsernameEt;
    @BindView(R.id.login_password_et)
    EditText mPasswordEt;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.reset_tv)
    TextView mResetTv;
    @BindView(R.id.login_eye_iv)
    ImageView mLoginEyeIv;
    @BindView(R.id.login_view)
    LinearLayout loginView;
    @BindView(R.id.login_soft_status_view)
    SoftKeyBoardStatusView statusView;

    private int scroll_dx;
    private int screenHeight;
    private boolean eyeOpen = false;
    private boolean autoLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 40);
        ButterKnife.bind(this);
        autoLogin = getIntent().getBooleanExtra("auto", true);
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        initViews();
    }

    /**
     * 初始化UI以及点击事件
     */
    private void initViews() {
        statusView.setSoftKeyBoardListener(this);
        RxView.clicks(mLoginEyeIv).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (eyeOpen) {
                    mPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mLoginEyeIv.setImageResource(R.drawable.ic_eye_close);
                    eyeOpen = false;
                } else {
                    mPasswordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mLoginEyeIv.setImageResource(R.drawable.ic_eye_open);
                    eyeOpen = true;
                }
            }
        });

        RxView.clicks(mLoginBtn).throttleFirst(Config.CLICK_SPAN, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                login();
            }
        });
        RxView.clicks(mResetTv).throttleFirst(Config.CLICK_SPAN, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //跳转重置的网页
                Intent intent = new Intent(LoginActivity.this, WebActivity.class);
                intent.putExtra("path", Config.BASE_URL + "business/forget.do?phoneos=0");
                startActivity(intent);
            }
        });
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        if (!username.equals(""))
            mUsernameEt.setText(username);
        if (!password.equals(""))
            mPasswordEt.setText(password);
        //自动登录操作
        if (!username.equals("") && !password.equals("") && autoLogin) {
            login();
        }
    }

    private void login() {
        final String username = mUsernameEt.getText().toString().replace(" ", "").trim();
        final String password = mPasswordEt.getText().toString().replace(" ", "");
        //for test
//        username = "18858731688";
//        password = "rite58731688";
        if (!Utils.isMobileExact(username)) {
            Toast.makeText(LoginActivity.this, "手机号码不合法", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiService.getService().login(username, Utils.Encrypt(password), 0)
                .subscribeOn(Schedulers.io())
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
                            String path = httpResult.getMsg();
                            if (path.startsWith("../")) {
                                path = Config.BASE_URL + path.substring(3);
                            }
                            SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("username", username);
                            editor.putString("password", password);
                            editor.apply();
                            ALog.e(httpResult.getMsg());
                            if (path != null && !path.equals("")) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("path", path);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            String msg = httpResult.getMsg();
                            if (msg != null && !msg.equals("")) {
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                mPasswordEt.setText("");
                            }
                        }
                    }
                });
    }


    @Override
    public void keyBoardStatus(int w, int h, int oldw, int oldh) {

    }

    @Override
    public void keyBoardVisible(int move) {
        int[] location = new int[2];
        mLoginBtn.getLocationOnScreen(location);
        int btnToBottom = screenHeight - location[1] - mLoginBtn.getHeight();
        scroll_dx = btnToBottom > move ? 0 : move - btnToBottom;
        loginView.scrollBy(0, scroll_dx);
    }

    @Override
    public void keyBoardInvisible(int move) {
        loginView.scrollBy(0, -scroll_dx);
    }
}
