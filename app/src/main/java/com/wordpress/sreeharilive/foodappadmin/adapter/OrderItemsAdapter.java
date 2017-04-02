package com.wordpress.sreeharilive.foodappadmin.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.sreeharilive.foodappadmin.R;
import com.wordpress.sreeharilive.foodappadmin.model.FoodItem;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {

    private ArrayList<FoodItem> items;
    private Context context;

    public OrderItemsAdapter(Context context, ArrayList<FoodItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public OrderItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.order_item,parent,false);
        return new OrderItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderItemsAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).getItemName());
        holder.itemQty.setText(String.valueOf(items.get(position).getQty()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQty;
        ViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.orderItemNameTextView);
            itemQty = (TextView) itemView.findViewById(R.id.orderQuantityTextView);
        }
    }
}
