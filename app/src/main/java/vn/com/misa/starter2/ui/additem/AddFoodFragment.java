package vn.com.misa.starter2.ui.additem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.CategorySpinnerAdapter;
import vn.com.misa.starter2.adapter.UnitSpinnerAdapter;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.model.entity.Unit;
import vn.com.misa.starter2.presenter.CategoryPresenter;
import vn.com.misa.starter2.ui.setuplistitem.ItemFoodPresenter;

/**
 * - Fragment thêm món ăn
 * - Thêm hình ảnh, tên, giá, đơn vị, nhóm
 * @author GIANG PHAN
 * @date: 22/01/2021
 */
public class AddFoodFragment extends Fragment{


    private static final String TAG = "AddFoodFragment";
    private static final int RESULT_OK = -1;
    private TextInputEditText etFoodPrice;

    private TextInputEditText etFoodName;

    private ImageView ivItemImage;

    private ImageView ivAddUnit;

    private ItemFoodPresenter mItemFoodPresenter;

    private MaterialButton btnAddItemFood;


    // các điều khiển
    private ImageView ivBackToSetupMenu;

    private Spinner spinnerUnit;
    private UnitSpinnerAdapter unitSpinnerAdapter;
    private UnitPresenter unitPresenter;

    //
    private Spinner spinnerCategory;
    private CategorySpinnerAdapter categorySpinnerAdapter;
    private CategoryPresenter categoryPresenter;

    //
    public static final int RESULT_LOAD_IMG = 113; // cờ đánh dấu
    private Bitmap selectedImage; // ảnh chọn từ thư viện

    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);

        // khởi tạo sự kiện
        ivBackToSetupMenu = view.findViewById(R.id.ivArrowBackSetupMenu);
        etFoodName = view.findViewById(R.id.tvFoodName);
        etFoodPrice = view.findViewById(R.id.tvFoodPrice);
        ivAddUnit = view.findViewById(R.id.ivAddUnit);
        ivItemImage = view.findViewById(R.id.ivItemImage);
        btnAddItemFood = view.findViewById(R.id.btnAddItemFood);

        mItemFoodPresenter =new ItemFoodPresenter(getActivity());

        // setup spinner for unit
        unitPresenter = new UnitPresenter(getActivity());
        spinnerUnit = view.findViewById(R.id.spinnerUnit);
        loadUnit();

        // setup spinner for category
        categoryPresenter = new CategoryPresenter(getActivity());
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        categorySpinnerAdapter = new CategorySpinnerAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,categoryPresenter.getListCategory());
        spinnerCategory.setAdapter(categorySpinnerAdapter);


        addEvents();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        unitSpinnerAdapter.addListUnit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }

    /**
     * Load unit
     * @author giangpb
     * @date 27/01/2021
     */
    private void loadUnit(){
        unitSpinnerAdapter = new UnitSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, unitPresenter.getAllUnit());
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


    private void addEvents(){
        ivBackToSetupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.setupMenuFragment,false).build();
//                navController.navigate(R.id.action_addFoodFragment_to_setupMenuFragment,null,navOptions);
                getActivity().onBackPressed();
            }
        });

        ivAddUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // hiển thị dialog cho phép thêm unit ()
                    showDialogAddUnit();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        ivItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chọn ảnh từ thiết bị
                openGallery();
            }
        });


//        etFoodPrice.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//
//                // hiển thị dialog
//                showDialogCalulator();
//                return false;
//            }
//        });

        // sự kiện thêm item
        btnAddItemFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Item item = new Item();
                    long time = System.currentTimeMillis();
                    item.setItemID(time+"");
                    Category category = (Category) spinnerCategory.getSelectedItem();
                    Unit unit = (Unit) spinnerUnit.getSelectedItem();
                    item.setCategoryID(category.getCategoryID());
                    item.setUnitID(unit.getUnitID());
                    if(selectedImage!=null)
                        item.setImage(getBitmapAsByteArray(selectedImage));
                    item.setItemName(etFoodName.getText().toString());
                    item.setPrice(Integer.parseInt(etFoodPrice.getText().toString()));
                    item.setItemCode(etFoodName.getText().toString().toUpperCase());
                    item.setPosition(mItemFoodPresenter.getAllItem().size());
                    mItemFoodPresenter.getItemInforInput(item);

                    NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.setupMenuFragment,false).build();
                    navController.navigate(R.id.action_addFoodFragment_to_setupMenuFragment,null,navOptions);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
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


    private void showDialogAddUnit(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_add_unit, null);
        final EditText etUnitName = alertLayout.findViewById(R.id.etUnitName);
        final MaterialButton btnAddUnit = alertLayout.findViewById(R.id.btnAddUnit);
        final MaterialButton btnCancel = alertLayout.findViewById(R.id.btnCancel);



        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(alertLayout);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        // xử lý sự kiện
        // sự kiện thêm unit
        btnAddUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // lấy thời gian hiện tại và tên unit để cập nhật cho unit
                    long time = System.currentTimeMillis();
                    unitPresenter. addUnit(time+"",etUnitName.getText().toString());
                    dialog.dismiss();
                    // load lại unit
                    loadUnit();
                    // set vị trí chọn hiện tại
                    spinnerUnit.setSelection(unitPresenter.getAllUnit().size()-1);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        // sự kiện huỷ bỏ
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    dialog.dismiss();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }




    /**
     * Hàm hiển thị dialog máy tính
     * @author: giangpb
     * @date: 25/01/2021
     */
    private void showDialogCalulator(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_caculator, null);
//        final EditText etUsername = alertLayout.findViewById(R.id.etClassName);
//        final MaterialButton btnAddClass = alertLayout.findViewById(R.id.btnAddClass);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(alertLayout);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    /**
     * Hàm lắng nghe kết quả nhận intent
     * @param requestCode mã yêu cầu
     * @param resultCode mã kết quả
     * @param data ảnh dạng bitmap
     * @uthor: giangpb
     * @date: 27/01/2021
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            selectedImage = BitmapFactory.decodeStream(imageStream);
            Glide.with(this).load(selectedImage).into(ivItemImage);

        }else {
            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

}