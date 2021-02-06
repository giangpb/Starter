package vn.com.misa.starter2.ui.setuplistitem;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.starter2.model.entity.OrderDetail;
import vn.com.misa.starter2.ui.additem.IAddItemModel;
import vn.com.misa.starter2.ui.additem.IAddItemPresenter;
import vn.com.misa.starter2.ui.setuplistitem.ItemFoodModel;
import vn.com.misa.starter2.model.entity.Item;

/**
 * ‐ Item presenter nhận dữ liệu từ model
 * ‐ @created_by giangpb on 1/26/2021
 */
public class ItemFoodPresenter implements IAddItemModel {
    private ItemFoodModel itemFoodModel;


    private Context mContext;

    public ItemFoodPresenter (Context context){
        this.mContext = context;
        itemFoodModel = new ItemFoodModel(context, this);
    }

    /**
     * Hàm nhận danh sách item từ model
     * @param categoryID mã danh mục
     * @return danh sách item theo mã danh mục
     */
    public ArrayList<Item> getListItemFood(String categoryID){
        return itemFoodModel.getAllListItem(categoryID);
    }

    /**
     * Hàm trả về toàn bộ danh sách item
     * @return danh sách item
     */
    public ArrayList<Item> getAllItem(){
        return itemFoodModel.getAllListItem();
    }

    /**
     * Hàm nhận dữ liệu nhập từ view
     * @param item các sản phẩm
     * @author giangpb
     * @date 27/01/2021
     */
    public void getItemInforInput(Item item){
        itemFoodModel.addItemFodd(item);
    }


    /**
     * Hàm cập nhật item
     * @param item item
     * @return kết quả cập nhật
     * @author giangpb
     * @date 27/01/2021
     */
    public boolean updateItem(Item item){
        return itemFoodModel.updateItemFood(item);
    }

    /**
     * Hàm nhận thông tin xoá từ model cập nhật tới view
     * @param categoryID mã danh mục
     * @return trả về đúng hoặc sai
     * @author: giangpb
     * @date: 26/01/2021
     */
    public boolean deleteAllItem(String categoryID){
        return itemFoodModel.deleteAllItem(categoryID);
    }

    public boolean deleteItem(String iemID){
        return itemFoodModel.deleteItem(iemID);
    }

    @Override
    public void addItemSuccess() {
    }

    @Override
    public void addItemFalse() {
    }

    /**
     * Hàm tính tiền hoá đơn chi tiết
     * @param data danh sách thực đơn chọn
     * @return tổng tiền
     * @author giangpb
     * @date 5/2/2021
     */
    public int tinhTienHoaDon(ArrayList<Item> data){
        return itemFoodModel.tinhTienHoaDon(data);
    }

    /**
     * Hàm tính tổng số lượng sản phẩm
     * @param data dữ liệu chi tiết orer
     * @return số lượng sản phẩm
     * @author giangpb
     * @date 5/02/2021
     */
    public int tongSoLuongSanPham(ArrayList<Item> data){
        return itemFoodModel.tongSoLuongSanPham(data);
    }


    /**
     * Hàm lấy thông tin chi tiết hoá đơn
     * @param orderID mã hoá đơn
     * @return danh sách sản phẩm theo hoá đơn
     * @author giangpb
     * @date 06/02/2021
     */
    public ArrayList<Item> getItemInOrderDetail(String orderID){
        return itemFoodModel.getItemInOrderDetail(orderID);
    }

//    public void reset
}
