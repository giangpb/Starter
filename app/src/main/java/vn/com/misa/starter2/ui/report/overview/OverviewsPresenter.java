package vn.com.misa.starter2.ui.report.overview;

import android.content.Context;

import java.util.ArrayList;

import vn.com.misa.starter2.ui.report.overview.dto.OverviewHours;

/**
 * ‐ @created_by giangpb on 2/23/2021
 */
public class OverviewsPresenter {

    private OverviewsModel overviewsModel;

    public OverviewsPresenter(Context context){
        overviewsModel = new OverviewsModel(context);
    }

    /**
     * Hàm thống kê tiền theo ngày
     * @param ngay ngày
     * @return tiền
     * @author giangpb
     * @date 23/02/2021
     */
    public int doanhThuTheoNgay(String ngay){
        return overviewsModel.doanhThuTheoNgay(ngay);
    }

    /**
     * Hàm thống kê doanh thu trung bình theo giờ và ngày
     * @param date ngày
     * @return danh sách để đổ lên bar chart
     * @author giangpb
     * @date 22/02/2021
     */
    public ArrayList<OverviewHours> getAllData(String date){
        return overviewsModel.getAllData(date);
    }

    /**
     * Hàm thống kê doanh thu trung bình theo giờ và ngày
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @return danh sách để đổ lên bar chart
     * @author giangpb
     * @date 28/02/2021
     */
    public ArrayList<OverviewHours> getAllData(String startDate, String endDate){
        return overviewsModel.getAllData(startDate, endDate);
    }

    /**
     * Hàm thống kê tiền theo ngày
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @return tiền
     * @author giangpb
     * @date 28/02/2021
     */
    public int doanhThuTheoNgay(String startDate, String endDate){
        return overviewsModel.doanhThuTheoNgay(startDate, endDate);
    }
}
