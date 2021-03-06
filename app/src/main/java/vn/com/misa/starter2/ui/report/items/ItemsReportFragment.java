package vn.com.misa.starter2.ui.report.items;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import vn.com.misa.starter2.R;
import vn.com.misa.starter2.adapter.ItemReportAdapter;
import vn.com.misa.starter2.adapter.spinn.SelectDaySpinner;
import vn.com.misa.starter2.adapter.spinn.SelectedDayReportItemsSpinner;
import vn.com.misa.starter2.ui.report.items.dto.ItemReport;

/**
 * @author GIANG PHAN
 * @date 19/02/2021
 */
public class ItemsReportFragment extends Fragment {
    private static final String TAG = "ItemsReportFragment";

    //
    ImageView ivBack;

    // vị trí chọn của spinner mặc định ban đầu
    public static int posOfItemSpinnerSelected = -1;
    private SelectedDayReportItemsSpinner selectDaySpinner;
    private Spinner spSelectDay;

    //
    private TextView tvDaySelected;
    private TextView tvTongSoLuong;
    private TextView tvTongTienDoanhThu;
    private TextView tvTienKM;

    // dinh dang tien
    private DecimalFormat decimalFormat;

    //
    private PieChart pieChart;
    private ItemsReportPresenter itemsReportPresenter;
    //
    private RecyclerView rcvLstItemReport;
    ItemReportAdapter itemReportAdapter;

    // view
    private NestedScrollView nvViewMain;
    private LinearLayout llNoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items_report, container, false);

        nvViewMain = view.findViewById(R.id.nvViewMain);
        llNoData = view.findViewById(R.id.llNoData);

        ivBack = view.findViewById(R.id.ivBack);
        tvTongSoLuong = view.findViewById(R.id.tvTongSoLuong);
        tvDaySelected = view.findViewById(R.id.tvDaySelected);
        tvTongTienDoanhThu = view.findViewById(R.id.tvTongTienDoanhThu);
        tvTienKM = view.findViewById(R.id.tvTienKM);
        //
        decimalFormat = new DecimalFormat("#,###");
        // khởi tạo các điều khiển
        spSelectDay = view.findViewById(R.id.spSelectDay);
        selectDaySpinner = new SelectedDayReportItemsSpinner(getContext(), R.layout.item_selected_spinner_date);
        spSelectDay.setAdapter(selectDaySpinner);
        itemsReportPresenter = new ItemsReportPresenter(getContext());
        rcvLstItemReport = view.findViewById(R.id.rcvLstItemReport);
        itemReportAdapter = new ItemReportAdapter(getContext());
        rcvLstItemReport.setHasFixedSize(true);
        rcvLstItemReport.setAdapter(itemReportAdapter);
        rcvLstItemReport.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        pieChart = (PieChart) view.findViewById(R.id.pieChart);
        // chart
        initChart();



        // khởi tạo các sự kiện
        addEvents();
        return view;
    }

    /** - Hàm khởi tạo chart
     * @author giangpb
     * @date 20/02/2021
     */
    private void initChart(){
        pieChart.setRotationEnabled(true);
        pieChart.setDescription(new Description());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        // set màu trắng nhỏ bên trong
        pieChart.setHoleRadius(50f);
        //
        pieChart.setTransparentCircleRadius(55f);


        //pieChart.setTransparentCircleAlpha(0);
        //pieChart.setCenterText("");
        //pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
    }

    /**
     *  hàm đổ dữ liệu lên piechart lọc theo ngày
     * @param data dữ liệu
     * @author giangpb
     * @date 21/02/2021
     */
    private void addDataSet(ArrayList<ItemReport> data) {
        pieChart.removeAllViews();
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        for(int i=0 ; i<data.size(); i++){
            yEntrys.add(new PieEntry(data.get(i).getItemPrice(),data.get(i).getItemName()));

            // chỉ đổ tối đa 6 loại
            if (i==6)
                break;
        }
        showPieChartData(yEntrys);
    }

    /**
     *
     * @param entries
     * @author giangpb
     * @date 19/02/2021
     */
    private void showPieChartData(List<PieEntry> entries) {
       // tvNoData.setVisibility(View.GONE);
        //nsvContent.setVisibility(View.VISIBLE);

        PieDataSet dataSet = new PieDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#0099fc"));
        colors.add(Color.parseColor("#fc7a7a"));
        colors.add(Color.parseColor("#07d42d"));
        colors.add(Color.parseColor("#fd9369"));
        colors.add(Color.parseColor("#7a89fe"));
        colors.add(Color.parseColor("#facb00"));
        colors.add(Color.parseColor("#af7cfb"));
        colors.add(Color.parseColor("#46d7ec"));
        dataSet.setColors(colors);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value > 5 ? new DecimalFormat("###,###,##0.00").format(value) + "%" : "";
            }
        });

        // set legend
       // pieChart.set
        pieChart.getLegend().setForm(Legend.LegendForm.CIRCLE);
        pieChart.getLegend().setTextSize(14f);
        pieChart.getLegend().setFormSize(10f);
        pieChart.getLegend().setXEntrySpace(24f);
        pieChart.getLegend().setWordWrapEnabled(true);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        //
        //pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); // set vertical alignment for legend
        //pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); // set horizontal alignment for legend
        //pieChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
