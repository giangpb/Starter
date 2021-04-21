package vn.com.misa.starter2.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.List;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.ui.setuplistitem.IOnClick;
import vn.com.misa.starter2.util.GIANGUtils;

/**
 * ‐ Adapter hiển thị danh sách
 * ‐ @created_by giangpb on 1/25/2021
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyHolder> {

    private IOnClick mIOnClick;

    private List<Item> mData;
    private Context mContext;
    private static final String TAG = "FoodAdapter";
//
    public FoodAdapter(Context context, List<Item> data, IOnClick iOnClick){
        this.mIOnClick = iOnClick;
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_setup_menu, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Item item = mData.get(position);
        holder.tvFoodName.setText(item.getItemName());
        holder.tvFoodPrice.setText(GIANGUtils.getInstance().convertPriceIntToString(item.getPrice()));
        if(item.getImage()!=null)
            Glide.with(mContext).load(item.getImage()).into(holder.ivFoodImage);
        else
            holder.ivFoodImage.setImageResource(R.drawable.ic_image_empty_70);
    }

    public void removeItem(Item item){
        mData.remove(item);
        notifyItemRemoved(item.getPosition()-1);
    }

    @Override
    public int getItemCount() {
        if(mData==null)
            return 0;
        return mData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView ivFoodImage;

        TextView tvFoodName;
        TextView tvFoodPrice;

        ImageView ivDeleteItem;

        RelativeLayout itemFood;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            ivFoodImage = itemView.findViewById(R.id.ivFoodImage);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
            ivDeleteItem = itemView.findViewById(R.id.ivDeleteItem);

            itemFood= itemView.findViewById(R.id.itemFood);

            // gán sự kiện
            ivDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        mIOnClick.onItemClick(mData.get(getAdapterPosition()));
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });

            itemFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        mIOnClick.onUpdateItem(mData.get(getAdapterPosition()));

                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });
        }
    }
}
