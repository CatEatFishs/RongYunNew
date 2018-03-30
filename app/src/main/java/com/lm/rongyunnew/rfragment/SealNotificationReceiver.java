package com.lm.rongyunnew.rfragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by Lm on 2018/3/28.
 * Email:1002464056@qq.com
 */

public class SealNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {

        Log.d("TAG","接受到消息------》onNotificationMessageArrived"+pushNotificationMessage.getTargetId()+"--"+pushNotificationMessage.getPushData());

        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        return false;
    }
}
