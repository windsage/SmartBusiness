package com.tairui.smartcommunity.tenant.net;

import com.tairui.smartcommunity.tenant.model.HttpResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Jeffery on 2017/5/4.
 */

public interface ApiInterface {

    @POST("app/loginsubmit.do")
    @FormUrlEncoded
    Observable<HttpResult> login(@Field("businessphone") String userPhone, @Field("password") String password, @Field("phoneos") int os);

    @POST("app/update.do")
    @FormUrlEncoded
    Observable<HttpResult> checkUpdate(@Field("version") String version, @Field("phoneos") int os);

}
