package com.lm.rongyunnew;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lm.rongyunnew.retrofit.UserBean;
import com.lm.rongyunnew.rfragment.Friend;
import com.lm.rongyunnew.rfragment.HomeActivity;
import com.lm.rongyunnew.simple2.BaseSubscriber;
import com.lm.rongyunnew.simple2.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

public class MainActivity extends AppCompatActivity implements RongIM.UserInfoProvider {

    private EditText ed;
    ArrayList<Friend> userIdList = new ArrayList<Friend>();
   // public static final String TOKEN ="AzDJwMVt2nRAZOFVsKTbKq+YsUIoF3ojin3K277sfOlvDwWFPGN4jClKhWxl7gE0N9pVf/zbHE5GfOaV2jK4tatdpZUyLdaH";
    //悟空  18673668974
    private static final String token1 = "ltIQGd2PMHtQNquy9FauVzp/dPyTEiStB6FwuVPqjyR5TkWat++/Q25vRZTFYmoEb1VTznHv/JTQ50r8fSDdA1UwGxWcxjW+";
    //贝吉塔 18673668975
    private static final String token2 = "+9n3twnAs5HLs0FjGkYHMhPN0mC32hu645j9aAVo6D42Y5ALOj5SuxI3JrUSXjSimeNFyPlOUhyWy3HoIAgbmrticAIEG1G+";
    //希特 18673668976
    private static final String token3 = "AlVE6YaATpAFTZy31SUtzFQqrKMgifCALlpd4GquzNtBgztK0k5Y5rkNZQDEAEsdCDVIkoazt1Ud/ku2O9etLORMCknkewf2";
    //海马客服 18673668977
    private static final String token4 = "vZFFeKnyF6add3wwV4N6JFQqrKMgifCALlpd4GquzNtBgztK0k5Y5ueWghQVNO4AXDyypJgOTEwd/ku2O9etLLdG2sJN6loq";

    private String string="";
    private String roomId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUserInfo();
        String packageName = this.getPackageName();
        log("----------->"+packageName);
        initView();
        connectRongServer(token1);
        initData();


        // sendParamsPost();

//   RetrofitClient.getServiceApi()
//                .getUserBean()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<UserBean>() {
//                    @Override
//                    protected void onError(String errorCode, String errorMessage) {
//                        toast("-------->shibai");
//                    }
//
//                    @Override
//                    public void onNext(UserBean userBean) {
//                        toast(userBean.toString());
//                    }
//                });






    }


    @Override
    protected void onDestroy() {
        RongIM.getInstance().disconnect();
        super.onDestroy();
    }

    private void initData() {
        findViewById(R.id.btn_chat_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //启动会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(MainActivity.this, "18673668974", "悟空");
            }
        });

        findViewById(R.id.btn_chatlist_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectRongServer(token2);
                //启动会话列表
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });
        findViewById(R.id.btn_createGroup_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectRongServer(token1);
                //创建群聊
                final List<String> testList = new ArrayList<String>();
                testList.add("18673668975");
                testList.add("18673668976");
                testList.add("18673668977");
                RongIM.getInstance().createDiscussion("123", testList, new RongIMClient.CreateDiscussionCallback() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                            ed = (EditText) findViewById(R.id.ed_id_main);
                            ed.setText(s);
                            roomId=s;


                        }
                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            //  Toast.makeText(MainActivity.this,errorCode.getValue()+"",Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });


        findViewById(R.id.btn_joingroup_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectRongServer(token1);
                RongIM.getInstance().startDiscussionChat(MainActivity.this,ed.getText().toString(),"456");
            }
        });


        //添加讨论组成员
        findViewById(R.id.btn_addgroup_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectRongServer(token1);
                final ArrayList<String> userIds = new ArrayList<String>();
                userIds.add("10");//增加 userId。


                RongIM.getInstance().addMemberToDiscussion(roomId, userIds, new RongIMClient.OperationCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Toast.makeText(MainActivity.this,errorCode.getValue()+"",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        //移除讨论组成员
        findViewById(R.id.btn_delgroup_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                            RongIM.getInstance().getRongIMClient().removeMemberFromDiscussion("bac45e36-3ef3-45b5-9047-dd25054c27ae", "10", new RongIMClient.OperationCallback() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(MainActivity.this, "移除成功", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    Toast.makeText(MainActivity.this, errorCode.getValue()+"", Toast.LENGTH_SHORT).show();
                                }
                            });
            }
        });


        //发送消息
        findViewById(R.id.btn_sendmessage_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 构造 TextMessage 实例
                TextMessage myTextMessage = TextMessage.obtain("发送消息");
                /* 生成 Message 对象。
                * "7127" 为目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
                * Conversation.ConversationType.PRIVATE 为私聊会话类型，根据需要，也可以传入其它会话类型，如群组，讨论组等。
                */
                Message myMessage = Message.obtain("18673668975", Conversation.ConversationType.PRIVATE, myTextMessage);

                RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        //消息本地数据库存储成功的回调
                        Toast.makeText(MainActivity.this, "本地数据库存储成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(Message message) {
                        //消息通过网络发送成功的回调
                        Toast.makeText(MainActivity.this, "网络发送成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        //消息发送失败的回调
                        Toast.makeText(MainActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void initView() {

    }
    private void connectRongServer(String token1) {
        RongIM.connect(token1, new RongIMClient.ConnectCallback() {
            //token1参数报错
            @Override
            public void onTokenIncorrect() {
                Log.e("TAG","参数错误");
                Toast.makeText(MainActivity.this, "token1参数报错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String s) {
                Log.e("TAG","成功");
                Toast.makeText(MainActivity.this, "连接成功 ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("TAG","失败");
                Toast.makeText(MainActivity.this, errorCode.getValue()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //初始化用户信息
    private void initUserInfo() {

        userIdList.add(new Friend("18673668974", "悟空", "http://pic1.win4000.com/pic/c/87/866c1411319.jpg"));//悟空图标
        userIdList.add(new Friend("18673668975", "贝吉塔", "http://pic1.win4000.com/pic/e/f1/4fb01408746.jpg"));//贝吉塔图标
        userIdList.add(new Friend("18673668976", "希特", "http://imgsrc.baidu.com/forum/w%3D580/sign=eefd92535082b2b7a79f39cc01accb0a/994fbd4bd11373f0cdbb65c8ad0f4bfbfaed04e8.jpg"));//希特图标
        userIdList.add(new Friend("18673668977", "比克", "http://pic1.win4000.com/pic/f/63/088a1410587.jpg"));//比克图标
        userIdList.add(new Friend("18673668977", "海马客服", "http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"));
        RongIM.setUserInfoProvider(this, true);
    }
    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void log(String text) {
        Log.e("TAG->Result", text);
    }

    @Override
    public UserInfo getUserInfo(String s) {
        for (Friend i : userIdList) {
            if (i.getUserId().equals(s)) {
                Log.e("TAG", i.getPortraitUri());
                return new UserInfo(i.getUserId(), i.getName(), Uri.parse(i.getPortraitUri()));
            }
        }
        Log.e("TAG", "UserId is : " + s);
        return null;

    }





        /*RetrofitClient.getServiceApi()
                .getUserBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<UserBean>() {
                    @Override
                    protected void onError(String errorCode, String errorMessage) {
                        toast("-------->shibai");
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        toast(userBean.toString());
                    }
                });*/



}
