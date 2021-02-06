package vn.com.misa.starter2.ui.order;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import vn.com.misa.starter2.R;

public class OrderActivity extends AppCompatActivity {

    // khai báo draw navigation
    public static DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public static ActionBarDrawerToggle drawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

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
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


}