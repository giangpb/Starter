package vn.com.misa.starter2.ui.updateitem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.CategorySpinnerAdapter;
import vn.com.misa.starter2.adapter.UnitSpinnerAdapter;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.model.entity.Unit;
import vn.com.misa.starter2.presenter.CategoryPresenter;
import vn.com.misa.starter2.ui.additem.UnitPresenter;
import vn.com.misa.starter2.ui.setuplistitem.ItemFoodPresenter;

public class UpdateItemActivity extends AppCompatActivity {
    private static final String TAG = "UpdateItemActivity";

    private ImageView ivArrowBackSetupMenu;

    private TextInputEditText etFoodName;
    private TextInputEditText etFoodPrice;
    private ImageView ivItemImage;
    private MaterialButton btnUpdateItem;
    private ImageView ivSelectUnit;

    // dropdown unit
    private Spinner spinnerUnit;
    private UnitSpinnerAdapter unitSpinnerAdapter;
    private UnitPresenter unitPresenter;

    // dropdown category
    private Spinner spinnerCategory;
    private CategorySpinnerAdapter categorySpinnerAdapter;
    private CategoryPresenter categoryPresenter;

    // item
    ItemFoodPresenter foodPresenter;

    //
    public static final int RESULT_LOAD_IMG = 113; // cờ đánh dấu
    private Bitmap selectedImage; // ảnh chọn từ thư viện



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        addControls();
        addEvents();
    }

    private void addControls(){

        foodPresenter = new ItemFoodPresenter(getApplicationContext());

        Intent intent = getIntent();
        String itemName = intent.getStringExtra("name");
        int price = intent.getIntExtra("price",0);
        String unitID = intent.getStringExtra("unit");
        String categoryID = intent.getStringExtra("category");
        byte[] image = intent.getByteArrayExtra("image");


        ivArrowBackSetupMenu = findViewById(R.id.ivArrowBackSetupMenu);
        etFoodName = findViewById(R.id.tvFoodName);
        etFoodPrice = findViewById(R.id.tvFoodPrice);
        ivItemImage = findViewById(R.id.ivItemImage);
        btnUpdateItem = findViewById(R.id.btnUpdateItem);
        ivSelectUnit = findViewById(R.id.ivSelectUnit);

        // khởi tạo và thiết lập dropdown unit
        spinnerUnit = findViewById(R.id.spinnerUnit);
        unitPresenter = new UnitPresenter(getApplicationContext());
        loadUnit();

        // chọn đơn vị
        spinnerUnit.setSelection(unitSpinnerAdapter.getItemPosition(unitID));

        // khởi tạo và thiết lập dropdown danh mục
        spinnerCategory = findViewById(R.id.spinnerCategory);
        categoryPresenter = new CategoryPresenter(getApplicationContext());
        categorySpinnerAdapter = new CategorySpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,categoryPresenter.getListCategory());
        spinnerCategory.setAdapter(categorySpinnerAdapter);

        // chọn danh mục
        spinnerCategory.setSelection(categorySpinnerAdapter.getItemPosition(categoryID));


        // gán thông tin
        etFoodName.setText(itemName);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        etFoodPrice.setText(decimalFormat.format(price)+"");

        // kiểm tra xem ảnh ban đầu
        if(image!=null)
            Glide.with(getApplicationContext()).load(image).into(ivItemImage);
        else
            ivItemImage.setImageResource(R.drawable.ic_picture_holder_108);
    }

    /**
     * Hàm chuyển chuỗi thành số
     * VD: 11,000 - 11000
     * @param s chuỗi
     * @return số
     * @author: giangpb
     * @date 27/01/2021
     */
    private int convertStringToInt(String s){
        String s2[] = s.split("\\.");
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<s2.length; i++){
            builder.append(s2[i]);
        }
        return Integer.parseInt(builder.toString());
    }

    /**
     * Hàm khởi tạo sự kiện onClick
     * @author giangpb
     * @date 27/01/2021
     */
    private void addEvents(){

        ivItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mở thư viện chọn ảnh
                openGallery();
            }
        });

        // xử lý sự kiện đóng màn hình
        ivArrowBackSetupMenu.setOnClickListener(new View.OnClickListener() {
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

        btnUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Item item = new Item();
                    item.setItemID(getIntent().getStringExtra("id"));
                    item.setPrice(convertStringToInt(etFoodPrice.getText().toString()));
                    item.setItemName(etFoodName.getText().toString());
                    Unit unit = (Unit) spinnerUnit.getSelectedItem();
                    Category category = (Category) spinnerCategory.getSelectedItem();
                    item.setUnitID(unit.getUnitID());
                    item.setCategoryID(category.getCategoryID());

                    // kiểm tra xem đã chọn ảnh chưa
                    if (selectedImage!= null)
                        item.setImage(getBitmapAsByteArray(selectedImage));

                    // xử lý sửa
                    if(foodPresenter.updateItem(item)){
                        finish();
                    }
                    else
                        Toast.makeText(UpdateItemActivity.this, "Update failed !!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        // hành động tạo mới unit
        ivSelectUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(UpdateItemActivity.this, "Đang sửa", Toast.LENGTH_SHORT).show();
//                    showDialogAddUnit();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }


    /**
     * Hàm hiển thị dialog thêm unit
     * @author giangpb
     * @date 27/01/2021
     *
     */
    private void showDialogAddUnit(){
        View alertLayout = getLayoutInflater().inflate(R.layout.dialog_add_unit, null);
        final EditText etUnitName = alertLayout.findViewById(R.id.etUnitName);
        final MaterialButton btnAddUnit = alertLayout.findViewById(R.id.btnAddUnit);
        final MaterialButton btnCancel = alertLayout.findViewById(R.id.btnCancel);


        AlertDialog.Builder alert = new AlertDialog.Builder(getApplication());
        alert.setView(alertLayout);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
//
//        // xử lý sự kiện
//        // sự kiện thêm unit
//        btnAddUnit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try{
//                    // lấy thời gian hiện tại và tên unit để cập nhật cho unit
//                    long time = System.currentTimeMillis();
//                    unitPresenter. addUnit(time+"",etUnitName.getText().toString());
//                    dialog.dismiss();
//                    // load lại unit
//                    loadUnit();
//                    // set vị trí chọn hiện tại
//                    spinnerUnit.setSelection(unitPresenter.getAllUnit().size()-1);
//                }
//                catch (Exception ex){
//                    Log.d(TAG, "onClick: "+ex.getMessage());
//                }
//            }
//        });
//
//        // sự kiện huỷ bỏ
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try{
//                    dialog.dismiss();
//                }
//                catch (Exception ex){
//                    Log.d(TAG, "onClick: "+ex.getMessage());
//                }
//            }
//        });
    }

    /**
     * Hàm load unint
     * @author giangpb
     * @date 27/01/2021
     */
    private void loadUnit(){
        unitSpinnerAdapter = new UnitSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, unitPresenter.getAllUnit());
        spinnerUnit.setAdapter(unitSpinnerAdapter);
    }


    /**
     * Hàm mở ảnh từ thư viện
     * @author: giangpb
     * @date 27/01/0221
     */
    private void openGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }


    /**
     * Nhận kết quả intent trả về
     * @param requestCode mã yêu cầu
     * @param resultCode mà kết quả
     * @param data dữ liệu -> Ảnh
     * @author giangpb
     * @date 27/01/2021
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            selectedImage = BitmapFactory.decodeStream(imageStream);
            Glide.with(this).load(selectedImage).into(ivItemImage);

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Ham chuyen doi anh bitmap thanh mang byte
     * @param bitmapImageSelected - anh chon tu thu vien
     * @return anh mang byte
     */
    public static byte[] getBitmapAsByteArray(Bitmap bitmapImageSelected) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmapImageSelected.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        return outputStream.toByteArray();
    }
}