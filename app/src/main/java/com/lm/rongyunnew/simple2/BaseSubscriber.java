package com.lm.rongyunnew.simple2;


import rx.Subscriber;

/**
 * Created by hcDarren on 2017/12/23.
 */

public  abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        // e ：网络异常，解析异常，结果处理过程中异常
        e.printStackTrace();
        if(e instanceof ErrorHandle.ServiceError){
            ErrorHandle.ServiceError serviceError = (ErrorHandle.ServiceError) e;
            onError("",serviceError.getMessage());
        }else {
            // 自己处理
            onError("","未知异常");
        }
    }

    protected abstract void onError(String errorCode, String errorMessage);
}
