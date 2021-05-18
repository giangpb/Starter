package vn.com.misa.starter2.ui.finishsetup;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.observers.BlockingBaseObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.service.APIService;
import vn.com.misa.starter2.util.GIANGUtils;

public class FinishPresenter {


    private IGetDataFromServiceContracts callBack;

    public FinishPresenter(IGetDataFromServiceContracts callBack) {
        this.callBack = callBack;
    }

    public void startSynch(int id) {
        try {
            callBack.onS();
            APIService.API_SERVICE.getAllPayment(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<SAInvoice>>() {
                        @Override
                        public void accept(List<SAInvoice> payments) throws Throwable {
                            if (payments != null && payments.size() > 0) {
                                callBack.onSuccess(payments);
                            } else {
                                callBack.onFailure("Lỗi kết nối với máy chủ");
                            }
                        }
                    });
        } catch (Exception ex) {
            GIANGUtils.getInstance().handlerLog(ex.getMessage());
        }
    }

    public void startSynSAInvoiceDetail(int userID) {
        callBack.onS();
        APIService.API_SERVICE.getAllPaymentDetail(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SAInvoiceDetail>>() {
                    @Override
                    public void accept(List<SAInvoiceDetail> saInvoiceDetails) throws Throwable {
                        if (saInvoiceDetails != null && saInvoiceDetails.size() > 0) {
                            callBack.onSuccessDetail(saInvoiceDetails);
                        } else {
                            callBack.onFailure("Lỗi kết nối với máy chủ");
                        }
                    }
                });
    }
}
