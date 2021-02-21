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

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.model.entity.Category;
import vn.com.misa.starter2.ui.payment.PaymentActivity;

/**
 * ‐ Danh sách spinner chọn thời gian để hiển thị theo danh sách payment
 * ‐ @created_by giangpb on 2/12/2021
 */
public class SelectDaySpinner extends ArrayAdapter<String> {


    // Your sent context
    private Context mContext;
    // Your custom values for the spinner (User)
    private String[] mData;

    public SelectDaySpinner(@NonNull Context context, int resource, String[] data) {
        super(context, resource);
        this.mContext = context;
        this.mData = data;
    }



    @Override
    public int getCount() {
        return mData.length;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mData[position];
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
        label.setText(mData[position]);

//        if(position== PaymentActivity.posOfItemSpinnerSelected){
////            textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_spinner_selected));
//                label.setTextColor(Color.BLACK);
//        } else {
////            textView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
//            label.setTextColor(Color.WHITE);
//        }

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.GRAY);
        label.setText(mData[position]);

        if(position== PaymentActivity.posOfItemSpinnerSelected){
//            textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_spinner_selected));
            label.setTextColor(Color.BLACK);
        } else {
//            textView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
            label.setTextColor(Color.GRAY);
        }

        return label;
    }

}