//
        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateXY(1000, 1000);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    /**
     * Hàm khởi tạo các sự kiện
     * @author giangpb
     * @date 19/02/2021
     */
    private void addEvents(){
        // sự kiện quay lại
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    getActivity().onBackPressed();
                }
                catch (Exception exx){
                    Log.d(TAG, "onClick: "+exx.getMessage());
                }
            }
        });

        //
        spSelectDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{

                    posOfItemSpinnerSelected=position;

                    //
                    String daySelected = null;
                    int tongSoLuong = 0;
                    ArrayList<ItemReport> data = null;

                    // format for querry
                    Instant now = Instant.now();
                    DateTimeFormatter DATE_TIME_FORMATTER_SHOW = DateTimeFormatter.ofPattern("EEEE - dd/MM/yyyy")
                            .withZone(ZoneId.systemDefault());

                    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            .withZone(ZoneId.systemDefault());

                    DateTimeFormatter DATE_TIME_FORMATTER_VN = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                            .withZone(ZoneId.systemDefault());

                    if(position ==0){ // fill theo today
                        daySelected = DATE_TIME_FORMATTER_SHOW.format(now);
                        data = itemsReportPresenter.getData(DATE_TIME_FORMATTER.format(now));
                        tongSoLuong = itemsReportPresenter.countQuantityItem(data);
                        // bind data
                        //addDataSet(DATE_TIME_FORMATTER.format(now)+"%");

                    }
                    if(position ==1){ // fill theo yesterday
                        Instant yesterday = now.minus(1, ChronoUnit.DAYS);

                        daySelected = DATE_TIME_FORMATTER_SHOW.format(yesterday);
                        data = itemsReportPresenter.getData(DATE_TIME_FORMATTER.format(yesterday));
                        tongSoLuong = itemsReportPresenter.countQuantityItem(data);
                        // bind data
                        //addDataSet(DATE_TIME_FORMATTER.format(yesterday)+"%");
                    }
                    if(position ==2){ // fill theo week
                        Instant week = now.minus(7, ChronoUnit.DAYS);

                        daySelected = DATE_TIME_FORMATTER_VN.format(week) +" - "+DATE_TIME_FORMATTER_VN.format(now);
                        data = itemsReportPresenter.getData(DATE_TIME_FORMATTER.format(week),DATE_TIME_FORMATTER.format(now));
                        tongSoLuong = itemsReportPresenter.countQuantityItem(data);
                    }

                    // set thời gian
                    tvDaySelected.setText(daySelected);
                    tvTongSoLuong.setText(tongSoLuong+"");

                    if(data !=null && data.size() >0){ // kiểm tra và hiển thị view
                        nvViewMain.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);

                        tvTongTienDoanhThu.setText(decimalFormat.format(itemsReportPresenter.countPriceItem(data)));
                        tvTienKM.setText(decimalFormat.format(itemsReportPresenter.countPriceItem(data)));
                        itemReportAdapter.addData(data);
                        addDataSet(data);
                    }
                    else{
                        nvViewMain.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);

                        tvTongTienDoanhThu.setText("0");
                        tvTienKM.setText("0");
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