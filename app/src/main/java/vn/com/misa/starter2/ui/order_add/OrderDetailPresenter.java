package vn.com.misa.starter2.ui.order_add;

import android.content.Context;

import java.util.ArrayList;

import vn.com.misa.starter2.model.entity.OrderDetail;

/**
 * ‐ Lớp chi tiết order lấy dữ liệu từ chi tiết order model
 * ‐ @created_by giangpb on 2/1/2021
 */
public class OrderDetailPresenter {

    private Context mContext;

    private OrderDetailModel orderDetailModel;



    public OrderDetailPresenter(Context context){
        this.mContext = context;
        orderDetailModel = new OrderDetailModel(context);
    }

    /**
     * Hàm lấy chi tiết order theo mã order
     * @param orderID mã order truyền vào
     * @return chi tiết order theo mã
     * @author giangpb
     * @date 1/02/2021
     */
    public ArrayList<OrderDetail> getOrderDetail(String orderID){
        return orderDetailModel.getOrderDetail(orderID);
    }
}
