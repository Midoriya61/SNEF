package com.tinlm.snef.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.activity.ProductByCategoryActivity;
import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.Categories;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>   {

    Context mContext;
    List<Categories> categoryList;

    public CategoriesAdapter(Context mContext, List<Categories> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_categories, viewGroup,false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder categoriesViewHolder, int i) {

        final Categories category = categoryList.get(i);
        Picasso.get().load(category.getImageSrc()).resize(400,250).into(categoriesViewHolder.imgCategory);
        categoriesViewHolder.txtCategogy.setText(category.getCategoryName());



        categoriesViewHolder.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductByCategoryActivity.class);
                intent.putExtra(ConstainApp.CATEGORYID, category.getCategoriesId());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    public class CategoriesViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCategory;
        TextView txtCategogy;
        LinearLayout layoutCategory;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            txtCategogy = itemView.findViewById(R.id.txtCategogy);
            layoutCategory = itemView.findViewById(R.id.layoutCategory);

        }
    }
}
