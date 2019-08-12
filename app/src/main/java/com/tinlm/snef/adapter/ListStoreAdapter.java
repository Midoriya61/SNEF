package com.tinlm.snef.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.activity.StoreActivity;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.model.Product;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.utilities.FlashSaleProductUtilities;
import com.tinlm.snef.utilities.LocationUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;

import java.util.ArrayList;
import java.util.List;

public class ListStoreAdapter extends RecyclerView.Adapter<ListStoreAdapter.ListStoreViewHolder>  {

    List<Store> listStore;
    Context mContext;

    public ListStoreAdapter(List<Store> moviesList) {
        this.listStore = moviesList;
    }

    public ListStoreAdapter(List<Store> listStore, Context mContext) {
        this.listStore = listStore;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ListStoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_store, viewGroup, false);

        return new ListStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListStoreViewHolder listStoreViewHolder, int i) {
        final Store currentStore = listStore.get(i);
        listStoreViewHolder.storeName.setText(currentStore.getStoreName());
//        LocationUtilities locationUtilities = new LocationUtilities();
//        locationUtilities.getAddressOfStoreById(currentStore);
        String openHour = "";
        if(currentStore.getOpenHour().equals(currentStore.getCloseHour())) {
            openHour = mContext.getResources().getString(R.string.Open24);

        }else
            openHour = currentStore.getOpenHour() + " - " + currentStore.getCloseHour();

        listStoreViewHolder.storeAddress.setText(currentStore.getAddress());

        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        Picasso.get().load(currentStore.getAvatar()).resize(0,height / 7).into(listStoreViewHolder.storeAvatar);
        listStoreViewHolder.storeWorkTime.setText(openHour);
        listStoreViewHolder.storeDistance.setText((Math.floor(currentStore.getDistance() * 100) / 100) + " km");
        if (currentStore.getRatingPoint() == 0) {
            listStoreViewHolder.ratingPoint.setText(mContext.getResources().getString(R.string.msg_still_not_rating));
        } else {
            listStoreViewHolder.ratingPoint.setText(mContext.getResources().getString(R.string.msg_rating) + ": " + currentStore.getRatingPoint()+ "/5");
        }
        final String finalOpenHour = openHour;
        listStoreViewHolder.viewStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StoreActivity.class);
                intent.putExtra(ConstainApp.JS_STORENAME, currentStore.getStoreName());
                intent.putExtra(ConstainApp.STOREAVATAR, currentStore.getAvatar());
                String address = currentStore.getAddress();
                intent.putExtra(ConstainApp.ADDRESS, address);
                intent.putExtra(ConstainApp.RATINGPOINT, currentStore.getRatingPoint());
                intent.putExtra(ConstainApp.STOREID, currentStore.getStoreId());
                intent.putExtra(ConstainApp.OPENHOUR, finalOpenHour);
                intent.putExtra(ConstainApp.STOREPHONE, currentStore.getPhone());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listStore.size();
    }

    public class ListStoreViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName,storeDistance, storeWorkTime, storeAddress, ratingPoint;
        private ImageView storeAvatar;
        private LinearLayout viewStore;

        public ListStoreViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeName);
            storeDistance = itemView.findViewById(R.id.storeDistance);
            storeWorkTime = itemView.findViewById(R.id.storeWorkTime);
            storeAddress = itemView.findViewById(R.id.storeAddress);
            storeAvatar = itemView.findViewById(R.id.storeAvatar);
            viewStore = itemView.findViewById(R.id.viewStore);
            ratingPoint = itemView.findViewById(R.id.ratingPoint);
        }
    }
    public void updateReceiptsList(List<Store> newlist) {
        listStore.clear();
        listStore.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
