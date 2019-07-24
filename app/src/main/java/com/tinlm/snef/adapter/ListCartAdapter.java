package com.tinlm.snef.adapter;

import android.content.Context;
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
import com.tinlm.snef.activity.OrderActivity;
import com.tinlm.snef.database.DBManager;
import com.tinlm.snef.model.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListCartAdapter extends RecyclerView.Adapter<ListCartAdapter.ListCartHolder> {

    private Context mContext;
    private List<Cart> cartList = new ArrayList<>();
    private Map<Integer, String> listImageCartProduct;
    private DecimalFormat df = new DecimalFormat("#,###,###,###");
    private TextView txtTotalCartPrice;

    private Runnable runnable;
    private Handler handler = new Handler();

    public ListCartAdapter(Context mContext, List<Cart> cartList,
                           Map<Integer, String> listImageCartProduct, TextView txtTotalCartPrice) {
        this.mContext = mContext;
        this.cartList = cartList;
        this.listImageCartProduct = listImageCartProduct;
        this.txtTotalCartPrice = txtTotalCartPrice;

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
    public void onBindViewHolder(@NonNull final ListCartHolder listCartHolder, int i) {
        final Cart cart = cartList.get(i);
        final int position = i;

        String productCartImage = listImageCartProduct.get(cart.getFspId());


        Picasso.get().load(productCartImage).resize(500, 500).into(listCartHolder.imgCartFood);


        listCartHolder.txtCartFoodName.setText(cart.getProductName());

        listCartHolder.btnCartQuantity.setNumber(String.valueOf(cart.getQuantity()));

        listCartHolder.txtCartPrice.setText(String.format("%,d", (int) ((cart.getPrice() * cart.getDiscount()) / 100)) + "");
//

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

        listCartHolder.btnCartQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                if (newValue != oldValue) {
                    cart.setQuantity(newValue);
                    DBManager dbManager = new DBManager(mContext);
                    dbManager.updateCartQuantity(cart);
                    notifyItemChanged(position);

//                    ((OrderActivity) mContext).recreate();
//                    ((OrderActivity) mContext).overridePendingTransition(0, 0);
//                    ((OrderActivity) mContext).startActivity(((OrderActivity) mContext).getIntent());
//                    ((OrderActivity) mContext).overridePendingTransition(0, 0);

//                    mContext.startActivity(((OrderActivity) mContext).getIntent());

//                    TextView txtTotalCartPrice = ((OrderActivity) mContext).findViewById(R.id.txtTotalCartPrice);
                    if (newValue > oldValue) {
                        txtTotalCartPrice.setText((String.format("%,d", Integer.parseInt(txtTotalCartPrice.getText().toString().replace(",", "")) + (int) ((cart.getPrice() * cart.getDiscount()) / 100))));
                    } else if (newValue < oldValue) {

                        txtTotalCartPrice.setText((String.format("%,d", Integer.parseInt(txtTotalCartPrice.getText().toString().replace(",", "")) - (int) ((cart.getPrice() * cart.getDiscount()) / 100))));
                    }

                }
            }
        });


        listCartHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager dbManager = new DBManager(mContext);
                dbManager.deleteCart(cart);
                ((OrderActivity) mContext).recreate();
                ((OrderActivity) mContext).overridePendingTransition(0, 0);
//                ((OrderActivity) mContext).recreate();
//                ((OrderActivity) mContext).overridePendingTransition(0, 0);

//                cartList.remove(position);
//                notifyItemRemoved(position);

                //
//                if (cartList.size() == 0) {
//
//                }


            }
        });

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
        TextView btnRemove;

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
