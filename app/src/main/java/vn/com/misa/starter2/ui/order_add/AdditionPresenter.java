package vn.com.misa.starter2.ui.order_add;

import android.content.Context;

import java.util.ArrayList;

import vn.com.misa.starter2.model.dto.AdditionCategory;

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
}
