package vn.com.misa.starter2.ui.order_add;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.dto.AdditionCategory;
import vn.com.misa.starter2.model.entity.Addition;

/**
 * ‐ addition lấy dữ liệu từ model
 * ‐ @created_by giangpb on 2/2/2021
 */
public class AdditionModel extends DatabaseHelper {
    private static final String TAG = "AdditionModel";

    public static final String TABLE_ADDITION = "InventoryItemAddition";

    Context mContext;

    public AdditionModel(Context context) {
        super(context);
        this.mContext = context;
    }

    /**
     * Hàm lấy danh sách danh mục bổ sung theo mã danh mục
     * @param categoryID mã danh mục
     * @return danh sách danh mục bổ sung
     * @author giangpb
     * @date 2/2/2021
     */
    public ArrayList<AdditionCategory> getListAddition(String categoryID){
        try{
            ArrayList<AdditionCategory> dsAdditionCategories = new ArrayList<>();
            connectSQLite();
            String sql = "SELECT ItemAdditionMap.InventoryItemAdditionID,InventoryItemAddition.Description  FROM ItemAdditionMap INNER JOIN InventoryItemCategory ON ItemAdditionMap.InventoryItemCategoryID =InventoryItemCategory.InventoryItemCategoryID INNER JOIN InventoryItemAddition ON InventoryItemAddition.InventoryItemAdditionID = ItemAdditionMap.InventoryItemAdditionID WHERE ItemAdditionMap.InventoryItemCategoryID = ?";
            Cursor cursor  =sqLiteDatabase.rawQuery(sql,new String[]{categoryID});
            while (cursor.moveToNext()){
                AdditionCategory additionCategory = new AdditionCategory(cursor.getString(0), cursor.getString(1));
                dsAdditionCategories.add(additionCategory);
            }
            return dsAdditionCategories;
        }
        catch (Exception ex){
            Log.d(TAG, "getListAddition: "+ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm thêm addition vào database
     * @param addition
     * @return kết quả
     * @author giangpb on 17/03/2021
     */
    public boolean addAddition(Addition addition){
        try{
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("InventoryItemAdditionID", addition.getAdditionID());
            values.put("Description", addition.getDescription());
            values.put("InventoryItemCategoryAdditionID", addition.getInventoryItemCategoryAdditionID());
            values.put("Position", addition.getPosition());
            sqLiteDatabase.insert(TABLE_ADDITION, null, values);
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "addAddition: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm xoá addition theo mã
     * @param additionID
     * @return kết quả
     * @author giangpb on 17/03/2021
     */
    public boolean removeAddition(String additionID){
        try{
            connectSQLite();
            sqLiteDatabase.delete(TABLE_ADDITION, "InventoryItemAdditionID = ?",new String[]{additionID});
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "removeAddition: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm lấy toàn bộ danh mục bổ sung
     * @return danh sách
     * @author giangpb
     * @date 20/02/2021
     */
    public ArrayList<Addition> getAllAddition(){
        try{
            ArrayList<Addition> data = new ArrayList<>();
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM InventoryItemAddition ORDER BY Position DESC", null);
            while (cursor.moveToNext()){
                Addition addition = new Addition();
                addition.setAdditionID(cursor.getString(0));
                addition.setDescription(cursor.getString(1));
                data.add(addition);
            }
            cursor.close();
            return data;
        }
        catch (Exception ex){
            Log.d(TAG, "getAllAddition: "+ex.getMessage());
        }
        return null;
    }

    /**
     * Hàm check tồn tại tên
     * -> Không cho thêm nữa
     * @param additionName
     * @return kết quả
     * @author giangpb on 17/03/2021
     */
    public boolean checkExistAddition(String additionName){
        try{
            connectSQLite();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM InventoryItemAddition WHERE Description = ?", new String[]{additionName});
            if (cursor.moveToNext())
                return true;
            cursor.close();
            return false;
        }
        catch (Exception ex){
            Log.d(TAG, "checkExistAddition: "+ex.getMessage());
        }
        return true;
    }
}
