package vn.com.misa.starter2.ui.chooserectaurant;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.RestaurantAdapter;
import vn.com.misa.starter2.datautils.DatabaseHelper;
import vn.com.misa.starter2.model.entity.Restaurant;
import vn.com.misa.starter2.presenter.ISelectRestaurantPresenter;

/**
 * - Fragment chọn loại hình nhà hàng
 * - Chạy lần đầu khi người dùng đăng ký lần đầu
 * @author GIANG PHAN
 * @date 21/01/2021
 */
public class SelectRestaurantFragment extends Fragment implements ISelectRestaurantPresenter {

    // điều hướng
    private NavController navController;

    // show alert progress;
    private AlertDialog dialog;

    private static final String TAG = "SelectRestaurantFragmen";

    // các bảng trong csdl
    public static final String TABLE_UNIT = "Unit";
    public static final String TABLE_CategoryAddition = "InventoryItemCategoryAddition";
    public static final String TABLE_Addition = "InventoryItemAddition";
    public static final String TABLE_Category = "InventoryItemCategory";
    public static final String TABLE_Item = "InventoryItem";
    public static final String TABLE_AdditionMap = "ItemAdditionMap";




    // danh sách danh mục ngà hàng
    private RecyclerView rcvListRestaurant;
    private RestaurantAdapter restaurantAdapter;

    // database
    SQLiteDatabase mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_restaurant, container, false);






        // hiển thị danh sách loại nhà hàng
        rcvListRestaurant = view.findViewById(R.id.rcvListRestaurant);
        restaurantAdapter = new RestaurantAdapter(getActivity(), this);
        rcvListRestaurant.setAdapter(restaurantAdapter);
        rcvListRestaurant.setHasFixedSize(true);
        rcvListRestaurant.setLayoutManager(new GridLayoutManager(getContext(),2));


        //
        // xoá database và tạo mới
        // có thể thêm cái dialog loading...

//        LayoutInflater inflater2 = getLayoutInflater();





        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // khởi tạo navController
        navController = Navigation.findNavController(view);

        View alertLayout = getLayoutInflater().inflate(R.layout.view_custom_progress_dialog, null);
