package com.tinlm.snef.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import com.tinlm.snef.activity.CartPaymentConfirmActivity;
import com.tinlm.snef.activity.OrderActivity;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.adapter.ListStoreOrderItemAdapter;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListCartPaymentConfirmAdapter extends RecyclerView.Adapter<ListCartPaymentConfirmAdapter.ListCartPaymentConfirmHolder> {

    Context mContext;
    List<Cart> cartList = new ArrayList<>();
    Map<Integer, String> listImageCartProduct;
    DecimalFormat df = new DecimalFormat("#,###,###,###");


    private Runnable runnable;
    private Handler handler = new Handler();

    public ListCartPaymentConfirmAdapter(Context mContext, List<Cart> cartList,
                                         Map<Integer, String> listImageCartProduct) {
        this.mContext = mContext;
        this.cartList = cartList;
        this.listImageCartProduct = listImageCartProduct;

    }

    //07/07/2019 HieuTB Create
    // Load into ListCartHolder
    @NonNull
    @Override
    public ListCartPaymentConfirmHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListCartPaymentConfirmHolder listCartPaymentConfirmHolder;
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_cartpaymentconfirm, viewGroup, false);
        listCartPaymentConfirmHolder = new ListCartPaymentConfirmHolder(v);


        return listCartPaymentConfirmHolder;
    }


    ////07/07/2019 HieuTB Create
    // Load data into layout
    @Override
    public void onBindViewHolder(@NonNull ListCartPaymentConfirmHolder listCartPaymentConfirmHolder, int i) {
        final Cart cart = cartList.get(i);
        String productCartImage = listImageCartProduct.get(cart.getFspId());
        Picasso.get().load(productCartImage).resize(500, 500).into(listCartPaymentConfirmHolder.imgCartFood);
        listCartPaymentConfirmHolder.txtCartFoodName.setText(cart.getProductName());
        listCartPaymentConfirmHolder.txtCartPrice.setText(String.format("%,d", (int) ((cart.getPrice() * cart.getDiscount()) / 100)) + "");
        listCartPaymentConfirmHolder.btnCartQuantity.setNumber(String.valueOf(cart.getQuantity()));
//        listCartPaymentConfirmHolder.txtTotalCartPrice.setText();

        listCartPaymentConfirmHolder.btnCartQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                if (newValue != oldValue) {
                    cart.setQuantity(newValue);
                    DBManager dbManager = new DBManager(mContext);
                    dbManager.updateCartQuantity(cart);

//                    TextView txtTotalCartPrice = vg.findViewById(R.id.txtTotalCartPrice);
                    TextView txtTotalPaymentConfirm = ((CartPaymentConfirmActivity) mContext).findViewById(R.id.txtTotalPaymentConfirm);
                    if (newValue > oldValue) {
                        txtTotalPaymentConfirm.setText((String.format("%,d", Integer.parseInt(txtTotalPaymentConfirm.getText().toString().replace(",", "")) + (int) ((cart.getPrice() * cart.getDiscount()) / 100))));
                    } else if (newValue < oldValue) {

                        txtTotalPaymentConfirm.setText((String.format("%,d", Integer.parseInt(txtTotalPaymentConfirm.getText().toString().replace(",", "")) - (int) ((cart.getPrice() * cart.getDiscount()) / 100))));
                    }

                }
            }
        });

        final int position = i;
        listCartPaymentConfirmHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager dbManager = new DBManager(mContext);
                dbManager.deleteCart(cart);
                cartList.remove(position);
                notifyItemRemoved(position);
                if (cartList.size() == 0) {

                }
            }
        });

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


    public class ListCartPaymentConfirmHolder extends RecyclerView.ViewHolder {

        ImageView imgCartFood;

        TextView txtCartFoodName, txtCartPrice, btnRemove;

        ElegantNumberButton btnCartQuantity;

        CardView btnCheckout;

        LinearLayout listCartPaymentConfirmLayout;

        public ListCartPaymentConfirmHolder(@NonNull View itemView) {
            super(itemView);
            imgCartFood = itemView.findViewById(R.id.imgCartFood);
            txtCartFoodName = itemView.findViewById(R.id.txtCartFoodName);
            txtCartPrice = itemView.findViewById(R.id.txtCartPrice);
            btnCheckout = itemView.findViewById(R.id.btnCheckout);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnCartQuantity = itemView.findViewById(R.id.btnCartQuantity);
            listCartPaymentConfirmLayout = itemView.findViewById(R.id.listCartLayout);
        }
    }
}
