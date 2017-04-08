package com.wordpress.sreeharilive.foodappadmin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wordpress.sreeharilive.foodappadmin.R;
import com.wordpress.sreeharilive.foodappadmin.adapter.OrderItemsAdapter;
import com.wordpress.sreeharilive.foodappadmin.model.FoodItem;
import com.wordpress.sreeharilive.foodappadmin.model.Order;

import java.util.Random;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView userIdTextView, localityTextView, addressTextView, totalTextView, modeOfPaymentTextView;
    RecyclerView ordersRecyclerView;

    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        order = (Order) getIntent().getSerializableExtra("ORDER");

        userIdTextView = (TextView) findViewById(R.id.userIDTextView);
        localityTextView = (TextView) findViewById(R.id.userLocalityTextView);
        addressTextView = (TextView) findViewById(R.id.userAddressTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        modeOfPaymentTextView = (TextView) findViewById(R.id.modeOfPaymentTextView);

        ordersRecyclerView = (RecyclerView) findViewById(R.id.orderItemsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ordersRecyclerView.setLayoutManager(layoutManager);
        ordersRecyclerView.setHasFixedSize(true);

        userIdTextView.setText(order.getUserID());
        localityTextView.setText(order.getLocality());
        addressTextView.setText(order.getAddress());
        totalTextView.setText(String.valueOf(order.getTotal()));
        modeOfPaymentTextView.setText(order.getModeOfPayment());

        OrderItemsAdapter adapter = new OrderItemsAdapter(this, order.getItems());
        ordersRecyclerView.setAdapter(adapter);

    }

    public void processOrder(View view) {
        FirebaseDatabase.getInstance().getReference().child("orders").child(order.getOrderID()).removeValue();
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("processed_orders")
                .child(order.getOrderID());
        orderRef.child("address").setValue(order.getAddress());
        orderRef.child("locality").setValue(order.getLocality());
        orderRef.child("mode_of_payment").setValue(order.getModeOfPayment());
        orderRef.child("ordered_at").setValue(order.getTimestamp());
        orderRef.child("delivered_at").setValue(System.currentTimeMillis());
        orderRef.child("userId").setValue(order.getUserID());
        for (FoodItem item : order.getItems()){
            DatabaseReference itemRef = orderRef.child("order")
                    .child(newId());
            itemRef.child("category")
                    .setValue(item.getCategory());
            itemRef.child("fid")
                    .setValue(item.getItemId());
            itemRef.child("item")
                    .setValue(item.getItemName());
            itemRef.child("quantity")
                    .setValue(item.getQty());
        }
        orderRef.child("order").child("total").setValue(order.getTotal());

        FirebaseDatabase.getInstance().getReference().child("users").child(order.getUserID()).child("pending_orders").child(order.getOrderID()).removeValue();
        FirebaseDatabase.getInstance().getReference().child("users").child(order.getUserID()).child("delivered_orders").child(order.getOrderID()).child("orderedAt").setValue(order.getTimestamp());
        FirebaseDatabase.getInstance().getReference().child("users").child(order.getUserID()).child("delivered_orders").child(order.getOrderID()).child("deliveredAt").setValue(System.currentTimeMillis());
        finish();

    }

    public String newId(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
