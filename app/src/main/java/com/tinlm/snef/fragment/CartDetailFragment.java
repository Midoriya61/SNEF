package com.tinlm.snef.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tinlm.snef.R;

public class CartDetailFragment extends Fragment {

    RecyclerView rcListCartItem;
    TextView txtTotalCartPrice;
    Button btnCheckout;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartdetail, container, false);

        rcListCartItem = view.findViewById(R.id.rcListCartItem);
        txtTotalCartPrice = view.findViewById(R.id.txtTotalCartPrice);
        btnCheckout = view.findViewById(R.id.btnCheckout);



        return view;
    }

//    private void createListCart() {
//        rcListCartItem = rcListCartItem.findViewById();
//        List<OrderDetail>  orderDetailList = new ArrayList<>();
//        Map<Integer, String> listImageProduct = new HashMap<>();
//        Map<Integer, Integer> listTotalPrice = new HashMap<>();
//        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
//        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
//
//        orderDetailList = orderDetailUtilities.getQuantityByFSPId(int i)
//        for (i = 0; i < orderDetailList.size(); i++) {
//
//            String productImage = imageUltilities.getOneImageByStoreProductId(orderDetailList.get(i).getFlashSaleProductId());
//            listImageProduct.put(orderDetailList.get(i).getFlashSaleProductId(),productImage );
//
//            int totalQuantity = orderDetailUtilities.getQuantityByFSPId(orderDetailList.get(i).getFlashSaleProductId());
//            listTotalPrice.put(orderDetailList.get(i).getFlashSaleProductId(), totalQuantity);
//
//
//        }


//        ListCartAdapter listCartAdapter = new ListCartAdapter(this, orderDetailList, listImageProduct, listTotalPrice);
//
//        orderDetailList.setItemAnimator(new DefaultItemAnimator());
//        orderDetailList.setLayoutManager(mLayoutManager);
//        orderDetailList.setAdapter(flashSaleProductAdapter);
}