//        final EditText etUsername = alertLayout.findViewById(R.id.etClassName);
//        final MaterialButton btnAddClass = alertLayout.findViewById(R.id.btnAddClass);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        getActivity().deleteDatabase(DatabaseHelper.DATABASE_NAME);
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.processCopy();

        mDatabase = getActivity().openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE,null);


        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());

            // thêm data vào bảng unit
            JSONArray jsonArray = obj.getJSONArray("UnitList");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put("UnitID",object.getString("UnitID"));
                values.put("UnitName", object.getString("UnitName"));
                values.put("InActive", object.getBoolean("Inactive"));
                mDatabase.insert(TABLE_UNIT,null,values);
            }

            // thêm data vào bảng CategoryAddition
            JSONArray jsonArrayCategoryAddition = obj.getJSONArray("InventoryItemCategoryAdditionList");
            for(int i=0; i<jsonArrayCategoryAddition.length(); i++){
                JSONObject object = jsonArrayCategoryAddition.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put("InventoryItemCategoryAdditionID",object.getString("InventoryItemCategoryAdditionID"));
                values.put("InventoryItemCategoryAdditionName", object.getString("InventoryItemCategoryAdditionName"));
                values.put("InActive", object.getBoolean("Inactive"));
                mDatabase.insert(TABLE_CategoryAddition,null,values);
            }


            // thêm data vào bảng TABLE_Addition
            JSONArray jsonArrayAddition = obj.getJSONArray("InventoryItemAdditionList");
            for(int i=0; i<jsonArrayAddition.length(); i++){
                JSONObject object = jsonArrayAddition.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put("InventoryItemAdditionID",object.getString("InventoryItemAdditionID"));
                values.put("Description",object.getString("Description"));
                values.put("InventoryItemCategoryAdditionID",object.getString("InventoryItemCategoryAdditionID"));
                values.put("UnitPrice",object.getDouble("UnitPrice"));
                values.put("InActive",object.getBoolean("InActive"));
                values.put("Position",object.getInt("Position"));
                mDatabase.insert(TABLE_Addition,null,values);
            }


            // thêm data vào bảng Category
            JSONArray jsonArrayCate = obj.getJSONArray("InventoryItemCategoryList");
            for(int i=0; i<jsonArrayCate.length(); i++){
                JSONObject object = jsonArrayCate.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put("InventoryItemCategoryID",object.getString("InventoryItemCategoryID"));
                values.put("InventoryItemCategoryCode", object.getString("ItemCategoryCode"));
                values.put("InventoryItemCategoryName", object.getString("ItemCategoryName"));
                values.put("IconPath", object.getString("IconPath"));
                values.put("SortOrder", object.getInt("SortOrder"));
                values.put("Inactive", object.getBoolean("InActive"));
                values.put("IconType", object.getInt("IconType"));
                values.put("CreateDate", object.getString("CreatedDate"));
                mDatabase.insert(TABLE_Category,null,values);
            }


            // thêm data vào bảng Item
            JSONArray jsonArrayItem = obj.getJSONArray("InventoryItemList");
            for(int i=0; i<jsonArrayItem.length(); i++){
                JSONObject object = jsonArrayItem.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put("InventoryItemID",object.getString("InventoryItemID"));
                values.put("InventoryItemCategoryID", object.getString("InventoryItemCategoryID"));
                values.put("InventoryItemCode", object.getString("InventoryItemCode"));
                values.put("InventoryItemName", object.getString("InventoryItemName"));
                values.put("Price", object.getInt("Price"));
                values.put("UnitID", object.getString("UnitID"));

                Bitmap imageBitmap = getBitmapFromAsset(getContext(),object.getString("ImagePath"));
                values.put("ImagePath", getBitmapAsByteArray(imageBitmap));
                values.put("Position", object.getInt("Position"));
//                values.put("InActive", object.getBoolean("InActive"));
                mDatabase.insert(TABLE_Item,null,values);
            }
//

//
            // thêm data vào bảng AdditionMap
            JSONArray jsonArrayAdditionMap = obj.getJSONArray("ItemAdditionMapList");
            for(int i=0; i<jsonArrayAdditionMap.length(); i++){
                JSONObject object = jsonArrayAdditionMap.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put("ItemAdditionMapID",object.getString("ItemAdditionMapID"));
                values.put("InventoryItemAdditionID",object.getString("ItemAdditionID"));
                values.put("InventoryItemCategoryID",object.getString("ItemCategoryID"));
                values.put("InActive",object.getBoolean("InActive"));
                mDatabase.insert(TABLE_AdditionMap,null,values);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();

    }

    /**
     * Ham chuyen doi anh bitmap thanh mang byte
     * @param bitmapImageSelected - anh chon tu thu vien
     * @return anh mang byte
     * @author: giangpb
     * @date: 26/01/2021
     */
    public static byte[] getBitmapAsByteArray(Bitmap bitmapImageSelected) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmapImageSelected.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Hàm đọc ảnh bitmap từ asset
     * @param context
     * @param filePath
     * @return ảnh bitmap
     * @author: giangpb
     * @date: 26/01/2021
     */
    private Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open("images"+filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }

    /**
     * Hàm đọc json từ asset
     * @return chuỗi json
     * @author: giangpb
     * @date: 25/01/2021
     */
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("data/init_restaurant_data_coffe.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    public void iSelectRestaurant(Restaurant restaurant) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("res",restaurant);
        navController.navigate(R.id.action_selectRestaurantFragment_to_setupMenuFragment, bundle);
        //Toast.makeText(getContext(), ""+restaurant.getRestaurantName(), Toast.LENGTH_SHORT).show();
    }
}