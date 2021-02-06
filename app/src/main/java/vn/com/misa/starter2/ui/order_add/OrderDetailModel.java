package vn.com.misa.starter2.ui.order_add;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.entity.OrderDetail;

/**
 * ‐ lớp oderDetaiModel tương tác cơ sở dữ liệu
 * ‐ @created_by giangpb on 2/1/2021
 */
public class OrderDetailModel extends DatabaseHelper {

    private static final String TAG = "OrderDetailModel";

    Context mContext;

    public OrderDetailModel(Context context) {
        super(context);
        this.mContext = context;
    }

    /**
     * Hàm lấy chi tiết order theo mã order
     * @param orderID mã order truyền vào
     * @return chi tiết order theo mã
     * @author giangpb
     * @date 1/02/2021
     */
    public ArrayList<OrderDetail> getOrderDetail(String orderID){
        try{
            ArrayList<OrderDetail> dsOrderDetails = new ArrayList<>();
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM OrderDetail WHERE OrderID = ?",new String[]{orderID});

            while (cursor.moveToNext()){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDetailID(cursor.getString(0));
                orderDetail.setOrderID(cursor.getString(1));
                orderDetail.setParentID(cursor.getString(2));
                orderDetail.setItemID(cursor.getString(3));
                orderDetail.setItemName(cursor.getString(4));
                orderDetail.setOrderDetailType(cursor.getInt(6));
                orderDetail.setUnitID(cursor.getString(7));
                orderDetail.setUnitPrice(cursor.getInt(9));
                orderDetail.setQuantity(cursor.getInt(10));
                orderDetail.setPromotionRate(cursor.getInt(11));
                orderDetail.setPromotionAmount(cursor.getInt(12));
                orderDetail.setAmount(cursor.getInt(14));
                orderDetail.setDateCreate(cursor.getString(17));
                dsOrderDetails.add(orderDetail);
            }
            cursor.close();
            return dsOrderDetails;
        }
        catch (Exception exx){
            Log.d(TAG, "getOrderDetail: "+exx.getMessage());
        }
        return null;
    }

    /**
     * Hàm cập nhật chi tiết hoá đơn vào sqlite
     * @param orderDetail chi tiết hoá đơn
     * @return đúng hoặc sai
     * author giangpb
     * @date 07/02/2021
     */
    public boolean updateOrderDetail(OrderDetail orderDetail){
        try{
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("ItemID", orderDetail.getItemID());
            values.put("ItemName", orderDetail.getItemName());
            values.put("UnitID", orderDetail.getUnitID());
            values.put("UnitPrice", orderDetail.getUnitPrice());
            values.put("Quantity", orderDetail.getQuantity());
            values.put("Amount", orderDetail.getAmount());
            values.put("SortOrder", 0);
            values.put("CreatedDate", orderDetail.getDateCreate());
            sqLiteDatabase.update("OrderDetail", values, "OrderDetailID = ?", new String[]{orderDetail.getOrderDetailID()});
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "updateOrderDetail: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm xoá order detail theo mã order
     * @param oderID mã order
     * @return kết quả đúng hoặc sai
     * @author giangpb
     * @date 06/02/2020
     */
    public boolean deleteOrderDetail(String oderID){
        try{

            connectSQLite();
            sqLiteDatabase.delete("OrderDetail", "OrderID = ?", new String[]{oderID});
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "deleteOrderDetail: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm thêm chi tiết hoá đơn vào sqlite
     * @param orderDetail chi tiết hoá đơn
     * @return đúng hoặc sai
     * author giangpb
     * @date 06/02/2021
     */
    public boolean themOrderDetail(OrderDetail orderDetail){
        try{
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("OrderDetailID", orderDetail.getOrderDetailID());
            values.put("OrderID", orderDetail.getOrderID());
            values.put("ItemID", orderDetail.getItemID());
            values.put("ItemName", orderDetail.getItemName());
            values.put("UnitID", orderDetail.getUnitID());
            values.put("UnitPrice", orderDetail.getUnitPrice());
            values.put("Quantity", orderDetail.getQuantity());
            values.put("Amount", orderDetail.getAmount());
            values.put("SortOrder", 0);
            values.put("CreatedDate", orderDetail.getDateCreate());
            sqLiteDatabase.insert("OrderDetail", null, values);
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "themOrderDetail: "+ex.getMessage());
        }
        return false;

    }
}
