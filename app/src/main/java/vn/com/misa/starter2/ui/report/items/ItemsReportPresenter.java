package vn.com.misa.starter2.ui.report.items;

import android.content.Context;

import java.util.ArrayList;

import vn.com.misa.starter2.ui.report.items.dto.ItemReport;
import vn.com.misa.starter2.ui.setuplistitem.ItemFoodPresenter;

/**
 * ‐ @created_by giangpb on 2/19/2021
 */
public class ItemsReportPresenter {
    private ItemsReportModel itemsReportModel;

    public ItemsReportPresenter(Context context){
        itemsReportModel = new ItemsReportModel(context);
    }

    /**
     * Hafm
     * @param date
     * @return
     */
    public ArrayList<ItemReport> getData(String date){
        return itemsReportModel.getData(date);
    }

    /**
     * Hàm tính tổng số lượng sản phẩm
     * @param data danh sách sản phẩm
     * @return số lượng
     * @author giangpb
     * @date 20/02/2021
     */
    public int countQuantityItem(ArrayList<ItemReport> data){
        return itemsReportModel.countQuantityItem(data);
    }

    /**
     * Hàm tính tổng tiền của báo cáo
     * @param data danh sách báo cáo theo thời gian
     * @return tiền
     * @author giangpb
     * @date 20/02/2021
     */
    public int countPriceItem(ArrayList<ItemReport> data){
        return itemsReportModel.countPriceItem(data);
    }
}
