package vn.com.misa.starter2.ui.collectmoney;

import android.content.Context;

import vn.com.misa.starter2.model.entity.Payment;

/**
 * ‐ thao tác với payment model
 * ‐ @created_by giangpb on 2/9/2021
 */
public class PaymentPresenter {

    private PaymentModel paymentModel;

    public PaymentPresenter(Context context){
        paymentModel = new PaymentModel(context);
    }

    /**
     * Hàm thêm payment
     * @param payment payment
     * @return kết quả
     * @author giangpb
     * @date 10/02/2021
     */
    public boolean addPayment(Payment payment){
        return paymentModel.addPayment(payment);
    }

}
