package vn.com.misa.starter2.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


import vn.com.misa.starter2.MainActivity;
import vn.com.misa.starter2.R;
import vn.com.misa.starter2.ui.login.dto.User;

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

//        etPass.setOnTouchListener(new View.OnTouchListener(){
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int inType = etPass.getInputType(); // backup the input type
//                etPass.setInputType(InputType.TYPE_NULL); // disable soft input
//                etPass.onTouchEvent(event); // call native handler
//                etPass.setInputType(inType); // restore input type
//                return true; // consume touch even
//            }
//        });

        SharedPreferences sharedPreferences = getSharedPreferences(KEY_LOGIN,MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone",null);
        if (phone!=null)
            etPhone.setText(phone);
    }


    public static class ProgressDialog extends AppCompatDialogFragment{

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                    new CountDownTimer(2000,100){
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            mDialog.dismiss();
                        }
                    };
                    User user = new User(etPhone.getText().toString(), etPass.getText().toString());
                    loginPresenter.receiverUserFromView(user);


                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone", etPhone.getText().toString());
        editor.commit();

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