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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.starter2.R;
import vn.com.misa.starter2.databinding.ActivitySynchronizeDataBinding;
import vn.com.misa.starter2.model.entity.Payment;
import vn.com.misa.starter2.service.APIService;
import vn.com.misa.starter2.ui.collectmoney.PaymentPresenter;
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
        synPresenter = new SynPresenter(this);
        paymentPresenter = new PaymentPresenter(SynchronizeData.this);
        mDialogLoading = GIANGUtils.getInstance().showDialog(SynchronizeData.this, getLayoutInflater());
        addEvents();
    }

    private void addEvents(){
        binding.btnSyn.setOnClickListener(v->{
            try{

                ////////// TEST ///////////
//                APIService.API_SERVICE.insertUser("","34","",2).enqueue(new Callback<Boolean>() {
//                    @Override
//                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                        if (response.body() != null){
//                            boolean res = response.body();
//                            if(res){
//                                Toast.makeText(SynchronizeData.this, "OK", Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                Toast.makeText(SynchronizeData.this, "FA", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Boolean> call, Throwable t) {
//                        Toast.makeText(SynchronizeData.this, "FA", Toast.LENGTH_SHORT).show();
//                    }
//                });

                ArrayList<Payment> lsstpayment = paymentPresenter.getAllPaymentForSynch();
                if (lsstpayment.size()>0){
                    for (Payment payment : lsstpayment){
                        synPresenter.handleSyn(payment);
                        paymentPresenter.updateStateSyn(payment.getRefID());
                    }
                }
                else{
                    Toast.makeText(this, "Danh sách rỗng", Toast.LENGTH_SHORT).show();
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
        mDialogLoading.dismiss();
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        mDialogLoading.dismiss();
        Toast.makeText(this, "Falise!", Toast.LENGTH_SHORT).show();
    }
}