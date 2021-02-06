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

    /**
     * Hàm thêm chi tiết hoá đơn vào sqlite
     * @param orderDetail chi tiết hoá đơn
     * @return đúng hoặc sai
     * @author giangpb
     * @date 06/02/2021
     */
    public boolean themOrderDetail(OrderDetail orderDetail){
        return orderDetailModel.themOrderDetail(orderDetail);
    }


    /**
     * Hàm cập nhật chi tiết hoá đơn vào sqlite
     * @param orderDetail chi tiết hoá đơn
     * @return đúng hoặc sai
     * author giangpb
     * @date 07/02/2021
     */
    public boolean updateOrderDetail(OrderDetail orderDetail){
        return orderDetailModel.updateOrderDetail(orderDetail);
    }

    /**
     * Hàm xoá order detail theo mã order
     * @param oderID mã order
     * @return kết quả đúng hoặc sai
     * @author giangpb
     * @date 06/02/2020
     */
    public boolean deleteOrderDetail(String oderID){
        return orderDetailModel.deleteOrderDetail(oderID);
    }
}
