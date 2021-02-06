package vn.com.misa.starter2.model.Model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.ui.chooserectaurant.SelectRestaurantFragment;

/**
 * ‐ model category lấy danh danh mục từ sqlite
 * ‐ @created_by giangpb on 1/25/2021
 */
public class CategoryModel extends DatabaseHelper {

    private Context mContext;

    public CategoryModel(Context context){
        super(context);
        this.mContext = context;
    }

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

}
