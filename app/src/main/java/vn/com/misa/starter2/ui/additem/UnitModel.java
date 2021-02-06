package vn.com.misa.starter2.ui.additem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.entity.Unit;
import vn.com.misa.starter2.ui.chooserectaurant.SelectRestaurantFragment;

/**
 * ‐ lấy danh sách đơn vị tính từ database
 * ‐ @created_by giangpb on 1/26/2021
 */
public class UnitModel extends DatabaseHelper {
    private static final String TAG = "UnitModel";

    private Context mContext;

    public UnitModel(Context context){
        super(context);
        this.mContext = context;
    }

    /**
     * Hàm lấy toàn bộ dữ liệu
     * @return danh sách đơn vị tính
     * @author giangpb
     * @date 26/01/2021
     */
    public ArrayList<Unit> getAllListUnit(){
        ArrayList<Unit> dsUnit = new ArrayList<>();

        connectSQLite();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Unit",null);

        while (cursor.moveToNext()){
            Unit unit = new Unit(cursor.getString(0),cursor.getString(1));
            dsUnit.add(unit);
        }
        cursor.close();

        return dsUnit;
    }

    /**
     * Hàm thêm đơn vị tính
     * @param id mã đơn vị
     * @param name tên đơn vị
     * @return trả về kết quả thêm (true | false)
     * @author giangpb
     * @date 27/01/2021
     */
    public boolean addUnit(String id, String name){
        try{
            connectSQLite();
            ContentValues values= new ContentValues();
            values.put("UnitID", id);
            values.put("UnitName", name);
            values.put("InActive", false);
            sqLiteDatabase.insert(SelectRestaurantFragment.TABLE_UNIT, null,values);
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "addUnit: "+ex.getMessage());
        }

        return false;
    }

}
