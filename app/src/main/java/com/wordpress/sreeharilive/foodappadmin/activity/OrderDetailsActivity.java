package com.wordpress.sreeharilive.foodappadmin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wordpress.sreeharilive.foodappadmin.R;
import com.wordpress.sreeharilive.foodappadmin.model.Order;

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

        

    }
}
