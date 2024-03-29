package com.tinlm.snef.constain;

import okhttp3.Cache;

public class ConstainServer {

    // Category
    public static final String CategoriesURL = "categories/";

    // Product
    public static final String ProductURL = "products/";
    public static final String GetListNameProduct = "getListNameProduct/";

    //FlashSaleProduct
    public static final String FlashSaleProductURL = "flashSaleProduct/";
    public static final String GetHotFlashSaleProductURL = "getHotFlashSaleProduct/";
    public static final String GetFSPByStoreId = "getFSPByStoreId/";
    public static final String GetAllFSP = "getAllFSP/";
    public static final String GetFSPByCategoryId = "getFSPByCategoryId/";
    public static final String GetFSPByName = "getFSPByName/";
    public static final String GetFSPById = "getFSPById/";
    public static final String GetRemaingQuantity = "getRemaingQuantity/";

    //Store Product Image
    public static final String StoreProductImageURL = "storeProductImage/";
    public static final String GetOneImage = "getOneImage/";
    public static final String GetImage = "getImage/";


    // Order Detail
    public static final String OrderDetail = "orderDetail/";
    public static final String GetQuantityByFSPId = "getQuantityByFSPId/";

    // Location
    public static final String LocationURL = "location/";

    // Store
    public static final String StoreURL = "store/";
    public static final String GetStoreById = "getById/";
    public static final String GetStoreByDistance = "getStoreByDistance/";

    // Customer
    public static final String CustomerURL = "customer/";
    public static final String LoginURL = "login/";
    public static final String CreateURL = "create/";
    public static final String UpdateURL = "update/";
    public static final String UpdatePasswordURL = "updatePassword/";

    //Order
    public static final String OrderURL = "order/";
    public static final String InsertNewOrder = "insertNewOrder/";
    public static final String GetLastOrderId = "getLastOrderId/";
    public static final String GetAllOrder = "getAllOrder/";
    public static final String GetOrderById = "getOrderById/";
    public static final String SubmitFeedback = "submitFeedback/";
    public static final String GetOrderByAccountId = "getOrderByAccountId/";

    //OrderDetail
    public static final String OrderDetailURL = "orderDetail/";
    public static final String InsertNewOrderDetail = "insertNewOrderDetail/";
    public static final String GetAllOrderDetailByOrderId = "getAllOrderDetailByOrderId/";


    //    public static final String BaseURL = "https://snef-api-part2.herokuapp.com/";
    public static final String BaseURL = "http://54.169.254.58:6432/";
//    public static final String BaseURL = "http://172.168.10.12:6432/";


}
