package vn.com.misa.starter2.ui.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.util.GIANGUtils;

/**
 * ‐ @created_by giangpb on 3/17/2021
 */
public class IconAdapter extends RecyclerView.Adapter<IconAdapter.MyHolder> {

    private Context context;

    private List<Icon> mData;


    public IconAdapter(Context context, List<Icon> data){
        this.context = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_icon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.ivIcon.setImageDrawable(GIANGUtils.getInstance().getMyDrawable(context,mData.get(position).getPath()));
    }

    @Override
    public int getItemCount() {
        return mData!=null?mData.size() :0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        FloatingActionButton fab;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
//            ivIcon = itemView.findViewById(R.id.ivIcon);
            fab = itemView.findViewById(R.id.fab);
        }
    }
}
