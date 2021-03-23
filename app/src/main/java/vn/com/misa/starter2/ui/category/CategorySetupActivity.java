package vn.com.misa.starter2.ui.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.CategorySetupAdapter;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.presenter.CategoryPresenter;
import vn.com.misa.starter2.util.GIANGUtils;

public class CategorySetupActivity extends AppCompatActivity implements CategorySetupAdapter.ICategorySetupListener{
    private static final String TAG = "CategorySetupActivity";

    // khai báo các điều khiển
    private ImageView ivBack;
    //
    private RecyclerView rcvLstCategory;
    CategorySetupAdapter categoryAdapter;
    private CategoryPresenter categoryPresenter;

    @BindView(R.id.rlAddCategory)
    RelativeLayout rlAddCategory;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_setup);
        ButterKnife.bind(this);

        // khởi tao các điều khiển
        addControls();

        // khởi tao các sự kiện
        addEvents();
    }

    private void addControls(){
        ivBack = findViewById(R.id.ivBack);
        rcvLstCategory = findViewById(R.id.rcvLstCategory);
        categoryPresenter = new CategoryPresenter(this);
        categoryAdapter = new CategorySetupAdapter(this , this);
        rcvLstCategory.setHasFixedSize(true);
        rcvLstCategory.setAdapter(categoryAdapter);
        rcvLstCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryAdapter.addData(categoryPresenter.getListCategory());
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

        rlAddCategory.setOnClickListener((v -> {
            Intent intent = new Intent(this, AddCategoryActivity.class);
            startActivity(intent);
        }));
    }

    @Override
    public void onUpdate(Category category) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }

    @Override
    public void onDelete(Category category) {

        // thêm cái popup trước khi xoá
        categoryPresenter.deleteCategory(category.getCategoryID());
    }
}