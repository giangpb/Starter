package vn.com.misa.starter2.ui.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.presenter.CategoryPresenter;
import vn.com.misa.starter2.util.GIANGUtils;
import vn.com.misa.starter2.util.ItemDecorationAlbumColumns;

public class AddCategoryActivity extends AppCompatActivity implements IIConListener {


    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.rcvListCategory)
    RecyclerView rcvListCategory;

    private List<Icon> data;
    private IconAdapter iconAdapter;

    @BindView(R.id.btnSave)
    MaterialButton btnSave;

    @BindView(R.id.etCategoryName)
    TextInputEditText etCategoryName;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private Icon mIcon = null;

    //
    private CategoryPresenter categoryPresenter;

    public static int ICON_SELECTED = -1;

    private Category category = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);
        initCategory();
        category = (Category) getIntent().getSerializableExtra("category");
        if (category ==null){
            ICON_SELECTED = 0;
            tvTitle.setText(getString(R.string.add_category_group));
        }
        else {
            ICON_SELECTED = getIconSelected(category, data);
            tvTitle.setText(getString(R.string.update_category_group));
            etCategoryName.setText(category.getCategoryName());
        }
        categoryPresenter = new CategoryPresenter(this);
        initListView();
        addEvents();
    }

    private int getIconSelected(Category category, List<Icon> lstIcon){
        for(int i=0; i<lstIcon.size(); i++){
            Icon icon  =lstIcon.get(i);
            if (icon.getPath().equals(category.getIconPath())){
                return i;
            }
        }
        return 0;
    }

    private void initListView(){
        iconAdapter = new IconAdapter(this, data, this);
        rcvListCategory.setAdapter(iconAdapter);
        rcvListCategory.setHasFixedSize(true);
        rcvListCategory.setLayoutManager(new GridLayoutManager(AddCategoryActivity.this,5));
        rcvListCategory.addItemDecoration(new ItemDecorationAlbumColumns(
                getResources().getDimensionPixelSize(R.dimen.photos_list_spacing),
                getResources().getInteger(R.integer.photo_list_preview_columns)));
    }

    private void initCategory(){
        data  = new ArrayList<>();
        Icon iconp = new Icon(1, "ic_tra_sua");
        Icon icon2 = new Icon(2, "ic_do_uong_coc");
        Icon icon3 = new Icon(3, "ic_do_uong");
        Icon icon4 = new Icon(4, "ic_coffee");
        Icon icon5 = new Icon(5, "ic_do_dong_chai");
        Icon icon6 = new Icon(6, "ic_banh_ngot");
        Icon icon7 = new Icon(7, "ic_bit_tet");
        Icon icon8 = new Icon(8, "ic_kem");
        Icon icon9 = new Icon(9, "ic_beer");
        Icon icon10 = new Icon(10, "ic_pho_bun");
        Icon icon11 = new Icon(11, "ic_pizza");
        Icon icon12 = new Icon(12, "ic_banh_mi");
        Icon icon13 = new Icon(13, "ic_com_van_phong");
        Icon icon14 = new Icon(14, "ic_banh_keo");
        data.add(iconp);
        data.add(icon2);
        data.add(icon3);
        data.add(icon4);
        data.add(icon5);
        data.add(icon6);
        data.add(icon7);
        data.add(icon8);
        data.add(icon9);
        data.add(icon10);
        data.add(icon11);
        data.add(icon12);
        data.add(icon13);
        data.add(icon14);
    }
    private void addEvents(){
        ivBack.setOnClickListener((view)->{
            onBackPressed();
        });

        btnSave.setOnClickListener((v)->{
            try{
                String categoryName = etCategoryName.getText().toString();
                if (categoryName.isEmpty()){
                    etCategoryName.setError(String.format(getString(R.string.errr_input),"Tên nhóm"));
//                    GIANGUtils.getInstance().showMessage(this,"Tên nhóm không được để trống !", 1);
                }
                else {
                    if(category != null){
                        // xử lý cập nhật
                        category.setCategoryName(categoryName);
                        category.setIconPath(mIcon.getPath());
                        category.setIconType(mIcon.getType());
                        boolean kq = categoryPresenter.updateCategory(category);
                        if (kq){
                            finish();
                        }
                        else {
                            GIANGUtils.getInstance().showMessage(this,"Lỗi cập nhật danh mục",0);
                        }
                    }
                    else{
                        // xử lý lưu danh mục xuống db
                        category = new Category();
                        long time = System.currentTimeMillis();
                        category.setCategoryID("ca"+time);
                        category.setCategoryName(categoryName);
                        category.setIconPath(mIcon.getPath());
                        category.setIconType(mIcon.getType());
                        category.setSortOrder(categoryPresenter.getListCategory().size());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
                        String currentDateAndTime = sdf.format(new Date());
                        category.setCreatedDate(currentDateAndTime);
                        boolean kq = categoryPresenter.addCategory(category);
                        if (kq){
                            finish();
                        }
                        else {
                            GIANGUtils.getInstance().showMessage(this,"Lỗi thêm danh mục",0);
                        }
                    }
                }
            }
            catch (Exception exx){
                exx.printStackTrace();
            }
        });
    }


    @Override
    public void onIconSelected(Icon icon) {
        mIcon = icon;
    }
}