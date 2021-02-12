package vn.com.misa.starter2.ui.collectmoney;


import android.content.Context;

import vn.com.misa.starter2.model.entity.PaymentDetail;

/**
 * ‐ Lớp payment detail tương tác với lớp model
 * ‐ @created_by giangpb on 2/12/2021
 */
public class PaymentDetailPresenter {

    private PaymentDetailModel paymentDetailModel;

    public PaymentDetailPresenter(Context context){
        paymentDetailModel = new PaymentDetailModel(context);
    }

    /**
     * Hàm thêm chi tiết payment
     * @param paymentDetail chi tiết payment
     * @return kết quả thêm
     * author giangpb
     * @date 12/02/2021
     */
    public boolean addPaymentDetail(PaymentDetail paymentDetail){
        return paymentDetailModel.addPaymentDetail(paymentDetail);
    }
}
