package com.wordpress.sreeharilive.foodappadmin.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wordpress.sreeharilive.foodappadmin.R;

import java.util.ArrayList;

public class UpdateStock extends AppCompatActivity {

    Spinner categorySpinner;
    Spinner itemSpinner;

    EditText quantityET;

    ArrayList<String> categories;
    ArrayList<String> itemIds;
    ArrayList<String> itemNames;

    String currentCategoryArray[];
    String currentItemsArray[];

    String chosenCategory = "";
    String selectedItemId = "";
    int currentQty = -500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock);

        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        itemSpinner = (Spinner) findViewById(R.id.itemSpinner);
        itemSpinner.setEnabled(false);

        quantityET = (EditText) findViewById(R.id.qtyEditText);
        quantityET.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference()
                .child("items")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        categories = new ArrayList<>();

                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            categories.add(child.getKey());
                        }

                        currentCategoryArray = new String[categories.size()];
                        categories.toArray(currentCategoryArray);

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UpdateStock.this,android.R.layout.simple_spinner_item,currentCategoryArray);
                        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                        categorySpinner.setAdapter(arrayAdapter);

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(UpdateStock.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenCategory = currentCategoryArray[position];
                progressDialog.show();
                FirebaseDatabase.getInstance().getReference()
                        .child("items")
                        .child(chosenCategory)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                itemIds = new ArrayList<>();
                                itemNames = new ArrayList<>();

                                for (DataSnapshot child : dataSnapshot.getChildren()){
                                    itemIds.add(child.getKey());
                                    itemNames.add(child.child("name").getValue().toString());
                                }

                                currentItemsArray = new String[itemNames.size()];
                                itemNames.toArray(currentItemsArray);

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UpdateStock.this,android.R.layout.simple_spinner_item,currentItemsArray);
                                arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                                itemSpinner.setAdapter(arrayAdapter);

                                itemSpinner.setEnabled(true);

                                progressDialog.dismiss();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                progressDialog.dismiss();
                                Toast.makeText(UpdateStock.this, "Something Bad Happened", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemId = itemIds.get(position);
                progressDialog.show();
                FirebaseDatabase.getInstance().getReference()
                        .child("items")
                        .child(chosenCategory)
                        .child(selectedItemId)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    String qty = dataSnapshot.child("count").getValue().toString();
                                    currentQty = Integer.parseInt(qty);
                                    quantityET.setEnabled(true);
                                    quantityET.setText(qty);
                                    progressDialog.dismiss();
                                }catch (NullPointerException ignored){}
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(UpdateStock.this, "Something Bad Happened", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void updateQuantity(View view) {
        if (chosenCategory.isEmpty()){
            Toast.makeText(this, "Choose Category", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedItemId.isEmpty()){
            Toast.makeText(this, "Choose Item", Toast.LENGTH_SHORT).show();
            return;
        }
        int newQty = Integer.parseInt(quantityET.getText().toString());
        if (newQty == currentQty){
            Toast.makeText(this, "Change quantity to update", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println("Item: " + selectedItemId + ", Qty: " + currentQty);
        FirebaseDatabase.getInstance().getReference().child("items")
                .child(chosenCategory)
                .child(selectedItemId)
                .child("count")
                .setValue(newQty);
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        finish();
    }
}
