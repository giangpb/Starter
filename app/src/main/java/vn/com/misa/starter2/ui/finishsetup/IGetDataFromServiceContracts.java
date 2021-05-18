package vn.com.misa.starter2.ui.finishsetup;

import java.util.List;

import vn.com.misa.starter2.model.entity.Payment;

public interface IGetDataFromServiceContracts {
    void onSuccess(List<SAInvoice> listPayment);
    void onSuccessDetail(List<SAInvoiceDetail> listPaymentDetail);
    void onFailure(String message);
    void onS();
}
