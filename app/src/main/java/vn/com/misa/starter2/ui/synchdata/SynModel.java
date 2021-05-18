package vn.com.misa.starter2.ui.synchdata;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.starter2.model.dto.User;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.model.entity.PaymentDetail;
import vn.com.misa.starter2.service.APIService;
import vn.com.misa.starter2.ui.login.LoginActivity;
import vn.com.misa.starter2.util.GIANGCache;
import vn.com.misa.starter2.util.GIANGUtils;

public class SynModel {
    private User mUser;

    private SynContracts.Model mCallBack;

    public SynModel(User user, SynContracts.Model callBack){
        this.mUser = user;
        this.mCallBack = callBack;
    }

    public void SynSAinvoiceDetail(PaymentDetail paymentDetail){
        mCallBack.onStart();
        APIService.API_SERVICE.insertSAInvoceDetail(
                paymentDetail.getPaymentDetailID(),
                paymentDetail.getPaymentID(),
                paymentDetail.getParentID(),
                paymentDetail.getItemID(),
                paymentDetail.getItemName(),
                paymentDetail.getRefDetailType(),
                paymentDetail.getUnitID(),
                paymentDetail.getUnitPrice(),
                paymentDetail.getQuantity(),
                paymentDetail.getPromotionRate(),
                paymentDetail.getPromotionAmount(),
                paymentDetail.getDiscountAmount(),
                paymentDetail.getAmount(),
                paymentDetail.getSortOrder(),
                paymentDetail.getCreatedDate(),
                mUser.getId()).enqueue(new Callback<Boolean>() {
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
                else{
                    mCallBack.onFailure();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }


    public void processSyn(Payment payment){
        try{
            mCallBack.onStart();
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
                    mUser.getId()
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
                    else{
                        mCallBack.onFailure();
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
