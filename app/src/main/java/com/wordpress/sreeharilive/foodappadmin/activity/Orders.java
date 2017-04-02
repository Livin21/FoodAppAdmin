package com.wordpress.sreeharilive.foodappadmin.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wordpress.sreeharilive.foodappadmin.R;
import com.wordpress.sreeharilive.foodappadmin.model.FoodItem;
import com.wordpress.sreeharilive.foodappadmin.model.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Orders extends AppCompatActivity {

    ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        orders = new ArrayList<>();

        fetchOrders();

    }

    private void fetchOrders() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference().child("orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            String orderId = child.getKey();
                            String address = child.child("address").getValue().toString();
                            String locality = child.child("locality").getValue().toString();
                            long timestamp = Long.parseLong(child.child("timestamp").getValue().toString());
                            String userId = child.child("userId").getValue().toString();
                            double total = Double.parseDouble(child.child("order").child("total").getValue().toString());
                            String modeOfPayment = child.child("mode_of_payment").getValue().toString();
                            ArrayList<FoodItem> items = new ArrayList<>();
                                for (DataSnapshot item : child.child("order").getChildren()){
                                    if (!item.getKey().equals("total")){
                                    String category = item.child("category").getValue().toString();
                                    String fid = item.child("fid").getValue().toString();
                                    String itemName = item.child("item").getValue().toString();
                                    int qty = Integer.parseInt(item.child("quantity").getValue().toString());
                                    items.add(new FoodItem(
                                            itemName,
                                            category,
                                            fid,
                                            qty
                                    ));
                                    }
                                }

                            Order order = new Order(orderId,address,locality,userId,total,items,timestamp,modeOfPayment);
                            orders.add(order);
                        }
                        Log.d("Orders",orders.toString());

                        Collections.sort(
                                orders,
                                new Comparator<Order>() {
                                    @Override
                                    public int compare(Order o1, Order o2) {
                                        if (o1.getTimestamp() > o2.getTimestamp()){
                                            return 1;
                                        }else if (o2.getTimestamp() > o1.getTimestamp()){
                                            return -1;
                                        }else return 0;
                                    }
                                }
                        );

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });

    }

}
