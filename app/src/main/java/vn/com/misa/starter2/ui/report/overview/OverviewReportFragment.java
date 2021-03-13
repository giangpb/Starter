package vn.com.misa.starter2.ui.report.overview;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.spinn.SelectedDayReportOverviewSpinner;
import vn.com.misa.starter2.ui.report.overview.dto.OverviewHours;

/**
 * @author GIANG PHAN
 * @date 19/02/2021
 */
public class OverviewReportFragment extends Fragment {
    private static final String TAG = "OverviewReportFragment";

    // khai báo các điều khiển
    private ImageView ivBack;
    private TextView tvTongTienThuDuoc;
    private TextView tvTienMat;
    private TextView tvDaySelected;

    // vị trí chọn của spinner mặc định ban đầu
    public static int posOfItemSpinnerSelected = -1;
    private SelectedDayReportOverviewSpinner selectDaySpinner;
    private Spinner spSelectDay;

    //
    private OverviewsPresenter overviewsPresenter;

    //
    private DecimalFormat decimalFormat;

    //bar
    private BarChart barChart;
    private BarData barData;
    private BarDataSet barDataSet;
    private ArrayList barEntries;

    // view
    private LinearLayout llViewMain;
    private LinearLayout llNoData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview_report, container, false);

        // khởi tạo các điều khiển
        llViewMain = view.findViewById(R.id.llViewMain);
        llNoData = view.findViewById(R.id.llNoData);

        ivBack = view.findViewById(R.id.ivBack);
        spSelectDay = view.findViewById(R.id.spSelectDay);
        selectDaySpinner = new SelectedDayReportOverviewSpinner(getContext(), R.layout.item_selected_spinner_date);
        spSelectDay.setAdapter(selectDaySpinner);
        tvTongTienThuDuoc = view.findViewById(R.id.tvTongTienThuDuoc);
        tvTienMat = view.findViewById(R.id.tvTienMat);
        tvDaySelected = view.findViewById(R.id.tvDaySelected);

        overviewsPresenter = new OverviewsPresenter(getContext());

        decimalFormat = new DecimalFormat("#,###");

        // bar
        barChart = view.findViewById(R.id.barChart);


        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);

        barChart.animateXY(1200, 1200);
        barChart.getLegend().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); //only intervals of 1 hour

        //IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawLabels(false);

        // xử lý sự kiện
        addEvents();

        return view;
    }

    private void setEntries(ArrayList<OverviewHours> data){
        // xoá bỏ vew cũ đi, cập nhật lại theo ngày
        barChart.removeAllViews();

        barEntries = new ArrayList();
        for (OverviewHours item :data){
            barEntries.add(new BarEntry((float)item.getHour(), (float)item.getMoney()));
        }
        barDataSet = new BarDataSet(barEntries,"Data set");
        //barDataSet.setBarBorderWidth(10f);
        barDataSet.setHighlightEnabled(false);
        barData = new BarData(barDataSet);
        //barData.setBarWidth(2f);
        barChart.setData(barData);

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        //barDataSet.notifyDataSetChanged();

        //barDataSet.setBarBorderWidth(20f);
        //barDataSet.setValueTextSize(16f);
    }

    /**
     * Hàm xử lý sự kiện onclick
     * @author giangpb
     * @date 10/02/2021
     */
    private void addEvents(){
        // sự kiện quay lại
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

        // sự kiện chọn ngày
        spSelectDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{

                    posOfItemSpinnerSelected = position;

                    //
                    String daySelected =null;
                    int doanhThu = 0;

                    ArrayList<OverviewHours> data = null;
                    // format for querry
                    Instant now = Instant.now();
                    DateTimeFormatter DATE_TIME_FORMATTER_SHOW = DateTimeFormatter.ofPattern("EEEE - dd/MM/yyyy").withZone(ZoneId.systemDefault());
                    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
                    DateTimeFormatter DATE_TIME_FORMATTER_VN = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());
                    if (position ==0){ // fill theo today
                        daySelected = DATE_TIME_FORMATTER_SHOW.format(now);
                        doanhThu = overviewsPresenter.doanhThuTheoNgay(DATE_TIME_FORMATTER.format(now));
                        data = overviewsPresenter.getAllData(DATE_TIME_FORMATTER.format(now));
                        //barChart.notifyDataSetChanged();

                    }
                    if(position ==1){// fill theo yesterday
                        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
                        daySelected = DATE_TIME_FORMATTER_SHOW.format(yesterday);
                        doanhThu = overviewsPresenter.doanhThuTheoNgay(DATE_TIME_FORMATTER.format(yesterday));
                        data = overviewsPresenter.getAllData(DATE_TIME_FORMATTER.format(yesterday));
                    }
                    if (position==2){ // fill theo week
                        Instant week = now.minus(7, ChronoUnit.DAYS);

                        daySelected = DATE_TIME_FORMATTER_VN.format(week) +" - "+DATE_TIME_FORMATTER_VN.format(now);

                        doanhThu = overviewsPresenter.doanhThuTheoNgay(DATE_TIME_FORMATTER.format(week), DATE_TIME_FORMATTER.format(now));
                        data = overviewsPresenter.getAllData(DATE_TIME_FORMATTER.format(week), DATE_TIME_FORMATTER.format(now));

                    }
                    tvDaySelected.setText(daySelected);
                    if(data != null && data.size()>0){
                        llViewMain.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                        // hiển thị ngày
                        tvTienMat.setText(decimalFormat.format(doanhThu));
                        tvTongTienThuDuoc.setText(decimalFormat.format(doanhThu));
                        setEntries(data);
                    }
                    else{
                        llViewMain.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    }
                }
                catch (Exception ex){
                    Log.d(TAG, "onItemSelected: "+ex.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}