package vn.com.misa.starter2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.datautils.RestaurantData;
import vn.com.misa.starter2.model.entity.Restaurant;
import vn.com.misa.starter2.presenter.ISelectRestaurantPresenter;

/**
 * ‐ adapter danh sách loại nhà hàng phục vụ
 * ‐ @created_by giangpb on 1/21/2021
 * ‐ @modified_by giangpb on 1/21/2021 ‐
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyHolder> {

    private ISelectRestaurantPresenter mISelectRestaurantPresenter;

    private static final String TAG = "RestaurantAdapter";

    private Context mContext;
    private ArrayList<Restaurant>mData;
    private RestaurantData restaurantData;

    public RestaurantAdapter(Context context, ISelectRestaurantPresenter iSelectRestaurantPresenter){
        restaurantData = new RestaurantData(context);
        this.mData = restaurantData.getListRestaurant();
        this.mContext = context;
        this.mISelectRestaurantPresenter = iSelectRestaurantPresenter;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.item_restaurant,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Restaurant restaurant = mData.get(position);
        holder.tvRestaurantName.setText(restaurant.getRestaurantName());
        holder.ivRestaurantImage.setImageResource(restaurant.getResRestaurantImage());
//        Glide.with(mContext).load(restaurant.getResRestaurantImage()).into(holder.ivRestaurantImage);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        MaterialCardView cadItemTypeRestaurant;

        ImageView ivRestaurantImage;
        TextView tvRestaurantName;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            ivRestaurantImage = itemView.findViewById(R.id.ivRestaurantImage);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);

            cadItemTypeRestaurant = itemView.findViewById(R.id.cadItemTypeRestaurant);

            // xử lý sự kiện onClick
            cadItemTypeRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Restaurant restaurant  = mData.get(getAdapterPosition());
                        mISelectRestaurantPresenter.iSelectRestaurant(restaurant);
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });
        }
    }
}
