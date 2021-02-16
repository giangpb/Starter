package vn.com.misa.starter2.ui.collectmoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.entity.PaymentDetail;

/**
 * ‐ lớp payment detail tương tác csdl
 * ‐ @created_by giangpb on 2/12/2021
 */
public class PaymentDetailModel extends DatabaseHelper {
    private static final String TAG = "PaymentDetailModel";

    public PaymentDetailModel(Context context) {
        super(context);
    }

    /**
     * Hàm thêm chi tiết payment
     * @param paymentDetail chi tiết payment
     * @return kết quả thêm
     * author giangpb
     * @date 12/02/2021
     */
    public boolean addPaymentDetail(PaymentDetail paymentDetail){
        try{
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("RefDetailID", paymentDetail.getPaymentDetailID());
            values.put("RefID", paymentDetail.getPaymentID());
            values.put("ParentID", paymentDetail.getParentID());
            values.put("ItemID", paymentDetail.getItemID());
            values.put("ItemName", paymentDetail.getItemName());
            values.put("RefDetailType", paymentDetail.getRefDetailType());
            values.put("UnitID", paymentDetail.getUnitID());
            values.put("UnitPrice", paymentDetail.getUnitPrice());
            values.put("Quantity", paymentDetail.getQuantity());
            values.put("PromotionRate", paymentDetail.getPromotionRate());
            values.put("PromotionAmount", paymentDetail.getPromotionAmount());
            values.put("DiscountAmount", paymentDetail.getDiscountAmount());
            values.put("Amount", paymentDetail.getAmount());
            values.put("SortOrder", paymentDetail.getSortOrder());
            values.put("CreatedDate", paymentDetail.getCreatedDate());

            sqLiteDatabase.insert("SAInvoiceDetail", null, values);
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "addPaymentDetail: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm lấy thông tin danh sách chi tiết payment
     * @param paymentID mã payment
     * @return danh sách
     * @author giangpb
     * @date 15/02/2021
     */
    public ArrayList<PaymentDetail> getAllPaymentDetail(String paymentID){
        try{
            ArrayList<PaymentDetail> data = new ArrayList<>();
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SAInvoiceDetail WHERE RefID = ?", new String[]{paymentID});
            while (cursor.moveToNext()){
                PaymentDetail paymentDetail = new PaymentDetail();
                paymentDetail.setItemName(cursor.getString(4));
                paymentDetail.setQuantity(cursor.getInt(8));
                paymentDetail.setUnitPrice(cursor.getInt(12));

                data.add(paymentDetail);
            }
            cursor.close();
            return data;
        }
        catch (Exception ex){
            Log.d(TAG, "getAllPaymentDetail: "+ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm tính tổng số lượng danh sách payment
     * @param paymentDetails danh sách
     * @return số lượng
     * @author giangpb
     * @date 15/02/2021
     */
    public int quantityCount(ArrayList<PaymentDetail> paymentDetails){
        int sum = 0;
        for(PaymentDetail paymentDetail : paymentDetails){
            sum += paymentDetail.getQuantity();
        }
        return sum;
    }
}
