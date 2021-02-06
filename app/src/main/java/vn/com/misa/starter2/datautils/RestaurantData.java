package vn.com.misa.starter2.datautils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Restaurant;

/**
 * ‐ Tạo danh sách nhà hàng
 * ‐ @created_by giangpb on 1/21/2021
 */
public class RestaurantData {
    private Context mContext;

    private static final String TAG = "RestaurantData";

    private ArrayList<Restaurant> mDataRestaurant;

    public RestaurantData(Context context){
        this.mContext = context;

        // khởi tạo danh sách nhà hàng
        mDataRestaurant = new ArrayList<>();
        Restaurant restaurantCafe = new Restaurant(1,"Quán Cafe", R.drawable.ic_res_type_cafe);
        Restaurant restaurantBunPho = new Restaurant(3,"Bún phở miến", R.drawable.ic_res_type_bunmien);
        Restaurant restaurantComVanPhong = new Restaurant(4,"Cơm văn phòng", R.drawable.ic_res_type_comvanphong);
        Restaurant restaurantTraSua = new Restaurant(2,"Quán Trà sữa", R.drawable.ic_res_type_trasua);
        Restaurant restaurantCheKem = new Restaurant(5,"Chè kem/ăn vặt", R.drawable.ic_res_type_chekemanvat);
        Restaurant restaurantBanhMy = new Restaurant(6,"Quán bánh mì", R.drawable.ic_res_type_banhmi);

        mDataRestaurant.add(restaurantCafe);
        mDataRestaurant.add(restaurantTraSua);
        mDataRestaurant.add(restaurantBunPho);
        mDataRestaurant.add(restaurantComVanPhong);
        mDataRestaurant.add(restaurantCheKem);
        mDataRestaurant.add(restaurantBanhMy);
    }
    public  ArrayList<Restaurant> getListRestaurant()
    {
        return mDataRestaurant;
    }

    private Drawable getImageFromAsset(String fileName){
        try{
            // get input stream
            InputStream ims = mContext.getAssets().open("images/"+fileName);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            return d;
        }
        catch (Exception ex){
            Log.d(TAG, "loadImageFromAsset: "+ex.getMessage());
        }
        return null;
    }
}
