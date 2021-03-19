package vn.com.misa.starter2.ui.order_add;

import android.content.Context;

import java.util.ArrayList;

import vn.com.misa.starter2.model.dto.AdditionCategory;
import vn.com.misa.starter2.model.entity.Addition;

/**
 * ‐ Mục đích Class thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
 * ‐ @created_by giangpb on 2/2/2021
 */
public class AdditionPresenter {
    private AdditionModel additionModel;

    public AdditionPresenter(Context context){
        additionModel = new AdditionModel(context);
    }

    public ArrayList<AdditionCategory> additionCategories(String categoryID){
        return additionModel.getListAddition(categoryID);
    }

    /**
     * Hàm lấy toàn bộ danh mục bổ sung
     * @return danh sách
     * @author giangpb
     * @date 20/02/2021
     */
    public ArrayList<Addition> getAllAddition(){
        return additionModel.getAllAddition();
    }

    /**
     * Hàm xoá addition theo mã
     * @param additionID
     * @return kết quả
     * @author giangpb on 17/03/2021
     */
    public boolean removeAddition(String additionID){
        return additionModel.removeAddition(additionID);
    }

    /**
     * Hàm thêm addition vào database
     * @param addition
     * @return kết quả
     * @author giangpb on 17/03/2021
     */
    public boolean addAddition(Addition addition){
        return additionModel.addAddition(addition);
    }

    /**
     * Hàm check tồn tại tên
     * -> Không cho thêm nữa
     * @param additionName
     * @return kết quả
     * @author giangpb on 17/03/2021
     */
    public boolean checkExistAddition(String additionName){
        return additionModel.checkExistAddition(additionName);
    }
}
