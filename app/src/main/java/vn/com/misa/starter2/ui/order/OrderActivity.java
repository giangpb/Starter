package vn.com.misa.starter2.ui.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Order;
import vn.com.misa.starter2.ui.finishsetup.FinishSetupFragment;
import vn.com.misa.starter2.ui.login.LoginActivity;
import vn.com.misa.starter2.ui.payment.PaymentActivity;

public class OrderActivity extends AppCompatActivity {

    // khai báo draw navigation
    public static DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public static ActionBarDrawerToggle drawerToggle;

    private SharedPreferences sharedPreferences;



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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnu_danh_sach_hoa_don:
                        drawerLayout.closeDrawer(Gravity.START);
                        Intent intentPayment = new Intent(OrderActivity.this, PaymentActivity.class);
                        startActivity(intentPayment);
                        return true;
                    case R.id.mnuLogout:
                        Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
                        startActivity(intent);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();
                        return true;
                }
                return false;
            }
        });
    }


}