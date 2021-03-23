package vn.com.misa.starter2.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Category;

/**
 * ‐ @created_by giangpb on 2/19/2021
 */
public class CategorySetupAdapter extends RecyclerView.Adapter<CategorySetupAdapter.MyHolder> {
    private static final String TAG = "CategorySetupAdapter";

    private ICategorySetupListener iCategorySetupListener;

    private Context mContext;
    private ArrayList<Category> mData;

    public CategorySetupAdapter(Context mContext , ICategorySetupListener iCategorySetupListener) {
        this.mContext = mContext;
        this.iCategorySetupListener = iCategorySetupListener;
    }

    public void addData(ArrayList<Category> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setup_category, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.fabImage.setImageDrawable(getMyDrawable(mContext, mData.get(position).getIconPath()));
        Log.d(TAG, "onBindViewHolder: "+mData.get(position).getIconPath());
        holder.tvNameCategory.setText(mData.get(position).getCategoryName());
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
        return mData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private FloatingActionButton fabImage;
        private TextView tvNameCategory;
        private ImageView ivUpdateCategory;
        private ImageView ivDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            fabImage = itemView.findViewById(R.id.fabImageCate);
            tvNameCategory = itemView.findViewById(R.id.tvCategoryName);
            ivUpdateCategory = itemView.findViewById(R.id.ivUpdateCategory);
            ivDelete = itemView.findViewById(R.id.ivDelete);

            ivUpdateCategory.setOnClickListener((v)->{
                try{
                    iCategorySetupListener.onUpdate(mData.get(getAdapterPosition()));
                }
                catch (Exception ex){
                    Log.d(TAG, "MyHolder: "+ex.getMessage());
                }
            });

            ivDelete.setOnClickListener((v)->{
                try{
                    iCategorySetupListener.onDelete(mData.get(getAdapterPosition()));
                    mData.remove(mData.get(getAdapterPosition()));
                    notifyItemRemoved(getAdapterPosition());
                }
                catch (Exception ex){
                    Log.d(TAG, "MyHolder: "+ex.getMessage());
                }
            });
        }
    }

    public interface ICategorySetupListener{
        void onUpdate(Category category);
        void onDelete(Category category);
    }
}
