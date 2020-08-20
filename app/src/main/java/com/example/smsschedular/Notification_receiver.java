package com.example.smsschedular;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;


public class Notification_receiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(intent.getStringExtra("address"),null,intent.getStringExtra("message"),null,null);
    }
}

