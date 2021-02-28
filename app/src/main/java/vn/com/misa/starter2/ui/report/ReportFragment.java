package vn.com.misa.starter2.ui.report;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vn.com.misa.starter2.R;

/**
 * Lớp báo cáo
 * @author GIANG PHAN
 * @date 17/02/2021
 */
public class ReportFragment extends Fragment {
    private static final String TAG = "ReportFragment";

    // khai báo các điều khiển
    private ImageView ivBack;

    // view
    private LinearLayout llOverviewReport;
    private LinearLayout llItemsReport;

    // navigation
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        // khởi tạo các điều khiển
        ivBack = view.findViewById(R.id.ivBack);
        llOverviewReport = view.findViewById(R.id.llOverviewReport);
        llItemsReport = view.findViewById(R.id.llItemsReport);

        // khởi tạo các sự kiện
        addEvents();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    /**
     * Hàm khởi tạo các sự kiện onlick
     * @author giangpb
     * @date 17/02/2021
     */
    private void addEvents(){
        // quay lại
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

        // doanh thu tổng quan
        llOverviewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    navController.navigate(R.id.action_reportFragment2_to_overviewReportFragment2);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });

        //
        llItemsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    navController.navigate(R.id.action_reportFragment2_to_itemsReportFragment);
                }
                catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }
}