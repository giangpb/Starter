package vn.com.misa.starter2.ui.order;

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

import com.google.android.material.navigation.NavigationView;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.ui.category.CategorySetupActivity;
import vn.com.misa.starter2.ui.finishsetup.FinishSetupFragment;
import vn.com.misa.starter2.ui.login.LoginActivity;
import vn.com.misa.starter2.ui.payment.PaymentActivity;
import vn.com.misa.starter2.ui.report.ReportActivity;
import vn.com.misa.starter2.ui.addition.AdditionSetupActivity;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        sharedPreferences = getSharedPreferences(FinishSetupFragment.SHARE_PRE_FINISH_SETUP, MODE_PRIVATE);
        addControls();

    }

    /**
     * Hàm khởi tạo các sự kiện
     * @author giangpb
     * @date 27/01/2021
     */
    private void addControls(){
        drawerLayout=findViewById(R.id.drawer_layout);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open_draw,R.string.close_draw);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView=findViewById(R.id.nav_view);
        llBaoCao=findViewById(R.id.llBaoCao);
        llLogout=findViewById(R.id.llLogout);

        //
        llDanhSachHoaDon = findViewById(R.id.llDanhSachHoaDon);
        llDanhSachThucDon = findViewById(R.id.llDanhSachThucDon);
        llSoThicPhucVu = findViewById(R.id.llSoThicPhucVu);

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
    private void addEvents(){

        llDanhSachHoaDon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
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
            @SuppressLint("WrongConstant")
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
            @SuppressLint("WrongConstant")
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
            @SuppressLint("WrongConstant")
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

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    finish();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @SuppressLint("WrongConstant")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.mnu_danh_sach_hoa_don:
//                        drawerLayout.closeDrawer(Gravity.START);
//                        Intent intentPayment = new Intent(OrderActivity.this, PaymentActivity.class);
//                        startActivity(intentPayment);
//                        return true;
//                    case R.id.mnuLogout:
//                        Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.clear();
//                        editor.commit();
//                        finish();
//                        return true;
//                    case R.id.mnuBaoCao:
//                        drawerLayout.closeDrawer(Gravity.START);
//                        Intent intentReport = new Intent(OrderActivity.this, ReportActivity.class);
//                        startActivity(intentReport);
//                        return true;
//                    case R.id.mnuDanhSachThucDon:
//                        drawerLayout.closeDrawer(Gravity.START);
//                        Intent intentCate = new Intent(OrderActivity.this, CategorySetupActivity.class);
//                        startActivity(intentCate);
//                        return true;
//                    case R.id.mnuSoThichPhucVu:
//                        Intent intentUnit = new Intent(OrderActivity.this, UnitSetupActivity.class);
//                        startActivity(intentUnit);
//                        drawerLayout.closeDrawer(Gravity.START);
//                        return true;
//                }
//                return false;
//            }
//        });
    }


}