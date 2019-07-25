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
    private List<OrderDetail> orderDetailList;
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

        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
        orderDetailList = orderDetailUtilities.getAllOrderDetailByOrderId(order.getOrderId());

        FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
        FlashSaleProduct fsp = flashSaleProductUtilities.getFSPById(orderDetailList.get(0).getFlashSaleProductId());

        StoreUtilities storeUtilities = new StoreUtilities();
        Store store = storeUtilities.getStoreById(fsp.getStoreId());

        listOrderHistoryHolder.txtStoreName.setText(store.getStoreName());

        listOrderHistoryHolder.txtDateOrder.setText(String.valueOf(order.getDateOrder()));

        if (order.isStatus() != true) {
            listOrderHistoryHolder.txtOrderStatus.setText(R.string.orderstatus_paid);
        } else {
            listOrderHistoryHolder.txtOrderStatus.setText(R.string.orderstatus_pickedup);
        }

        int totalQuantity = 0;
        for (int a = 0; a < orderDetailList.size(); a++) {
            totalQuantity += orderDetailList.get(a).getQuantity();
        }
        listOrderHistoryHolder.txtTotalQuantity.setText(String.format("%,d", (int) totalQuantity));

        int totalOrderPrice = 0;
        for (int a = 0; a < orderDetailList.size(); a++) {
            totalOrderPrice += orderDetailList.get(a).getOrderDetailPrice();
        }
        listOrderHistoryHolder.txtTotalOrderPrice.setText(String.format("%,d", (int) totalOrderPrice));

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

        TextView txtStoreName, txtDateOrder, txtTotalQuantity, txtTotalOrderPrice, txtOrderStatus;

        TextView txtStoreNameOD;

        LinearLayout listOrderHistoryLayout;

        public ListOrderHistoryHolder(@NonNull View itemView) {
            super(itemView);
            txtStoreName = itemView.findViewById(R.id.txtStoreName);
            txtDateOrder = itemView.findViewById(R.id.txtDateOrder);
            txtTotalQuantity = itemView.findViewById(R.id.txtTotalQuantity);
            txtTotalOrderPrice = itemView.findViewById(R.id.txtTotalOrderPrice);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            listOrderHistoryLayout = itemView.findViewById(R.id.listOrderHistoryLayout);
        }
    }
}
