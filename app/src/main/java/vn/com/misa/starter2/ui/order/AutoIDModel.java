package vn.com.misa.starter2.ui.order;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import vn.com.misa.starter2.datautils.DatabaseHelper;

/**
 * - Tự động tạo id
 * ‐ @created_by giangpb on 2/10/2021
 */
public class AutoIDModel extends DatabaseHelper {
    private static final String TAG = "AutoIDModel";

    public AutoIDModel(Context context) {
        super(context);
    }

    /**
     * Hàm tạo id với order
     * @param orderID mã order
     * @return kết quả thêm
     * @author giangpb
     * @date 11/02/2021
     */
    public boolean addAutoID(String orderID){
        try{
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("Name", orderID);
            sqLiteDatabase.insert("AutoID",null, values);
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "addAutoID: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm lấy id
     * @param orderID mã order
     * @return mã
     * @author giangpb
     * @date 11/01/2021
     */
    public int getIDAuto(String orderID){
        int id = 0;
        connectSQLite();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ID FROM AutoID WHERE Name = ? ",new String[]{orderID});
        if(cursor.moveToNext())
            id = cursor.getInt(0);
        cursor.close();
        return id;
    }
}
