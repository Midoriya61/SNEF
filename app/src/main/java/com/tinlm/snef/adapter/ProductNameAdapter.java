package com.tinlm.snef.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.activity.SearchProductByNameActivity;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductNameAdapter extends RecyclerView.Adapter<ProductNameAdapter.ProductNameViewHolder> {

    Context mContext;
    List<Product> productList;

    public ProductNameAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }



    @NonNull
    @Override
    public ProductNameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_name,viewGroup, false);
        return new ProductNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductNameViewHolder productNameViewHolder, int i) {
        final Product product = productList.get(i);
        productNameViewHolder.name.setText(product.getProductName());
        productNameViewHolder.productNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchProductByNameActivity.class);
                intent.putExtra(ConstainApp.SEARCHPRODUCTNAME, product.getProductName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductNameViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RelativeLayout productNameLayout;

        public ProductNameViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            productNameLayout = itemView.findViewById(R.id.productNameLayout);
        }
    }



    public void updateReceiptsList(List<Product> newlist) {
        productList.clear();
        productList.addAll(newlist);
        this.notifyDataSetChanged();
    }

}
