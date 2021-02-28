package vn.com.misa.starter2.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.ui.order_add.AddOrderFragment;
import vn.com.misa.starter2.ui.order_add.ICategoryListener;

/**
 * ‐ adapter danh mục
 * ‐ @created_by giangpb on 1/28/2021
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {

    private ICategoryListener mICategoryListener;

    private static final String TAG = "CategoryAdapter";

    private ArrayList<Category> mData;

    private Context mContext;

    private TextView tvSelected=null;
    private ImageView ivSelected =null;
    private Category categorySlected = null;

    public CategoryAdapter(Context context, ArrayList<Category> data, ICategoryListener iCategoryListener){
        this.mICategoryListener = iCategoryListener;
        this.mContext = context;
        this.mData = data;
    }

    /**
     * Hàm cập nhật số lượng cho mỗi danh mục
     * @param category danh mục
     * @param position vị trí
     * @author giangpb
     * @date 17/02/2021
     */
    public void updateCategory(Category category, int position){
        mData.set(position, category);
        notifyItemChanged(position);
        categorySlected = mData.get(position);
    }

    /**
     *
     */
    public void resetCategory(){
        for(Category category :mData){
            category.setCount(0);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        // bind dữ liệu và set thuộc tính, đổi màu danh sách category
        holder.tvCategoryName.setText(mData.get(position).getCategoryName());
        Drawable icon = getMyDrawable(mContext,mData.get(position).getIconPath());
        holder.ivCategoryImage.setImageDrawable(icon);
        holder.tvCategoryName.setTextColor(mContext.getResources().getColor(R.color.greyDark200));

        // đầu tiên set màu mặc định ban đầu
        if(position == AddOrderFragment.categoryPositionSelected){
            holder.ivCategoryImage.setImageDrawable(getMyDrawable(mContext,mData.get(AddOrderFragment.categoryPositionSelected).getIconPath()+"_fill"));
            holder.tvCategoryName.setTextColor(mContext.getResources().getColor(R.color.purple_500));
            tvSelected = holder.tvCategoryName;
            ivSelected = holder.ivCategoryImage;
            categorySlected = mData.get(AddOrderFragment.categoryPositionSelected);
        }
        // set số lượng mỗi danh mục
        if(mData.get(position).getCount()>0){
            holder.tvCategoryCount.setVisibility(View.VISIBLE);
            holder.tvCategoryCount.setText(mData.get(position).getCount()+"");
        }
        else{
            holder.tvCategoryCount.setVisibility(View.GONE);
        }
    }

    /**
     * Hàm Lấy icon xml drawable từ 1 đường dẫn
     * @param c màn hình truyền vào
     * @param ImageName đường dẫn
     * @return trả về 1 drawble
     * @author: giangpb
     * @date 28/01/2021
     */
    Drawable getMyDrawable(Context c, String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }

    @Override
    public int getItemCount() {
        if(mData ==null)
            return 0;
        return mData.size();
    }





    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        ImageView ivCategoryImage;

        RelativeLayout rlItemCategory;

        TextView tvCategoryCount;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);

            rlItemCategory = itemView.findViewById(R.id.rlItemCategory);

            tvCategoryCount = itemView.findViewById(R.id.tvCategoryCount);


            // sự kiện
            rlItemCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{

                        Category category = mData.get(getAdapterPosition());
                        // set màu thay đổi
                        mICategoryListener.onClickItem(category);
                        tvCategoryName.setTextColor(mContext.getResources().getColor(R.color.purple_500));
                        ivCategoryImage.setImageDrawable(getMyDrawable(mContext,category.getIconPath()+"_fill"));
//
                        if(category == categorySlected)
                            return;

                        // kiểm tra và set màu mặc định
                        if (tvSelected !=null &&categorySlected !=null &&ivSelected!=null){
                            tvSelected.setTextColor(mContext.getResources().getColor(R.color.greyDark200));
                            ivSelected.setImageDrawable(getMyDrawable(mContext,categorySlected.getIconPath()));
                        }
                        tvSelected = tvCategoryName;
                        ivSelected = ivCategoryImage;
                        categorySlected = category;
                    }
                    catch (Exception ex){
                        ex.getMessage();
                    }
                }
            });
        }
    }
}
