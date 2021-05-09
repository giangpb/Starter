package vn.com.misa.starter2.ui.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.util.App;
import vn.com.misa.starter2.util.GIANGUtils;

/**
 * ‐ @created_by giangpb on 3/17/2021
 */
public class IconAdapter extends RecyclerView.Adapter<IconAdapter.MyHolder> {

    private static final String TAG = "IconAdapter";

    private IIConListener iiConListener;

    private Context context;

    private List<Icon> mData;

    private ImageView ivSelected= null;


    public IconAdapter(Context context, List<Icon> data, IIConListener iiConListener){
        this.context = context;
        this.mData = data;
        this.iiConListener = iiConListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_icon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.ivIcon.setImageDrawable(GIANGUtils.getInstance().getMyDrawable(context,mData.get(position).getPath()));
        iiConListener.onIconSelected(mData.get(0));
        if (position == AddCategoryActivity.ICON_SELECTED){
            holder.ivIcon.setBackground(App.self().getDrawable(R.drawable.bg_icon_category_checked));
            holder.ivIcon.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
            ivSelected = holder.ivIcon;
        }

    }

    @Override
    public int getItemCount() {
        return mData!=null?mData.size() :0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        //FloatingActionButton fab;
        @SuppressLint("UseCompatLoadingForDrawables")
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            //fab = itemView.findViewById(R.id.fab);

            // events
            ivIcon.setOnClickListener(v->{
                try{
                    if (ivSelected != null){
                        ivSelected.setBackground(App.self().getDrawable(R.drawable.bg_menu_icon_border));
                        ivSelected.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.purple_500)));
                    }
                    ivIcon.setBackground(App.self().getDrawable(R.drawable.bg_icon_category_checked));
                    ivIcon.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                    ivSelected = ivIcon;
                    iiConListener.onIconSelected(mData.get(getAdapterPosition()));
                }
                catch (Exception exception){
                    GIANGUtils.getInstance().handlerLog(exception.getMessage());
                }
            });
        }
    }
}
