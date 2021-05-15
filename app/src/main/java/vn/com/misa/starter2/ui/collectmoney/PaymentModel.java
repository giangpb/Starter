package vn.com.misa.starter2.ui.collectmoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.util.GIANGUtils;

/**
 * ‐ Tương tác csdl payment
 * ‐ @created_by giangpb on 2/9/2021
 */
public class PaymentModel extends DatabaseHelper {
    private static final String TAG = "PaymentModel";

    Context mContext;

    public PaymentModel(Context context) {
        super(context);
        this.mContext = context;
    }


    /**
     * Hàm thêm payment
     *
     * @param payment payment
     * @return kết quả
     * @author giangpb
     * @date 10/02/2021
     */
    public boolean addPayment(Payment payment) {
        try {
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("RefID", payment.getRefID());
            values.put("RefType", 550);
            values.put("RefNo", payment.getRefNO());
            values.put("RefDate", payment.getRefDate());
            values.put("Amount", payment.getAmount());
            values.put("PromotionItemsAmount", payment.getPromotionItemsAmount());
            values.put("TotalItemAmount", payment.getTotalItemAmount());
            values.put("PromotionRate", payment.getPromotionRate());
            values.put("PromotionAmount", payment.getPromotionAmount());
            values.put("DiscountAmount", payment.getDiscountAmount());
            values.put("PreTaxAmount", payment.getPreTaxAmount());
            values.put("TotalAmount", payment.getTotalAmount());
            values.put("ReceiveAmount", payment.getReceiveAmount());
            values.put("ReturnAmount", payment.getReturnAmount());
            values.put("PaymentStatus", 3);
            values.put("OrderID", payment.getOrderID());
            values.put("OrderType", 1);
            values.put("TableName", payment.getTableName());
            values.put("CreatedDate", payment.getCreatedDate());
            sqLiteDatabase.insert("SAInvoice", null, values);
            close();
            return true;
        } catch (Exception ex) {
            Log.d(TAG, "addPayment: " + ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm lấy toàn bộ danh sách payment
     *
     * @return danh sách
     * @author giangpb
     * @date 12/02/2021
     */
    public ArrayList<Payment> getAllPayment() {
        try {
            connectSQLite();
            ArrayList<Payment> lstPayment = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SAInvoice", null);
            while (cursor.moveToNext()) {
                Payment payment = new Payment();
                payment.setRefID(cursor.getString(0));
                payment.setRefNO(cursor.getString(2));
                payment.setRefDate(cursor.getString(3));
                payment.setAmount(cursor.getInt(4));
                payment.setPromotionRate(cursor.getInt(7));
                payment.setPromotionAmount(cursor.getInt(8));
                payment.setDiscountAmount(cursor.getInt(9));
                payment.setTotalAmount(cursor.getInt(11));
                payment.setReceiveAmount(cursor.getInt(12));
                payment.setReturnAmount(cursor.getInt(13));
                payment.setOrderID(cursor.getString(15));
                payment.setTableName(cursor.getString(17));
                lstPayment.add(payment);
            }
            cursor.close();
            close();
            return lstPayment;
        } catch (Exception ex) {
            Log.d(TAG, "getAllPayment: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm cập nhật trạng thái sau khi đồng bộ xong
     * @param refID
     * @return
     */
    public boolean synSuccess(String refID) {
        try {
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("isSyn", 1);
            sqLiteDatabase.update("SAInvoice", values, "RefID=?", new String[]{refID});
            close();
            return true;
        } catch (Exception ex) {
            GIANGUtils.getInstance().handlerLog(ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm lấy toàn bộ danh sách payment để đồng bộ
     *
     * @return danh sách
     * @author giangpb
     * @date 12/02/2021
     */
    public ArrayList<Payment> getAllPaymentForSynch() {
        try {
            connectSQLite();
            ArrayList<Payment> lstPayment = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SAInvoice WHERE isSyn = 0", null);
            while (cursor.moveToNext()) {
                Payment payment = new Payment();
                payment.setRefID(cursor.getString(0));
                payment.setRefNO(cursor.getString(2));
                payment.setRefDate(cursor.getString(3));
                payment.setAmount(cursor.getInt(4));
                payment.setPromotionRate(cursor.getInt(7));
                payment.setPromotionAmount(cursor.getInt(8));
                payment.setDiscountAmount(cursor.getInt(9));
                payment.setTotalAmount(cursor.getInt(11));
                payment.setReceiveAmount(cursor.getInt(12));
                payment.setReturnAmount(cursor.getInt(13));
                payment.setOrderID(cursor.getString(15));
                payment.setTableName(cursor.getString(17));
                lstPayment.add(payment);
            }
            cursor.close();
            close();
            return lstPayment;
        } catch (Exception ex) {
            Log.d(TAG, "getAllPayment: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm lấy toàn bộ danh sách payment theo ngày
     *
     * @param date ngày hiện tại
     * @return danh sách
     * @author giangpb
     * @date 14/02/2021
     */
    public ArrayList<Payment> getAllPayment(String date) {
        try {
            connectSQLite();
            ArrayList<Payment> lstPayment = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SAInvoice WHERE CreatedDate like ? ORDER BY RefNo DESC", new String[]{date});
            while (cursor.moveToNext()) {
                Payment payment = new Payment();
                payment.setRefID(cursor.getString(0));
                payment.setRefNO(cursor.getString(2));
                payment.setRefDate(cursor.getString(3));
                payment.setAmount(cursor.getInt(4));
                payment.setPromotionRate(cursor.getInt(7));
                payment.setPromotionAmount(cursor.getInt(8));
                payment.setDiscountAmount(cursor.getInt(9));
                payment.setTotalAmount(cursor.getInt(11));
                payment.setReceiveAmount(cursor.getInt(12));
                payment.setReturnAmount(cursor.getInt(13));
                payment.setOrderID(cursor.getString(15));
                payment.setTableName(cursor.getString(17));
                lstPayment.add(payment);
            }
            cursor.close();
            close();
            return lstPayment;
        } catch (Exception ex) {
            Log.d(TAG, "getAllPayment: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm lấy toàn bộ danh sách payment 1 tuần
     *
     * @param date1 ngày tuần trước
     * @param date2 ngày hiện tại
     * @return danh sách
     * @author giangpb
     * @date 18/02/2021
     */
    public ArrayList<Payment> getAllPayment(String date1, String date2) {
        try {
            connectSQLite();
            ArrayList<Payment> lstPayment = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SAInvoice WHERE CreatedDate BETWEEN ? AND ? ORDER BY RefNo DESC", new String[]{date1, date2});
            while (cursor.moveToNext()) {
                Payment payment = new Payment();
                payment.setRefID(cursor.getString(0));
                payment.setRefNO(cursor.getString(2));
                payment.setRefDate(cursor.getString(3));
                payment.setAmount(cursor.getInt(4));
                payment.setPromotionRate(cursor.getInt(7));
                payment.setPromotionAmount(cursor.getInt(8));
                payment.setDiscountAmount(cursor.getInt(9));
                payment.setTotalAmount(cursor.getInt(11));
                payment.setReceiveAmount(cursor.getInt(12));
                payment.setReturnAmount(cursor.getInt(13));
                payment.setOrderID(cursor.getString(15));
                payment.setTableName(cursor.getString(17));
                lstPayment.add(payment);
            }
            cursor.close();
            close();
            return lstPayment;
        } catch (Exception ex) {
            Log.d(TAG, "getAllPayment: " + ex.getMessage());
        }
        return null;
    }
}
