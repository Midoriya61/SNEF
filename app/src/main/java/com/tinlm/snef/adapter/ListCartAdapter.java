package com.tinlm.snef.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.model.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListCartAdapter extends RecyclerView.Adapter<ListCartAdapter.ListCartHolder> {

    Context mContext;
    List<Cart> cartList = new ArrayList<>();
    Map<Integer, String> listImageCartProduct;


    private Runnable runnable;
    private Handler handler = new Handler();

    public ListCartAdapter(Context mContext, List<Cart> cartList,
                           Map<Integer, String> listImageCartProduct) {
        this.mContext = mContext;
        this.cartList = cartList;
        this.listImageCartProduct = listImageCartProduct;

    }

    //07/07/2019 HieuTB Create
    // Load into ListCartHolder
    @NonNull
    @Override
    public ListCartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListCartHolder listCartHolder;
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_cart, viewGroup, false);
        listCartHolder = new ListCartHolder(v);


        return listCartHolder;
    }


    ////07/07/2019 HieuTB Create
    // Load data into layout
    @Override
    public void onBindViewHolder(@NonNull ListCartHolder listCartHolder, int i) {
        final Cart cart = cartList.get(i);


        String productCartImage = listImageCartProduct.get(cart.getFspId());


        Picasso.get().load(productCartImage).resize(500,500).into(listCartHolder.imgCartFood);


        listCartHolder.txtCartFoodName.setText(cart.getProductName());


//        listCartHolder.txtCartPrice.setText("23");


        listCartHolder.btnCartQuantity.setNumber(String.valueOf(cart.getQuantity()));
        listCartHolder.txtCartPrice.setText(String.format("%,d",(int)((cart.getPrice() * cart.getDiscount())/100)) + "");
//
//        listCartHolder.btnCartQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
//            @Override
//            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//                cart.setQuantity(newValue);

//            }
//        });

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

    }

    ////07/07/2019 HieuTB Create
    // Get size of list
    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class ListCartHolder extends RecyclerView.ViewHolder {

        ImageView imgCartFood;

        TextView txtCartFoodName, txtCartPrice;

        ElegantNumberButton btnCartQuantity;
        Button btnRemove;

        LinearLayout listCartLayout;

        public ListCartHolder(@NonNull View itemView) {
            super(itemView);
            imgCartFood = itemView.findViewById(R.id.imgCartFood);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            txtCartFoodName = itemView.findViewById(R.id.txtCartFoodName);
            txtCartPrice = itemView.findViewById(R.id.txtCartPrice);
            btnCartQuantity = itemView.findViewById(R.id.btnCartQuantity);
            listCartLayout = itemView.findViewById(R.id.listCartLayout);
        }
    }
}
