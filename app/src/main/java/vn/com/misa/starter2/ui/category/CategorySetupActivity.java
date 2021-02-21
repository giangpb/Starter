package vn.com.misa.starter2.ui.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.CategoryAdapter;
import vn.com.misa.starter2.adapter.CategorySetupAdapter;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.presenter.CategoryPresenter;
import vn.com.misa.starter2.ui.order_add.ICategoryListener;

public class CategorySetupActivity extends AppCompatActivity{
    private static final String TAG = "CategorySetupActivity";

    // khai báo các điều khiển
    private ImageView ivBack;
    //
    private RecyclerView rcvLstCategory;
    CategorySetupAdapter categoryAdapter;
    private CategoryPresenter categoryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_setup);

        // khởi tao các điều khiển
        addControls();

        // khởi tao các sự kiện
        addEvents();
    }

    private void addControls(){
        ivBack = findViewById(R.id.ivBack);
        rcvLstCategory = findViewById(R.id.rcvLstCategory);
        categoryPresenter = new CategoryPresenter(this);
        categoryAdapter = new CategorySetupAdapter(this, categoryPresenter.getListCategory());
        rcvLstCategory.setHasFixedSize(true);
        rcvLstCategory.setAdapter(categoryAdapter);
        rcvLstCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
    }

    private void addEvents(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    finish();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }

}