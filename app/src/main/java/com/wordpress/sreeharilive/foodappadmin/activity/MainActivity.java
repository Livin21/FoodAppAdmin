package com.wordpress.sreeharilive.foodappadmin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wordpress.sreeharilive.foodappadmin.R;

import static com.wordpress.sreeharilive.foodappadmin.R.id.processOrderButton;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton showOrdersButton = (ImageButton) findViewById(R.id.showOrdersButton);
        ImageButton updateStockButton = (ImageButton) findViewById(R.id.updateStockButton);
        ImageButton processOderButton =(ImageButton) findViewById(R.id.processOrderButton);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing In");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseAuth.getInstance().signInWithEmailAndPassword("sreeharivijayan619@gmail.com","password").addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Error Signing In", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
        );

        showOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OrdersActivity.class));
            }
        });

        updateStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UpdateStockActivity.class));
            }
        });
        processOderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProcessOrderActivity.class));
            }
        });

    }
}
