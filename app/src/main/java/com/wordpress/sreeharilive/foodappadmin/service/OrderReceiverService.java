package com.wordpress.sreeharilive.foodappadmin.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.wordpress.sreeharilive.foodappadmin.R;
import com.wordpress.sreeharilive.foodappadmin.activity.OrderDetailsActivity;
import com.wordpress.sreeharilive.foodappadmin.model.FoodItem;
import com.wordpress.sreeharilive.foodappadmin.model.Order;

import java.util.ArrayList;

public class OrderReceiverService extends Service {

    ArrayList<String> notifiedOrders;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notifiedOrders = new ArrayList<>();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        FirebaseDatabase.getInstance().getReference().child("orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                try {
                    String orderId = dataSnapshot.getKey();
                    String locality = dataSnapshot.child("locality").getValue().toString();
                    String address = dataSnapshot.child("address").getValue().toString();
                    String mode = dataSnapshot.child("mode_of_payment").getValue().toString();
                    long timestamp = Long.parseLong(
                            dataSnapshot.child("timestamp").getValue().toString()
                    );
                    String userId = dataSnapshot.child("userId").getValue().toString();
                    double total = 0;
                    ArrayList<FoodItem> foodItems = new ArrayList<>();
                    for (DataSnapshot item : dataSnapshot.child("order").getChildren()){
                        if (item.getKey().equals("total")){
                            total = Double.parseDouble(item.getValue().toString());
                        }else {
                            String category = item.child("category").getValue().toString();
                            String fid = item.child("fid").getValue().toString();
                            String name = item.child("item").getValue().toString();
                            int qty = Integer.parseInt(
                                    item.child("quantity").getValue().toString()
                            );
                            foodItems.add(new FoodItem(name,category,fid,qty));
                        }
                    }
                    Order order = new Order(orderId,address,locality,userId,total,foodItems,timestamp,mode);


                    Context context = OrderReceiverService.this;


                    if (!notifiedOrders.contains(orderId)){
                        notifiedOrders.add(orderId);

                        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
                        wl.acquire();

                        android.support.v4.app.NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(context)
                                        .setSmallIcon(R.mipmap.ic_launcher_round)
                                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                                        .setContentTitle("Order Received")
                                        .setContentText(locality)
                                        .setDefaults(Notification.DEFAULT_ALL)
                                        .setPriority(Notification.PRIORITY_MAX)
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Address: " + address));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                        }
                        Intent resultIntent = new Intent(context, OrderDetailsActivity.class);
                        resultIntent.putExtra("ORDER",order);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                        stackBuilder.addParentStack(OrderDetailsActivity.class);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent((int) (Math.random()*100), PendingIntent.FLAG_ONE_SHOT);
                        mBuilder.setContentIntent(resultPendingIntent);
                        //Notification notification = new Notification.BigTextStyle().bigText(data.getReference() + ": " + data.getScripture()).build();
                        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify((int) (Math.random()*100), mBuilder.build());
                        wl.release();

                    }

                }catch (NullPointerException ignored){}

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return START_REDELIVER_INTENT;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent("com.wordpress.sreeharilive.foodappadmin.service.RESTART_SERVICE");
        sendBroadcast(intent);
    }
}
