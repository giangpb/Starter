package vn.com.misa.starter2.ui.additem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.MainThread;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.CategorySpinnerAdapter;
import vn.com.misa.starter2.adapter.UnitSpinnerAdapter;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.model.entity.Unit;
import vn.com.misa.starter2.presenter.CategoryPresenter;
import vn.com.misa.starter2.ui.setuplistitem.ItemFoodPresenter;
import vn.com.misa.starter2.util.GIANGUtils;

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
        etFoodPrice = view.findViewById(R.id.etFoodPrice);
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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showData(StringBuilder stringBuilder){
        if (stringBuilder.length()>0){
            etFoodPrice.setText(stringBuilder.toString());
        }
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
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.setupMenuFragment,false).build();
                navController.navigate(R.id.action_addFoodFragment_to_setupMenuFragment,null,navOptions);
            }
        });

        etFoodPrice.setOnClickListener(v->showDialogCalculator());

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

        // sự kiện thêm item
        btnAddItemFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Objects.requireNonNull(etFoodName.getText()).toString().isEmpty()){
                        etFoodName.setError(String.format(getString(R.string.errr_input),"Tên món"));
                    }
                    else{
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
                        item.setPosition(mItemFoodPresenter.positionItemInCategory(category.getCategoryID())+1);
                        boolean kq = mItemFoodPresenter.addItem(item);
                        if (kq){
                            NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.setupMenuFragment,false).build();
                            navController.navigate(R.id.action_addFoodFragment_to_setupMenuFragment,null,navOptions);
                        }
                        else{
                            GIANGUtils.getInstance().showMessage(getActivity(),"Thêm lỗi !",0);
                        }
                    }
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
    private void showDialogCalculator(){
        View alertLayout = getLayoutInflater().inflate(R.layout.view_input_money, null);
        ImageView ivCloseDialog = alertLayout.findViewById(R.id.ivCloseDialog);
        ImageButton btnDell = alertLayout.findViewById(R.id.btnDell);
        TextView tvValue = alertLayout.findViewById(R.id.tvValue);
        TextView btnXong = alertLayout.findViewById(R.id.btnXong);
        TextView btnClearAll = alertLayout.findViewById(R.id.btnClearAll);

        TextView num0 = alertLayout.findViewById(R.id.num0);
        TextView num1 = alertLayout.findViewById(R.id.num1);
        TextView num2 = alertLayout.findViewById(R.id.num2);
        TextView num3 = alertLayout.findViewById(R.id.num3);
        TextView num4 = alertLayout.findViewById(R.id.num4);
        TextView num5 = alertLayout.findViewById(R.id.num5);
        TextView num6 = alertLayout.findViewById(R.id.num6);
        TextView num7 = alertLayout.findViewById(R.id.num7);
        TextView num8 = alertLayout.findViewById(R.id.num8);
        TextView num9 = alertLayout.findViewById(R.id.num9);
        TextView num000 = alertLayout.findViewById(R.id.num000);

        StringBuilder stringBuilder = new StringBuilder();

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        alert.setCancelable(false);

        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // sự kiện
        num0.setOnClickListener(v->{
            if (stringBuilder.length()>0){
                stringBuilder.append("0");
                tvValue.setText(stringBuilder.toString());
            }
        });

        num1.setOnClickListener(v->{
            stringBuilder.append("1");
            tvValue.setText(stringBuilder.toString());
        });

        num2.setOnClickListener(v->{
            stringBuilder.append("2");
            tvValue.setText(stringBuilder.toString());
        });

        num3.setOnClickListener(v->{
            stringBuilder.append("3");
            tvValue.setText(stringBuilder.toString());
        });

        num4.setOnClickListener(v->{
            stringBuilder.append("4");
            tvValue.setText(stringBuilder.toString());
        });

        num5.setOnClickListener(v->{
            stringBuilder.append("5");
            tvValue.setText(stringBuilder.toString());
        });

        num6.setOnClickListener(v->{
            stringBuilder.append("6");
            tvValue.setText(stringBuilder.toString());
        });

        num7.setOnClickListener(v->{
            stringBuilder.append("7");
            tvValue.setText(stringBuilder.toString());
        });

        num8.setOnClickListener(v->{
            stringBuilder.append("8");
            tvValue.setText(stringBuilder.toString());
        });

        num9.setOnClickListener(v->{
            stringBuilder.append("9");
            tvValue.setText(stringBuilder.toString());
        });

        btnClearAll.setOnClickListener(v->{
            stringBuilder.delete(0, stringBuilder.length());
            tvValue.setText("0");
        });
        btnDell.setOnClickListener(v->{
            int length = stringBuilder.length();
            if (length>=1){
                stringBuilder.deleteCharAt(length-1);
                tvValue.setText(stringBuilder.toString());
            }
            else{
                tvValue.setText("0");
            }
        });
        // đóng dialog
        ivCloseDialog.setOnClickListener(v -> {
            dialog.dismiss();
        });
        btnXong.setOnClickListener(v->{
            EventBus.getDefault().post(stringBuilder);
            dialog.dismiss();
        });



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