package vn.com.misa.starter2.ui.order_add;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.dto.AdditionCategory;

/**
 * ‐ addition lấy dữ liệu từ model
 * ‐ @created_by giangpb on 2/2/2021
 */
public class AdditionModel extends DatabaseHelper {
    private static final String TAG = "AdditionModel";

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
}
