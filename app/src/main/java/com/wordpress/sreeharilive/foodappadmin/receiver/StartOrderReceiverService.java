package com.wordpress.sreeharilive.foodappadmin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wordpress.sreeharilive.foodappadmin.service.OrderReceiverService;


public class StartOrderReceiverService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, OrderReceiverService.class));
    }
}
