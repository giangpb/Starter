package vn.com.misa.starter2.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.starter2.MainActivity;
import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.dto.User;
import vn.com.misa.starter2.service.APIService;
import vn.com.misa.starter2.util.GIANGCache;
import vn.com.misa.starter2.util.GIANGUtils;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    private static final String TAG = "LoginActivity";

    public static final String KEY_LOGIN = "LOGIN_GP";

    //
    private LoginPresenter loginPresenter;

    //
    private TextInputEditText etPhone;
    private TextInputEditText etPass;

    //
    private MaterialButton btnLogin;

    //
    private AlertDialog mDialog;

    private BroadcastReceiver internetReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            // lấy trạng thái điều khiển kết nối
            // khác null tức là có kết nối internet
            if (connectivityManager.getActiveNetwork() != null){
                btnLogin.setVisibility(View.VISIBLE);
            }
            else{
                btnLogin.setVisibility(View.GONE);
            }
        }
    };

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
        registerReceiver(internetReceiver,filter);
    }

    /**
     * Hàm huỷ đăng ký lắng nghe sự kiện kết nối mạng
     * @author: giangpb
     * @date: 20/01/2021
     */
    private void unregisterBroadcastReceiver(){
        if(internetReceiver!=null){
            unregisterReceiver(internetReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();

        initDialog();

        //
        addEvents();
    }

    private void initDialog(){
        View alertLayout = getLayoutInflater().inflate(R.layout.view_custom_progress_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        mDialog = alert.create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void addControls(){
        loginPresenter = new LoginPresenter(this);
        etPhone = findViewById(R.id.etPhone);
        etPass = findViewById(R.id.etPassWord);
        btnLogin = findViewById(R.id.btnLogin);

        User user = GIANGCache.getInstance().get(KEY_LOGIN, User.class);
        if (user!=null)
            etPhone.setText(user.getPhone());
    }


    public static class ProgressDialog extends AppCompatDialogFragment{

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.view_custom_progress_dialog, null);
            builder.setView(view);
            builder.create().setCanceledOnTouchOutside(false);
            builder.create().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            return builder.create();
        }

    }

    private void addEvents(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mDialog.show();
                    User user = new User();
                    user.setPhone(Objects.requireNonNull(etPhone.getText()).toString());
                    user.setPassword(Objects.requireNonNull(etPass.getText()).toString());
                    loginPresenter.receiverUserFromView(user);
                }
                catch (Exception ex){
                    GIANGUtils.getInstance().handlerLog(ex.getMessage());
                }
            }
        });
    }

    @Override
    public void onLoginSuccess(User user) {
        mDialog.dismiss();
        GIANGCache.getInstance().put(KEY_LOGIN, user);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFalse() {
        mDialog.dismiss();
        Toast.makeText(this, "Login false !", Toast.LENGTH_SHORT).show();
    }
}