package com.lm.rongyunnew.simple2;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hcDarren on 2017/12/16.
 */

public abstract class HttpCallback<T> implements Callback<Result<T>> {
    @Override
    public void onResponse(Call<Result<T>> call, Response<Result<T>> response) {
        Result<T> result = response.body();
        // 在这里处理 错误的 body
        if(result == null){
            // 处理错误情况 ，像 401 等等
            ResponseBody responseBody = response.errorBody();
        }
        if(!result.isOk()){
            onError(result.bol,result.msg);
            return;
        }
        // 解析,获取类上面的泛型
        Class <T> dataClass = (Class <T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Gson gson = new Gson();
        T data = gson.fromJson(result.data.toString(),dataClass);
        onSucceed(data);
    }

    @Override
    public void onFailure(Call<Result<T>> call, Throwable t) {
        // 处理失败，联网，解析出错，自己弄一弄
    }

    public abstract void onSucceed(T result);

    public abstract void onError(String code,String msg);
}
