package vn.com.misa.starter2.ui.listorder;

import android.content.Context;

import java.util.ArrayList;

import vn.com.misa.starter2.model.entity.Order;

/**
 * ‐ presenter oreder lấy dữ liệu từ model
 * ‐ @created_by giangpb on 2/1/2021
 */
public class OrderPresenter {

    private Context mContext;
    private OrderModel orderModel;

    public OrderPresenter(Context context){
        this.mContext = context;
        orderModel  =new OrderModel(context);
    }


    public ArrayList<Order> getAllOrder(){
        return orderModel.getAllOrder();
    }

    public boolean addOrder(Order order){
        return orderModel.storeOrder(order);
    }


    /**
     * Hàm cập nhật order
     * @param order order
     * @return kết quả
     * @author giangpb
     * @date 06/02/2021
     */
    public boolean updateOrder(Order order){
        return orderModel.updateOrder(order);
    }


    /**
     * Hàm xoá order theo mã
     * @param orderID mã truyền vào
     * @return kết quả đúng hoặc sai
     * author giangpb
     * @date 6/2/2021
     */
    public boolean deleteOrder(String orderID){
        return orderModel.deleteOrder(orderID);
    }
}
