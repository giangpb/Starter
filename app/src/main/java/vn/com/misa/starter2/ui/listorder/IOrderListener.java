package vn.com.misa.starter2.ui.listorder;

import vn.com.misa.starter2.model.entity.Order;

/**
 * ‐ Interface lắng nghe sự kiện nhấn vào danh sách order
 * ‐ @created_by giangpb on 2/1/2021
 */
public interface IOrderListener {

    /**
     * Sự kiện click vào item order
     * @param order oder
     * @author giangpb
     * @date 27/01/2021
     */
    void onOrderClickListener(Order order);

    void onDeleteOrderClickListener(Order order, int pos);

    void onPaymentOrderClickListener(Order order);
}
