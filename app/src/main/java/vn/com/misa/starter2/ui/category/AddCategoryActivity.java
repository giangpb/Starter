package vn.com.misa.starter2.ui.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.misa.starter2.R;

public class AddCategoryActivity extends AppCompatActivity {


    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.rcvListCategory)
    RecyclerView rcvListCategory;
    private List<Icon> data;
    private IconAdapter iconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);
        initCategory();
        initListView();
        addEvents();
    }

    private void initListView(){
        iconAdapter = new IconAdapter(this, data);
        rcvListCategory.setAdapter(iconAdapter);
        rcvListCategory.setHasFixedSize(true);
        rcvListCategory.setLayoutManager(new GridLayoutManager(this,5));
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
    }


}