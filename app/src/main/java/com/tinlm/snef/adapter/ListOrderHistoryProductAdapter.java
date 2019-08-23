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
import com.tinlm.snef.activity.FlashSalesProductDetailActivity;
import com.tinlm.snef.activity.OrderActivity;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.model.OrderDetail;
import com.tinlm.snef.utilities.FlashSaleProductUtilities;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListOrderHistoryProductAdapter extends RecyclerView.Adapter<ListOrderHistoryProductAdapter.ListOrderHistoryProductHolder> {

    Context mContext;
    List<OrderDetail> orderDetailList = new ArrayList<>();
//    Map<Integer, String> listImageOrderHistory;
    DecimalFormat df = new DecimalFormat("#,###,###,###");


    private Runnable runnable;
    private Handler handler = new Handler();

    public ListOrderHistoryProductAdapter(Context mContext, List<OrderDetail> orderDetailList) {
        this.mContext = mContext;
        this.orderDetailList = orderDetailList;
//        this.listImageOrderHistory = listImageOrderHistory;


    }

    //07/07/2019 HieuTB Create
    // Load into ListCartHolder
    @NonNull
    @Override
    public ListOrderHistoryProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListOrderHistoryProductHolder listOrderHistoryProductHolder;
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_orderhistoryproduct, viewGroup, false);
        listOrderHistoryProductHolder = new ListOrderHistoryProductHolder(v);


        return listOrderHistoryProductHolder;
    }


    ////07/07/2019 HieuTB Create
    // Load data into layout
    @Override
    public void onBindViewHolder(@NonNull final ListOrderHistoryProductHolder listOrderHistoryProductHolder, int i) {

        final OrderDetail orderDetail = orderDetailList.get(i);
        final int position = i;

//        String productOrderHistoryImage = listImageOrderHistory.get(orderDetail.getFlashSaleProductId());

        FlashSaleProductUtilities flashSaleProductUtilities = new FlashSaleProductUtilities();
        final FlashSaleProduct fsp = flashSaleProductUtilities.getFSPById(orderDetail.getFlashSaleProductId());

        Picasso.get().load(fsp.getImageSrc()).resize(500, 500).into(listOrderHistoryProductHolder.imgCartFood);


        listOrderHistoryProductHolder.txtCartFoodName.setText(fsp.getProductName());

        listOrderHistoryProductHolder.txtQuantity.setText(String.valueOf(orderDetail.getQuantity()));

//        listOrderHistoryProductHolder.txtCartPrice.setText(String.format("%,d", (int) ((fsp.getPrice() * fsp.getDiscount()) / 100)) + "");
//
        float orderDetailPrice = orderDetail.getOrderDetailPrice()/orderDetail.getQuantity();
        listOrderHistoryProductHolder.txtCartPrice.setText(String.format("%,d", (int) orderDetailPrice));

//        btnChange.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
//            @Override
//            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//                if(newValue > quantity ) {
//                    view.setNumber(String.valueOf(oldValue));
//                    CustomDialogFragment cdf = new CustomDialogFragment();
//                    cdf.show(getSupportFragmentManager(), intent.getStringExtra(ConstainApp.USERNAME));
//                } else {
//                    addToCart.setText("Add " + newValue + " to cart");
//                    totalPrice.setText(String.format("%,d",(intent.getIntExtra(ConstainApp.DISCOUNT,0) * (int)price/100)* newValue) );
//                }
//            }
//        });


//                    ((OrderActivity) mContext).startActivity(((OrderActivity) mContext).getIntent());
//                    ((OrderActivity) mContext).overridePendingTransition(0, 0);

//                    mContext.startActivity(((OrderActivity) mContext).getIntent());

//                    TextView txtTotalCartPrice = ((OrderActivity) mContext).findViewById(R.id.txtTotalCartPrice);
//                    if (newValue > oldValue) {
//                        txtTotalCartPrice.setText((String.format("%,d", Integer.parseInt(txtTotalCartPrice.getText().toString().replace(",", "")) + (int) ((cart.getPrice() * cart.getDiscount()) / 100))));
//                    } else if (newValue < oldValue) {
//
//                        txtTotalCartPrice.setText((String.format("%,d", Integer.parseInt(txtTotalCartPrice.getText().toString().replace(",", "")) - (int) ((cart.getPrice() * cart.getDiscount()) / 100))));
//                    }





//                ((OrderActivity) mContext).recreate();
//                ((OrderActivity) mContext).overridePendingTransition(0, 0);

//                cartList.remove(position);
//                notifyItemRemoved(position);

                //
//                if (cartList.size() == 0) {
//
//                }



//        listCartHolder.listCartLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, FlashSalesProductDetailActivity.class);
//                intent.putExtra(ConstainApp.FLASHSALEPRODUCTID,orderDetail.getFlashSaleProductId());
//                intent.putExtra(ConstainApp.PRODUCTNAME,orderDetail.getProductName());
//                intent.putExtra(ConstainApp.PRICE,orderDetail.getPrice());
//                intent.putExtra(ConstainApp.QUANTITY,orderDetail.getQuantity());
//
//                mContext.startActivity(intent);
//            }
//        });

//        Timestamp timestamp = new Timestamp(flashSaleProduct.getEndDate().getTime() + 86399999);
//        String countDownStart = countDownStart(timestamp + "");
//        listCartHolder.txtDateExpired.setText(countDownStart + "");
        listOrderHistoryProductHolder.imgCartFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FlashSalesProductDetailActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(ConstainApp.FLASHSALEPRODUCTID,fsp.getFlashSaleProductId());
                intent.putExtra(ConstainApp.PRODUCTNAME,fsp.getProductName());
                intent.putExtra(ConstainApp.DESCRIPTION,fsp.getDescription());
                intent.putExtra(ConstainApp.DISCOUNT,fsp.getDiscount());
                intent.putExtra(ConstainApp.STOREID,fsp.getStoreId());
                intent.putExtra(ConstainApp.PRICE,fsp.getPrice());
                intent.putExtra(ConstainApp.QUANTITY,fsp.getQuantity());
                intent.putExtra(ConstainApp.TOTALQUANTITY,fsp.getTotalQuantity());
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//                String endDate = df.format(flashSaleProduct.getEndDate());
                String endDate = fsp.getEndDate();
                intent.putExtra(ConstainApp.ENDDATE,endDate);
                intent.putExtra(ConstainApp.STOREPRODUCTID, fsp.getStoreProductId());

                mContext.startActivity(intent);
            }
        });

    }

    ////07/07/2019 HieuTB Create
    // Get size of list
    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }


    public class ListOrderHistoryProductHolder extends RecyclerView.ViewHolder {

        ImageView imgCartFood;

        TextView txtCartFoodName, txtCartPrice;

        TextView txtQuantity;

        LinearLayout listCartLayout;

        public ListOrderHistoryProductHolder (@NonNull View itemView) {
            super(itemView);
            imgCartFood = itemView.findViewById(R.id.imgCartFood);
            txtCartFoodName = itemView.findViewById(R.id.txtCartFoodName);
            txtCartPrice = itemView.findViewById(R.id.txtCartPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            listCartLayout = itemView.findViewById(R.id.listCartLayout);
        }
    }
}
