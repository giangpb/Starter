package vn.com.misa.starter2.ui.payment;

import vn.com.misa.starter2.model.entity.Payment;

/**
 * ‐ Contracts sự kiện click item payment
 * ‐ @created_by giangpb on 2/14/2021
 */
public interface IPaymentClickListener {
    void onPaymentClick(Payment payment);
}
