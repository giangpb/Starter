package vn.com.misa.starter2.ui.report.overview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.com.misa.starter2.R;

/**
 * @author GIANG PHAN
 * @date 19/02/2021
 */
public class OverviewReportFragment extends Fragment {
    private static final String TAG = "OverviewReportFragment";

    // khai báo các điều khiển
    private ImageView ivBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview_report, container, false);

        // khởi tạo các điều khiển
        ivBack = view.findViewById(R.id.ivBack);

        addEvents();
        return view;
    }

    private void addEvents(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    getActivity().onBackPressed();
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }
}