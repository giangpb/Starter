package vn.com.misa.starter2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.dto.AdditionCategory;

/**
 * ‐ adapter bổ sung danh mục cho item
 * ‐ @created_by giangpb on 2/2/2021
 */
public class AdditionAdapter extends RecyclerView.Adapter<AdditionAdapter.MyViewHolder> {

    private ArrayList<AdditionCategory> mData;

    // constructer
    public AdditionAdapter(){

    }

    public void addItemAddition(ArrayList<AdditionCategory> data){
        if(mData==null)
            mData = new ArrayList<>();
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addition, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AdditionCategory additionCategory = mData.get(position);
        holder.cbAddition.setText(additionCategory.getAdditionDescription());
    }

    @Override
    public int getItemCount() {
        if(mData==null)
            return 0;
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbAddition;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbAddition = itemView.findViewById(R.id.cbAddition);
        }
    }
}
