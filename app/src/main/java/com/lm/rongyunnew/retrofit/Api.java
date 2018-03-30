package com.lm.rongyunnew.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Lm on 2018/3/26.
 * Email:1002464056@qq.com
 */

public interface Api {
    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    Call<UserBean> getCall();
}
