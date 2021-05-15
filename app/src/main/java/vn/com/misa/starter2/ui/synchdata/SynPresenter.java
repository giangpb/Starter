package vn.com.misa.starter2.ui.synchdata;

import vn.com.misa.starter2.model.entity.Payment;

public class SynPresenter implements SynContracts.Model{

    private SynContracts.View callView;


    private SynModel synModel;

    public SynPresenter(SynContracts.View view){
        this.callView = view;
        synModel = new SynModel(this);
    }

    public void handleSyn(Payment payment){
        synModel.processSyn(payment);
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
