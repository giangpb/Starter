package vn.com.misa.starter2.ui.additem;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.starter2.model.entity.Unit;

/**
 * ‐ Lớp presenter unit nhận dữ liệu từ model, truyền qua view
 * ‐ @created_by giangpb on 1/26/2021
 */
public class UnitPresenter {
    private UnitModel unitModel;

    public UnitPresenter(Context context){
        unitModel=new UnitModel(context);
    }

    /**
     * Hàm lấy toàn bộ dữ liệu
     * @return danh sách đơn vị tính
     * @author giangpb
     * @date 26/01/2021
     */
    public ArrayList<Unit> getAllUnit(){
        return unitModel.getAllListUnit();
    }

    /**
     * Hàm thêm đơn vị tính
     * @param id mã đơn vị
     * @param name tên đơn vị
     * @return trả về kết quả thêm (true | false)
     * @author giangpb
     * @date 27/01/2021
     */
    public boolean addUnit(String id, String name){
        return unitModel.addUnit(id, name);
    }
}
