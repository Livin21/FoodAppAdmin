package com.wordpress.sreeharilive.foodappadmin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.wordpress.sreeharilive.foodappadmin.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton showOrdersButton = (ImageButton) findViewById(R.id.showOrdersButton);
        ImageButton updateStockButton = (ImageButton) findViewById(R.id.updateStockButton);

        showOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Orders.class));
            }
        });

        updateStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UpdateStock.class));
            }
        });

    }
}
