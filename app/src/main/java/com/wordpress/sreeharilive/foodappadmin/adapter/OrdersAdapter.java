package com.wordpress.sreeharilive.foodappadmin.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wordpress.sreeharilive.foodappadmin.R;
import com.wordpress.sreeharilive.foodappadmin.activity.OrderDetailsActivity;
import com.wordpress.sreeharilive.foodappadmin.model.Order;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>{

    private ArrayList<Order> orders;
    private Context context;

    public OrdersAdapter(Context context, ArrayList<Order> orders) {
        this.orders = orders;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.orders_list_item,parent,false);
        return new OrdersAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.addressTV.setText(orders.get(position).getAddress());
        holder.localityTV.setText(orders.get(position).getLocality());
        holder.userIdTV.setText(orders.get(position).getUserID());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("ORDER",orders.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView addressTV;
        TextView localityTV;
        TextView userIdTV;
        View rootView;
        ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            addressTV = (TextView) itemView.findViewById(R.id.userAddressTextView);
            localityTV = (TextView) itemView.findViewById(R.id.userLocalityTextView);
            userIdTV = (TextView) itemView.findViewById(R.id.userIDTextView);
        }
    }
}
