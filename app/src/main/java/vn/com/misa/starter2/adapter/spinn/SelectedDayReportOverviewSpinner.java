package vn.com.misa.starter2.adapter.spinn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.ui.report.items.ItemsReportFragment;
import vn.com.misa.starter2.ui.report.overview.OverviewReportFragment;

/**
 * ‐ @created_by giangpb on 2/23/2021
 */
public class SelectedDayReportOverviewSpinner extends ArrayAdapter<String> {
    // Your sent context
    private Context mContext;
    // Your custom values for the spinner (User)
    private String[] mData;

    public SelectedDayReportOverviewSpinner(@NonNull Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        mData = mContext.getResources().getStringArray(R.array.choose_day);
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

        // custom view
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner_show_date, parent, false);
        TextView tvDay = convertView.findViewById(R.id.tvDay);
        tvDay.setText(mData[position]);
//        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
//        TextView label = (TextView) super.getView(position, convertView, parent);
//        label.setTextColor(Color.BLACK);
//        // Then you can get the current item using the values array (Users array) and the current position
//        // You can NOW reference each method you has created in your bean object (User class)
//        label.setText(mData[position]);
//        return label;
        return convertView;
    }

    // And here is when the "chooser" is popped up
// Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // custom view
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner_date, parent, false);
        TextView tvDay = convertView.findViewById(R.id.tvDay);
        LinearLayout llItem = convertView.findViewById(R.id.llItemSelected);
        tvDay.setText(mData[position]);

        //
//        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
//        label.setTextColor(Color.GRAY);
//        label.setText(mData[position]);
//
        if (position == OverviewReportFragment.posOfItemSpinnerSelected) {
//            textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_spinner_selected));
            llItem.setBackgroundColor(parent.getContext().getResources().getColor(R.color.grey_separate));
        } else {
//            textView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
            llItem.setBackgroundColor(parent.getContext().getResources().getColor(R.color.white));
        }
//        return label;
        return convertView;
    }
}
