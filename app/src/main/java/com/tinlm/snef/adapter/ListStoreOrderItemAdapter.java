package com.tinlm.snef.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.activity.PayPalActivity;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.StoreOrderItem;

import java.text.DecimalFormat;
import java.util.List;

public class ListStoreOrderItemAdapter extends RecyclerView.Adapter<ListStoreOrderItemAdapter.ListStoreOrderItemHolder> {

    Context mContext;
    List<StoreOrderItem> storeOrderItems;
    DecimalFormat df = new DecimalFormat("#,###,###,###");

    //    List<OrderDetail> orderDetails;


    public ListStoreOrderItemAdapter(Context mContext, List<StoreOrderItem> storeOrderItems) {
        this.mContext = mContext;
        this.storeOrderItems = storeOrderItems;

    }

    @NonNull
    @Override
    public ListStoreOrderItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_store_order_item, viewGroup, false);
        return new ListStoreOrderItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListStoreOrderItemHolder listStoreOrderItemHolder, int a) {
        final StoreOrderItem storeOrderItem = storeOrderItems.get(a);
        final int positon = a;
//        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
//        Map<Integer, String> listImageProduct = new HashMap<>();
        DBManager dbManager = new DBManager(mContext);
        List<Cart> cartList = dbManager.getAllCartByStoreName(storeOrderItems.get(a).getStoreName());
//        for (int i = 0; i < cartList.size(); i++) {
//
//            String productImage = imageUltilities.getOneImageByStoreProductId(cartList.get(i).getFspId());
//            listImageProduct.put(cartList.get(i).getFspId(), productImage);
//
//        }

        ListCartAdapter listCartAdapter = new ListCartAdapter(mContext, cartList, listStoreOrderItemHolder.txtTotalCartPrice);

//        listStoreOrderItemHolder.rcListCartItem.getAdapter();

        listStoreOrderItemHolder.rcListCartItem.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        listStoreOrderItemHolder.rcListCartItem.setAdapter(listCartAdapter);

        listCartAdapter.notifyDataSetChanged();

        int totalAmount = 0;

        for (int i = 0; i < cartList.size(); i++) {

            totalAmount += (((cartList.get(i).getPrice() * (100 - cartList.get(i).getDiscount())) / 100) * cartList.get(i).getQuantity());
        }
        listStoreOrderItemHolder.txtTotalCartPrice.setText(String.valueOf(df.format(totalAmount)));


//        listStoreOrderItemHolder.cardOrderStoreItem.setOnClickListener(new View.OnClickListener() {
////
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, CartActivity.class);
//                intent.putExtra(ConstainApp.JS_STORENAME,storeOrderItem.getStoreName());
//                mContext.startActivity(intent);
//            }
//        });
        listStoreOrderItemHolder.tvStoreName.setText(storeOrderItem.getStoreName());

        listStoreOrderItemHolder.btnCheckout.setOnClickListener(new View.OnClickListener() {
            //
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PayPalActivity.class);
                intent.putExtra(ConstainApp.JS_STORENAME, storeOrderItem.getStoreName());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return storeOrderItems.size();
    }

    public static class ListStoreOrderItemHolder extends RecyclerView.ViewHolder {

        TextView tvStoreName, txtTotalCartPrice;

        CardView cardOrderStoreItem, btnCheckout;

        RecyclerView rcListCartItem;


        public ListStoreOrderItemHolder(@NonNull View itemView) {
            super(itemView);
            txtTotalCartPrice = itemView.findViewById(R.id.txtTotalCartPrice);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            cardOrderStoreItem = itemView.findViewById(R.id.cardOrderStoreItem);
            rcListCartItem = itemView.findViewById(R.id.rcListCartItem);
            btnCheckout = itemView.findViewById(R.id.btnCheckout);

        }
    }
}
