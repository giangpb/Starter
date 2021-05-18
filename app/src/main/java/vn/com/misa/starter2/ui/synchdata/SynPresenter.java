package vn.com.misa.starter2.ui.synchdata;

import vn.com.misa.starter2.model.dto.User;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.model.entity.PaymentDetail;

public class SynPresenter implements SynContracts.Model{

    private SynContracts.View callView;


    private SynModel synModel;

    public SynPresenter(SynContracts.View view, User user){
        this.callView = view;
        synModel = new SynModel(user, this);
    }

    public void handleSyn(Payment payment){
        synModel.processSyn(payment);
    }

    public void handleSynDetai(PaymentDetail paymentDetail){
        synModel.SynSAinvoiceDetail(paymentDetail);
    }

    @Override
    public void onStart() {
        callView.onStartSync();
    }

    @Override
    public void onSuccess() {
        callView.onSuccess();
    }

    @Override
    public void onFailure() {
        callView.onFailure();
    }

}
