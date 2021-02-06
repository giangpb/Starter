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
}
