package vn.com.misa.starter2.ui.synchdata;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.starter2.model.dto.User;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.service.APIService;
import vn.com.misa.starter2.ui.login.LoginActivity;
import vn.com.misa.starter2.util.GIANGCache;
import vn.com.misa.starter2.util.GIANGUtils;

public class SynModel {

    private SynContracts.Model mCallBack;

    public SynModel(SynContracts.Model callBack){
        this.mCallBack = callBack;
    }


    public void processSyn(Payment payment){
        try{
            mCallBack.onStart();
            User user = GIANGCache.getInstance().get(LoginActivity.KEY_LOGIN, User.class);
            int id = user.getId();
            APIService.API_SERVICE.insertSAInvoice(payment.getRefID(),
                    payment.getRefType(),
                    payment.getRefNO(),
                    payment.getRefDate(),
                    payment.getAmount(),
                    payment.getPromotionItemsAmount(),
                    payment.getTotalItemAmount(),
                    payment.getPromotionRate(),
                    payment.getPromotionAmount(),
                    payment.getDiscountAmount(),
                    payment.getPreTaxAmount(),
                    payment.getTotalAmount(),
                    payment.getReceiveAmount(),
                    payment.getReturnAmount(),
                    payment.getPaymentStatus(),
                    payment.getOrderID(),
                    payment.getOrderType(),
                    payment.getTableName(),
                    payment.getRefDate(),
                    id
                    ).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body() != null){
                        boolean res = response.body();
                        if(res){
                            mCallBack.onSuccess();
                        }
                        else{
                            mCallBack.onFailure();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    mCallBack.onFailure();
                }
            });
        }
        catch (Exception exx){
            GIANGUtils.getInstance().handlerLog(exx.getMessage());
        }
    }
}
