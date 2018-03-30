package com.lm.rongyunnew.rfragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lm.rongyunnew.R;

import io.rong.imkit.RongIM;

import static android.R.attr.type;

public class ReceiveActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        tv = (TextView) findViewById(R.id.tv);
        String pushData = getIntent().getStringExtra("pushData");
        tv.setText(pushData);

    }
}
