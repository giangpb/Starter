package vn.com.misa.starter2.ui.synchdata;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.starter2.R;
import vn.com.misa.starter2.databinding.ActivitySynchronizeDataBinding;
import vn.com.misa.starter2.model.dto.User;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.model.entity.PaymentDetail;
import vn.com.misa.starter2.service.APIService;
import vn.com.misa.starter2.ui.collectmoney.PaymentDetailPresenter;
import vn.com.misa.starter2.ui.collectmoney.PaymentPresenter;
import vn.com.misa.starter2.ui.login.LoginActivity;
import vn.com.misa.starter2.ui.order.entities.NumNotiSyn;
import vn.com.misa.starter2.util.GIANGCache;
import vn.com.misa.starter2.util.GIANGConstants;
import vn.com.misa.starter2.util.GIANGUtils;

@RequiresApi(api = Build.VERSION_CODES.M)
public class SynchronizeData extends AppCompatActivity implements SynContracts.View{

    ActivitySynchronizeDataBinding binding;

    private SynPresenter synPresenter;

    private AlertDialog mDialogLoading;

    private PaymentPresenter paymentPresenter;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            // lấy trạng thái điều khiển kết nối
            // khác null tức là có kết nối internet
            if (connectivityManager.getActiveNetwork() != null){
                binding.llNoInternet.setVisibility(View.GONE);
                binding.btnSyn.setVisibility(View.VISIBLE);
            }
            else{
                binding.llNoInternet.setVisibility(View.VISIBLE);
                binding.btnSyn.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_synchronize_data);
        User user = GIANGCache.getInstance().get(LoginActivity.KEY_LOGIN,User.class);
        synPresenter = new SynPresenter(this, user);
        paymentPresenter = new PaymentPresenter(SynchronizeData.this);
        mDialogLoading = GIANGUtils.getInstance().showDialog(SynchronizeData.this, getLayoutInflater());
        addEvents();
    }

    private void addEvents(){
        binding.btnSyn.setOnClickListener(v->{
            try{
                EventBus.getDefault().post(new NumNotiSyn());
                ArrayList<Payment> lsstpayment = paymentPresenter.getAllPaymentForSynch();
                if (lsstpayment.size()>0){
                    for (Payment payment : lsstpayment){
                        synPresenter.handleSyn(payment);
                        paymentPresenter.updateStateSyn(payment.getRefID());
                    }
                    PaymentDetailPresenter paymentDetailPresenter = new PaymentDetailPresenter(getApplicationContext());
                    List<PaymentDetail> data= paymentDetailPresenter.getAllPaymentDetailSynch();
                    if (data.size()>0){
                        for (PaymentDetail paymentDetail : data){
                            synPresenter.handleSynDetai(paymentDetail);
                            paymentDetailPresenter.updateSynchDone(paymentDetail.getPaymentDetailID());
                        }
                    }
                }
                else{
                    Toast.makeText(this, "Không có dữ liệu để đồng bộ!", Toast.LENGTH_SHORT).show();
                }

            }
            catch (Exception ex){
                GIANGUtils.getInstance().handlerLog(ex.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadcast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBroadcastReceiver();
    }

    /**
     * Hàm lắng nghe sự kiện đóng mở wifi
     * @author: giangpb
     * @date: 20/01/2021
     */
    private void registerBroadcast(){
        IntentFilter filter  =new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver,filter);
    }

    /**
     * Hàm huỷ đăng ký lắng nghe sự kiện kết nối mạng
     * @author: giangpb
     * @date: 20/01/2021
     */
    private void unregisterBroadcastReceiver(){
        if(broadcastReceiver!=null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    public void back(View view) {
        try{
            finish();
        }
        catch (Exception ex){
            GIANGUtils.getInstance().handlerException(ex);
        }
    }

    @Override
    public void onStartSync() {
        mDialogLoading.show();
    }

    @Override
    public void onSuccess() {
        GIANGCache.getInstance().put(GIANGConstants.CACHE_COUNT_SYNC, new NumNotiSyn());
        mDialogLoading.dismiss();
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        mDialogLoading.dismiss();
        Toast.makeText(this, "Falise!", Toast.LENGTH_SHORT).show();
    }
}