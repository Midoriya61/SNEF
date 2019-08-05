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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.activity.SearchActivity;
import com.tinlm.snef.activity.SearchProductByNameActivity;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;

import java.util.List;

public class ListFoundAdapter extends RecyclerView.Adapter<ListFoundAdapter.ListFoundViewHolder> {

    private List<String> listData;
    private Context mContext;
    private RelativeLayout notifiYetData;

    public ListFoundAdapter(List<String> listData, Context mContext, RelativeLayout notifiYetData) {
        this.listData = listData;
        this.mContext = mContext;
        this.notifiYetData = notifiYetData;
    }

    @NonNull
    @Override
    public ListFoundViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_found, viewGroup, false);

        return new ListFoundAdapter.ListFoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFoundViewHolder listFoundViewHolder, int i) {

        final String data = listData.get(i);
        listFoundViewHolder.productName.setText(data);
        final int position = i;
        listFoundViewHolder.productName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchProductByNameActivity.class);
                intent.putExtra(ConstainApp.SEARCHPRODUCTNAME, data);
                mContext.startActivity(intent);
            }
        });

        listFoundViewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager dbManager = new DBManager(mContext);
                dbManager.deleteFound(data);
                listData.remove(position);
//                if (listData.size() == 0) {
//                    notifiYetData.setVisibility(View.VISIBLE);
//                }
                notifyItemRemoved(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ListFoundViewHolder extends RecyclerView.ViewHolder {

        private TextView productName, btnRemove;


        public ListFoundViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }

}
