package com.lm.rongyunnew;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lm.rongyunnew.rfragment.ReceiveActivity;

import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.RealTimeLocationMessageProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.ipc.RongExceptionHandler;
import io.rong.imlib.model.Message;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;

/**
 * Created by Lm on 2018/3/26.
 * Email:1002464056@qq.com
 */

public class app extends Application implements RongIMClient.OnReceiveMessageListener {
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * OnCreate 会被多个进程重入，这段保护代码，
         * 确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            //IMKit SDK调用第一步 初始化
            RongIM.init(this);
        }
        RongIM.setOnReceiveMessageListener(this);




    }
    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    @Override
    public boolean onReceived(Message message, int i) {
        Log.d("TAG","接受到消息------》onReceived"+message.getTargetId()+"--"+message.getContent());
//        Intent intent = new Intent(this,ReceiveActivity.class);
//        String pushData = message.getTargetId();
//        intent.putExtra("pushData",pushData);
//        startActivity(intent);
        return false;
    }



}
