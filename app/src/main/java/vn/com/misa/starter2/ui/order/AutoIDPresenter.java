package vn.com.misa.starter2.ui.order;

import android.content.Context;

/**
 * ‐ Giao tiếp với lớp model autoID
 * ‐ @created_by giangpb on 2/10/2021
 */
public class AutoIDPresenter {
    private AutoIDModel autoIDModel;

    public AutoIDPresenter(Context context){
        autoIDModel = new AutoIDModel(context);
    }

    /**
     * Hàm tạo id với order
     * @param orderID mã order
     * @return kết quả thêm
     * @author giangpb
     * @date 11/02/2021
     */
    public boolean addAutoID(String orderID){
        return autoIDModel.addAutoID(orderID);
    }

    /**
     * Hàm lấy id
     * @param orderID mã order
     * @return mã
     * @author giangpb
     * @date 11/01/2021
     */
    public int getIDAuto(String orderID){
        return autoIDModel.getIDAuto(orderID);
    }
}
