package com.tinlm.snef.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.FlashSaleProduct;
import com.tinlm.snef.utilities.CategoriesUtilities;
import com.tinlm.snef.utilities.OrderDetailUtilities;
import com.tinlm.snef.utilities.StoreProductImageUtilities;
import com.tinlm.snef.utilities.StoreProductUtilities;

import java.util.List;

public class FlashSaleProductAdapter extends RecyclerView.Adapter<FlashSaleProductAdapter.FlashSaleProductHolder> {
    Context mContext;
    List<FlashSaleProduct> flashSaleProductList;
    private Runnable runnable;
    private Handler handler = new Handler();
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public FlashSaleProductAdapter(Context mContext, List<FlashSaleProduct> flashSaleProductList) {
        this.mContext = mContext;
        this.flashSaleProductList = flashSaleProductList;
    }

    //6/14/2019 TinLM Create
    // Load into FlashSaleProductHolder
    @NonNull
    @Override
    public FlashSaleProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_flash_sale_product, viewGroup, false);
        return new FlashSaleProductHolder(v);
    }

    ////6/14/2019 TinLM Create
    // Load data into layout
    @Override
    public void onBindViewHolder(@NonNull FlashSaleProductHolder flashSaleProductHolder, int i) {
        FlashSaleProduct flashSaleProduct = flashSaleProductList.get(i);

        StoreProductImageUtilities imageUltilities = new StoreProductImageUtilities();
        String productImage = imageUltilities.getImageByStoreProductId(flashSaleProduct.getStoreProductId());

        Picasso.get().load("https://res.cloudinary.com/dr4hpc9gi/image/upload/v1558970388/FoodStoreImage/discount.png")
                .resize(200,120).into(flashSaleProductHolder.imgDiscount);
        if(productImage == null) {
            Picasso.get().load("https://res.cloudinary.com/dr4hpc9gi/image/upload/v1559727025/noimage.jpg")
                    .resize(500,550).into(flashSaleProductHolder.imgFood);
        }else {
            Picasso.get().load(productImage).resize(500,550).into(flashSaleProductHolder.imgFood);
        }

        flashSaleProductHolder.txtFoodName.setText(flashSaleProduct.getProductName());

        int productPrice = (int) flashSaleProduct.getPrice();
        flashSaleProductHolder.txtPrice.setText((String.format("%,d", productPrice)) + "");
        flashSaleProductHolder.txtDiscount.setText("-" + flashSaleProduct.getDiscount() + "%");

        String strProductPrice = (String.format("%,d", productPrice));

        flashSaleProductHolder.textPriceDiscount.setText(String.format("%,d",(int)((flashSaleProduct.getPrice() * flashSaleProduct.getDiscount())/100)) + "");

        OrderDetailUtilities orderDetailUtilities = new OrderDetailUtilities();
        int totalQuantity = orderDetailUtilities.getQuantityByFSPId(flashSaleProduct.getFlashSaleProductId());



        int numberSout = (flashSaleProductHolder.barStillSale.getLayoutParams().width *totalQuantity )/ (flashSaleProduct.getQuantity());
        flashSaleProductHolder.barSale.getLayoutParams().width = numberSout;
        if(totalQuantity == flashSaleProduct.getQuantity()) {
            flashSaleProductHolder.statusSale.setText(ConstainApp.StatusSouldOut);
        } else if (totalQuantity >= flashSaleProduct.getQuantity() * (100/80)) {
            flashSaleProductHolder.statusSale.setText(ConstainApp.StatusNearlySouldOut);
        } else if (totalQuantity > 0) {
            flashSaleProductHolder.statusSale.setText(ConstainApp.StatusSould + flashSaleProduct.getQuantity());
        } else {
            flashSaleProductHolder.statusSale.setText(ConstainApp.StatusJustOpen);
        }
//        Timestamp timestamp = new Timestamp(flashSaleProduct.getEndDate().getTime() + 86399999);
//        String countDownStart = countDownStart(timestamp + "");
//        flashSaleProductHolder.txtDateExpired.setText(countDownStart + "");

    }

    ////6/14/2019 TinLM Create
    // Get size of list
    @Override
    public int getItemCount() {
        return flashSaleProductList.size();
    }
    public class FlashSaleProductHolder extends RecyclerView.ViewHolder {

        ImageView imgFood, imgDiscount;

        TextView txtDiscount, txtFoodName, textPriceDiscount, txtPrice, statusSale;

        RelativeLayout barStillSale,barSale;

        public FlashSaleProductHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            imgDiscount = itemView.findViewById(R.id.imgDiscount);
            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            textPriceDiscount = itemView.findViewById(R.id.textPriceDiscount);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            statusSale = itemView.findViewById(R.id.statusSale);
            barStillSale = itemView.findViewById(R.id.barStillSale);
            barSale = itemView.findViewById(R.id.barSale);
        }
    }
}
