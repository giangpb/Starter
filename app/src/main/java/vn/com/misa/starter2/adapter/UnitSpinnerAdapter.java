package vn.com.misa.starter2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.com.misa.starter2.model.entity.Unit;

/**
 * ‐ Hiển thị lên spinner danh sách unit
 * ‐ @created_by giangpb on 1/26/2021
 */
public class UnitSpinnerAdapter extends ArrayAdapter<Unit> {


    // Your sent context
    private Context mContext;
    // Your custom values for the spinner (User)
    private ArrayList<Unit> mData;

    public UnitSpinnerAdapter(@NonNull Context context, int resource, ArrayList<Unit> data) {
        super(context, resource);
        this.mContext = context;
        this.mData = data;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Nullable
    @Override
    public Unit getItem(int position) {
        return mData.get(position);
    }

    /**
     * ham lay vi tri
     * @param unitID
     * @return vi tri cua lop chon
     * @author: giangpb
     * @date: 26/01/2021
     */
    public int getItemPosition(String unitID){
        int pos=-1;
        for(int i=0; i<mData.size(); i++){
            if(mData.get(i).getUnitID().equals(unitID)){
                pos = i;
                break;
            }
        }
        return pos;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(mData.get(position).getUnitName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(mData.get(position).getUnitName());

        return label;
    }

}
