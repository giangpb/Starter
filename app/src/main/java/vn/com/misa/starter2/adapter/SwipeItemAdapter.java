package vn.com.misa.starter2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Item;
import vn.com.misa.starter2.model.entity.OrderDetail;
import vn.com.misa.starter2.ui.order_add.IFoodListener;

/**
 * ‐ Adapter danh sách chọn món
 * ‐ @created_by giangpb on 1/29/2021
 */
public class SwipeItemAdapter extends RecyclerView.Adapter<SwipeItemAdapter.MySwipeHolder> {
    private Context mContext;
    private ArrayList<Item> mData;

    private DecimalFormat decimalFormat;


    private IFoodListener mIFoodListener;



    private static final String TAG = "SwipeItemAdapter";


    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public SwipeItemAdapter(Context context, IFoodListener iFoodListener){
        this.mContext =context;
        this.mIFoodListener = iFoodListener;
        decimalFormat = new DecimalFormat("#,###");
    }

    /**
     * Hàm xoá hết dữ liệu trước khi thêm và thông báo cập nhật
     * @author giangpb
     * @date 27/01/2021
     */
    public void clearAllItem(){
        if (mData==null)
            mData = new ArrayList<>();
        mData.clear();
        notifyDataSetChanged();
    }


    /**
     * Hàm thêm danh sách data
     * @param data danh sách data
     * @author giangpb
     * @date 27/01/2021
     */
    public void addListItem(ArrayList<Item> data){
        if(mData==null)
            mData = new ArrayList<>();
        this.mData = data;
        notifyDataSetChanged();
    }

    /**
     * Hàm thêm từng item sản phẩm và thông báo cập nhật tại vị trí của sản phẩm
     * @param item sản phẩm
     * @author giangpb
     * @date 27/01/2021
     */
    public void addItem(Item item){
        if(mData ==null)
            mData = new ArrayList<>();
        mData.add(item);
        notifyItemChanged(item.getPosition()-1);
    }


    @NonNull
    @Override
    public MySwipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_food_category,parent, false);
        return new MySwipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySwipeHolder holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout,mData.get(position).getItemID());
        viewBinderHelper.closeLayout(mData.get(position).getItemID());

        // đặt mặc định cho số lượng đếm
        holder.tvCount.setText("");

        Item item = mData.get(position);

        holder.tvItemName.setText(item.getItemName());
        holder.tvItemPrice.setText(decimalFormat.format(item.getPrice()));
        Glide.with(mContext).load(item.getImage()).into(holder.ivFoodImage);

        if(item.getQuantity()>0)
            holder.tvCount.setText(item.getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        if(mData == null)
            return 0;
        return mData.size();
    }

    public class MySwipeHolder extends RecyclerView.ViewHolder {
        private RelativeLayout btnEdit;
        private RelativeLayout btnUnit;

        private TextView tvItemName;

        private TextView tvItemPrice;

        private ImageView ivFoodImage;

        private SwipeRevealLayout swipeRevealLayout;

        private RelativeLayout itemFood;

        private TextView tvCount;

        public MySwipeHolder(@NonNull View itemView) {
            super(itemView);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnUnit = itemView.findViewById(R.id.btnUnit);
            ivFoodImage = itemView.findViewById(R.id.ivFoodImage);
            tvItemName= itemView.findViewById(R.id.tvFoodName);
            tvItemPrice = itemView.findViewById(R.id.tvFoodPrice);

            tvCount = itemView.findViewById(R.id.tvFoodCount);

            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);

            itemFood = itemView.findViewById(R.id.itemFood);


            // xử lý sự kiện click
            itemFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
//                        int count = mData.get(getAdapterPosition()).getQuantity();
//                        count++;
//                        mData.get(getAdapterPosition()).setQuantity(count);
                        mIFoodListener.onClickItemSwipe(mData.get(getAdapterPosition()));
//                        notifyItemChanged(getAdapterPosition());
                    }
                    catch (Exception ex){
                        Log.d(TAG, "onClick: "+ex.getMessage());
                    }
                }
            });
        }
    }
}
