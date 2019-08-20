package com.tinlm.snef.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.activity.AllOrderHistoryActivity;
import com.tinlm.snef.activity.OrderActivity;
import com.tinlm.snef.activity.OrderHistoryActivity;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.model.Order;
import com.tinlm.snef.model.OrderDetail;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.utilities.FlashSaleProductUtilities;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.StoreUtilities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListOrderHistoryAdapter extends RecyclerView.Adapter<ListOrderHistoryAdapter.ListOrderHistoryHolder> {

    private Context mContext;
    private List<Order> orderList;
    private DecimalFormat df = new DecimalFormat("#,###,###,###");

    private Runnable runnable;
    private Handler handler = new Handler();

    public ListOrderHistoryAdapter(Context mContext, List<Order> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }

    //07/07/2019 HieuTB Create
// Load into ListCartHolder
    @NonNull
    @Override
    public ListOrderHistoryAdapter.ListOrderHistoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListOrderHistoryAdapter.ListOrderHistoryHolder listOrderHistoryHolder;
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_orderhistory, viewGroup, false);
        listOrderHistoryHolder = new ListOrderHistoryAdapter.ListOrderHistoryHolder(v);


        return listOrderHistoryHolder;
    }


    ////07/07/2019 HieuTB Create
// Load data into layout
    @Override
    public void onBindViewHolder(@NonNull final ListOrderHistoryHolder listOrderHistoryHolder, final int i) {
        final Order order = orderList.get(i);
        final int position = i;

        listOrderHistoryHolder.txtOrderID.setText(String.valueOf(order.getOrderId()));

        listOrderHistoryHolder.txtDateOrder.setText(String.valueOf(order.getDateOrder()));

        if (order.isStatus() != true) {
            listOrderHistoryHolder.txtOrderStatus.setText(R.string.orderstatus_paid);
        } else {
            listOrderHistoryHolder.txtOrderStatus.setText(R.string.orderstatus_pickedup);
            listOrderHistoryHolder.statusView.setBackgroundResource(R.drawable.corner_sttcollected_bottom);
        }

        listOrderHistoryHolder.txtTotalQuantity.setText(String.format("%,d", (int) order.getOrderQuantity()));

        listOrderHistoryHolder.txtTotalOrderPrice.setText(String.format("%,d", (int) order.getTotalPrice()));

        listOrderHistoryHolder.txtStoreName.setText(order.getStoreName());

        listOrderHistoryHolder.listOrderHistoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderHistoryActivity.class);
                intent.putExtra(ConstainApp.JS_ORDERID, orderList.get(i).getOrderId());
                mContext.startActivity(intent);
            }
        });

    }

    ////07/07/2019 HieuTB Create
// Get size of list
    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class ListOrderHistoryHolder extends RecyclerView.ViewHolder {

        TextView txtStoreName, txtDateOrder, txtTotalQuantity, txtTotalOrderPrice, txtOrderStatus, txtOrderID;

        LinearLayout listOrderHistoryLayout, statusView;

        public ListOrderHistoryHolder(@NonNull View itemView) {
            super(itemView);
            txtStoreName = itemView.findViewById(R.id.txtStoreName);
            txtOrderID = itemView.findViewById(R.id.txtOrderID);
            txtDateOrder = itemView.findViewById(R.id.txtDateOrder);
            txtTotalQuantity = itemView.findViewById(R.id.txtTotalQuantity);
            txtTotalOrderPrice = itemView.findViewById(R.id.txtTotalOrderPrice);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            listOrderHistoryLayout = itemView.findViewById(R.id.listOrderHistoryLayout);
            statusView = itemView.findViewById(R.id.statusView);
        }
    }
}
