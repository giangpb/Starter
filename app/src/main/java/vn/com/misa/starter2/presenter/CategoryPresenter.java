package vn.com.misa.starter2.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.starter2.model.Model.CategoryModel;
import vn.com.misa.starter2.model.entity.Category;

/**
 * ‐ Lớp danh mục nhận dữ liệu từ model và cập nhật lên view
 * - Nhận hành động từ view và báo cho model
 * ‐ @created_by giangpb on 1/25/2021
 */
public class CategoryPresenter {

    private Context mContext;

    private CategoryModel categoryModel;

    public CategoryPresenter(Context context){
        this.mContext = context;
        categoryModel = new CategoryModel(context);
    }

    /**
     * Lấy dữ liệu từ model
     * @return danh sách danh mục
     * @author: giangpb
     * @date: 25/01/2021
     */
    public ArrayList<Category> getListCategory(){
        return categoryModel.getListCategory();
    }

    /**
     * Hàm thêm danh mục
     * @param category
     * @return kết quả
     * @author giangpb
     * @date 20/03/2021
     */
    public boolean addCategory(Category category){
        return categoryModel.addCategory(category);
    }

    /**
     * Hàm xoá danh mục trong db
     * @param categoryID mã danh mục
     * @return kết quả
     * @author giangpb
     * @date 20/03/2021
     */
    public boolean deleteCategory(String categoryID){
        return categoryModel.deleteCategory(categoryID);
    }
}
