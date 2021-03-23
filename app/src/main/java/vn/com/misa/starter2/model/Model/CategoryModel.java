package vn.com.misa.starter2.model.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.ui.chooserectaurant.SelectRestaurantFragment;
import vn.com.misa.starter2.ui.setupmenu.SetupMenuFragment;

/**
 * ‐ model category lấy danh danh mục từ sqlite
 * ‐ @created_by giangpb on 1/25/2021
 */
public class CategoryModel extends DatabaseHelper {

    private static final String TAG = "CategoryModel";
    private static final String TABLE = "InventoryItemCategory";

    private Context mContext;

    public CategoryModel(Context context){
        super(context);
        this.mContext = context;
    }

    /**
     * Hàm lấy danh sách danh mục
     * @return danh sách danh mục
     */
    public ArrayList<Category> getListCategory(){
        // kết nối database
        connectSQLite();

        ArrayList<Category> categoryList = new ArrayList<>();
        Cursor cursor  = sqLiteDatabase.query(SelectRestaurantFragment.TABLE_Category,null,null,null,null,null,"SortOrder");
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String code = cursor.getString(1);
            String name = cursor.getString(2);
            String iconPath = cursor.getString(3);
            int sortOrder = cursor.getInt(4);
            int iconType = cursor.getInt(6);
            String createdDate = cursor.getString(7);

            Category category = new Category(id,code,name,iconPath,createdDate,sortOrder,iconType);
            categoryList.add(category);
        }
        cursor.close();
        return categoryList;
    }

    /**
     * Hàm thêm danh mục
     * @param category
     * @return kết quả
     * @author giangpb
     * @date 20/03/2021
     */
    public boolean addCategory(Category category){
        try{
            connectSQLite();
            ContentValues values = new ContentValues();
            values.put("InventoryItemCategoryID", category.getCategoryID());
            values.put("InventoryItemCategoryCode", category.getCategoryCode());
            values.put("InventoryItemCategoryName", category.getCategoryName());
            values.put("IconPath", category.getIconPath());
            values.put("SortOrder", category.getSortOrder());
            values.put("Inactive", category.isActive());
            values.put("IconType", category.getIconType());
            values.put("CreateDate", category.getCreatedDate());
            sqLiteDatabase.insert(TABLE, null, values);
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "addCategory: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Hàm xoá danh mục trong db
     * @param categoryID mã danh mục
     * @return kết quả
     * @author giangpb
     * @date 20/03/2021
     */
    public boolean deleteCategory(String categoryID){
        try{
            connectSQLite();
            sqLiteDatabase.delete(TABLE,"InventoryItemCategoryID = ?", new String[]{categoryID});
            return true;
        }
        catch (Exception ex){
            Log.d(TAG, "deleteCategory: "+ex.getMessage());
        }
        return false;
    }

}
