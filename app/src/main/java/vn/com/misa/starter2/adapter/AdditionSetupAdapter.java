package vn.com.misa.starter2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Addition;
import vn.com.misa.starter2.model.entity.Unit;
import vn.com.misa.starter2.ui.addition.IAdditionListener;

/**
 * ‐ @created_by giangpb on 2/19/2021
 */
public class AdditionSetupAdapter extends RecyclerView.Adapter<AdditionSetupAdapter.MyHolder> {

    private static final String TAG = "AdditionSetupAdapter";

    private IAdditionListener mAdditionListener;

    private ArrayList<Addition> mData;

    public AdditionSetupAdapter(ArrayList<Addition> mData, IAdditionListener iAdditionListener) {
        this.mData = mData;
        mAdditionListener = iAdditionListener;
    }

    // thêm addition
    public void addAddition(Addition addition){
        mData.add(0, addition);
        notifyDataSetChanged();
    }

    // xoá và cập nhật tại vị trí
    public void removeAddition(Addition addition, int pos){
        mData.remove(addition);
        notifyItemRemoved(pos);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setup_unit, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvUnitName.setText(mData.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvUnitName;
        private ImageView ivDeleteUnit;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvUnitName = itemView.findViewById(R.id.tvUnitName);
            ivDeleteUnit = itemView.findViewById(R.id.ivDeleteUnit);

            ivDeleteUnit.setOnClickListener((view) -> {
                try {
                    mAdditionListener.removeAddition(mData.get(getAdapterPosition()), getAdapterPosition());
                } catch (Exception exx) {
                    Log.d(TAG, "MyHolder: " + exx.getMessage());
                }
            });
        }
    }
}
