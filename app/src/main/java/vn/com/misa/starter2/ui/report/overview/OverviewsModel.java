package vn.com.misa.starter2.ui.report.overview;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.ui.report.overview.dto.OverviewHours;

/**
 * ‐ @created_by giangpb on 2/23/2021
 */
public class OverviewsModel extends DatabaseHelper {
    private static final String TAG = "OverviewsModel";

    public OverviewsModel(Context context) {
        super(context);
    }

    /**
     * Hàm thống kê tiền theo ngày
     * @param ngay ngày
     * @return tiền
     * @author giangpb
     * @date 23/02/2021
     */
    public int doanhThuTheoNgay(String ngay){
        try{
            int x = 0;
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(Amount) FROM SAInvoiceDetail WHERE CreatedDate like ? AND RefDetailType = 1", new String[]{ngay+"%"});
            if (cursor.moveToNext())
                x = cursor.getInt(0);
            cursor.close();
            return x;
        }
        catch (Exception ex){
            Log.d(TAG, "doanhThuTheoNgay: "+ex.getMessage());
        }
        return -1;
    }

    /**
     * Hàm thống kê tiền theo ngày
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @return tiền
     * @author giangpb
     * @date 28/02/2021
     */
    public int doanhThuTheoNgay(String startDate, String endDate){
        try{
            int x = 0;
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(Amount) FROM SAInvoiceDetail WHERE CreatedDate BETWEEN ? AND ? AND RefDetailType = 1", new String[]{startDate+"%", endDate +"%"});
            if (cursor.moveToNext())
                x = cursor.getInt(0);
            cursor.close();
            return x;
        }
        catch (Exception ex){
            Log.d(TAG, "doanhThuTheoNgay: "+ex.getMessage());
        }
        return -1;
    }

    /**
     * Hàm thống kê doanh thu trung bình theo giờ và ngày
     * @param date ngày
     * @return danh sách để đổ lên bar chart
     * @author giangpb
     * @date 22/02/2021
     */
    public ArrayList<OverviewHours> getAllData(String date){
        try{
            ArrayList<OverviewHours> data = new ArrayList<>();
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT strftime ('%H',CreatedDate) hour, sum(Amount) FROM SAInvoiceDetail GROUP BY strftime ('%H',CreatedDate) HAVING CreatedDate like ? AND RefDetailType = 1 ORDER BY strftime ('%H',CreatedDate)", new String[]{date+"%"});
            while (cursor.moveToNext()){
                OverviewHours overviewHours = new OverviewHours();
                overviewHours.setHour(cursor.getInt(0));
                overviewHours.setMoney(cursor.getInt(1));
                data.add(overviewHours);
            }
            cursor.close();
            return data;
        }
        catch (Exception ex){
            Log.d(TAG, "getAllData: "+ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm thống kê doanh thu trung bình theo giờ và ngày
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @return danh sách để đổ lên bar chart
     * @author giangpb
     * @date 28/02/2021
     */
    public ArrayList<OverviewHours> getAllData(String startDate, String endDate){
        try{
            ArrayList<OverviewHours> data = new ArrayList<>();
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT strftime ('%H',CreatedDate) hour, sum(Amount) FROM SAInvoiceDetail GROUP BY strftime ('%H',CreatedDate) HAVING CreatedDate BETWEEN ? AND ? AND RefDetailType = 1 ORDER BY strftime ('%H',CreatedDate)", new String[]{startDate+"%", endDate+"%"});
            while (cursor.moveToNext()){
                OverviewHours overviewHours = new OverviewHours();
                overviewHours.setHour(cursor.getInt(0));
                overviewHours.setMoney(cursor.getInt(1));
                data.add(overviewHours);
            }
            cursor.close();
            return data;
        }
        catch (Exception ex){
            Log.d(TAG, "getAllData: "+ex.getMessage());
        }
        return null;
    }
}
