package com.tinlm.snef.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.model.Cart;
import com.tinlm.snef.model.OrderDetail;
import com.tinlm.snef.model.Store;
import com.tinlm.snef.model.StoreOrderItem;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private Context context;


    public DBManager(Context context) {
        super(context, ConstainApp.JS_DB_NAME, null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + ConstainApp.JS_CART + " ( " +
                ConstainApp.JS_FSPID + " integer primary key, " +
                ConstainApp.JS_IMAGEPRODUCT + " TEXT, " +
                ConstainApp.JS_PRODUCTNAME + " TEXT, " +
                ConstainApp.JS_STORENAME + " TEXT, " +
                ConstainApp.JS_PRICE + " float, " +
                ConstainApp.JS_DISCOUNT + " integer, " +
                ConstainApp.JS_QUANTITY + " integer) ";
        db.execSQL(sqlQuery);

//        String sqlQueryOrderDetail = "CREATE TABLE " + ConstainApp.LIST_ORDER_DETAIL + " ( " +
//                    ConstainApp.JS_ORDERDETAILID + " integer primary key, " +
//                    ConstainApp.JS_ORDERID + " integer, " +
//                    ConstainApp.JS_FSPID + " integer, " +
//                    ConstainApp.JS_QUANTITY + " integer, " +
//                    ConstainApp.JS_ORDERDETAILPRICE + " float) ";
//        db.execSQL(sqlQueryOrderDetail);

        Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ ConstainApp.LIST_ORDER_DETAIL);
        onCreate(db);
    }

    //Add new a order detail
    public void addOrderDetail(OrderDetail orderDetail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ConstainApp.JS_ORDERDETAILID, orderDetail.getOrderDetailId());
        values.put(ConstainApp.JS_ORDERID, orderDetail.getOrderId());
        values.put(ConstainApp.JS_FSPID, orderDetail.getFspId());
        values.put(ConstainApp.JS_QUANTITY, orderDetail.getQuantity());
        values.put(ConstainApp.JS_ORDERDETAILPRICE, orderDetail.getOrderDetailPrice());

        //Neu de null thi khi value bang null thi loi

        db.insert(ConstainApp.LIST_ORDER_DETAIL,null,values);
        db.close();
    }


    //Add new a cart
    public void addCart(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ConstainApp.JS_FSPID, cart.getFspId());
        values.put(ConstainApp.JS_IMAGEPRODUCT, cart.getFspId());
        values.put(ConstainApp.JS_PRODUCTNAME, cart.getProductName());
        values.put(ConstainApp.JS_STORENAME, cart.getStoreName());
        values.put(ConstainApp.JS_PRICE, cart.getPrice());
        values.put(ConstainApp.JS_DISCOUNT, cart.getDiscount());
        values.put(ConstainApp.JS_QUANTITY, 1);

        //Neu de null thi khi value bang null thi loi

        db.insert(ConstainApp.JS_CART,null,values);

        db.close();
    }

    public List<OrderDetail> getAllOrderDetail() {
        List<OrderDetail> ORList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ConstainApp.LIST_ORDER_DETAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDetailId(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_ORDERDETAILID)));
                orderDetail.setOrderId(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_ORDERID)));
                orderDetail.setFspId(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_FSPID)));
                orderDetail.setQuantity(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_QUANTITY)));
                orderDetail.setOrderDetailPrice(cursor.getFloat(cursor.getColumnIndex(ConstainApp.JS_ORDERDETAILPRICE)));
                ORList.add(orderDetail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ORList;
    }



    /*
    Update name of cart
     */

    public int updateCart(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ConstainApp.JS_QUANTITY,cart.getQuantity() + 1);
        return db.update(ConstainApp.JS_CART,values,ConstainApp.JS_FSPID +"=?",new String[] { String.valueOf(cart.getFspId())});
    }

    public int updateCartQuantity(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ConstainApp.JS_QUANTITY,cart.getQuantity());
        return db.update(ConstainApp.JS_CART,values,ConstainApp.JS_FSPID +"=?",new String[] { String.valueOf(cart.getFspId())});
    }

    /*
     Getting All cart
      */

    public List<Cart> getAllCart() {
        List<Cart> cartList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ConstainApp.JS_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setFspId(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_FSPID)));
                cart.setImageProduct(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_IMAGEPRODUCT)));
                cart.setProductName(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_PRODUCTNAME)));
                cart.setStoreName(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_STORENAME)));
                cart.setPrice(cursor.getFloat(cursor.getColumnIndex(ConstainApp.JS_PRICE)));
                cart.setDiscount(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_DISCOUNT)));
                cart.setQuantity(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_QUANTITY)));
                cartList.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartList;
    }

    /*
    Delete a cart by ID
     */
    public void deleteCart(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ConstainApp.JS_CART, ConstainApp.JS_FSPID + " = ?",
                new String[] { String.valueOf(cart.getFspId()) });
        db.close();
    }

    /*
     Getting All cart group by store
      */

    public List<StoreOrderItem> getAllCartGroupStore() {
        List<StoreOrderItem> storeOrderItems = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  " + ConstainApp.JS_STORENAME + ", COUNT("  + ConstainApp.JS_FSPID + ") as " + ConstainApp.JS_PCount +
                " FROM " + ConstainApp.JS_CART + " GROUP BY " + ConstainApp.JS_STORENAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                StoreOrderItem storeOrderItem = new StoreOrderItem();
                storeOrderItem.setStoreName(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_STORENAME)));
                storeOrderItem.setQuantityOrder(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_PCount)));

                storeOrderItems.add(storeOrderItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return storeOrderItems;
    }

    /*
     Getting All cart group by store
      */



    public Cart getProductById(int fspId) {
        Cart cart = null;
        // Select All Query
        String selectQuery = "SELECT * " +
                " FROM " + ConstainApp.JS_CART + " WHERE " + ConstainApp.JS_FSPID + "=" + fspId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            cart = new Cart();
            cart.setFspId(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_FSPID)));
            cart.setImageProduct(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_IMAGEPRODUCT)));
            cart.setProductName(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_PRODUCTNAME)));
            cart.setStoreName(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_STORENAME)));
            cart.setPrice(cursor.getFloat(cursor.getColumnIndex(ConstainApp.JS_PRICE)));
            cart.setDiscount(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_DISCOUNT)));
            cart.setQuantity(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_QUANTITY)));

        }
        cursor.close();
        db.close();
        return cart;
    }

    public Store getStoreByName(String storeName) {
        Store store = null;
        // Select All Query
        String selectQuery = "SELECT * " +
                " FROM " + ConstainApp.JS_CART + " WHERE " + ConstainApp.JS_STORENAME + "=" + storeName;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            store = new Store();
            store.setStoreId(cursor.getInt(cursor.getColumnIndex(ConstainApp.STOREID)));
        }
        cursor.close();
        db.close();
        return store;
    }

    public List<Cart> getProductByStoreName(String storeName) {
        List<Cart> cartList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * " +
                " FROM " + ConstainApp.JS_CART + " WHERE " + ConstainApp.JS_STORENAME + "=" + "'"+storeName+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setFspId(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_FSPID)));
                cart.setImageProduct(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_IMAGEPRODUCT)));
                cart.setProductName(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_PRODUCTNAME)));
                cart.setStoreName(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_STORENAME)));
                cart.setPrice(cursor.getFloat(cursor.getColumnIndex(ConstainApp.JS_PRICE)));
                cart.setDiscount(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_DISCOUNT)));
                cart.setQuantity(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_QUANTITY)));
                cartList.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return (cartList);
    }

//    public List<OrderDetail> getAllOD() {
//        List<OrderDetail> ODList = new ArrayList<>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + ConstainApp.LIST_ORDER_DETAIL;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                OrderDetail orderDetail = new OrderDetail();
//                orderDetail.setOrderDetailId(cursor.getInt(cursor.getColumnIndex(ConstainApp.ORDERID)));
//                cart.setImageProduct(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_IMAGEPRODUCT)));
//                cart.setProductName(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_PRODUCTNAME)));
//                cart.setStoreName(cursor.getString(cursor.getColumnIndex(ConstainApp.JS_STORENAME)));
//                cart.setPrice(cursor.getFloat(cursor.getColumnIndex(ConstainApp.JS_PRICE)));
//                cart.setDiscount(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_DISCOUNT)));
//                cart.setQuantity(cursor.getInt(cursor.getColumnIndex(ConstainApp.JS_QUANTITY)));
//                cartList.add(cart);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return cartList;
//    }

}
