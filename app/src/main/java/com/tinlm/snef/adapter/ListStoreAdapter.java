package com.tinlm.snef.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.FlashSaleProduct;
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
        LocationUtilities locationUtilities = new LocationUtilities();
        locationUtilities.getAddressOfStoreById(currentStore);

        if(currentStore.getOpenHour().equals(currentStore.getClodeHour())) {
            listStoreViewHolder.storeWorkTime.setText(ConstainApp.Open24);
        }else
            listStoreViewHolder.storeWorkTime.setText(currentStore.getOpenHour() + " - " + currentStore.getClodeHour());

        FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
        List<FlashSaleProduct> flashSaleProducts = flashSaleProductUtilities.getFSPByStoreId(currentStore.getStoreId());

        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
        List<String> listImageFood = new ArrayList<>();
        // lấy hình ảnh cho store
        for (FlashSaleProduct fsp: flashSaleProducts
        ) {
            String fsImage = imageUltilities.getOneImageByStoreProductId(fsp.getStoreProductId());
            listImageFood.add(fsImage);
        }
        List<ImageView> image = new ArrayList<>();
        image.add(listStoreViewHolder.imageFood1);
        image.add(listStoreViewHolder.imageFood2);
        image.add(listStoreViewHolder.imageFood3);
        image.add(listStoreViewHolder.imageFood4);
        List<String> listFood = listImageFood;
        for (int position = 0; i < listFood.size(); position++) {
            if(position == 4) {
                break;
            }
            Picasso.get().load(listFood.get(position)).into(image.get(position));
        }
        listStoreViewHolder.storeDistance.setText((Math.floor(currentStore.getDistance() * 100) / 100) + "km");

    }

    @Override
    public int getItemCount() {
        return listStore.size();
    }

    public class ListStoreViewHolder extends RecyclerView.ViewHolder {

        private ImageView storeIcon;
        private TextView storeName,storeDistance, storeWorkTime;
        private ImageView imageFood1, imageFood2, imageFood3, imageFood4;
        private LinearLayout viewStore;

        public ListStoreViewHolder(@NonNull View itemView) {
            super(itemView);
            storeIcon = itemView.findViewById(R.id.storeIcon);
            storeName = itemView.findViewById(R.id.storeName);
            storeDistance = itemView.findViewById(R.id.storeDistance);
            storeWorkTime = itemView.findViewById(R.id.storeWorkTime);

            imageFood1 = itemView.findViewById(R.id.imageFood1);
            imageFood2 = itemView.findViewById(R.id.imageFood2);
            imageFood3 = itemView.findViewById(R.id.imageFood3);
            imageFood4 = itemView.findViewById(R.id.imageFood4);

            viewStore = itemView.findViewById(R.id.viewStore);
        }
    }
}
