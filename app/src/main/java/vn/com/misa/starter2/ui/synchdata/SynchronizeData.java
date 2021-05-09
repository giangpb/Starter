package vn.com.misa.starter2.ui.synchdata;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.databinding.ActivitySynchronizeDataBinding;
import vn.com.misa.starter2.util.GIANGUtils;

@RequiresApi(api = Build.VERSION_CODES.M)
public class SynchronizeData extends AppCompatActivity {

    ActivitySynchronizeDataBinding binding;

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
}