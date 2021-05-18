package vn.com.misa.starter2.ui.collectmoney;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.starter2.model.entity.PaymentDetail;
import vn.com.misa.starter2.ui.finishsetup.SAInvoiceDetail;

/**
 * ‐ Lớp payment detail tương tác với lớp model
 * ‐ @created_by giangpb on 2/12/2021
 */
public class PaymentDetailPresenter {

    private PaymentDetailModel paymentDetailModel;

    public PaymentDetailPresenter(Context context) {
        paymentDetailModel = new PaymentDetailModel(context);
    }

    public List<PaymentDetail> getAllPaymentDetailSynch() {
        return paymentDetailModel.getAllPaymentDetailSynch();
    }

    public boolean updateSynchDone(String id) {
        return paymentDetailModel.updateSynchDone(id);
    }

    /**
     * Hàm thêm chi tiết payment
     *
     * @param paymentDetail chi tiết payment
     * @return kết quả thêm
     * author giangpb
     * @date 12/02/2021
     */
    public boolean addPaymentDetail(PaymentDetail paymentDetail) {
        return paymentDetailModel.addPaymentDetail(paymentDetail);
    }

    public boolean addSAInvoiceDetail(SAInvoiceDetail paymentDetail) {
        return paymentDetailModel.addSAInvoiceDetail(paymentDetail);
    }

    /**
     * Hàm lấy thông tin danh sách chi tiết payment
     *
     * @param paymentID mã payment
     * @return danh sách
     * @author giangpb
     * @date 15/02/2021
     */
    public ArrayList<PaymentDetail> getAllPaymentDetail(String paymentID) {
        return paymentDetailModel.getAllPaymentDetail(paymentID);
    }

    /**
     * Hàm tính tổng số lượng danh sách payment
     *
     * @param paymentDetails danh sách
     * @return số lượng
     * @author giangpb
     * @date 15/02/2021
     */
    public int quantityCount(ArrayList<PaymentDetail> paymentDetails) {
        return paymentDetailModel.quantityCount(paymentDetails);
    }
}
