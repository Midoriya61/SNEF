package com.tinlm.snef.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.model.OrderDetail;
import com.tinlm.snef.model.StoreOrderItem;

import java.util.List;

public class ListStoreOrderItemAdapter extends RecyclerView.Adapter<ListStoreOrderItemAdapter.ListStoreOrderItemHolder> {

    Context mContext;
    List<StoreOrderItem> storeOrderItems;

    public ListStoreOrderItemAdapter(Context mContext, List<StoreOrderItem> storeOrderItems) {
        this.mContext = mContext;
        this.storeOrderItems = storeOrderItems;
    }

    @NonNull
    @Override
    public ListStoreOrderItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_store_order_item, viewGroup,false);
        return new ListStoreOrderItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListStoreOrderItemHolder listStoreOrderItemHolder, int i) {
        StoreOrderItem  storeOrderItem = storeOrderItems.get(i);
        listStoreOrderItemHolder.tvStoreName.setText(storeOrderItem.getStoreName());
        listStoreOrderItemHolder.tvNumberProduct.setText(storeOrderItem.getQuantityOrder() + " > ");
    }

    @Override
    public int getItemCount() {
        return storeOrderItems.size();
    }

    public static class ListStoreOrderItemHolder extends RecyclerView.ViewHolder {

        TextView tvStoreName,tvNumberProduct;

        CardView cardOrderStoreItem;

        public ListStoreOrderItemHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvNumberProduct = itemView.findViewById(R.id.tvNumberProduct);
            cardOrderStoreItem = itemView.findViewById(R.id.cardOrderStoreItem);

        }
    }
}
