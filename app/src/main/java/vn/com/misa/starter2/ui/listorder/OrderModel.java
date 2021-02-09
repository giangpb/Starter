package vn.com.misa.starter2.ui.listorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.entity.Order;

/**
 * ‐ lớp order lấy dữ liệu từ database
 * ‐ @created_by giangpb on 2/1/2021
 */
public class OrderModel extends DatabaseHelper {

    private static final String TAG = "OrderModel";

    public OrderModel(Context context) {
        super(context);
    }

    /**
     * Hàm lấy toàn bộ danh sách order
     * @return danh sách
     * @author giangpb
     * @date 1/2/2021
     */
    public ArrayList<Order> getAllOrder(){
        ArrayList<Order> dsOder = new ArrayList<>();

        try{
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Order1 WHERE OrderStatus = ?",new String[]{"1"});
            while (cursor.moveToNext()){
                Order order = new Order();

                order.setOrderID(cursor.getString(0));
                order.setOrderNo(cursor.getString(1));
                order.setOrderDate(cursor.getString(2));
                order.setOrderType(cursor.getInt(3));
                order.setOrderStatus(cursor.getInt(4));
                order.setAmount(cursor.getInt(5));
                order.setPromotionItemsAmount(cursor.getInt(6));
                order.setTotalItemAmount(cursor.getInt(7));
                order.setPromotionRate(cursor.getInt(8));
                order.setPromotionAmount(cursor.getInt(9));
                order.setDiscountAmount(cursor.getInt(10));
                order.setTotalAmount(cursor.getInt(12));

                order.setTableName(cursor.getString(14));
                order.setItemNames(cursor.getString(15));
                order.setDateCrate(cursor.getString(16));

                dsOder.add(order);
            }
            cursor.close();
            return dsOder;
        }
        catch (Exception ex){
            Log.d(TAG, "getAllOrder: "+ex.getMessage());
        }
        return null;
    }



    /**
     * Hàm lưu order
     * @param order order
     * @return kết quả
     * @author giangpb
     * @date 06/02/2021
     */
    public boolean storeOrder(Order order){
        try {
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("OrderID", order.getOrderID());
            values.put("OrderDate", order.getOrderDate());
            values.put("OrderStatus", order.getOrderStatus());
            values.put("Amount", order.getAmount());
            values.put("TotalAmount", order.getTotalAmount());
            values.put("ItemNames", order.getItemNames());
            sqLiteDatabase.insert("Order1", null,values);
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "storeOrder: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm cập nhật order
     * @param order order
     * @return kết quả
     * @author giangpb
     * @date 06/02/2021
     */
    public boolean updateOrder(Order order){
        try {
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("OrderDate", order.getOrderDate());
            values.put("OrderStatus", order.getOrderStatus());
            values.put("Amount", order.getAmount());
            values.put("TotalAmount", order.getTotalAmount());
            values.put("ItemNames", order.getItemNames());
            sqLiteDatabase.update("Order1", values,"OrderID = ?", new String[]{order.getOrderID()});
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "storeOrder: "+ex.getMessage());
        }
        return false;
    }


    /**
     * Hàm xoá order theo mã
     * @param orderID mã truyền vào
     * @return kết quả đúng hoặc sai
     * author giangpb
     * @date 6/2/2021
     */
    public boolean deleteOrder(String orderID){
        try{
            connectSQLite();
            sqLiteDatabase.delete("Order1", "OrderID = ?", new String[]{orderID});
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "deleteOrder: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm thanh toán oreder thành công!
     * @param orderID mã order
     * @return kết quả
     * @author giangpb
     * @date 08/02/2021
     */
    public boolean paymentDone(String orderID){
        try{

            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("OrderStatus", 2);
            sqLiteDatabase.update("Order1", values,"OrderID = ?", new String[]{orderID});
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "paymentDone: "+ex.getMessage());
        }
        return false;
    }
}
