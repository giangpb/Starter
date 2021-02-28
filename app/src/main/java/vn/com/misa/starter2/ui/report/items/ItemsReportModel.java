package vn.com.misa.starter2.ui.report.items;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.ui.report.items.dto.ItemReport;

/**
 * ‐ @created_by giangpb on 2/19/2021
 */
public class ItemsReportModel extends DatabaseHelper {
    private static final String TAG = "ItemsReportModel";

    public ItemsReportModel(Context context) {
        super(context);
    }

    /**
     * Hàm lấy danh sách thống kê theo sản phẩm theo ngày
     * @param date ngày
     * @return danh sách sản phẩm
     * @author giangpb
     * @date 23/02/2021
     */
    public ArrayList<ItemReport> getData(String date){
        try{
            ArrayList<ItemReport> data = new ArrayList<>();
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT ItemID, ItemName, SUM(Quantity) ,sum(Amount)  FROM SAInvoiceDetail GROUP BY ItemID HAVING CreatedDate like ? AND RefDetailType = 1 ORDER BY sum(Amount) DESC", new String[]{date+"%"});
            while (cursor.moveToNext()){
                ItemReport itemReport = new ItemReport();
                itemReport.setItemID(cursor.getString(0));
                itemReport.setItemName(cursor.getString(1));
                itemReport.setItemQuantity(cursor.getInt(2));
                itemReport.setItemPrice(cursor.getInt(3));
                data.add(itemReport);
            }
            cursor.close();
            return data;
        }
        catch (Exception ex){
            Log.d(TAG, "getData: "+ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm lấy danh sách thống kê theo sản phẩm theo ngày
     * @param dateStart ngày bắt đầu
     * @param dateEnd ngày kết thúc
     * @return danh sách sản phẩm
     * @author giangpb
     * @date 27/02/2021
     */
    public ArrayList<ItemReport> getData(String dateStart, String dateEnd){
        try{
            ArrayList<ItemReport> data = new ArrayList<>();
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT ItemID, ItemName, SUM(Quantity) ,sum(Amount) FROM SAInvoiceDetail GROUP BY ItemID HAVING CreatedDate BETWEEN ? AND ? AND RefDetailType = 1 ORDER BY sum(Amount) DESC", new String[]{dateStart+"%", dateEnd+"%"});
            while (cursor.moveToNext()){
                ItemReport itemReport = new ItemReport();
                itemReport.setItemID(cursor.getString(0));
                itemReport.setItemName(cursor.getString(1));
                itemReport.setItemQuantity(cursor.getInt(2));
                itemReport.setItemPrice(cursor.getInt(3));
                data.add(itemReport);
            }
            cursor.close();
            return data;
        }
        catch (Exception ex){
            Log.d(TAG, "getData: "+ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm tính tổng số lượng sản phẩm
     * @param data danh sách sản phẩm
     * @return số lượng
     * @author giangpb
     * @date 20/02/2021
     */
    public int countQuantityItem(ArrayList<ItemReport> data){
        int sum = 0;
        for(ItemReport item :data){
            sum += item.getItemQuantity();
        }
        return sum;
    }

    /**
     * Hàm tính tổng tiền của báo cáo
     * @param data danh sách báo cáo theo thời gian
     * @return tiền
     * @author giangpb
     * @date 20/02/2021
     */
    public int countPriceItem(ArrayList<ItemReport> data){
        int sum = 0;
        for(ItemReport item :data){
            sum += item.getItemPrice();
        }
        return sum;
    }
}
