package vn.com.misa.starter2.ui.order;

import androidx.annotation.MainThread;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.dto.User;
import vn.com.misa.starter2.ui.aboutapp.AboutAppActivity;
import vn.com.misa.starter2.ui.category.CategorySetupActivity;
import vn.com.misa.starter2.ui.finishsetup.FinishSetupFragment;
import vn.com.misa.starter2.ui.login.LoginActivity;
import vn.com.misa.starter2.ui.order.entities.NumNotiSyn;
import vn.com.misa.starter2.ui.payment.PaymentActivity;
import vn.com.misa.starter2.ui.report.ReportActivity;
import vn.com.misa.starter2.ui.addition.AdditionSetupActivity;
import vn.com.misa.starter2.ui.synchdata.SynchronizeData;
import vn.com.misa.starter2.util.GIANGCache;
import vn.com.misa.starter2.util.GIANGConstants;
import vn.com.misa.starter2.util.GIANGUtils;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = "OrderActivity";

    // khai báo draw navigation
    public static DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public static ActionBarDrawerToggle drawerToggle;

    private SharedPreferences sharedPreferences;

    // navigation
    private LinearLayout llDanhSachHoaDon;
    private LinearLayout llDanhSachThucDon;
    private LinearLayout llSoThicPhucVu;
    private LinearLayout llBaoCao;
    private LinearLayout llLogout;
    private RelativeLayout rlSynchData;
    private LinearLayout llAboutApp;

    private TextView txtUser;

    private TextView tvnumSynch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        sharedPreferences = getSharedPreferences(FinishSetupFragment.SHARE_PRE_FINISH_SETUP, MODE_PRIVATE);
        addControls();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void upDateCountSynch(NumNotiSyn notiSyn){
        GIANGUtils.getInstance().handlerLog(notiSyn.getCount()+"");
        if (notiSyn.getCount()>0){
            tvnumSynch.setVisibility(View.VISIBLE);
            tvnumSynch.setText(notiSyn.getCount()+"");
        }
        else{
            tvnumSynch.setVisibility(View.GONE);
        }
    }

    /**
     * Hàm khởi tạo các sự kiện
     * @author giangpb
     * @date 27/01/2021
     */
    @SuppressLint("SetTextI18n")
    private void addControls(){
        drawerLayout=findViewById(R.id.drawer_layout);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open_draw,R.string.close_draw);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView=findViewById(R.id.nav_view);
        llBaoCao=findViewById(R.id.llBaoCao);
        llLogout=findViewById(R.id.llLogout);
        llAboutApp=findViewById(R.id.llAboutApp);

        //
        llDanhSachHoaDon = findViewById(R.id.llDanhSachHoaDon);
        llDanhSachThucDon = findViewById(R.id.llDanhSachThucDon);
        llSoThicPhucVu = findViewById(R.id.llSoThicPhucVu);
        rlSynchData = findViewById(R.id.rlSynchData);

        txtUser = findViewById(R.id.txtUser);

        tvnumSynch = findViewById(R.id.tvnumSynch);

        NumNotiSyn numNotiSyn= GIANGCache.getInstance().get(GIANGConstants.CACHE_COUNT_SYNC, NumNotiSyn.class);
        if (numNotiSyn.getCount()>0){
            tvnumSynch.setVisibility(View.VISIBLE);
            tvnumSynch.setText(numNotiSyn.getCount()+"");
        }
        else{
            tvnumSynch.setVisibility(View.GONE);
        }

        // check Quyền
        User user = GIANGCache.getInstance().get(LoginActivity.KEY_LOGIN, User.class);
        if (user.getPermission() == 0){
            llBaoCao.setVisibility(View.GONE);
            llDanhSachThucDon.setVisibility(View.GONE);
            llSoThicPhucVu.setVisibility(View.GONE);
        }
        else if (user.getPermission() == 1){
            llBaoCao.setVisibility(View.VISIBLE);
            llDanhSachThucDon.setVisibility(View.VISIBLE);
            llSoThicPhucVu.setVisibility(View.VISIBLE);
        }
        txtUser.setText(user.getUserName());
        // khởi tạo sự kiện
        addEvents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    /**
     * Hàm khởi tạo sự kiện
     * @author giangpb
     * @date 08/02/2021
     */
    @SuppressLint("WrongConstant")
    private void addEvents(){

        llDanhSachHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    drawerLayout.closeDrawer(Gravity.START);
                    Intent intentPayment = new Intent(OrderActivity.this, PaymentActivity.class);
                    startActivity(intentPayment);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        llDanhSachThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    drawerLayout.closeDrawer(Gravity.START);
                    Intent intentCate = new Intent(OrderActivity.this, CategorySetupActivity.class);
                    startActivity(intentCate);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        llSoThicPhucVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intentUnit = new Intent(OrderActivity.this, AdditionSetupActivity.class);
                    startActivity(intentUnit);
                    drawerLayout.closeDrawer(Gravity.START);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        llBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    drawerLayout.closeDrawer(Gravity.START);
                    Intent intentReport = new Intent(OrderActivity.this, ReportActivity.class);
                    startActivity(intentReport);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
        rlSynchData.setOnClickListener(v->{
            try{
                drawerLayout.closeDrawer(Gravity.START);
                Intent intentSynch= new Intent(OrderActivity.this, SynchronizeData.class);
                startActivity(intentSynch);
            }
            catch (Exception ex){
                Log.d(TAG, "onClick: "+ex.getMessage());
            }
        });

        //about
        llAboutApp.setOnClickListener(view->{
            try {
                drawerLayout.closeDrawer(Gravity.START);
                Intent intentAbout= new Intent(OrderActivity.this, AboutAppActivity.class);
                startActivity(intentAbout);
            }
            catch (Exception exx){
                GIANGUtils.getInstance().handlerLog(exx.getMessage());
            }
        });

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    finish();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

    }


}