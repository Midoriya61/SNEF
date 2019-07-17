package com.tinlm.snef.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tinlm.snef.R;
import com.tinlm.snef.activity.FlashSalesProductDetailActivity;
import com.tinlm.snef.constain.ConstainApp;

import com.tinlm.snef.model.FlashSaleProduct;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class FlashSaleProductAdapter extends RecyclerView.Adapter<FlashSaleProductAdapter.FlashSaleProductHolder> {
    Context mContext;
    List<FlashSaleProduct> flashSaleProductList;
    String sreenName;

    private Runnable runnable;
    private Handler handler = new Handler();
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public FlashSaleProductAdapter(Context mContext, List<FlashSaleProduct> flashSaleProductList,
                                   String sreenName) {
        this.mContext = mContext;
        this.flashSaleProductList = flashSaleProductList;
        this.sreenName = sreenName;
    }

    //6/14/2019 TinLM Create
    // Load into FlashSaleProductHolder
    @NonNull
    @Override
    public FlashSaleProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FlashSaleProductHolder flashSaleProductHolder;
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_flash_sale_product, viewGroup, false);
        flashSaleProductHolder = new FlashSaleProductHolder(v);
        return flashSaleProductHolder;
    }


    ////6/14/2019 TinLM Create
    // Load data into layout
    @Override
    public void onBindViewHolder(@NonNull FlashSaleProductHolder flashSaleProductHolder, int i) {
        final FlashSaleProduct flashSaleProduct = flashSaleProductList.get(i);
        Picasso.get().load("https://res.cloudinary.com/dr4hpc9gi/image/upload/v1558970388/FoodStoreImage/discount.png")
                .resize(200,120).into(flashSaleProductHolder.imgDiscount);
        if(sreenName.equals(ConstainApp.SCHotProductHomeFragment)) {
            if(flashSaleProduct.getImageSrc() == null) {
                Picasso.get().load("https://res.cloudinary.com/dr4hpc9gi/image/upload/v1559727025/noimage.jpg")
                        .resize(500,500).into(flashSaleProductHolder.imgFood);
            }else {
                Picasso.get().load(flashSaleProduct.getImageSrc()).resize(500,500).into(flashSaleProductHolder.imgFood);
            }
        } else {
            WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            if(flashSaleProduct.getImageSrc() == null) {
                Picasso.get().load("https://res.cloudinary.com/dr4hpc9gi/image/upload/v1559727025/noimage.jpg")
                        .resize(width / 2,height / 3).into(flashSaleProductHolder.imgFood);
            }else {
                Picasso.get().load(flashSaleProduct.getImageSrc()).resize(width / 2,height / 3).into(flashSaleProductHolder.imgFood);
            }
        }


        flashSaleProductHolder.txtFoodName.setText(flashSaleProduct.getProductName());

        int productPrice = (int) flashSaleProduct.getPrice();
        flashSaleProductHolder.txtPrice.setText((String.format("%,d", productPrice)) + "");
        flashSaleProductHolder.txtDiscount.setText("-" + flashSaleProduct.getDiscount() + "%");

//        String strProductPrice = (String.format("%,d", productPrice));

        flashSaleProductHolder.textPriceDiscount.setText(String.format("%,d",(int)(productPrice - ((flashSaleProduct.getPrice()
                * flashSaleProduct.getDiscount())/100))) + "");


        int totalQuantity = flashSaleProduct.getTotalQuantity();

        //width of bar process sould
        int numberSout = (flashSaleProductHolder.barStillSale.getLayoutParams().width *totalQuantity )/ (flashSaleProduct.getQuantity());
        flashSaleProductHolder.barSale.getLayoutParams().width = numberSout;

        // set status of product
        if(totalQuantity == flashSaleProduct.getQuantity()) {

            flashSaleProductHolder.statusSale.setText( mContext.getResources().getString(R.string.StatusSouldOut));
        } else if (totalQuantity >= flashSaleProduct.getQuantity() * (100/80)) {
            flashSaleProductHolder.statusSale.setText( mContext.getResources().getString(R.string.StatusNearlySouldOut));
//            flashSaleProductHolder.statusSale.setText(R.string.StatusNearlySouldOut);
        } else if (totalQuantity > 0) {
            flashSaleProductHolder.statusSale.setText( mContext.getResources().getString(R.string.StatusSould) + " " + totalQuantity);
//            flashSaleProductHolder.statusSale.setText(R.string.StatusSould + " " + flashSaleProduct.getQuantity());
        } else {
            flashSaleProductHolder.statusSale.setText( mContext.getResources().getString(R.string.StatusJustOpen));
//            flashSaleProductHolder.statusSale.setText(R.string.StatusJustOpen);

        }

        flashSaleProductHolder.flashSaleProductLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FlashSalesProductDetailActivity.class);
                intent.putExtra(ConstainApp.FLASHSALEPRODUCTID,flashSaleProduct.getFlashSaleProductId());
                intent.putExtra(ConstainApp.PRODUCTNAME,flashSaleProduct.getProductName());
                intent.putExtra(ConstainApp.DESCRIPTION,flashSaleProduct.getDescription());
                intent.putExtra(ConstainApp.DISCOUNT,flashSaleProduct.getDiscount());
                intent.putExtra(ConstainApp.STOREID,flashSaleProduct.getStoreId());
                intent.putExtra(ConstainApp.PRICE,flashSaleProduct.getPrice());
                intent.putExtra(ConstainApp.QUANTITY,flashSaleProduct.getQuantity());
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//                String endDate = df.format(flashSaleProduct.getEndDate());
                String endDate = flashSaleProduct.getEndDate();
                intent.putExtra(ConstainApp.ENDDATE,endDate);
                intent.putExtra(ConstainApp.STOREPRODUCTID, flashSaleProduct.getStoreProductId());

                mContext.startActivity(intent);
            }
        });

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

        LinearLayout flashSaleProductLayout;

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
            flashSaleProductLayout = itemView.findViewById(R.id.flashSaleProductLayout);
        }
    }
}
